package nextextz.crawler.pack;

import nextextz.text.pack.text.Text;

/**
 * Represents resource.
 */
public interface Resource {
    /**
     * Returns text.
     *
     * @return text.
     */
    Text getText();

    /**
     * Returns location.
     *
     * @return location.
     */
    ResourceLocation getResourceLocation();
}
