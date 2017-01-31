package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;

import java.util.List;

/**
 * Performs page merge.
 */
public interface PageContentMerger {

    /**
     * Merges a page and its descendants. The merge operation is performed on page XML in storage format.
     *
     * @param parent      Parent page.
     * @param descendants List of descendant pages.
     *
     * @return Merged page body as XML string.
     */
    String merge(Page parent, List<Page> descendants);

}
