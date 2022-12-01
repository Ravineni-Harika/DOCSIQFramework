package com.Automation.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.Automation.Base.ActionEngine;
import com.Automation.Utils.TimeUtil;

public class Docs_Loginpage extends ActionEngine {

	@FindBy(id = "company")
	WebElement companytextbox;

	@FindBy(id = "username")
	WebElement usernametextbox;

	@FindBy(id = "password")
	WebElement passwordtextbox;

	@FindBy(id = "btnSubmit")
	WebElement loginbtn;

	@FindBy(id = "Logout")
	WebElement logoutbtn;
	
	
	@FindBy(xpath= "//span[@title='Master Plant ( 320M )']")
	WebElement MasterPlant;
	

	@FindBy(xpath= "//span[@title='EPIQ320_P1 ( P320 )']")
	WebElement learniqplant;
	
	@FindBy(xpath = "//span[@title='CTPLHYD PLT3 ( CBR3 )']")
	WebElement docsiqplant;
	
	@FindBy(xpath = "//span[@title='PerT-PLT1 ( PerT )']")
	WebElement Performanceplant;

	public void docsiqlogin(String company, String username, String password) {

		sendText(companytextbox, company, "companytextbox");

		sendText(usernametextbox, username, "usernametextbox");

		sendText(passwordtextbox, password, "passwordtextbox");

		click(loginbtn, "loginbtn");

//		try {
//			if (driver.findElement(By.xpath("//*[@id='btnYes']")).isDisplayed()) {
//				driver.findElement(By.xpath("//*[@id='btnYes']")).click();
//			}
//		} catch (Exception e) {
//		}

	}
	public void Masterplantselection() {

		click(MasterPlant, "MasterPlantselection");

	}


	public void learniqplantselection() {

		click(learniqplant, "learniqselection");

	}

	public void dociqplantselection() {
		

		click(docsiqplant, "docsiqselection");
	}

	public void performanceplantselection() {
		TimeUtil.shortWait();

		click(Performanceplant, "Plant");
	}

	public void docsiqlogout() {

		switchToDefaultContent(driver);
		click(logoutbtn, "logoutbtn");

	}

}
