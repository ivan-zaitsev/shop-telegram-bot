package ua.ivan909020.bot.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final ConfigReader INSTANCE = new ConfigReader();

    private Properties properties;

    private ConfigReader() {
        this.properties = new Properties();
        this.load();
    }

    public static ConfigReader getInstance() {
        return INSTANCE;
    }

    private void load() {
        try (InputStream stream = ConfigReader.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed load config file", e);
        }
    }

    public String get(String name) {
        return properties.getProperty(name);
    }

}
