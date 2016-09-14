package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.Operation;
import com.skopik.confluence.plugins.productivity.api.PageSplitter;
import com.skopik.confluence.plugins.productivity.api.Settings;

import javax.annotation.PostConstruct;

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
        return null;
    }

}
