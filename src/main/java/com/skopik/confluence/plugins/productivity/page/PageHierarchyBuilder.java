package com.skopik.confluence.plugins.productivity.page;

import java.util.List;

/**
 * Build pages hierarchy from a list of {@link PageData} objects.
 */
public interface PageHierarchyBuilder {

    /**
     * Builds a hierarchy from a flat list of {@link PageData} objects. The hierarchy is computed based on the level of
     * number of each object.
     *
     * @param pagesData List of {@link PageData} objects.
     *
     * @return Tree of {@link PageData} objects.
     */
    List<PageData> build(List<PageData> pagesData);

}
