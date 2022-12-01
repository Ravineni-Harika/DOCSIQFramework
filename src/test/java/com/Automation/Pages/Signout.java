package com.Automation.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Base.ActionEngine;
import com.Automation.Utils.TimeUtil;

public class Signout extends ActionEngine {

	@FindBy(xpath = "//a[@href='#']//i[@class='ft-power font-red font-size-20']")
	WebElement SignoutBtn;
	@FindBy(xpath = "//a[@id='logoutBtn']")
	WebElement Signout;

	public Signout() {
		PageFactory.initElements(driver, this);
	}

	public void signOutPage() {

		switchToDefaultContent(driver);
		jsClick(SignoutBtn, "SignOut Button");
		TimeUtil.shortWait();

	}
	public void SSOsignOutPage() {

		switchToDefaultContent(driver);
		jsClick(Signout, "SignOut Button");
		TimeUtil.shortWait();

	}

}
