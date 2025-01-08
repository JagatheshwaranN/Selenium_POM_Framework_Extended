package com.qa.stf.app.testcases;

import com.qa.stf.app.base.BaseTest;
import com.qa.stf.app.pages.PageManager;
import com.qa.stf.base.DriverManager;
import com.qa.stf.constant.TestConstants;
import com.qa.stf.util.DataSupplier;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;

import static com.qa.stf.app.constant.AppConstants.*;

public class TimePageTest extends BaseTest {

    PageManager pageManager;

    @Test(dataProviderClass = DataSupplier.class, dataProvider = "fetchData")
    public void timePageTest(Hashtable<String, String> data) {

        var classObject = TimePageTest.class;
        Method[] methods = classObject.getMethods();
        if (!DataSupplier.isTestRunnable(methods[0].getName(), DriverManager.excelReader)) {
            throw new SkipException("Skipping the Test - " + StringUtils.capitalize(methods[0].getName())
                    + " as the RunMode is set to N");
        }
        if (data.get("RunMode").equalsIgnoreCase(TestConstants.RUN_MODE_NO)) {
            throw new SkipException("Skipping the TestCase - " + StringUtils.capitalize(methods[0].getName())
                    + " as the RunMode for the Test Data is set to N");
        }
        pageManager = new PageManager();
        Assert.assertEquals(pageManager.getPageComponent().getPageUrl(), LOGIN_PAGE_URL);
        Assert.assertEquals(pageManager.getPageComponent().getPageTitle(), LOGIN_PAGE_TITLE);
        pageManager.getLoginPage().doLogin(data.get("UserName"), data.get("Password"));
        Assert.assertTrue(pageManager.getDashboardPage().verifyDashboardPageHeader());
        pageManager.getDashboardPage().navigateToTimePage();
        Assert.assertTrue(pageManager.getTimePage().verifyTimeSheetPageHeader());
        pageManager.getTimePage().navigateToPunchInOutSection();
        pageManager.getTimePage().addPunchInDetail();
        pageManager.getTimePage().addPunchOutDetail();
        pageManager.getTimePage().navigateToEmployeeRecordsSection();
        String userName = pageManager.getDashboardPage().fetchUserNameFromDropDown();
        Assert.assertTrue(pageManager.getTimePage().searchEmployeeAttendanceRecord(userName));
    }

}