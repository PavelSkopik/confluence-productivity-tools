package com.skopik.confluence.plugins.productivity.model;

import com.skopik.confluence.plugins.productivity.page.PageOperationType;

import java.util.ArrayList;
import java.util.List;

public class OperationResult {

    boolean success = true;
    PageOperationType operationType;
    List<PageData> newPages = new ArrayList<>();

    public OperationResult(PageOperationType type) {
        this.operationType = type;
    }

    public OperationResult(PageOperationType type, List<PageData> newPages) {
        this.operationType = type;
        this.newPages = newPages;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public PageOperationType getOperationType() {
        return operationType;
    }

    public List<PageData> getNewPages() {
        return newPages;
    }

}
