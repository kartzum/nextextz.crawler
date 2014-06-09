package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.Crawler;
import nextextz.crawler.pack.Resource;
import nextextz.crawler.pack.ResourceLocation;
import nextextz.text.pack.text.Text;

/**
 * Factory.
 */
public final class BasicFactory {
    private BasicFactory() {
    }

    /**
     * Creates new crawler.
     *
     * @param resourceLocation location.
     * @param handler          handler.
     * @return crawler.
     */
    public static Crawler createCrawler(ResourceLocation resourceLocation, BasicCrawlerHandler handler) {
        return new BasicCrawler(resourceLocation, handler);
    }

    /**
     * Returns new location.
     *
     * @param location location as string.
     * @return location.
     */
    public static ResourceLocation createResourceLocation(String location) {
        return new BasicResourceLocation(location);
    }

    /**
     * Returns new resource.
     *
     * @param resourceLocation  location.
     * @param text              text.
     * @param resourceLocations locations.
     * @return new resource.
     */
    public static Resource createResource(
            ResourceLocation resourceLocation, Text text, Iterable<ResourceLocation> resourceLocations) {
        return new BasicResource(resourceLocation, text, resourceLocations);
    }
}
