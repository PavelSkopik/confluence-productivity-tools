package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.core.DefaultSaveContext;
import com.atlassian.confluence.core.Modification;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.OperationSettings;
import com.skopik.confluence.plugins.productivity.api.PageContentMerger;
import com.skopik.confluence.plugins.productivity.api.PageOperation;
import com.skopik.confluence.plugins.productivity.model.OperationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MergePagesOperation implements PageOperation<OperationResult> {

    private static final Logger logger = LoggerFactory.getLogger(MergePagesOperation.class);

    private AttachmentManager attachmentManager;
    private PageManager pageManager;
    private TransactionTemplate transactionTemplate;
    private PageContentMerger contentMerger;
    private OperationSettings settings;

    public MergePagesOperation(PageManager pageManager, AttachmentManager attachmentManager, PageContentMerger contentMerger, TransactionTemplate transactionTemplate, OperationSettings settings) {
        this.pageManager = pageManager;
        this.attachmentManager = attachmentManager;
        this.contentMerger = contentMerger;
        this.transactionTemplate = transactionTemplate;
        this.settings = settings;
    }

    @Override
    public OperationResult run() {
        return transactionTemplate.execute(new TransactionCallback<OperationResult>() {
            @Override
            public OperationResult doInTransaction() {
                OperationResult operationResult = new OperationResult(settings.getOperationType());
                Page parent = pageManager.getPage(settings.getPageId());

                if (parent == null) {
                    operationResult.setSuccess(false);
                    return operationResult;
                }

                List<Page> descendants = pageManager.getDescendants(parent);
                mergePages(parent, descendants);

                return operationResult;
            }
        });
    }

    /**
     * Merges descendant pages into a parent page.
     *
     * @param parent      Parent page.
     * @param descendants List of descendant pages.
     */
    private void mergePages(final Page parent, List<Page> descendants) {
        pageManager.saveNewVersion(parent, new Modification<Page>() {
            @Override
            public void modify(Page page) {
                page.setBodyAsString(contentMerger.merge(parent, descendants));
            }
        }, DefaultSaveContext.SUPPRESS_NOTIFICATIONS);

        descendants.forEach(page -> {
            copyAttachments(page, parent);
            if (settings.isDeleteJoinedPages())
                pageManager.trashPage(page);
        });
    }

    /**
     * Copies attachments from one page to another.
     *
     * @param sourcePage Source  page.
     * @param targetPage Target page.
     */
    private void copyAttachments(Page sourcePage, Page targetPage) {
        try {
            attachmentManager.copyAttachments(sourcePage, targetPage);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
