package com.qa.taf.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.taf.util.ConstantUtil;
import com.qa.taf.util.DriverType;
import com.qa.taf.util.EnvType;
import com.qa.taf.util.ExcelReaderUtil;
import com.qa.taf.util.FileReaderUtil;

public class DriverManager extends FileReaderUtil {

	private WebDriver driver;
	private ChromeOptions options;
	private static ThreadLocal<WebDriver> driverLocal = new ThreadLocal<WebDriver>();
	private static DriverType driverType = null;
	private static EnvType envType = null;
	public static ExcelReaderUtil excelReaderUtil = new ExcelReaderUtil(
			System.getProperty("user.dir") + ConstantUtil.EXCEL_FILE_PATH);
	public static BasePage page;

	public WebDriver getDriver() {
		return driverLocal.get();
	}

	public void setDriver(WebDriver driver) {
		driverLocal.set(driver);
	}

	public void launchBrowser(String browser) {
		if (getDataFromPropFile(ConstantUtil.BROWSER).equalsIgnoreCase(browser)) {
			options = new ChromeOptions();
			options.addArguments(ConstantUtil.CHROME_LAUNCH_OPTION);
			driver = new ChromeDriver(options);
			setDriver(driver);
			page = new BasePage();
			driver.get(getDataFromPropFile(ConstantUtil.APP_URL));
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private WebDriver createDriver() {
		switch (envType) {
		case LOCAL:
			driver = createLocalDriver();
			break;
		case REMOTE:
			driver = createRemoteDriver();
			break;
		}
		return driver;
	}

	private WebDriver createLocalDriver() {
		switch (driverType) {
		case CHROME:
			driver = new ChromeDriver();
			break;
		case FIREFOX:
			driver = new FirefoxDriver();
			break;
		case EDGE:
			driver = new EdgeDriver();
			break;
		}
		return driver;
	}

	private WebDriver createRemoteDriver() {
		// TODO Auto-generated method stub
		return null;
	}

}
