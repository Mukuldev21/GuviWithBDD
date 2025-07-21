package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    protected static Properties properties;
    protected static JsonObject jsonConfig;

    public static Properties loadProperties(String filePath) {

        properties = new Properties();
        try(FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        }
        return  properties;
    }

    public static JsonObject loadJsonConfig(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            jsonConfig = new Gson().fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            System.err.println("Error loading JSON config file: " + e.getMessage());
        }
        return jsonConfig;
    }

    public static String getPropertyValue(String key) {
        if (properties == null) {
            throw new IllegalStateException("Properties not loaded. Call loadProperties first.");
        }
        return properties.getProperty(key);
    }

    public static String getJsonConfigValue(String key) {
        if (jsonConfig == null) {
            throw new IllegalStateException("JSON config not loaded. Call loadJsonConfig first.");
        }
        return jsonConfig.get(key).getAsString();
    }

}
// This class provides methods to read properties from a .properties file and JSON configuration from a .json file.