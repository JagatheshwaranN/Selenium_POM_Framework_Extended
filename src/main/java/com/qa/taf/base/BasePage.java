package com.qa.taf.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class BasePage extends Page {

	public BasePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String getPageTitle() {
		return getDriver().getTitle();
	}

	@Override
	public String getPageHeader(WebElement element, String elementLabel) {
		waitForElementVisible(element, elementLabel);
		return element.getText();
	}

	@Override
	public void waitForElementVisible(WebElement element, String elementLabel) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception ex) {
			System.out.println("Error occured while wait for an element : " + elementLabel + "\n" + ex);
			Assert.fail();
		}

	}

	@Override
	public void waitForPageTitle(String title) {
		try {
			wait.until(ExpectedConditions.titleContains(title));
		} catch (Exception ex) {
			System.out.println("Error occured while wait for the page title : " + title + "\n" + ex);
			Assert.fail();
		}
	}

	@Override
	public WebElement generateElement(By locator, String locatorLabel) {
		WebElement element = null;
		try {
			element = getDriver().findElement(locator);
		} catch (Exception ex) {
			System.out.println("Error occured while construct of an web element : " + locatorLabel + "\n" + ex);
			Assert.fail();
		}
		return element;
	}

	@Override
	public WebElement generateElement(String locator, String locatorLabel) {
		WebElement element = null;
		try {
			element = getDriver().findElement(By.xpath(locator));
		} catch (Exception ex) {
			System.out.println("Error occured while construct of an web element : " + locatorLabel + "\n" + ex);
			Assert.fail();
		}
		return element;
	}

}