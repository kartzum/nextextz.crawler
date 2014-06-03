package nextextz.crawler.pack.example;

import nextextz.crawler.pack.Crawler;
import nextextz.crawler.pack.Resource;
import nextextz.crawler.pack.ResourceLocation;
import nextextz.crawler.pack.basic.BasicCrawlerHandler;
import nextextz.crawler.pack.basic.BasicFactory;
import nextextz.text.pack.lexer.HtmlLexer;
import nextextz.text.pack.lexer.HtmlLexerHandler;

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
        public void handle(Resource resource) {
            final HtmlLexerHandlerConsole handler = new HtmlLexerHandlerConsole();
            final HtmlLexer lexer = new HtmlLexer(resource.getText(), handler);
            for (; ; ) {
                lexer.execute();
                if (!handler.next()) {
                    break;
                }
            }
        }

        @Override
        public void handleException(Exception exception) {
            exception.printStackTrace();
        }
    }

    private static class HtmlLexerHandlerConsole implements HtmlLexerHandler {
        private HtmlLexer.Token tail;

        @Override
        public void handle(HtmlLexer.Token token) {
            tail = token;

            if (token.getType() == HtmlLexer.TokenType.CONTENT) {
                final String value = token.getValue().trim();
                if (value.length() > 0) {
                    System.out.println(token.getValue());
                }
            }
        }

        public boolean next() {
            boolean result = true;
            if (tail != null) {
                if (tail.getType() == HtmlLexer.TokenType.EMPTY) {
                    result = false;
                }
            }
            return result;
        }
    }
}
