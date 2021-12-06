package store_data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetPropertyValue {

    private InputStream inputStream;
    private String db_url;
    private String db_username;
    private String db_password;

    public String getDb_url() {
        return db_url;
    }

    public String getDb_username() {
        return db_username;
    }

    public String getDb_password() {
        return db_password;
    }

    public void getPropValues() throws IOException {

        try {
            Properties properties = new Properties();
            String propFileName = "application.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            db_url = properties.getProperty("db_url");
            db_username = properties.getProperty("db_username");
            db_password = properties.getProperty("db_password");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            assert inputStream != null;
            inputStream.close();
        }
    }
}
