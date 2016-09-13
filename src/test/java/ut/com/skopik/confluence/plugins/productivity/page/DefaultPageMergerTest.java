package ut.com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.skopik.confluence.plugins.productivity.page.DefaultPageContentMerger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultPageMergerTest {

    private static final String PAGE_BODY = "<p>paragraph</p>";

    private DefaultPageContentMerger pageMerger;
    private Page parent;
    private Page ancestor;
    private List<Page> descendants;

    @Before
    public void setup() {
        ancestor = createPage(7L, "Page", PAGE_BODY);
        parent = createPage(0L, "Page", PAGE_BODY);
        parent.setAncestors(Arrays.asList(ancestor));
        descendants = getPageTree(parent, ancestor);

        this.pageMerger = new DefaultPageContentMerger();
    }

    @Test
    public void mergePages() {
        String expected = PAGE_BODY + "<h2>Page 1</h2>" + PAGE_BODY + "<h2>Page 2</h2>" + PAGE_BODY + "<h3>Page 3</h3>" + PAGE_BODY;
        assertEquals(expected, pageMerger.merge(parent, descendants));
    }

    private List<Page> getPageTree(Page parent, Page ancestor) {
        List<Page> pages = new ArrayList<>();
        List<Page> ancestors = new ArrayList<>();

        ancestors.add(ancestor);
        ancestors.add(parent);

        for (int i = 0; i < 3; i++) {
            Page p = createPage(i + 1L, "Page", PAGE_BODY);

            if (i == 2) {
                p.setParentPage(pages.get(i - 1));
                ancestors.add(pages.get(i - 1));
            } else {
                p.setParentPage(parent);
            }

            p.setAncestors(new ArrayList<>(ancestors));

            pages.add(p);
        }

        return pages;
    }

    private Page createPage(Long id, String title, String body) {
        Page p = new Page();
        p.setId(id);
        p.setTitle(title + " " + id);
        p.setBodyAsString(body);
        return p;
    }

}
