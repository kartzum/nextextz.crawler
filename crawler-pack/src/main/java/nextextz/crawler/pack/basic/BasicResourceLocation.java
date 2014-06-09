package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.ResourceLocation;

import static com.google.common.base.Preconditions.checkNotNull;

class BasicResourceLocation implements ResourceLocation {
    private final String location;

    public BasicResourceLocation(String location) {
        checkNotNull(location);

        this.location = location;
    }

    @Override
    public String getLocation() {
        return location;
    }
}
