package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.stereotype.Component;

/**
 * Created by skopa01 on 9/3/2016.
 */

public class DefaultPageOperationsManager implements PageOperationsManager {

    @ComponentImport
    private PageManager pageManager;

    @ComponentImport
    private SpaceManager spaceManager;

    public void mergeChildren(Long pageId) {

    }

    public void split(Long pageId) {

    }
}
