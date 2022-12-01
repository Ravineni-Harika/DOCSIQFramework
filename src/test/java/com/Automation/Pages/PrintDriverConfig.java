package com.Automation.Pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.Automation.Base.ActionEngine;
import com.Automation.Utils.TimeUtil;

public class PrintDriverConfig extends ActionEngine {
	@FindBy(xpath = "//a[@class='caliber-product-dms product-icon1']")
	WebElement Docsiqicon;

	@FindBy(xpath = "//*[@id=\"MenuModule_DMS\"]/li[3]/a")
	WebElement Configuration;

	@FindBy(xpath = "//*[@id=\"DMS_Configuration\"]/li[2]/a")
	WebElement initiate;

	@FindBy(xpath = "//a[@id='DMS_Configuration_Initiate_MENU0131']")
	WebElement Printdriver;

	@FindBy(xpath = "//input[@id='PrintDriver_DriverName']")
	WebElement Drivernametextbox;

	@FindBy(xpath = "//*[@id=\"btnModal_PrintDriver\"]/i")
	WebElement printdriveraddbutton;

	@FindBy(xpath = "//td[@id='DriverNameTd_1']/input[@class='margin-right-10 caliber-labeled-option']")
	WebElement PrinterNameselect;

	@FindBy(xpath = "//div[@class='sub-page-layout-footer-actions']/button[@id='PrintDriver_selectBtn']")
	WebElement PrinterNameaddbutton;

	@FindBy(xpath = "//input[@id='PrintDriver_DriverDesc']")
	WebElement PrinterNametextbox;

	// @FindBy(xpath = "//*[@id=\"btnModal_PrintDriver_UserGrp\"]/span")
	// WebElement UsergroupAddButton;

	// @FindBy(xpath = "//input[@class='radioCls margin-right-10
	// caliber-labeled-option']")
	// WebElement Usergroupselect;

	// @FindBy(xpath = "//button[@id='PrintDriver_UserGrp_selectBtn']")
	// WebElement AddButton;

	@FindBy(xpath = "//*[@id=\"btnModal_PrintDriver_PrintType\"]/span")
	WebElement Printtype;

	@FindBy(xpath = "//button[@class='PrintDriver_PrintTypeAddRemoveBtn btn btn-sm  notselected']")
	WebElement controlledcopy;

	@FindBy(xpath = "//tbody/tr[2]/td[2]/button[1]")
	WebElement uncontrolledcopy;

	@FindBy(xpath = "//button[@id='PrintDriver_PrintType_selectBtn']")
	WebElement printtypesAddButton;

	@FindBy(xpath = "//input[@id='TrailPrintBtn']")
	WebElement trailprint;

	@FindBy(xpath = "//button[@id='btnPrint']")
	WebElement print;

	@FindBy(xpath = "//div[@class='sa-confirm-button-container']/button[@id='OkBtn']")
	WebElement okbutton;

	@FindBy(xpath = "//*[@id=\"TrailPrintModal\"]/div/div/div[1]/span")
	WebElement closebutton;

	@FindBy(xpath = "//button[@id='btnSubmit_1']")
	WebElement Submitbutton;

	@FindBy(xpath = "//button[@class='toast-close-button']")
	WebElement button;

	public void Printdriver1(String Drivername,String Docpath) throws AWTException {

		click(button, "button");
		click(Docsiqicon, "Docsiqicon");

		click(Configuration, "Configuration");

		click(initiate, "initiate");

		click(Printdriver, "Printdriver");

		switchToBodyFrame(driver);

		sendText(Drivernametextbox, Drivername, "Drivername");

		click(printdriveraddbutton, "printdriveraddbutton");

		click(PrinterNameselect, "PrinterNameselect");

		click(PrinterNameaddbutton, "PrinterNameaddbutton");

		click(Printtype, "Printtype");

		click(controlledcopy, "controlledcopy");

		click(uncontrolledcopy, "uncontrolledcopy");

		click(printtypesAddButton, "printtypesAddButton");

		click(trailprint, "trailprint");
		TimeUtil.shortWait();
		driver.switchTo().frame(0);
		jsClick(print, "print");
		TimeUtil.mediumWait();
		click(okbutton, "okbutton");
		TimeUtil.shortWait();
		click(closebutton, "closebutton");
		switchToDefaultContent(driver);
		switchToBodyFrame(driver);
		TimeUtil.shortWait();
		Actions a = new Actions(driver); // scroll up a page
		a.sendKeys(Keys.PAGE_UP).build().perform();
		jsClick(Submitbutton, "Submitbutton");

	}

}
