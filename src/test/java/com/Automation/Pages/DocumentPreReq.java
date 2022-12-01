package com.Automation.Pages;

import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.Automation.Base.ActionEngine;
import com.Automation.Utils.ConfigsReader;
import com.Automation.Utils.TextUtils;
import com.Automation.Utils.TimeUtil;

public class DocumentPreReq extends ActionEngine{
	Properties prop;
	public static String DocPreReq = "";

	public static String getDocPreReq() {
		return DocPreReq;
	}

	public static void setDocPreReq(String preReq) {
		DocPreReq = preReq;
	}
	@FindBy(xpath = "//a[@class='caliber-product-dms product-icon1']")
	WebElement docsicon;
	@FindBy(xpath = "//body/div[2]/ul[1]/li[1]/ul[1]/li[2]/a[1]")
	WebElement documentmanagermenu;
//	//ul[@id='MenuModule_DMS']//li[2]//ul[@id='DMS_Document Manager']//li[2]//a[text()='Document Manager']
	@FindBy(xpath = "//ul[@id='DMS_Document Manager']//a[text()='Transactions']")
	WebElement TransactionMenu;
	@FindBy(id = "DMS_Document Manager_Transactions_MENU0001")
	WebElement DPRmenu;
	@FindBy(xpath = "//input[@id='closeGlobalSearchtxt']")
	WebElement Searchbox;
	@FindBy(xpath = "//table[@id='ListTab']/tbody/tr[@class='click-row odd']/td[1]")
	WebElement MainDocType;
	@FindBy(xpath = "//button[@id='btnModal_PreparationRequest_Repository']") 
	WebElement RepositoryBTN;
	@FindBy(xpath = "//table[@id='ListTab']/tbody/tr[@class='repcls odd']/td[1]")
	WebElement Repository;
	@FindBy(xpath = "//tbody/tr[1]/td[1]/input[@class='radioCls margin-right-10 caliber-labeled-option']")
	WebElement sectionid;
	@FindBy(id = "PreparationRequest_Repository_Section_selectBtn")
	WebElement Addbtn;
	@FindBy(id = "PreparationRequest_DocumentCode")
	WebElement DocUcode;
	@FindBy(id = "PreparationRequest_DocumentDesc")
	WebElement DocumentDescption;
	@FindBy(id = "PreparationRequest_CcNo")
	WebElement CCNO;
	@FindBy(id = "btnConfirm_1")
	WebElement Submitbutton1;
	@FindBy(xpath = "//input[@id='btnConfirm']")
	WebElement Submitbtn;
	

	
	public void Document_preparation_Registration(String DPR,String Description,String CCnumber)
	{
		
		String s = "";
		prop = ConfigsReader.readProperties("./configs/configuration.properties");
		if (prop.getProperty("EnableAppendRandomValue").equalsIgnoreCase("YES")) {
			s = TextUtils.randomvalue(5);
			System.out.println("Generated S Value is: " + s);
		}
		setDocPreReq(DPR = "DPR Performance testing" + s);
		click(docsicon, "Icon");
//		sendText(Searchbox, "docs-iq -> Document Manager -> Transactions -> Document Preparation Request", "Search box");
		jsClick(documentmanagermenu, "Doc Managermenu");
		click(TransactionMenu, "TransactionMenu");
		click(DPRmenu, "DPRmenu");
		switchToBodyFrame(driver);
		click(MainDocType, "Main Doc Type");
		click(RepositoryBTN, "Add Item Click");
		TimeUtil.mediumWait();
		click(Repository, "Repository");
		SelectRadioBtnAndCheckbox(driver, sectionid);
		click(Addbtn, "Add Btn Click");
		TimeUtil.shortWait();
		sendText(DocUcode,DPR, "DPR Performance testing");
		sendText(DocumentDescption,Description, "Description");
		sendText(CCNO, CCnumber, "CCNO");
		click(Submitbtn, "SUBMIT");
		switchToDefaultContent(driver);
		
		}
		
	}
	

