package com.qa.taf.base;

import java.net.MalformedURLException;
import java.net.URI;

import com.qa.taf.constant.BrowserType;
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

    private static final Logger log = LogManager.getFormatterLogger(DriverManager.class);

    private WebDriver driver;
    private ChromeOptions gcOptions;
    private FirefoxOptions ffOptions;
    private EdgeOptions meOptions;
    public static ThreadLocal<WebDriver> driverLocal = new ThreadLocal<>();
    public static ExcelReaderUtil excelReaderUtil = new ExcelReaderUtil(
            System.getProperty("user.dir") + ConstantUtil.EXCEL_FILE_PATH);
    public static ExtentReports report = ExtentUtil.getInstance();
    public static ExtentTest test;
    public static BasePage page;

    public void setDriver(WebDriver driver) {
        driverLocal.set(driver);
    }

    public WebDriver getDriver() {
        return driverLocal.get();
    }

    public void launchBrowser() {
        driver = createDriver();
        setDriver(driver);
        page = new BasePage();
        driver.get(getDataFromPropFile(ConstantUtil.APP_URL));
    }

    private WebDriver createDriver() {
        return driver = switch (getEnvType().toString()) {
            case "LOCAL" -> createLocalDriver();
            case "REMOTE" -> {
                try {
                    yield createRemoteDriver();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> throw new IllegalArgumentException("Unexpected Value ==> " + getEnvType().toString());
        };
    }

    private WebDriver createLocalDriver() {

        return driver = switch (getBrowserType().toString()) {
            case "CHROME" -> {
                log.info("Chrome driver is initialized for local test execution");
                gcOptions = new ChromeOptions();
                //gcOptions.addArguments(ConstantUtil.CHROME_REMOTE_ORIGIN);
                gcOptions.addArguments(ConstantUtil.BROWSER_MAXIMIZE);
                yield new ChromeDriver(gcOptions);
            }
            case "FIREFOX" -> {
                log.info("Firefox driver is initialized for local test execution");
                ffOptions = new FirefoxOptions();
                ffOptions.addArguments(ConstantUtil.BROWSER_MAXIMIZE);
                yield new FirefoxDriver(ffOptions);
            }
            case "EDGE" -> {
                log.info("Edge driver is initialized for local test execution");
                meOptions = new EdgeOptions();
                meOptions.addArguments(ConstantUtil.BROWSER_MAXIMIZE);
                yield new EdgeDriver(meOptions);
            }
            default -> throw new IllegalArgumentException("Unexpected Value ==> " + getBrowserType().toString());
        };
    }

    private WebDriver createRemoteDriver() throws MalformedURLException {

        return driver = switch (getBrowserType().toString()) {
            case "CHROME" -> {
                gcOptions = new ChromeOptions();
                gcOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                gcOptions.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
                gcOptions.addArguments(ConstantUtil.CHROME_REMOTE_ORIGIN);
                gcOptions.addArguments(ConstantUtil.BROWSER_MAXIMIZE);
                yield new RemoteWebDriver(URI.create("http://192.168.1.10:4444").toURL(), gcOptions);
            }
            case "FIREFOX" -> {
                ffOptions = new FirefoxOptions();
                ffOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                ffOptions.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
                yield new RemoteWebDriver(URI.create("http://192.168.1.10:4444").toURL(), ffOptions);
            }
            case "EDGE" -> {
                meOptions = new EdgeOptions();
                meOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                meOptions.setCapability(CapabilityType.BROWSER_NAME, BrowserType.EDGE);
                meOptions.addArguments(ConstantUtil.BROWSER_MAXIMIZE);
                yield new RemoteWebDriver(URI.create("http://192.168.1.10:4444").toURL(), meOptions);
            }
            default -> throw new IllegalArgumentException("Unexpected Value ==> " + getBrowserType().toString());
        };
    }

}

