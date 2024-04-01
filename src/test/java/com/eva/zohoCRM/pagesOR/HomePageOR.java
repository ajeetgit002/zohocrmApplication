package com.eva.zohoCRM.pagesOR;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.Getter;
@Getter
public class HomePageOR {
	WebDriver ldriver;
	public HomePageOR(WebDriver rdriver) {
		this.ldriver=rdriver;
		PageFactory.initElements(ldriver,this );
	}
	
	
	
@FindBy(xpath = "//span[@id='show-uName']")
private WebElement accNameINT;


@FindBy(xpath = "//div[@class='lyteMenuItems']/descendant::a[@id='Visible_Accounts']")
private WebElement accLK;



@FindBy(xpath = "//a[@id='Visible_Leads']")
private WebElement leadsLK;
}

