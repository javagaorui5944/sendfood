package com.fenghuo.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** 操作properties文件类 */
public class PropertiesUtils {
	
	private PropertiesUtils() {
		// 构造方法私有化，外部不能实例化该类 
	}
	
	private static Properties properties ;
	static {
		properties = new Properties();
		
		try {
			// 解析文件config.properties
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("sendfood.properties");
			properties.load(is);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取properties文件属性
	 * 
	 * @param key
	 *            config.properties文件key
	 * @return config.properties文件key对应value
	 */
	public static String getProp(String key) {
		return properties.getProperty(key);
	}
}