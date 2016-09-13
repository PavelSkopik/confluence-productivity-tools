package com.skopik.confluence.plugins.productivity.api;

import com.atlassian.confluence.pages.Page;

import java.util.List;

public interface PageContentMerger {

    /**
     *
     * @param parent
     * @param descendants
     * @return
     */
    String merge(Page parent, List<Page> descendants);

}
