package com.skopik.confluence.plugins.productivity.page;

import java.util.ArrayList;
import java.util.List;

public class DefaultPageHierarchyBuilder implements PageHierarchyBuilder {

    @Override
    public List<PageData> build(List<PageData> pagesData) {
        List<PageData> hierarchy = new ArrayList<>();
        PageData currentParent = null;
        PageData previousPage = null;
        int highestLevel = 0;

        for (PageData p : pagesData) {
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

    /**
     * Retrieves a parent for a given level.
     *
     * @param pageData Current {@link PageData} being evaluated as a parent.
     * @param level    Hierarchy level.
     *
     * @return Parent {@link PageData}.
     */
    private PageData getParent(PageData pageData, int level) {
        if (pageData == null)
            return null;

        return pageData.getLevel() == level - 1 ? pageData : getParent(pageData.getParent(), level);
    }

}