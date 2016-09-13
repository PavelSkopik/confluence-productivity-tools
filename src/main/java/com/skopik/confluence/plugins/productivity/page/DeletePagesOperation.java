package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.Operation;
import com.skopik.confluence.plugins.productivity.api.Settings;

import java.util.List;

public class DeletePagesOperation implements Operation<Boolean> {

    private PageManager pageManager;
    private Settings settings;
    private TransactionTemplate transactionTemplate;

    public DeletePagesOperation(PageManager pageManager, TransactionTemplate transactionTemplate, Settings settings) {
        this.pageManager = pageManager;
        this.transactionTemplate = transactionTemplate;
        this.settings = settings;
    }

    @Override
    public Boolean run() {
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction() {
                Page parent = pageManager.getPage(settings.getPageId());

                if (parent == null)
                    return false;

                if (settings.getOperationType().equals(PageOperationType.DELETE_SELF_DESCENDANTS)) {
                    trashPages(pageManager.getDescendants(parent));
                    pageManager.trashPage(parent);
                }

                if (settings.getOperationType().equals(PageOperationType.DELETE_DESCENDANTS))
                    trashPages(pageManager.getDescendants(parent));

                return true;
            }
        });
    }

    private void trashPages(List<Page> pages) {
        pages.forEach(pageManager::trashPage);
    }
}
