package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.skopik.confluence.plugins.productivity.api.PageContentMerger;
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
     *
     * @param page
     * @param startPage
     * @return
     */
    private String getHeadingMarkup(Page page, Page startPage) {
        int level = (page.getAncestors().size() - startPage.getAncestors().size()) + 1;
        return "<h" + level + ">" + page.getTitle() + "</h" + level + ">";
    }
}
