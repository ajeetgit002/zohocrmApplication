package com.eva.zohoCRM.pages;

import org.openqa.selenium.WebDriver;

import com.eva.zohoCRM.pagesOR.AccountLandingOR;
import com.eva.zohoCRM.utilities.Utils;

public class AccountLandingPage extends AccountLandingOR {
	Utils ut;

	public AccountLandingPage(Utils ut, WebDriver driver) {
		super(driver);
		this.ut = ut;
	}

	public void clickCreateACCButton() {
		ut.click(getCreatnewaccBT());
	}

}
