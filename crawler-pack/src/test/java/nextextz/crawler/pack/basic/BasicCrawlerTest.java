package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.Resource;
import nextextz.crawler.pack.ResourceLocation;
import nextextz.text.pack.lexer.HtmlLexer;
import nextextz.text.pack.text.Factory;
import nextextz.text.pack.text.Text;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class BasicCrawlerTest {
    private ResourceService resourceService;
    private ResourceLocation resourceLocation;

    @Before
    public void setUp() {
        resourceService = mock(ResourceService.class);
        resourceLocation = BasicFactory.createResourceLocation("950");
    }

    @Test(expected = NullPointerException.class)
    public void test_null() {
        new BasicCrawler(null, null, null);
    }

    @Test
    public void test_load() {
        prepareResourceService("");
        final BasicCrawlerHandler handler = mock(BasicCrawlerHandler.class);
        final BasicCrawler crawler = new BasicCrawler(resourceLocation, handler, resourceService);
        crawler.run();
        verify(handler).handleFinish(any(Resource.class));
    }

    @Test
    public void test_urls() {
        prepareResourceService("<meta ><body><a href=\"href1\"><a><a/></body>");
        final BasicCrawlerHandlerCollector handler = new BasicCrawlerHandlerCollector();
        final BasicCrawler crawler = new BasicCrawler(resourceLocation, handler, resourceService);
        crawler.run();
        assertTrue(handler.getResource() != null);
    }

    private void prepareResourceService(String value) {
        final Text text = Factory.createText(value);
        when(resourceService.load(resourceLocation)).thenReturn(text);
    }

    private static class BasicCrawlerHandlerCollector implements BasicCrawlerHandler {
        private Resource resource;

        @Override
        public void handleStart(ResourceLocation resourceLocation) {
        }

        @Override
        public void handleFinish(Resource resource) {
            this.resource = resource;
        }

        @Override
        public void handleException(Exception exception) {
        }

        @Override
        public void handle(HtmlLexer.Token token) {
        }

        public Resource getResource() {
            return resource;
        }
    }
}
