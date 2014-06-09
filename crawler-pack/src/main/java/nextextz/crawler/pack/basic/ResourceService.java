package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.ResourceLocation;
import nextextz.text.pack.text.Text;

interface ResourceService {
    Text load(ResourceLocation resourceLocation);
}
