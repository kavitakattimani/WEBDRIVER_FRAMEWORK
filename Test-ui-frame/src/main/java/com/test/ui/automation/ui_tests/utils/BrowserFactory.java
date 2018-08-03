package com.test.ui.automation.ui_tests.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;


/**
 * The BrowserFactory class has the code for setting up and initialization of
 * the driver.
 *
 */
public class BrowserFactory {

	public static WebDriver driver;
	static Logger logger = Logger.getLogger(BrowserFactory.class.getName());
	public static final String TUNNEL_IDENTIFIER = "";
	public static final String PARENT_TUNNEL = "";
	// Adding browser version and platform
	static String defaultBrowser = "chrome";
	static String defaultPlatform = "macOS 10.12";
	static String defaultBrowserversion = "61.0";
	static String defaultRemote = "no";
	static String defaulURL = "http://google.com";
	static CommonMethods commonMethods = new CommonMethods();
	static Map<String, String> env = System.getenv();

	public enum LOGIN_DETAILS {
		sauceUsername("SAUCE_USERNAME"), sauceAccesskey("SAUCE_ACCESS_KEY"), uipageUsername(
				"UI_USERNAME"), uipagePassword("UI_PASSWORD");
		private final String value;

		private LOGIN_DETAILS(String key) {
			this.value = key;
		}

		public String toString() {
			return this.value;
		}
	}

	/**
	 * This method is used to setup the driver , start the browser and launch the
	 * url.
	 * 
	 * @param browserName
	 *            input from config file
	 * @param url
	 *            input from config file
	 * @param remote
	 *            input from config file
	 * @param version
	 *            input from config file
	 * @param platform
	 *            input from config file
	 * @return driver instance
	 */

	public static String getRemoteUrl() {
		String remoteURL = "http://" + env.get(LOGIN_DETAILS.sauceUsername.toString()) + ":"
				+ env.get(LOGIN_DETAILS.sauceAccesskey.toString()) + "@ondemand.saucelabs.com:80/wd/hub";
		return remoteURL;
	}

	public static WebDriver startBrowser(String browserName, String url, String remote, String platform,
			String browserVersion) throws MalformedURLException {
		logger.info("***** Setting up driver *****" + "In Method :"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		if (browserName.equalsIgnoreCase("${browser}")) {
			browserName = defaultBrowser;
		}
		if (url.equalsIgnoreCase("${url}")) {
			url = defaulURL;
		}
		if (remote.equalsIgnoreCase("${remote}")) {
			remote = defaultRemote;
		}
		if (platform.equalsIgnoreCase("${platform}")) {
			platform = defaultPlatform;
			System.out.println("defaultplatform" + defaultPlatform);
		}
		if (browserVersion.equalsIgnoreCase("${version}")) {
			browserVersion = defaultBrowserversion;
		}

		if (remote.equalsIgnoreCase("yes"))
			setRemoteWebDriver(browserName, url, platform, browserVersion);
		else
			setWebDriver(browserName, url, platform, browserVersion);
		commonMethods.launchBaseURL(url);
		commonMethods.signIn(env.get(LOGIN_DETAILS.uipageUsername.toString()),
				env.get(LOGIN_DETAILS.uipagePassword.toString()));
		return driver;
	}

	private static WebDriver setWebDriver(String browserName, String url, String platform, String version) {
		// TODO Auto-generated method stub
		logger.info("***** Setting up driver *****" + "In Method :"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		switch (browserName) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver");
			driver = new FirefoxDriver();
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
			driver = new ChromeDriver();
			break;
		case "safari":
			driver = new SafariDriver();
			break;
		}

		return driver;
	}

	/**
	 * This Method is used to setup remote webdriver based on the browser data
	 * provided
	 * 
	 * @param browserName
	 * @throws MalformedURLException
	 */

	private static WebDriver setRemoteWebDriver(String browserName, String url, String platform, String version)
			throws MalformedURLException {
		DesiredCapabilities caps = null;
		logger.info("***** Setting up driver *****" + "In Method :"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		switch (browserName) {
		case "chrome":
			caps = DesiredCapabilities.chrome();
			break;
		case "firefox":
			caps = DesiredCapabilities.firefox();
			System.out.println("Inside firefox");
			break;
		case "safari":
			caps = DesiredCapabilities.safari();
			break;
		default:
			logger.info("***** case default so failuer is caused *****" + "In Method :"
					+ Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		caps.setCapability("platform", platform);
		caps.setCapability("version", version);
		// if working on tunnels you may need the below
		/*caps.setCapability("tunnelIdentifier", TUNNEL_IDENTIFIER);
		caps.setCapability("parentTunnel", PARENT_TUNNEL);*/
		driver = new RemoteWebDriver(new URL(getRemoteUrl()), caps);
		return driver;
	}

}