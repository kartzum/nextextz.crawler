package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.Resource;
import nextextz.crawler.pack.ResourceLocation;
import nextextz.text.pack.lexer.HtmlLexerHandler;

/**
 * Provides functionality for handling information from crawler.
 */
public interface BasicCrawlerHandler extends HtmlLexerHandler {
    /**
     * Handles start.
     *
     * @param resourceLocation location.
     */
    void handleStart(ResourceLocation resourceLocation);

    /**
     * Handles resource.
     *
     * @param resource resource.
     */
    void handleFinish(Resource resource);

    /**
     * Handles exception.
     *
     * @param exception exception.
     */
    void handleException(Exception exception);
}
