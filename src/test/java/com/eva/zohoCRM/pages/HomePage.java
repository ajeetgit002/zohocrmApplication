package com.eva.zohoCRM.pages;

import org.openqa.selenium.WebDriver;

import com.eva.zohoCRM.pagesOR.HomePageOR;
import com.eva.zohoCRM.utilities.ReadConfig;
import com.eva.zohoCRM.utilities.Utils;

public class HomePage extends HomePageOR {
	Utils ut;
	ReadConfig config;

	public HomePage(Utils ut, WebDriver driver) {
		super(driver);
		this.ut = ut;
		config = new ReadConfig();
	}

	public void verifyHemePageHeader() {
		
		ut.verifyText(ut.getText(getAccNameINT()), config.getHomePageHeaderOfACC());

	}

	public void gotoAccount() {
		ut.click(getAccLK());
	}
	
	public void gotoLeads() {
		ut.click(getLeadsLK());
	}

}
