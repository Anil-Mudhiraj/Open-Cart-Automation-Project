package com.wipro.testbase;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.wipro.utilites.ReadConfig;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	
	public WebDriver driver;
	
	@BeforeTest
	public void setUpTest()  {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
	}

//	@AfterTest
//	public void terminateTest() {
//		driver.close();
//	}
}
