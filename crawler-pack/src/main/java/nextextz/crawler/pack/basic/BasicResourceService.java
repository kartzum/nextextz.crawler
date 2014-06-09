package nextextz.crawler.pack.basic;

import nextextz.crawler.pack.ResourceLocation;
import nextextz.text.pack.text.Text;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class BasicResourceService implements ResourceService {
    @Override
    public Text load(ResourceLocation resourceLocation) {
        Text result;
        try {
            final URL urlLocal = new URL(resourceLocation.getLocation());
            final URLConnection urlConnection = urlLocal.openConnection();
            try (InputStream inputStream = urlConnection.getInputStream()) {
                String encoding = urlConnection.getContentEncoding();
                encoding = encoding != null ? encoding : "UTF-8";
                final String value = IOUtils.toString(inputStream, encoding);
                result = nextextz.text.pack.text.Factory.createText(value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
