package ut.com.skopik.confluence.plugins.productivity.page;

import com.skopik.confluence.plugins.productivity.page.DefaultPageHierarchyBuilder;
import com.skopik.confluence.plugins.productivity.page.PageData;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PageHierarchyBuilderTest {

    private DefaultPageHierarchyBuilder hiearchyBuilder;
    private List<PageData> pages;

    @Before
    public void setup() {
        hiearchyBuilder = new DefaultPageHierarchyBuilder();
        int[] levels = {2, 3, 3, 4, 5, 3, 2, 3, 3};
        pages = getPages(levels);
    }

    @Test
    public void testBuildHierarchy() {
        List<PageData> tree = hiearchyBuilder.build(pages);
        assertEquals(2, tree.size());
        assertEquals(3, tree.get(0).getChildren().size());
        assertEquals(1, tree.get(0).getChildren().get(1).getChildren().size());
        assertEquals(1, tree.get(0).getChildren().get(1).getChildren().get(0).getChildren().size());
        assertEquals(2, tree.get(1).getChildren().size());
    }

    private List<PageData> getPages(int[] levels) {
        List<PageData> pages = new ArrayList<>();

        for (int i = 0; i < levels.length; i++) {
            PageData page = new PageData();
            page.setLevel(levels[i]);
            page.setTitle(String.valueOf(levels[i]));
            pages.add(page);
        }

        return pages;
    }


}
