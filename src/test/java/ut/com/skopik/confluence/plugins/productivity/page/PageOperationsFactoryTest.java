package ut.com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.PageManager;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.skopik.confluence.plugins.productivity.api.Settings;
import com.skopik.confluence.plugins.productivity.exception.UnsupportedPageOperationException;
import com.skopik.confluence.plugins.productivity.page.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PageOperationsFactoryTest {

    @Mock
    private PageManager pageManager;

    @Mock
    private TransactionTemplate transactionTemplate;

    @InjectMocks
    private PageOperationsFactory operationsFactory;

    private Settings settings;

    @Before
    public void setup(){
        settings= new Settings();
        MockitoAnnotations.initMocks(PageOperationsFactoryTest.class);
    }

    @Test
    public void  testGetOperation() throws UnsupportedPageOperationException {
        settings.setOperationType(PageOperationType.DELETE_DESCENDANTS);
        assertTrue(operationsFactory.get(settings) instanceof DeletePagesOperation);

        settings.setOperationType(PageOperationType.DELETE_SELF_DESCENDANTS);
        assertTrue(operationsFactory.get(settings) instanceof DeletePagesOperation);

        settings.setOperationType(PageOperationType.SPLIT);
        assertTrue(operationsFactory.get(settings) instanceof SplitPageOperation);

        settings.setOperationType(PageOperationType.MERGE_DESCENDANTS);
        assertTrue(operationsFactory.get(settings) instanceof MergePagesOperation);
    }

}
