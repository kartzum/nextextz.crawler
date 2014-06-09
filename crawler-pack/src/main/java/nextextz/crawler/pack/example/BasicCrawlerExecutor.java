package nextextz.crawler.pack.example;

import nextextz.crawler.pack.Crawler;
import nextextz.crawler.pack.Resource;
import nextextz.crawler.pack.ResourceLocation;
import nextextz.crawler.pack.basic.BasicCrawlerHandler;
import nextextz.crawler.pack.basic.BasicFactory;
import nextextz.text.pack.lexer.HtmlLexer;

public class BasicCrawlerExecutor {
    public static void main(String[] args) {
        String location = "http://edition.cnn.com/";
        if (args.length > 0) {
            location = args[0];
        }
        final ResourceLocation resourceLocation = BasicFactory.createResourceLocation(location);
        final Crawler crawler = BasicFactory.createCrawler(resourceLocation, new BasicCrawlerHandlerConsole());
        crawler.run();
    }

    private static class BasicCrawlerHandlerConsole implements BasicCrawlerHandler {
        @Override
        public void handleStart(ResourceLocation resourceLocation) {
            System.out.println("Start: " + resourceLocation.getLocation());
        }

        @Override
        public void handleFinish(Resource resource) {
            System.out.println("Finish: " + resource.getResourceLocation().getLocation());
            System.out.println("Links:");
            for (ResourceLocation resourceLocation : resource.getResourceLocations()) {
                System.out.println(resourceLocation.getLocation());
            }
        }

        @Override
        public void handleException(Exception exception) {
            exception.printStackTrace();
        }

        @Override
        public void handle(HtmlLexer.Token token) {
            if (token.getType() == HtmlLexer.TokenType.CONTENT) {
                final String value = token.getValue().trim();
                if (value.length() > 0) {
                    System.out.println(token.getValue());
                }
            }
        }
    }
}
