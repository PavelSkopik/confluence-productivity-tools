package com.skopik.confluence.plugins.productivity.api;

import com.atlassian.confluence.pages.Page;
import com.skopik.confluence.plugins.productivity.model.PageData;

import java.util.List;

public interface PageSplitter {

    /**
     *
     * @param page
     * @return
     */
    List<PageData> split(Page page);

}
