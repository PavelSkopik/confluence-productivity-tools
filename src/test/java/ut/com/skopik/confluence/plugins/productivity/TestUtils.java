package ut.com.skopik.confluence.plugins.productivity;

import com.atlassian.confluence.pages.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skopa01 on 9/13/2016.
 */
public class TestUtils {

    public static List<Page> createPages(int count) {
        List<Page> pages = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            pages.add(new Page());
        }

        return pages;
    }

}
