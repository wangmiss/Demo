package com.wangxi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyPropertiesUtil {
	public static String getProperty(String property,String name){
		Properties properties = new Properties();

		InputStream resourceAsStream = MyPropertiesUtil.class.getClassLoader().getResourceAsStream(property);

		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String property2 = properties.getProperty(name);

		return property2;
	}
}
