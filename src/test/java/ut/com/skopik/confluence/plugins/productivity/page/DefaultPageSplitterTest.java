package ut.com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.skopik.confluence.plugins.productivity.page.DefaultPageSplitter;
import com.skopik.confluence.plugins.productivity.page.PageData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DefaultPageSplitterTest {

    private static final String STORAGE_FORMAT = "<p>Paragraph</p><h2>Heading 2</h2><p>Paragraph</p><ac:image ac:alt=\"This image describes the icon that appears when the download is complete.\" ac:title=\"This image describes the icon that appears when the download is complete.\"><ri:attachment ri:filename=\"time_mgr.png\" /></ac:image><p>Paragraph</p><h3>Heading 3</h3><p>Paragraph</p><h3>Heading 3 2</h3><p>Paragraph</p><p><ac:structured-macro ac:macro-id=\"96487ac6-3d82-47bf-8b10-70fb796c6d64\" ac:name=\"gliffy\" ac:schema-version=\"1\"><ac:parameter ac:name=\"name\">This image displays the high-level process for installing CA PPM.</ac:parameter></ac:structured-macro></p><h4>Heading 4</h4><p>Paragraph</p><h5>Heading 5</h5><h3>Heading 3 3</h3><p>Paragraph</p><p>Paragraph</p><h2>Heading 2</h2><p>Paragraph</p><h3>Heading 3</h3><p>Paragraph</p>";
    private DefaultPageSplitter pageSplitter;
    private Page page;

    @Before
    public void setup() {
        this.page = new Page();
        this.page.setBodyAsString(STORAGE_FORMAT);
        this.pageSplitter = new DefaultPageSplitter();
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

    @Test
    public void  getAttachments(){
        List<PageData> result = pageSplitter.split(page);
        assertEquals(1, result.get(0).getAttachmentNames().size());
        assertEquals(1, result.get(0).getChildren().get(1).getAttachmentNames().size());
    }

}
