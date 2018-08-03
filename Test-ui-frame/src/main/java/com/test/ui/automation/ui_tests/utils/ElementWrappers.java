package com.test.ui.automation.ui_tests.utils;

import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ElementWrappers extends  BrowserFactory{
	public enum LocatorType {
		xpath,
		css,
		classname,
		link,
		id
	}
	private static final Logger logger = Logger.getLogger(ElementWrappers.class.getSimpleName());
	long maxWaitSeconds =1000;
	

	public WebElement getElementByLocator(String locator){
		WebElement element = null;
		try{
			LocatorType identifier = LocatorType.valueOf(locator.substring(0, locator.indexOf("=")).toLowerCase());
			locator = locator.substring(locator.indexOf("=")+1, locator.length());
			By by = getWebLocator(locator, identifier);
			// Wait for the element to be visible
			WebDriverWait wait = new WebDriverWait(driver, maxWaitSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			element = driver .findElement(by);
		}catch(TimeoutException e){
			logger.info("Element could not be located using locator - "+locator);
		}
		return element;
		}

	
	public boolean isElementPresent(String locator){
		boolean flag = false;

		WebElement element = null;
		try{
			element = getElementByLocator(locator);
			flag = true;
		}catch(org.openqa.selenium.ElementNotVisibleException ex) {
			flag = false;
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			flag = false;
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			flag = false;
		}catch(org.openqa.selenium.TimeoutException ex){
			flag = false;
		}

		if(element == null)
			flag = false;
		else
			flag = true;

		return flag;
	}
	
	public String getPageSource(){
		return driver.getPageSource();
	}

	public String getText(String locator){
		WebElement element = getElementByLocator(locator);
		sleep(2000);
		String text = element.getText();
		return text;
	}

	public void sendKeys(String locator, String text){
		WebElement element = getElementByLocator(locator);
		sleep(2000);
		element.clear();
		element.sendKeys(text);
	}

	public void clickElement(String locator){
		WebElement mapElement = getElementByLocator(locator);
		clickOnElement(mapElement);
		sleep(2000);
	}
	
	public void clickOnElement(WebElement mapElement) {
		if(mapElement != null) {
			String browserName ="";
			if(browserName.contains("IE"))
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", mapElement);
			else
				mapElement.click();
		}
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
		driver.get(url);
	}
	
	public boolean isElementEnabled(String locator){
		boolean flag = false;
		try {
			WebElement element=getElementByLocator(locator);
			if (element != null && element.isEnabled()) {
				flag = true;
			}
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			flag = false;
		} catch(org.openqa.selenium.TimeoutException ex){
			flag = false;
		}
		return flag;
	}
		
	public WebDriverWait getUserDefinedWebDriverWait(WebDriver driver, long waitDuration) {
		WebDriverWait userDefinedWait = null;
		if(driver != null) {
			userDefinedWait = new WebDriverWait(driver, waitDuration);
		}
		return userDefinedWait;

	}
	
	public void sleep(long timeout){

		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private By getWebLocator(String locator, LocatorType identifier) {
		By by = null;
		switch (identifier) {
		case xpath:
			by = By.xpath(locator);
			break;
		case classname:
			by = By.className(locator);
			break;
		case css:
			by = By.cssSelector(locator);
			break;
		case id:
			by = By.id(locator);
			break;
		case link:
			//element = driver.findElement(By.linkText(locator));
			by = By.linkText(locator);
			break;
		}
		return by;
	}
	
	
}
