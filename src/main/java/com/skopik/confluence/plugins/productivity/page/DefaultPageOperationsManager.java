package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.skopik.confluence.plugins.productivity.model.PageMergeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Pavel Skopik on 9/3/2016.
 */
@Component
public class DefaultPageOperationsManager implements PageOperationsManager {

    private static final Logger logger = LoggerFactory.getLogger(PageOperationsManager.class);

    @Autowired
    private PageManager pageManager;

    @Autowired
    private SpaceManager spaceManager;

    @Autowired
    private PageMerger pageMerger;

    public void mergeDescendants(Long pageId) {
        Page parent = pageManager.getPage(pageId);

        if (parent != null) {
            pageManager.getDescendants(parent).forEach(p -> {

            });
        }
    }

    public void split(Long pageId) {

    }

    public void deletePageAndDescendants(Long pageId) {
        Page currentPage = pageManager.getPage(pageId);

        if (currentPage != null) {
            pageManager.getDescendants(currentPage).forEach(pageManager::trashPage);
            pageManager.trashPage(currentPage);
        }


    }
}
