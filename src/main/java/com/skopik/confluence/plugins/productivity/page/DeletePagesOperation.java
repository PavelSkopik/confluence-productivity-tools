package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.Operation;
import com.skopik.confluence.plugins.productivity.api.Settings;
import com.skopik.confluence.plugins.productivity.model.OperationResult;

import java.util.List;

public class DeletePagesOperation implements Operation<OperationResult> {

    private PageManager pageManager;
    private Settings settings;
    private TransactionTemplate transactionTemplate;

    public DeletePagesOperation(PageManager pageManager, TransactionTemplate transactionTemplate, Settings settings) {
        this.pageManager = pageManager;
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

                if (settings.getOperationType().equals(PageOperationType.DELETE_SELF_DESCENDANTS)) {
                    trashPages(pageManager.getDescendants(parent));
                    pageManager.trashPage(parent);
                }

                if (settings.getOperationType().equals(PageOperationType.DELETE_DESCENDANTS))
                    trashPages(pageManager.getDescendants(parent));

                return operationResult;
            }
        });
    }

    /**
     * Moves pages to trash.
     *
     * @param pages List of  pages.
     */
    private void trashPages(List<Page> pages) {
        pages.forEach(pageManager::trashPage);
    }
}
