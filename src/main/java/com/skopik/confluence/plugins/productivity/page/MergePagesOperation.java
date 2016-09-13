package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.core.DefaultSaveContext;
import com.atlassian.confluence.core.Modification;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.Operation;
import com.skopik.confluence.plugins.productivity.api.PageContentMerger;
import com.skopik.confluence.plugins.productivity.api.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by skopa01 on 9/8/2016.
 */
public class MergePagesOperation extends AbstractBulkOperation implements Operation<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(MergePagesOperation.class);

    private TransactionTemplate transactionTemplate;
    private PageContentMerger contentMerger;
    private Settings settings;

    public MergePagesOperation(PageManager pageManager, AttachmentManager attachmentManager, TransactionTemplate transactionTemplate, Settings settings) {
        this.pageManager = pageManager;
        this.attachmentManager = attachmentManager;
        this.transactionTemplate = transactionTemplate;
        this.settings = settings;
    }

    @PostConstruct
    public void init() {
        this.contentMerger = new DefaultPageContentMerger();
    }

    @Override
    public Boolean run() {
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction() {
                Page parent = pageManager.getPage(settings.getPageId());

                if (parent == null)
                    return false;

                List<Page> descendants = pageManager.getDescendants(parent);
                mergePages(parent, descendants);

                return true;
            }
        });
    }

    /**
     * @param parent
     * @param descendants
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
            pageManager.trashPage(page);
        });
    }

}
