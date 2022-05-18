package com.wipro.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wipro.utilites.ReadConfig;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.build.Plugin.Factory.UsingReflection.Priority;

public class test {

	WebDriver driver;
	
	@BeforeTest
	public void setUptest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
	}
	
	@Test(priority = 1)
	public void openGoogle() {
		driver.get("https://www.google.com/");
	}
	
	public void openGoogle
}
