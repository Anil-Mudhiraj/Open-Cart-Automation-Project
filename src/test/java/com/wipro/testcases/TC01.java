// Name : 

package com.wipro.testcases;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wipro.testbase.*;
import com.wipro.utilites.ReadConfig;

public class TC01 extends BaseTest {
	

	ReadConfig config = new ReadConfig();
	public String opencartUrl = config.getApplicationUrl();
	public String registrationDetailsFilePath = config.getRegistrationDetailsFilePath();
	public String productReviewFilePath = config.getProductReviewFilePath();
	public String productPricesFilePath = config.getProductPricesFilePath();
	
	Map<String,String> customerDetails = new HashMap<String,String>();
	
	@Test(priority = 1)
	public void testRegistrationOfCustomer() throws Exception{
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//Step - 1 --> Launch Opencart application
		driver.get(opencartUrl);
		Thread.sleep(2000);
		driver.manage().window().maximize();
		// Step -2 --> clicking on my account 
		driver.findElement(By.xpath("//a[@title='My Account']")).click();
		//clicking on register button
		driver.findElement(By.xpath("//a[text()='Register']")).click();
		
		//get cosutomer Details
		getCustomerDetails();
		
		//Step -3 --> Fill in account details
	
		
		driver.findElement(By.name("firstname")).sendKeys(customerDetails.get("firstname"));
		driver.findElement(By.name("lastname")).sendKeys(customerDetails.get("lastname"));
		driver.findElement(By.name("email")).sendKeys(customerDetails.get("email"));
		driver.findElement(By.name("telephone")).sendKeys(customerDetails.get("telephone"));
		driver.findElement(By.name("password")).sendKeys(customerDetails.get("password"));
		driver.findElement(By.name("confirm")).sendKeys(customerDetails.get("confirmpassword"));
		driver.findElement(By.name("agree")).click();
		Thread.sleep(2000);
		
		//Step -4 --> Click on submit button
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		String message = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
		String warningMessage = "Warning: E-Mail Address is already registered!";
		String successMessage = "Your Account Has Been Created!";
		
		if(message.equals(successMessage)) {
			Assert.assertTrue(true);
		}else {
			Assert.assertTrue(false);
		}
			
	}
	
	
	public void getCustomerDetails() throws IOException{
		FileInputStream inputStream = new FileInputStream(registrationDetailsFilePath);
		XSSFWorkbook workbook  = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		XSSFRow row = sheet.getRow(1);
		String firstName = row.getCell(0).getStringCellValue();
		String lastName = row.getCell(1).getStringCellValue();
		String email = row.getCell(2).getStringCellValue();
		String telephone =  row.getCell(3).getRawValue();
		String password = row.getCell(4).getRawValue();
		String confirmPassword = row.getCell(5).getRawValue();
		
		customerDetails.put("firstname", firstName);
		customerDetails.put("lastname", lastName);
		customerDetails.put("email", email);
		customerDetails.put("telephone",telephone);
		customerDetails.put("password", password);
		customerDetails.put("confirmpassword",confirmPassword);
		inputStream.close();
	}


	@Test(priority = 2)
	public void testContactUsFunctionality() {
		//Step - 5 --> Click on contact us button.
		driver.findElement(By.xpath("//a[text()='Contact Us']")).click();
		
		String registeredName = driver.findElement(By.name("name")).getText();
		String registeredEmail = driver.findElement(By.name("email")).getText();
		
		try {
			if((customerDetails.get("firstname").equals(registeredName)) && (customerDetails.get("email").equals(registeredEmail))) {
				Assert.assertTrue(true);
			}else {
				Assert.assertTrue(false);
			}
		}
		catch(Throwable e) {
			System.out.println(e);
		}
		
		
		//Step - 6 --> Type the Enquiry
		
		driver.findElement(By.id("input-enquiry")).sendKeys("This is to Change of Address/Phone number");
		driver.findElement(By.xpath("//div[@class='pull-right']/input")).click();
		
		String contactUsSuccessMessage = "Your enquiry has been successfully sent to the store owner!";
		String message = driver.findElement(By.xpath("//div[@id='content']/p")).getText();
		
		if(contactUsSuccessMessage.equals(message)) {
			Assert.assertTrue(true);
		}else {
			Assert.assertTrue(false);
		}
		
		//Step - 8 --> Click on Continue to display homepage
		driver.findElement(By.xpath("//a[text()='Continue']")).click();
	}


	@Test(priority=3)
	public void testReviewProductFunctionality() throws IOException,InterruptedException{
		//Step - 9 --> clicking on samsung tab

		driver.findElement(By.xpath("//div[@class='swiper-wrapper']/div[4]/a")).click();
		
		//Step -10 --> 	clicking on review tab
		driver.findElement(By.xpath("//ul[@class='nav nav-tabs']/li[2]/a")).click();
		
		
		// Step - 11 --> testing with review less than 25 words
		FileInputStream inputStream = new FileInputStream(productReviewFilePath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet  newSheet = workbook.getSheet("Sheet1");
		
		
		XSSFRow row  = newSheet.getRow(0);
		
		String reviewComment = row.getCell(0).getStringCellValue();
		String rating = row.getCell(1).getRawValue();
		driver.findElement(By.id("input-review")).sendKeys(reviewComment);
		List<WebElement> radioButtons = driver.findElements(By.name("rating"));
		for(WebElement ele : radioButtons) {
			String value = ele.getAttribute("value");
			if(rating.equals(value)) {
				ele.click();
				break;
			}
		}
		
		
		driver.findElement(By.id("button-review")).click();
		Thread.sleep(2000);
		
		String actualReviewMessage = "Warning: Review Text must be between 25 and 1000 characters!";
				
		String obtainedReviewMessage  = driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText();
		if(actualReviewMessage.equals(obtainedReviewMessage)) {
			Assert.assertTrue(true);
		}
		else {
			Assert.assertFalse(false);
		}
		
		//Step -12 --> testing with review greater than 25 words
		row = newSheet.getRow(1);
		
		reviewComment = row.getCell(0).getStringCellValue();
		System.out.println(reviewComment);
		 rating = row.getCell(1).getRawValue();
		System.out.println(rating);
		
		driver.findElement(By.id("input-review")).sendKeys(reviewComment);
//				
		radioButtons = driver.findElements(By.name("rating"));
		for(WebElement ele : radioButtons) {
			String value = ele.getAttribute("value");
			if(rating.equals(value)) {
				ele.click();
				break;
			}
		}
		
		
		driver.findElement(By.id("button-review")).click();
		Thread.sleep(2000);
		
		 actualReviewMessage = "Thank you for your review. It has been submitted to the webmaster for approval.";
				
		 obtainedReviewMessage  = driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible']")).getText();
		if(actualReviewMessage.equals(obtainedReviewMessage)) {
			Assert.assertTrue(true);
		}
		else {
			Assert.assertTrue(false);
		}
		
	}


	@Test(priority = 4)
	public void testAddToCart() throws InterruptedException, IOException{

		//Step - 13 --> clicking on add to wishlist button
		driver.findElement(By.xpath("//div[@id='content']/div/div[2]/div/button[1]")).click();
		
		// add prices of tab in different currencies into excel file
		
		//Step - 15 --> click on wishlist button
		driver.findElement(By.id("wishlist-total")).click();
		
		//Step - 16 --> Click on pound Sterling
		driver.findElement(By.xpath("//div[@class='pull-left']/form/div")).click();
		driver.findElement(By.name("GBP")).click();
		
		//Step - 17 --> Retrieve the value and write into any flat file.
		
		FileWriter writer = new FileWriter(productPricesFilePath);
		
		String price = driver.findElement(By.xpath("//div[@class='price']")).getText();
		
		writer.write("Price in pound sterling : "+ price);
		writer.write("\n");
		
		
		// Step - 18 -->Click on " Euro ".
		driver.findElement(By.xpath("//div[@class='pull-left']/form/div")).click();
		driver.findElement(By.name("EUR")).click();
		
		// Step - 19 --> Retrieve the value and write into any flat file.
		price = driver.findElement(By.xpath("//div[@class='price']")).getText();
		
		writer.write("Price in Euro  : "+ price);
		writer.write("\n");
		
		// Step - 20 --> Click on "US Dollar".
		driver.findElement(By.xpath("//div[@class='pull-left']/form/div")).click();
		driver.findElement(By.name("USD")).click();
		
		// Step - 21 --> Retrieve the value and write into any flat file.
		price = driver.findElement(By.xpath("//div[@class='price']")).getText();
		
		writer.write("Price in Euro  : "+ price);
		writer.write("\n");
		
		writer.close();
		
		//Step - 22 --> clicking on add to cart button
		driver.findElement(By.xpath("//button[@data-original-title='Add to Cart']")).click();
		Thread.sleep(1000);
		
		//Step - 24 -- > remove item from whishlist by clicking on remove item button
		driver.findElement(By.xpath("//a[@data-original-title='Remove']")).click();
		
		//Step 25 -- > click on continue
		driver.findElement(By.xpath("//a[text()='Continue']"));
		//Step - 26 -- > logout
		driver.findElement(By.xpath("//a[@title='My Account']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
	}

}
