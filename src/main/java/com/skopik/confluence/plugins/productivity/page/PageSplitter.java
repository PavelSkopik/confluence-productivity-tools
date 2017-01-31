package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;

import java.util.List;

/**
 * Splits a Confluence page into multiple pages based on headings in the content. Heading levels determine nesting of
 * the resulting pages.
 */
public interface PageSplitter {

    /**
     * Splits a page into multiple pages. Split pages are represented by a list of {@link PageData objects}.
     *
     * @param page Confluence page.
     *
     * @return List of {@link PageData objects}.
     */
    List<PageData> split(Page page);

}
