package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by skopa01 on 9/5/2016.
 */
@Component
public class DefaultPageMerger implements PageMerger {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPageMerger.class);

    @Override
    public String mergePageBody(List<Page> pages) {
        StringBuilder buffer = new StringBuilder();

        pages.forEach(page -> {
            buffer.append(getHeadingMarkup(page));
            buffer.append(page.getBodyAsString());
        });

        return buffer.toString();
    }

    /**
     *
     * @param page
     * @return
     */
    private String getHeadingMarkup(Page page) {
        int level = page.getAncestors().size();
        return "<h" + level + ">" + page.getTitle() + "</h" + level + ">";
    }
}
