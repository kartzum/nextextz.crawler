package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.Resource;
import nextextz.crawler.pack.ResourceLocation;
import nextextz.text.pack.text.Text;

import static com.google.common.base.Preconditions.checkNotNull;

class BasicResource implements Resource {
    private final ResourceLocation resourceLocation;
    private final Text text;
    private final Iterable<ResourceLocation> resourceLocations;

    public BasicResource(ResourceLocation resourceLocation, Text text, Iterable<ResourceLocation> resourceLocations) {
        checkNotNull(resourceLocation);
        checkNotNull(text);
        checkNotNull(resourceLocations);

        this.resourceLocation = resourceLocation;
        this.text = text;
        this.resourceLocations = resourceLocations;
    }

    @Override
    public Text getText() {
        return text;
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public Iterable<ResourceLocation> getResourceLocations() {
        return resourceLocations;
    }
}
