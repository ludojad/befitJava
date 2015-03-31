package tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Przemo on 2015-03-01.
 */
public class AppProperties {
    public static String get(String key) throws IOException, IllegalArgumentException, NullPointerException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        prop.load(input);
        if (prop.getProperty(key) == null) {
            throw new IllegalArgumentException("Key [" + key + "] not found in properties file");
        }
        return prop.getProperty(key);
    }
}
