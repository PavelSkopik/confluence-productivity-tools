package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.Operation;
import com.skopik.confluence.plugins.productivity.api.PageSplitter;
import com.skopik.confluence.plugins.productivity.api.Settings;
import com.skopik.confluence.plugins.productivity.model.PageData;

import javax.annotation.PostConstruct;
import java.util.List;

public class SplitPageOperation implements Operation<Boolean> {

    private PageManager pageManager;
    private TransactionTemplate transactionTemplate;
    private Settings settings;
    private PageSplitter pageSplitter;

    public SplitPageOperation(PageManager pageManager, TransactionTemplate transactionTemplate, Settings settings) {
        this.pageManager = pageManager;
        this.transactionTemplate = transactionTemplate;
        this.settings = settings;
    }

    @PostConstruct
    public void init() {
        this.pageSplitter = new DefaultPageSplitter();
    }

    @Override
    public Boolean run() {
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction() {
                Page page = pageManager.getPage(settings.getPageId());

                if (page != null) {
                    List<PageData> newPages = pageSplitter.split(page);

                    for(PageData pageData : newPages){
                        createPage(pageData, page.getParent(), page.getSpace());
                    }
                }

                return true;
            }
        });
    }

    private void createPage(PageData pageData, Page parentPage, Space space){
        Page newPage = new Page();
        newPage.setTitle(pageData.getTitle());
        newPage.setParentPage(parentPage);
        newPage.setBodyAsString(pageData.getBody());
        parentPage.addChild(newPage);

        pageManager.saveContentEntity(newPage, null);

        if(pageData.getChildren().size() > 0){
            for(PageData p : pageData.getChildren()){
                createPage(p, newPage.getParent(), space);
            }
        }
    }

}
