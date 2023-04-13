package com.qa.taf.base;

import com.qa.taf.util.ConstantUtil;
import com.qa.taf.util.DriverType;
import com.qa.taf.util.EnvType;
import com.qa.taf.util.FileReaderUtil;

public class BrowserManager extends FileReaderUtil {

	private static String browser;
	private static String env;

	public static String getBrowser() {
		return browser;
	}

	public static void setBrowser(String browser) {
		BrowserManager.browser = browser;
	}

	public static String getEnv() {
		return env;
	}

	public static void setEnv(String env) {
		BrowserManager.env = env;
	}

	public DriverType getBrowserType() {
		if (System.getenv(ConstantUtil.BROWSER) != null && !System.getenv(ConstantUtil.BROWSER).isEmpty()) {
			setBrowser(System.getenv(ConstantUtil.BROWSER));
		} else {
			setBrowser(getDataFromPropFile(ConstantUtil.BROWSER));
		}
		properties.setProperty(ConstantUtil.BROWSER, getBrowser());
		return switch (getBrowser().toString()) {
		case "Chrome" -> {
			yield DriverType.CHROME;
		}
		case "Firefox" -> {
			yield DriverType.FIREFOX;
		}
		case "Edge" -> {
			yield DriverType.EDGE;
		}
		default -> throw new IllegalArgumentException(
				getBrowser().toString() + " value is not found in the Configuration Property file");
		};
	}

	public EnvType getEnvType() {
		if (System.getenv(ConstantUtil.ENV) != null && !System.getenv(ConstantUtil.ENV).isEmpty()) {
			setEnv(System.getenv(ConstantUtil.ENV));
		} else {
			setEnv(getDataFromPropFile(ConstantUtil.ENV));
		}
		properties.setProperty(ConstantUtil.ENV, getEnv());
		if (getEnv().equalsIgnoreCase("Local"))
			return EnvType.LOCAL;
		else if (getEnv().equalsIgnoreCase("Remote"))
			return EnvType.REMOTE;
		else
			throw new RuntimeException(getEnv() + " value is not found in the Configuration Property file");
	}
}
