package tools;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Przemo on 2015-03-01.
 */
public class AppProperties {
    final static Logger log = Logger.getLogger(AppProperties.class);

    public static String get(String key) {
        Properties prop = new Properties();
        InputStream input;

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (Exception e) {
            log.error(e);
        }
        return prop.getProperty(key);
    }
}
