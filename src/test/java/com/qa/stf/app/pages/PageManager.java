package com.qa.stf.app.pages;

import com.qa.stf.base.BasePage;
import com.qa.stf.base.DriverManager;
import com.qa.stf.handler.*;
import com.qa.stf.util.EncryptionManager;

public class PageManager {

	private LoginPage loginPage;

	private DashboardPage dashboardPage;

	private AdminPage adminPage;

	private TimesheetPage timesheetPage;

	private BasePage basePage;

	private VerificationHandler verificationHelper;

	private DropDownHandler dropDownHandler;

	private DateTimeHandler datePickerHandler;

	private InteractionHandler interactionHandler;

	private JavaScriptHandler javaScriptHandler;

	private WaitHandler waitHandler;

	private EncryptionManager encryptionManager;

	public BasePage getPageComponent() {
		return (basePage == null) ? basePage = new BasePage(DriverManager.getInstance()) : basePage;
	}

	public LoginPage getLoginPage() {
		return (loginPage == null) ? loginPage = new LoginPage(DriverManager.getInstance()) : loginPage;
	}

	public DashboardPage getDashboardPage() {
		return (dashboardPage == null) ? dashboardPage = new DashboardPage(DriverManager.getInstance()) : dashboardPage;
	}

	public AdminPage getAdminPage() {
		return (adminPage == null) ? adminPage = new AdminPage(DriverManager.getInstance()) : adminPage;
	}

	public TimesheetPage getTimePage() {
		return (timesheetPage == null) ? timesheetPage = new TimesheetPage(DriverManager.getInstance()) : timesheetPage;
	}

	public VerificationHandler getVerificationHelper() {
		return (verificationHelper == null) ? verificationHelper = new VerificationHandler() : verificationHelper;
	}

	public DropDownHandler getDropDownHandler() {
		return (dropDownHandler == null) ? dropDownHandler = new DropDownHandler(new VerificationHandler()) : dropDownHandler;
	}

	public DateTimeHandler getDatePickerHandler() {
		return (datePickerHandler == null) ? datePickerHandler = new DateTimeHandler(DriverManager.getInstance(), new VerificationHandler()) : datePickerHandler;
	}

	public InteractionHandler getInteractionHandler() {
		return (interactionHandler == null) ? interactionHandler = new InteractionHandler(DriverManager.getInstance(), new VerificationHandler()) : interactionHandler;
	}

	public JavaScriptHandler getJavaScriptHandler() {
		return (javaScriptHandler == null) ? javaScriptHandler = new JavaScriptHandler(DriverManager.getInstance(), new VerificationHandler()) : javaScriptHandler;
	}

	public WaitHandler getWaitHandler() {
		return (waitHandler == null) ? waitHandler = new WaitHandler(DriverManager.getInstance()) : waitHandler;
	}

	public EncryptionManager getEncryptionManager() {
		return (encryptionManager == null) ? encryptionManager = new EncryptionManager() : encryptionManager;
	}

}

