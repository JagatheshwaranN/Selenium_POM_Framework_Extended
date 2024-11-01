package com.qa.taf.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.qa.taf.base.BasePage;

/**
 * 
 * @author Jaga
 *
 */
public class MouseActionHelper extends BasePage {

	private static Logger log = LogManager.getFormatterLogger(MouseActionHelper.class);

	public void mouseHover(WebElement element1, WebElement element2, String elementLabel) {
		var builder = new Actions(driverManager.getDriver());
		builder.moveToElement(element1).build().perform();
		element2.click();
		log.info("The mouse hovered and clicked on the " + "'" + elementLabel + "'" + " element");
	}
}

