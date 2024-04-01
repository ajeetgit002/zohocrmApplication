package com.eva.zohoCRM.pages;

import org.openqa.selenium.WebDriver;

import com.eva.zohoCRM.pagesOR.CreateNewAccountPageOR;
import com.eva.zohoCRM.utilities.Utils;

public class CreateNewAccountPage extends CreateNewAccountPageOR {

	Utils ut;

	public CreateNewAccountPage(Utils ut, WebDriver driver) {
		super(driver);
		this.ut = ut;
	}

	public void clickOnACCImage() {
		ut.click(getUploadIMG());
	}

	public void selectImage() {
		ut.inputValue(getSendIMG(), "C:\\Users\\Ajit\\Documents\\WhatsApp Image 2024-03-30 at 12.17.40_6fd25221.jpg");
	}

	
	public void cancelImagesUpload() {
		ut.click(getCancelImageBT());
	}
	public void enterAccountInfo() {
		ut.inputValue(getAccNameTB(), "ShivaShin Yadav");
		ut.inputValue(getAccSiteTB(), "www.eva.com");

		ut.click(getRatingDD());
		ut.click(getRatingvalueDD());
		ut.inputValue(getPhoneTB(), "8556498585");
		ut.inputValue(getFoxTB(), "1223-3433-4444");
		ut.inputValue(getWebsiteTB(), "www.mocrosoft.com");
		ut.jsScrollVertically(200);
		ut.jsInputValueMethod(getAccNumberTB(), "56353332", "Account number");

		ut.click(getAccTypeDD());

		ut.jsScrollVertically(200);

		ut.click(getSelectvalueDD());

		ut.jsScrollVertically(200);

		ut.click(getIndustryDD());
		ut.click(getSelectindustryvalueDD());

		ut.jsScrollVertically(400);
		ut.inputValue(getDescriptionTB(), "for sell item that is reason");

	}

	public void clickOnSaveButton() {
		ut.click(getSaveBT());
	}

}
