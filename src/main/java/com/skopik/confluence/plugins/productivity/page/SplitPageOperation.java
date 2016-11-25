package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.OperationSettings;
import com.skopik.confluence.plugins.productivity.api.PageOperation;
import com.skopik.confluence.plugins.productivity.api.PageSplitter;
import com.skopik.confluence.plugins.productivity.model.OperationResult;
import com.skopik.confluence.plugins.productivity.model.PageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Splits a single page into multiple pages based on heading levels.
 */
public class SplitPageOperation implements PageOperation<OperationResult> {

    private PageManager pageManager;
    private TransactionTemplate transactionTemplate;
    private OperationSettings settings;
    private PageSplitter pageSplitter;

    public SplitPageOperation(PageManager pageManager, PageSplitter pageSplitter, TransactionTemplate transactionTemplate, OperationSettings settings) {
        this.pageManager = pageManager;
        this.pageSplitter = pageSplitter;
        this.transactionTemplate = transactionTemplate;
        this.settings = settings;
    }

    @Override
    public OperationResult run() {
        return transactionTemplate.execute(new TransactionCallback<OperationResult>() {
            @Override
            public OperationResult doInTransaction() {
                Page page = pageManager.getPage(settings.getPageId());
                List<PageData> newPages = new ArrayList<>();

                if (page != null) {
                    newPages = pageSplitter.split(page);

                    for (PageData pageData : newPages) {
                        createPage(pageData, page, page.getSpace());
                    }
                }

                return new OperationResult(settings.getOperationType(), newPages);
            }
        });
    }

    /**
     * Creates a new page.
     *
     * @param pageData   Object to create a new page from.
     * @param parentPage A page to set as a parent.
     * @param space      Target space.
     */
    private void createPage(PageData pageData, Page parentPage, Space space) {
        Page newPage = new Page();
        newPage.setTitle(pageData.getTitle());
        newPage.setParentPage(parentPage);
        newPage.setBodyAsString(pageData.getBody());
        newPage.setSpace(space);
        parentPage.addChild(newPage);

        pageManager.saveContentEntity(newPage, null);
        pageData.setNewPageId(newPage.getId());

        // TODO: copy attachments
        // TODO: check for duplicate titles

        if (pageData.getChildren().size() > 0) {
            for (PageData p : pageData.getChildren()) {
                createPage(p, newPage, space);
            }
        }
    }

}
