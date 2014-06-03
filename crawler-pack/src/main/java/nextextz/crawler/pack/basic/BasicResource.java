package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.Resource;
import nextextz.crawler.pack.ResourceLocation;
import nextextz.text.pack.text.Text;

class BasicResource implements Resource {
    private final ResourceLocation resourceLocation;
    private final Text text;

    public BasicResource(ResourceLocation resourceLocation, Text text) {
        this.resourceLocation = resourceLocation;
        this.text = text;
    }

    @Override
    public Text getText() {
        return text;
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }
}
