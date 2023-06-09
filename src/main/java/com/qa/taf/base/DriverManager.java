package com.qa.taf.base;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qa.taf.constant.ConstantUtil;
import com.qa.taf.util.ExcelReaderUtil;
import com.qa.taf.util.ExtentUtil;

public class DriverManager extends BrowserManager {

	private static Logger log = LogManager.getFormatterLogger(DriverManager.class);
	private WebDriver driver;
	private ChromeOptions gcOptions;
	private FirefoxOptions ffOptions;
	private EdgeOptions meOptions;
	public static ThreadLocal<WebDriver> driverLocal = new ThreadLocal<WebDriver>();
	public static ExcelReaderUtil excelReaderUtil = new ExcelReaderUtil(
			System.getProperty("user.dir") + ConstantUtil.EXCEL_FILE_PATH);
	public static ExtentReports report = ExtentUtil.getInstance();
	public static ExtentTest test;
	public static BasePage page;

	public WebDriver getDriver() {
		return driverLocal.get();
	}

	public void setDriver(WebDriver driver) {
		driverLocal.set(driver);
	}

	public void launchBrowser() {
		driver = createDriver();
		setDriver(driver);
		page = new BasePage();
		driver.get(getDataFromPropFile(ConstantUtil.APP_URL));
		try {
			Thread.sleep(7000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private WebDriver createDriver() {

		return driver = switch (getEnvType().toString()) {
		case "LOCAL" -> {
			yield driver = createLocalDriver();
		}
		case "REMOTE" -> {
			yield driver = createRemoteDriver();
		}
		default -> throw new IllegalArgumentException("Unexpected Value ==> " + getEnvType().toString());
		};
	}

	private WebDriver createLocalDriver() {

		return driver = switch (getBrowserType().toString()) {
		case "CHROME" -> {
			log.info("Chrome driver is initialized for local test execution");
			gcOptions = new ChromeOptions();
			gcOptions.addArguments(ConstantUtil.CHROME_REMOTE_ORIGIN);
			gcOptions.addArguments(ConstantUtil.BROWSER_MAXIMIZE);
			yield driver = new ChromeDriver(gcOptions);
		}
		case "FIREFOX" -> {
			log.info("Firefox driver is initialized for local test execution");
			yield driver = new FirefoxDriver();
		}
		case "EDGE" -> {
			log.info("Edge driver is initialized for local test execution");
			meOptions = new EdgeOptions();
			meOptions.addArguments(ConstantUtil.BROWSER_MAXIMIZE);
			yield driver = new EdgeDriver();
		}
		default -> throw new IllegalArgumentException("Unexpected Value ==> " + getBrowserType().toString());
		};
	}

	private WebDriver createRemoteDriver() {

		return driver = switch (getBrowserType().toString()) {
		case "CHROME" -> {
			gcOptions = new ChromeOptions();
			gcOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
			gcOptions.setCapability(CapabilityType.BROWSER_NAME, ConstantUtil.GC_BROWSER);
			gcOptions.addArguments(ConstantUtil.CHROME_REMOTE_ORIGIN);
			gcOptions.addArguments(ConstantUtil.BROWSER_MAXIMIZE);
			try {
				log.info("Chrome driver is initialized for remote test execution");
				driver = new RemoteWebDriver(new URL("http://192.168.1.10:4444"), gcOptions);
			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			}
			yield driver;
		}
		case "FIREFOX" -> {
			ffOptions = new FirefoxOptions();
			ffOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
			ffOptions.setCapability(CapabilityType.BROWSER_NAME, ConstantUtil.FF_BROWSER);
			try {
				log.info("Firefox driver is initialized for remote test execution");
				driver = new RemoteWebDriver(new URL("http://192.168.1.10:4444"), ffOptions);
			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			}
			yield driver;
		}
		case "EDGE" -> {
			meOptions = new EdgeOptions();
			meOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
			meOptions.setCapability(CapabilityType.BROWSER_NAME, ConstantUtil.ME_BROWSER);
			meOptions.addArguments(ConstantUtil.BROWSER_MAXIMIZE);
			try {
				log.info("Edge driver is initialized for remote test execution");
				driver = new RemoteWebDriver(new URL("http://192.168.1.10:4444"), meOptions);
			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			}
			yield driver;
		}
		default -> throw new IllegalArgumentException("Unexpected Value ==> " + getBrowserType().toString());
		};
	}
}
