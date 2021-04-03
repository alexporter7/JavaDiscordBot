package main;

import java.util.HashMap;

public class DiscordBotProperties {

    public HashMap<String, String> properties;

    public DiscordBotProperties() {
        this.properties = new HashMap<>();
    }

    public void addProperty(String propertyName, String propertyValue) {
        this.properties.put(propertyName, propertyValue);
    }

    public HashMap<String, String> getProperties() {
        return this.properties;
    }

    public String getProperty(String propertyName) {
        return this.properties.get(propertyName);
    }

    public void setProperty(String propertyName, String propertyValue) {
        this.properties.put(propertyName, propertyValue);
    }

}
