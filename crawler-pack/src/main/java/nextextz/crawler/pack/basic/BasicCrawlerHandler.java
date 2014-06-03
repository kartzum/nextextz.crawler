package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.Resource;

/**
 * Provides functionality for handling information from crawler.
 */
public interface BasicCrawlerHandler {
    /**
     * Handles resource.
     *
     * @param resource resource.
     */
    void handle(Resource resource);

    /**
     * Handles exception.
     *
     * @param exception exception.
     */
    void handleException(Exception exception);
}
