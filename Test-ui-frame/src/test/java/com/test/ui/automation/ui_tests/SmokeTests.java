package com.test.ui.automation.ui_tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.ui.automation.ui_tests.utils.BrowserFactory;
import com.test.ui.automation.ui_tests.utils.ExcelReader;
import com.test.ui.automation.ui_tests.utils.LoadProperties;

public class SmokeTests {
	/*
	 * *UI Tests
	 */
	WebDriver driver;
	static Logger logger = Logger.getLogger(SmokeTests.class.getName());
	FileInputStream input = null;
	Properties prop;
	String browser;
	String url;
	String remote;
	ExcelReader excelReader;
	LoadProperties loadProps;
	String platform;
	String version;

	/**
	 * Setup method for activities such as read configuration file, initializing the 
	 * driver based on browser and url details.
	 * @throws MalformedURLException 
	 */
	@BeforeClass
	public void initialize() throws MalformedURLException {
		logger.info("***** Executing initialize() *****"+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("***** Loading the configuration file *****"+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
		loadProps = new LoadProperties();
		/*try {
			input = new FileInputStream("gdprconfig.properties");
			prop = new Properties();
			prop.load(input);
		} catch (IOException e) {
			logger.info(e.getMessage());

		}*/
		browser = loadProps.readProperty("browser");
		url = loadProps.readProperty("url");
		remote = loadProps.readProperty("remote");
		platform = loadProps.readProperty("remote");
		version = loadProps.readProperty("remote");
		logger.info("***** Starting "+browser+" browser *****"+ "In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
		driver=BrowserFactory.startBrowser(browser, url,remote, platform, version);

	}

	@Test (dataProvider="TestData")
	public void launchURL(String xpath, String expected) throws InterruptedException {
		logger.info("***** Launching google url *****"+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
		WebElement googleLogo = driver.findElement(By.xpath(xpath.toString()));
		String logo = googleLogo.getText().toString();
		System.out.println("**** Xpath from excel is "+xpath);
		System.out.println("#######"+logo);
		Thread.sleep(10000);
		Assert.assertEquals(logo, expected.toString());
		
	}

	/**
	 * Teardown method for post test actions
	 */	

	@AfterClass
	public void tearDown() {
		logger.info("***** Executing the tests complete. Quitting the driver ....."+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.quit();
	}

	@DataProvider(name="TestData" )
	public Object[][] getTestData(){
		Object[][] testData = null;
		String fileName = "DataSheet/UITests.xls";
		testData = ExcelReader.readExcelData(fileName, "smoke", "GDPRSmokeTests");

		return testData;
	}


}
