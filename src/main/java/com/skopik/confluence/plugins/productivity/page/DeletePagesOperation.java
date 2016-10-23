package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.PageOperation;
import com.skopik.confluence.plugins.productivity.api.Settings;
import com.skopik.confluence.plugins.productivity.model.OperationResult;

import java.util.List;

/**
 * Implements page deletion.
 */
public class DeletePagesOperation implements PageOperation<OperationResult> {

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

                trashPages(parent, settings.getOperationType());

                return operationResult;
            }
        });
    }

    /**
     * Moves pages to trash.
     *
     * @param parent        Page to start the trash operation from.
     * @param operationType PageOperation type. Determines the type of deletion.
     */
    private void trashPages(Page parent, PageOperationType operationType) {
        if (operationType.equals(PageOperationType.DELETE_SELF_DESCENDANTS)) {
            trashPages(pageManager.getDescendants(parent));
            pageManager.trashPage(parent);
        }

        if (operationType.equals(PageOperationType.DELETE_DESCENDANTS))
            trashPages(pageManager.getDescendants(parent));
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
