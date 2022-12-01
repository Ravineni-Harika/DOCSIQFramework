package com.Automation.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigsReader {

//	static Properties prop;
//	
//	public static void readProperties(String filePath) {
//		
//		try {
//			FileInputStream fis=new FileInputStream(filePath);
//			prop=new Properties();
//			prop.load(fis);
//			fis.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static String getPropValue(String key) {
//		
//		return prop.getProperty(key);
//	}
//}
static Properties prop;
	
	public static Properties readProperties(String filePath) {
		
		try {
			FileInputStream fis=new FileInputStream(filePath);
			prop=new Properties();
			prop.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public static String getPropValue(String key) {
		
		return prop.getProperty(key);
	}
}