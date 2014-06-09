package nextextz.crawler.pack.basic;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import nextextz.crawler.pack.Crawler;
import nextextz.crawler.pack.ResourceLocation;
import nextextz.text.pack.lexer.HtmlLexer;
import nextextz.text.pack.lexer.HtmlLexerHandler;
import nextextz.text.pack.text.Factory;
import nextextz.text.pack.text.SymbolProvider;
import nextextz.text.pack.text.Text;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

class BasicCrawler implements Crawler {
    private final ResourceLocation resourceLocation;
    private final BasicCrawlerHandler handler;
    private final ResourceService resourceService;

    public BasicCrawler(ResourceLocation resourceLocation, BasicCrawlerHandler handler) {
        this(resourceLocation, handler, new BasicResourceService());
    }

    public BasicCrawler(
            ResourceLocation resourceLocation, BasicCrawlerHandler handler, ResourceService resourceService) {
        checkNotNull(resourceLocation);
        checkNotNull(handler);
        checkNotNull(resourceService);

        this.resourceLocation = resourceLocation;
        this.handler = handler;
        this.resourceService = resourceService;
    }

    @Override
    public void run() {
        try {
            handler.handleStart(resourceLocation);

            final Text text = resourceService.load(resourceLocation);

            final BasicHtmlLexerHandler lexerHandler = new BasicHtmlLexerHandler(handler);
            final HtmlLexer lexer = new HtmlLexer(text, lexerHandler);
            for (; ; ) {
                lexer.execute();
                if (!lexerHandler.next()) {
                    break;
                }
            }

            handler.handleFinish(
                    BasicFactory.createResource(resourceLocation, text, lexerHandler.getResourceLocations()));
        } catch (Exception e) {
            handler.handleException(e);
        }
    }

    private static class BasicHtmlLexerHandler implements HtmlLexerHandler {
        private static final Character START_SYMBOL = '<';
        private static final Character FINISH_SYMBOL = '>';
        private static final Character SPACE = ' ';
        private static final Character SLASH = '/';

        private static final String TAG_A = "a";

        private static final String HREF_PATTERN =
                "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";
        private static final Pattern MATCHER_HREF = Pattern.compile(HREF_PATTERN);

        private final BasicCrawlerHandler handler;
        private final Collection<ResourceLocation> resourceLocations = Lists.newArrayList();

        private HtmlLexer.Token tail;

        public BasicHtmlLexerHandler(BasicCrawlerHandler handler) {
            this.handler = handler;
        }

        @Override
        public void handle(HtmlLexer.Token token) {
            tail = token;
            handler.handle(token);

            if (token.getType() == HtmlLexer.TokenType.TAG) {
                final String tagName = extractTagName(Factory.createSymbolProvider(token.getValue()));
                if (TAG_A.equalsIgnoreCase(tagName)) {
                    final String hrefValue = extractHrefValue(token.getValue());
                    if (!Strings.isNullOrEmpty(hrefValue)) {
                        resourceLocations.add(BasicFactory.createResourceLocation(hrefValue));
                    }
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

        public Iterable<ResourceLocation> getResourceLocations() {
            return resourceLocations;
        }

        private String extractTagName(SymbolProvider symbolProvider) {
            final StringBuilder buffer = new StringBuilder();
            for (; ; ) {
                final Character symbol = symbolProvider.getSymbol();
                if (symbol == null) {
                    break;
                }
                if (START_SYMBOL == symbol && buffer.length() == 0) {
                    symbolProvider.move();
                    continue;
                }
                if (SPACE == symbol && buffer.length() == 0) {
                    symbolProvider.move();
                    continue;
                }
                if (SLASH == symbol) {
                    symbolProvider.move();
                    continue;
                }
                if ((SPACE == symbol && buffer.length() > 0) || (START_SYMBOL == symbol) || (FINISH_SYMBOL == symbol)) {
                    break;
                }
                buffer.append(symbol);
                symbolProvider.move();
            }
            return buffer.toString();
        }

        private String extractHrefValue(String value) {
            String result = null;
            final Matcher matcher = MATCHER_HREF.matcher(value);
            if (matcher.find()) {
                result = matcher.group(1);
            }
            return result;
        }
    }
}
