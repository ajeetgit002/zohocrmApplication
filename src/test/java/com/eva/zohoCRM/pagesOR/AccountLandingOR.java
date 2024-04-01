package com.eva.zohoCRM.pagesOR;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.Getter;
@Getter
public class AccountLandingOR {
WebDriver driver;
	
	public AccountLandingOR(WebDriver rdriver) {
		this.driver=rdriver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath ="//button[@cscript-id='system_button']" )
	private WebElement creatnewaccBT;
	
	
}
