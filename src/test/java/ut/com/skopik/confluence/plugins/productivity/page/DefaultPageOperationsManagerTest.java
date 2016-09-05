package ut.com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;

import com.atlassian.confluence.pages.PageManager;
import com.skopik.confluence.plugins.productivity.page.DefaultPageOperationsManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel Skopik on 9/5/2016.
 */
public class DefaultPageOperationsManagerTest {

    private static final String PAGE_BODY = "<p>This is paragraph</p>";

    @InjectMocks
    private DefaultPageOperationsManager pageOperationsManager;

    @Mock
    private PageManager pageManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(DefaultPageOperationsManagerTest.class);
    }

    @Test
    public void mergeChildren() {

    }

    private List<Page> getPageTree() {
        List<Page> pages = new ArrayList<Page>();

        Page parent = new Page();
        parent.setId(0);
        pages.add(parent);

        for (int i = 0; i < 3; i++) {
            Page p = new Page();
            p.setParentPage(parent);
            p.setBodyAsString(PAGE_BODY);
            pages.add(p);
        }

        return pages;
    }

}
