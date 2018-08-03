package com.test.ui.automation.ui_tests.utils;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.awt.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CommonMethods extends  BrowserFactory{
	static Logger logger = Logger.getLogger(CommonMethods.class.getName());
	long maxWaitSeconds =1000;
	ElementWrappers elementWrappers =  new ElementWrappers();
	String passwordElementXpath = "";
	String usernameElementXpath = "";
	
	/**
	 * Below utility method is used for uploading a file by providing the file path
	 * @param WebElement of the input box where file path would be given
	 * @param File location that needs to be uploaded
	 */
	public void fileUploadByProvidingPath(WebElement inputBoxElement, String filePath ){
		logger.info("***** Uploading file *****"+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
		inputBoxElement.sendKeys(filePath);
		logger.info("***** Completed file upload *****"+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Below utility method is used for uploading a file by choosing the path in a window
	 * @param WebElement of the choose button to open a file selection window
	 * @param File location that needs to be uploaded
	 * @throws InterruptedException 
	 */
	public void fileUploadBySelecingPathFromWindow(WebElement clickChooseFile, String filePath) throws InterruptedException{
		logger.info("***** Uploading file *****"+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
		// Specify the file location 
		StringSelection file = new StringSelection(filePath);

		// Copy to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(file,null);

		//Click on the button to open the window for choosing the file
		clickChooseFile.click();
		Thread.sleep(2000);

		try {
			// Create object of Robot class
			Robot robot = new Robot();
			Thread.sleep(1000);

			// Cmd + Tab is needed since it launches a Java app and the browser looses focus
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.delay(1000);

			//Open Goto window
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_G);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_G);
			robot.delay(1000);

			//Paste the clipboard value
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_V);
			robot.delay(1000);

			//Press Enter key to close the Goto window and Upload window
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			//Thread.sleep(5000);
			logger.info("***** Completed file upload *****"+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread.sleep(1000);

	}

	public String getTitle(){
		String title = driver.getTitle();
		return title;
	}

	public String getCurrentURL(){
		String url = driver.getCurrentUrl();
		return url;
	}

	public void launchBaseURL(String url){
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		logger.info("***** Launching url *****"+" "+url+" "+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.get(url);
	}

	public void signIn(String userName, String password) {
		logger.info("***** Signing in *****"+" "+"In Method :"+Thread.currentThread().getStackTrace()[1].getMethodName());
		elementWrappers.sleep(2000);
		WebElement passwordElement = elementWrappers.getElementByLocator(passwordElementXpath);
		elementWrappers.sleep(1000);
		passwordElement.sendKeys(password);
		WebElement usernameElement = elementWrappers.getElementByLocator(usernameElementXpath);
		elementWrappers.sleep(1000);
		usernameElement.sendKeys(userName);
		elementWrappers.sleep(1000);
		usernameElement.submit();
		elementWrappers.sleep(2000);
	}
}