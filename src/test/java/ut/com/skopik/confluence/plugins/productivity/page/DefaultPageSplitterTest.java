package ut.com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.skopik.confluence.plugins.productivity.model.PageData;
import com.skopik.confluence.plugins.productivity.page.DefaultPageSplitter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DefaultPageSplitterTest {

    private static final String STORAGE_FORMAT = "<p>Paragraph</p><h2>Heading 2</h2><p>Paragraph</p><p>Paragraph</p><h3>Heading 3</h3><p>Paragraph</p><h3>Heading 3 2</h3><p>Paragraph</p><h4>Heading 4</h4><p>Paragraph</p><h5>Heading 5</h5><h3>Heading 3 3</h3><p>Paragraph</p><p>Paragraph</p><h2>Heading 2</h2><p>Paragraph</p><h3>Heading 3</h3><p>Paragraph</p>";
    private DefaultPageSplitter pageSplitter;
    private Page page;

    @Before
    public void setup() {
        this.page = new Page();
        this.page.setBodyAsString(STORAGE_FORMAT);
        this.pageSplitter = new DefaultPageSplitter();
        this.pageSplitter.init();
    }

    @Test
    public void splitPages(){
        List<PageData> result = pageSplitter.split(page);
        assertEquals(2, result.size());
//        assertEquals("<p xmlns=\"http://www.w3.org/1999/xhtml\">Paragraph</p><p xmlns=\"http://www.w3.org/1999/xhtml\">Paragraph</p>", result.get(0).getBody());
//        assertEquals("<p xmlns=\"http://www.w3.org/1999/xhtml\">Paragraph</p>", result.get(1).getBody());
//        assertEquals("<p xmlns=\"http://www.w3.org/1999/xhtml\">Paragraph</p>", result.get(2).getBody());
//        assertEquals("<p xmlns=\"http://www.w3.org/1999/xhtml\">Paragraph</p>", result.get(3).getBody());
//        assertEquals("<p xmlns=\"http://www.w3.org/1999/xhtml\">Paragraph</p>", result.get(4).getBody());
    }

}
