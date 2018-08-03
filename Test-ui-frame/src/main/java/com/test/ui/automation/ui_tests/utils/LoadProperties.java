package com.test.ui.automation.ui_tests.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import com.test.ui.automation.ui_tests.SmokeTests;

public class LoadProperties {
	FileInputStream input = null;
	Properties prop;
	static Logger logger = Logger.getLogger(SmokeTests.class.getName());

	public String readProperty(String propertyname) {
		try {
			input = new FileInputStream("config.properties");
			prop = new Properties();
			prop.load(input);
		} catch (IOException e) {
			logger.info(e.getMessage());

		}

		return System.getProperty(propertyname, prop.getProperty(propertyname));
	}
}
