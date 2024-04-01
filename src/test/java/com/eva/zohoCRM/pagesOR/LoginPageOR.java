package com.eva.zohoCRM.pagesOR;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.Getter;
@Getter
public class LoginPageOR {

	WebDriver ldriver;
	
	public LoginPageOR(WebDriver rdriver) {
		this.ldriver=rdriver;
		PageFactory.initElements(ldriver, this);
	}
	
	@FindBy(xpath = "//div[@class='zgh-localization init']/following-sibling::div//a[text()='SIGN IN']")
	private WebElement clickONSignInLK;
	
	@FindBy(xpath = "//input[@id='login_id']")
	private WebElement emailTB;
	
	@FindBy(xpath = "//button[@id='nextbtn']")
	private WebElement clickNextBT;
	
	@FindBy(xpath = "//input[@id='password']")
	private WebElement  passwordTB;
	
	@FindBy(xpath = "//button[@id='nextbtn']//span[text()='Sign in']")
	private WebElement signInBT;
	
	
}
