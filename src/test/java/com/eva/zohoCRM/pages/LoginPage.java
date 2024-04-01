package com.eva.zohoCRM.pages;

import org.openqa.selenium.WebDriver;

import com.eva.zohoCRM.pagesOR.LoginPageOR;
import com.eva.zohoCRM.utilities.ReadConfig;
import com.eva.zohoCRM.utilities.Utils;

public class LoginPage extends LoginPageOR {
Utils ut;
ReadConfig config;
	public LoginPage(Utils ut,WebDriver driver) {
		super(driver);
		
		this.ut=ut;
		config=new ReadConfig();
	}
	
	public void openUrl() {
		ut.implicityWaitSecond(20);
		ut.maximize();
		ut.openUrl(config.getUrl());
		
	}
	
	public void clickSinInLK() {
		ut.click(getClickONSignInLK());
	}

	public void enterEmail() {
		ut.inputValue(getEmailTB(), config.getEmailOrUsername());
	}
	
	public void clickOnNext() {
		ut.click(getClickNextBT());
	}
	public void enterPassword() {
		ut.inputValue(getPasswordTB(), config.getPassword());
	}
	
	public void clickOnSignInButton() {
		ut.click(getSignInBT());
	}
	
}
