package com.syder.itservices.filesmanager.utils;

import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesManager {
	final static Logger logger = Logger.getLogger(PropertiesManager.class);
	private static Properties instance = null;

    private PropertiesManager() {
    }

    private static Properties getInstance() {
        if (instance == null) {
        	logger.debug("properties are null: loading file contents");
            try {
                instance = new Properties();
                instance.load(PropertiesManager.class.getResourceAsStream("/application.properties"));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return instance;
    }
    
    public static String getProperty(String key) {
    	logger.debug("accessing property: " + key);
        Properties properties = getInstance();
        String value = properties.getProperty(key);
    	logger.debug("property returns value: " + value);
        return value;
    }
}