package com.eva.zohoCRM.testCases;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eva.zohoCRM.pages.AccountLandingPage;
import com.eva.zohoCRM.pages.CreateNewAccountPage;
import com.eva.zohoCRM.pages.HomePage;
import com.eva.zohoCRM.pages.LoginPage;
import com.eva.zohoCRM.utilities.Utils;

public class TestCases {
	Utils ut = Utils.getInstance();

	@BeforeClass
	public void creteReports() {
		ut.createExtentReport("ZOHO CRM Application");
	}

	@Test(groups = "@regression")
	public void vcerifyValidLogin() {

		ut.createTestReport("vcerifyValidLogin");
		WebDriver driver = ut.getDriver("firefox");

		LoginPage lo = new LoginPage(ut, driver);
		lo.openUrl();
		lo.clickSinInLK();
		lo.enterEmail();
		lo.clickOnNext();
		lo.enterPassword();
		lo.clickOnSignInButton();

		HomePage home = new HomePage(ut, driver);
		home.verifyHemePageHeader();
		home.gotoAccount();
		AccountLandingPage acclanding = new AccountLandingPage(ut, driver);
		acclanding.clickCreateACCButton();

		CreateNewAccountPage crtnewacc = new CreateNewAccountPage(ut, driver);
		crtnewacc.clickOnACCImage();
		crtnewacc.selectImage();
		crtnewacc.cancelImagesUpload();
		crtnewacc.enterAccountInfo();
		crtnewacc.clickOnSaveButton();

	}

	@AfterMethod
	public void teardown(ITestResult result) {
		String[] groups = result.getMethod().getGroups();

		for (String group : groups) {
			ut.addtags(group);

		}

		ut.tearDown_Quit();
		ut.flushReport();

	}

}
