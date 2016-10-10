package com.skopik.confluence.plugins.productivity.page;

import com.skopik.confluence.plugins.productivity.model.PageData;

import java.util.ArrayList;
import java.util.List;

public class PageHierarchyBuilder {

    public List<PageData> build(List<PageData> pages) {
        List<PageData> hierarchy = new ArrayList<>();
        PageData currentParent = null;
        PageData previousPage = null;
        int highestLevel = 0;

        for (PageData p : pages) {
            if (highestLevel == 0) {
                highestLevel = p.getLevel();
                currentParent = p;
            }

            if (previousPage != null && (previousPage.getLevel() < p.getLevel())) {
                currentParent = previousPage;
            }

            if (p.getLevel() > currentParent.getLevel()) {
                p.setParent(currentParent);
                currentParent.addChild(p);
            } else {
                if (previousPage == null) {
                    hierarchy.add(p);
                } else {
                    PageData parent = getParent(previousPage.getParent(), p.getLevel());
                    if (parent != null) {
                        parent.addChild(p);
                    } else {
                        hierarchy.add(p);
                    }
                }
            }

            previousPage = p;
        }

        return hierarchy;
    }

    private PageData getParent(PageData pageData, int level) {
        if (pageData == null)
            return null;

        return pageData.getLevel() == level - 1 ? pageData : getParent(pageData.getParent(), level);
    }

}