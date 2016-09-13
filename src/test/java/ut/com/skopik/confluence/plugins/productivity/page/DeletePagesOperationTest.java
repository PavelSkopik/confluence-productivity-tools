package ut.com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.core.transaction.NoOpTransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.Settings;
import com.skopik.confluence.plugins.productivity.page.DeletePagesOperation;
import com.skopik.confluence.plugins.productivity.page.PageOperationType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ut.com.skopik.confluence.plugins.productivity.TestUtils;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class DeletePagesOperationTest {

    private DeletePagesOperation operation;

    @Mock
    private PageManager pageManager;

    private TransactionTemplate transactionTemplate;
    private Settings settings;
    private Page parent;
    private List<Page> descendants;

    @Before
    public void setup() {
        settings = new Settings();
        settings.setPageId(0L);

        parent = new Page();
        parent.setId(0L);

        descendants = TestUtils.createPages(3);

        operation = new DeletePagesOperation(pageManager, new NoOpTransactionTemplate(), settings);
    }

    @Test
    public void deletePagesReturnsFalse() {
        Mockito.when(pageManager.getPage(0L)).thenReturn(null);
        assertFalse(operation.run());
    }

    @Test
    public void deleteDescendants(){
        Mockito.when(pageManager.getPage(0L)).thenReturn(parent);
        Mockito.when(pageManager.getDescendants(parent)).thenReturn(descendants);
        settings.setOperationType(PageOperationType.DELETE_DESCENDANTS);
        operation.run();
        Mockito.verify(pageManager, times(3)).trashPage(new Page());
    }

    @Test
    public void deleteSelfDescendants(){
        Mockito.when(pageManager.getPage(0L)).thenReturn(parent);
        Mockito.when(pageManager.getDescendants(parent)).thenReturn(descendants);
        settings.setOperationType(PageOperationType.DELETE_SELF_DESCENDANTS);
        operation.run();
        Mockito.verify(pageManager, times(4)).trashPage(new Page());
    }


}
