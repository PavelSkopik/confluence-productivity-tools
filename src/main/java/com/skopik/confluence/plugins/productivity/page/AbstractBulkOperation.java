package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class AbstractBulkOperation {

    private static final Logger logger = LoggerFactory.getLogger(AbstractBulkOperation.class);

    protected AttachmentManager attachmentManager;
    protected PageManager pageManager;

    /**
     *
     * @param sourcePage
     * @param targetPage
     */
    protected void copyAttachments(Page sourcePage, Page targetPage) {
        try {
            attachmentManager.copyAttachments(sourcePage, targetPage);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     * @param targetPage
     * @param sourcePages
     */
    protected void copyAttachments(Page targetPage, List<Page> sourcePages) {
        sourcePages.forEach(page -> copyAttachments(page, targetPage));
    }

    /**
     *
     * @param pages
     */
    protected void trashPages(List<Page> pages) {
        pages.forEach(pageManager::trashPage);
    }

}
