package com.skopik.confluence.plugins.productivity.page;

/**
 * Created by skopa01 on 9/3/2016.
 */
public interface PageOperationsManager {

    /**
     *
     * @param pageId
     */
    void mergeDescendants(Long pageId);

    /**
     *
     * @param pageId
     */
    void split(Long pageId);

    /**
     *
     * @param pageId
     */
    void deletePageAndDescendants(Long pageId);
}
