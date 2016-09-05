package ut.com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.skopik.confluence.plugins.productivity.page.DefaultPageMerger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by skopa01 on 9/5/2016.
 */
public class DefaultPageMergerTest {

    private static final String PAGE_BODY = "<p>This is paragraph</p>";

    private DefaultPageMerger pageMerger;

    @Before
    public void setup() {
        this.pageMerger = new DefaultPageMerger();
    }

    @Test
    public void mergePages() {
        List<Page> pages = getPageTree();
        String result = pageMerger.mergePageBody(pages);

        assertTrue(true);
    }

    private List<Page> getPageTree() {
        List<Page> pages = new ArrayList<>();

        Page parent = new Page();
        parent.setId(0);
        parent.setTitle("Page 0");

        pages.add(parent);

        for (int i = 0; i < 3; i++) {
            Page p = new Page();
            p.setBodyAsString(PAGE_BODY);
            p.setId(i + 1);
            p.setTitle("Page " + (i + 1));

            if (i == 2) {
                p.setParentPage(pages.get(i));
            } else {
                p.setParentPage(parent);
            }

            pages.add(p);
        }

        return pages;
    }

}
