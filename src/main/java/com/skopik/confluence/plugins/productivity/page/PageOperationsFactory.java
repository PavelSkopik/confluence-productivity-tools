package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.Operation;
import com.skopik.confluence.plugins.productivity.api.Settings;
import com.skopik.confluence.plugins.productivity.exception.UnsupportedPageOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class PageOperationsFactory {

    @Autowired
    private PageManager pageManager;

    @Autowired
    private AttachmentManager attachmentManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public Operation get(Settings settings) throws UnsupportedPageOperationException {

        if (settings.getOperationType().equals(PageOperationType.MERGE_DESCENDANTS)) {
            return new MergePagesOperation(pageManager, attachmentManager, transactionTemplate, settings);
        } else if (settings.getOperationType().equals(PageOperationType.SPLIT)) {
            throw new NotImplementedException();
        } else if (settings.getOperationType().equals(PageOperationType.DELETE_SELF_DESCENDANTS)) {
            throw new NotImplementedException();
        } else if (settings.getOperationType().equals(PageOperationType.DELETE_DESCENDANTS)) {
            throw new NotImplementedException();
        } else {
            throw new UnsupportedPageOperationException("Operation " + settings.getOperationType().toString() + " is not supported.");
        }

    }

}
