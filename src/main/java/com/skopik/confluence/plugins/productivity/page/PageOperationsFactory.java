package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.exception.UnsupportedPageOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Page operations factory creates new page operations based on the supplied type.
 */
@Service
public class PageOperationsFactory {

    @Autowired
    private PageManager pageManager;

    @Autowired
    private AttachmentManager attachmentManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * Creates a new page operation.
     *
     * @param settings Operation settings.
     *
     * @return {@link PageOperation}
     * @throws UnsupportedPageOperationException
     */
    public PageOperation create(OperationSettings settings) throws UnsupportedPageOperationException {
        if (settings.getOperationType().equals(PageOperationType.MERGE_DESCENDANTS)) {
            return new MergePagesOperation(pageManager, attachmentManager, new DefaultPageContentMerger(), transactionTemplate, settings);
        } else if (settings.getOperationType().equals(PageOperationType.SPLIT)) {
            return new SplitPageOperation(pageManager, new DefaultPageSplitter(), transactionTemplate, settings);
        } else if (settings.getOperationType().equals(PageOperationType.DELETE_SELF_DESCENDANTS) || settings.getOperationType().equals(PageOperationType.DELETE_DESCENDANTS)) {
            return new DeletePagesOperation(pageManager, transactionTemplate, settings);
        } else {
            throw new UnsupportedPageOperationException("Operation " + settings.getOperationType().toString() + " is not supported.");
        }

    }

}
