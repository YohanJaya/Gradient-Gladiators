package com.yohan.gladiators;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameConfig {

    private final Properties notebook = new Properties();

    public GameConfig(String fileName) {
        try (InputStream is = getClass().getResourceAsStream("/" + fileName)) {
            if (is == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            notebook.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file: " + fileName, e);
        }
    }

    private String require(String name) {
        String value = notebook.getProperty(name);
        if (value == null) {
            throw new IllegalArgumentException("Missing required config key: " + name);
        }
        return value;
    }

    public int getInt(String name) {
        return Integer.parseInt(require(name));
    }

    public float getFloat(String name) {
        return Float.parseFloat(require(name));
    }

    public String getString(String name) {
        return require(name);
    }
}