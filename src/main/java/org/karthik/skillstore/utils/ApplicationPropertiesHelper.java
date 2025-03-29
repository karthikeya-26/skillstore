package org.karthik.skillstore.utils;

import org.karthik.skillstore.dbutil.Database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationPropertiesHelper {

    private static Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ApplicationPropertiesHelper.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find app.properties");
                throw new RuntimeException("app.properties file not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    public static Properties getProperties() {
        if (properties.isEmpty()) {
            loadProperties();
        }
        return properties;
    }
}
