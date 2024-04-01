package com.eva.zohoCRM.pagesOR;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.Getter;

@Getter
public class CreateNewAccountPageOR {

	WebDriver driver;

	public CreateNewAccountPageOR(WebDriver rdriver) {
		this.driver = rdriver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//img[@id='Accounts_photo']")
	private WebElement uploadIMG;
	
	@FindBy(xpath = "//input[@id='upload_photo']")
	private WebElement sendIMG;
	
	@FindBy(xpath = "//span[@class='crmutilIconBg w28 h28 pA right20']//crmutil-icon[@icon-name='close-9']")
	private WebElement cancelImageBT;

	@FindBy(xpath = "//input[@id='Crm_Accounts_ACCOUNTNAME_LInput']")
	private WebElement accNameTB;

	@FindBy(xpath = "//input[@tabindex='9']")
	private WebElement accSiteTB;

	@FindBy(xpath = "//input[@id='Crm_Accounts_ACCOUNTNUMBER_LInput']")
	private WebElement accNumberTB;

	@FindBy(xpath = "//lyte-dropdown[@id='Crm_Accounts_ACCOUNTTYPE']")
	private WebElement accTypeDD;

	@FindBy(xpath = "//lyte-drop-item[text()='Competitor']")
	private WebElement selectvalueDD;

	@FindBy(xpath = "//lyte-dropdown[@id='Crm_Accounts_INDUSTRY']")
	private WebElement industryDD;
	@FindBy(xpath = "//lyte-drop-item[text()='Education']")
	private WebElement selectindustryvalueDD;

	@FindBy(xpath = "//lyte-input[@id='Crm_Accounts_ANNUALREVENUE']")
	private WebElement annualRevenueTB;

	@FindBy(xpath = "//lyte-dropdown[@id='Crm_Accounts_RATING']")
	private WebElement ratingDD;

	@FindBy(xpath = "//lyte-drop-item[text()='Active']")
	private WebElement ratingvalueDD;

	@FindBy(xpath = "//input[@id='Crm_Accounts_PHONE_LInput']")
	private WebElement phoneTB;

	@FindBy(xpath = "//input[@id='Crm_Accounts_FAX_LInput']")
	private WebElement foxTB;

	@FindBy(xpath = "//input[@id='Crm_Accounts_WEBSITE_LInput']")
	private WebElement websiteTB;

	@FindBy(xpath = "//lyte-input[@id='cruxTextArea']//textarea")
	private WebElement descriptionTB;

	@FindBy(xpath = "//div[text()='Description']")
	private WebElement descritionText;

	// Save Button

	@FindBy(xpath = "//button[@id='crm_create_savebutn']")
	private WebElement saveBT;

}
