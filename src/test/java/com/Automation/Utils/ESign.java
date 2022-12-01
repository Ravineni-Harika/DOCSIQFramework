package com.Automation.Utils;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Base.ActionEngine;


public class ESign extends ActionEngine{
	@FindBy(id = "txtESignPassword")
	WebElement Esign;

	@FindBy(id = "Submit_Esign")
	WebElement EsignProceed;
	
	public ESign() {
		PageFactory.initElements(driver, this);
	}


	public   void e_Sign(String eSign) {
		sendText(Esign, eSign, "Esign");
		click(EsignProceed, "Proceedbtn");

	}
}