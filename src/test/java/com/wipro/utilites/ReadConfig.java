package com.wipro.utilites;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	Properties properties;
	
	public ReadConfig() {
		File filePath = new File("./src\\test\\resources\\config\\config.properties");
		
		try {
			FileInputStream inputStream = new FileInputStream(filePath);
			properties = new Properties();
			properties.load(inputStream);
			
		}
		catch(Exception e) {
			System.out.println("Exception is :"+e.getMessage());
		}
	}
	
	public String getApplicationUrl() {
		String url = properties.getProperty("baseUrl");
		return url;
	}
	
	public String getRegistrationDetailsFilePath() {
		String path = properties.getProperty("registrationDetailsPath");
		return path;
	}
	
	public String getProductReviewFilePath() {
		String path = properties.getProperty("productReviewFilepath");
		return path;
	}
	
	public String getProductPricesFilePath() {
		String path = properties.getProperty("productPrices");
		return path;
	}
	

	public static void main(String[] args) {
		ReadConfig obj = new ReadConfig();
		System.out.println(obj.getRegistrationDetailsFilePath());
	}
	
	
}
