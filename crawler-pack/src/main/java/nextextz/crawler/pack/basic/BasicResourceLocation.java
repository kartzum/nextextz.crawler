package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.ResourceLocation;

class BasicResourceLocation implements ResourceLocation {
    private final String location;

    public BasicResourceLocation(String location) {
        this.location = location;
    }

    @Override
    public String getLocation() {
        return location;
    }
}
