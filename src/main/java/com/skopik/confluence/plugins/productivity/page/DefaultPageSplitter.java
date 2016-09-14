package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.skopik.confluence.plugins.productivity.api.PageSplitter;
import com.skopik.confluence.plugins.productivity.model.PageData;

import java.util.List;

public class DefaultPageSplitter implements PageSplitter {

    @Override
    public List<PageData> split(Page page) {
        return null;
    }


}
