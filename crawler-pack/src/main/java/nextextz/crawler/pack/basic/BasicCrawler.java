package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.Crawler;
import nextextz.crawler.pack.ResourceLocation;
import nextextz.text.pack.text.Text;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class BasicCrawler implements Crawler {
    private final ResourceLocation resourceLocation;
    private final BasicCrawlerHandler handler;

    public BasicCrawler(ResourceLocation resourceLocation, BasicCrawlerHandler handler) {
        this.resourceLocation = resourceLocation;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            final URL urlLocal = new URL(resourceLocation.getLocation());
            final URLConnection urlConnection = urlLocal.openConnection();
            try (InputStream inputStream = urlConnection.getInputStream()) {
                String encoding = urlConnection.getContentEncoding();
                encoding = encoding != null ? encoding : "UTF-8";
                final String value = IOUtils.toString(inputStream, encoding);
                final Text text = nextextz.text.pack.text.Factory.createText(value);
                handler.handle(BasicFactory.createResource(resourceLocation, text));
            }
        } catch (Exception e) {
            handler.handleException(e);
        }
    }
}
