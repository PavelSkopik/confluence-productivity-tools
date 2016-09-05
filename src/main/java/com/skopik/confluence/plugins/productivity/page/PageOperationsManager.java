package com.skopik.confluence.plugins.productivity.page;

/**
 * Created by skopa01 on 9/3/2016.
 */
public interface PageOperationsManager {
    void mergeChildren(Long pageId);

    void split(Long pageId);
}
