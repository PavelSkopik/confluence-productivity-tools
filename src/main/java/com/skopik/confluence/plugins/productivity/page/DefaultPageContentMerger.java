package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPageContentMerger implements PageContentMerger {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPageContentMerger.class);

    @Override
    public String merge(Page parent, List<Page> descendants) {
        StringBuilder buffer = new StringBuilder();

        buffer.append(parent.getBodyAsString());

        descendants.forEach(page -> {
            buffer.append(getHeadingMarkup(page, parent));
            buffer.append(page.getBodyAsString());
        });

        return buffer.toString();
    }

    /**
     * Returns headings markup for a given page being merged. The method computes the correct heading level based on
     * the
     * page position in the tree.
     *
     * @param page      Page being merged.
     * @param startPage Page into which descendants are merged into.
     *
     * @return Headings markup as XML string.
     */
    private String getHeadingMarkup(Page page, Page startPage) {
        int level = (page.getAncestors().size() - startPage.getAncestors().size()) + 1;
        return "<h" + level + ">" + page.getTitle() + "</h" + level + ">";
    }
}
