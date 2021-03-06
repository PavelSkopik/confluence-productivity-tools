package com.skopik.confluence.plugins.productivity.api;

import com.skopik.confluence.plugins.productivity.page.PageOperationType;

public class Settings {

    private long pageId;
    private PageOperationType operationType;

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public PageOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(PageOperationType operationType) {
        this.operationType = operationType;
    }
}
