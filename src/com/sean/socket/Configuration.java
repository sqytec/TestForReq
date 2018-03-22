package com.sean.socket;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Configuration {
	private static Logger logger = Logger.getLogger(Configuration.class);

	public static Properties getProperites() {
		File file = new File(getConfigFilePath());
		Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			properties.load(fis);
		} catch (Exception e) {
			logger.error("Get [config.properties] failed!");

			Closer.close(new Closeable[] { fis });
		} finally {
			Closer.close(new Closeable[] { fis });
		}
		return properties;
	}

	private static String getConfigFilePath() {
		String path = System.getProperty("Ipconfig.dir");
		return ((path == null) ? "IpConfig.properties" : path);
	}
}