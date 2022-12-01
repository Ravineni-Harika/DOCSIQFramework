package com.Automation.Pages;

import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.Automation.Base.ActionEngine;
import com.Automation.Utils.ConfigsReader;
import com.Automation.Utils.TextUtils;
import com.Automation.Utils.TimeUtil;

public class MytaskDocTask extends ActionEngine {
	Properties prop;
	@FindBy(xpath = "//a[@class='caliber-product-dms product-icon1']")
	WebElement docsicon;
	@FindBy(xpath = "//body/div[2]/ul[1]/li[1]/ul[1]/li[2]/a[1]")
	WebElement documentmanagermenu;
	@FindBy(xpath = "//ul[@id='DMS_Document Manager']//a[text()='Transactions']")
	WebElement TransactionMenu;
	@FindBy(id = "DMS_Document Manager_Transactions_MENU0041")
	WebElement MytaskMenu;
	@FindBy(xpath = "//span[contains(text(),'Document Tasks')]")
	WebElement MyDocumenttaskMenu;
	@FindBy(xpath = "//span[@id='btnAdvSearch']")
	WebElement Btnsearch;
	@FindBy(xpath = "//span[@class='select2-selection__arrow']")
	WebElement Selectbtn;
	@FindBy(xpath = "//input[@class='select2-search__field']")
	WebElement SearchField;
	@FindBy(xpath = "//ul[@id='select2-SearchType-results']/li[1]")
	WebElement Selectoption;
	@FindBy(id = "SrhUniqueCode")
	WebElement Unicode;
	@FindBy(id = "displayBtn")
	WebElement Applybtn;
	@FindBy(xpath = "//table[@id='ListTab']/tbody/tr[1]/td[1]")
	WebElement DisplayedRecord;
	@FindBy(xpath = "//table[@id='ListTab']/tbody/tr[2]/td[1]")
	WebElement DisplayedRecord2;
	@FindBy(xpath = "//table[@id='ListTab']/tbody/tr[3]/td[1]")
	WebElement DisplayedRecord3;
	@FindBy(xpath = "//table[@id='ListTab']/tbody/tr[1]/td[1]")
	WebElement DisplayedUnicodeRecord;
	@FindBy(xpath = "//table[@id='SubDocListTab']/tbody[1]/tr[1]/td[2]")
	WebElement Anexxure;
	@FindBy(xpath = "//textarea[@id='Preparation_RevisionChangeDetails']")
	WebElement RevisionDetails;
	@FindBy(xpath = "//button[@id='btnModal_Preparation_ToUserToGroup_0']")
	WebElement ApproverAdditem;
	@FindBy(xpath = "//input[@id='Preparation_ToUserToGroup_0WorkType_A1_1']")
	WebElement AssigntoGroup;
	@FindBy(xpath = "//button[@id='Preparation_ToUserToGroup_0_selectBtn']")
	WebElement Addbtn;
	@FindBy(xpath = "//div[@class='file-select']/label[@class='file-select-button']")
	WebElement ExternalDocument;
	@FindBy(id = "btnSubmitTmp")
	WebElement submit;
	@FindBy(id = "txtESignPassword")
	WebElement Password;
	@FindBy(id = "Submit_Esign")
	WebElement SubmitEsign;
	@FindBy(id = "PreparationRequest_DocumentCode")
	WebElement Documentcode;
	@FindBy(id = "PreparationRequest_DocumentDesc")
	WebElement DocumentDesc;
	@FindBy(xpath = "//button[@id='btnModal_PreparationRequest_Repository']")
	WebElement Repository;
	@FindBy(xpath = "//table[@id='ListTab']/tbody/tr[1]/td[1]")
	WebElement Repositorybtn;
	@FindBy(xpath = "//div[@id='modalBody_PreparationRequest_Repository_Section']//tbody/tr[1]/td[1]/input[1]")
	WebElement Section;
	@FindBy(xpath = "//button[@id='PreparationRequest_Repository_Section_selectBtn']")
	WebElement SectionAddbtn;
	@FindBy(xpath = "//button[@id='btnModal_PreparationRequest_FileAttach']")
	WebElement FileAttach;
	@FindBy(xpath = "//table[@id='multipopupfilter2']/tbody/tr[1]/td[3]/button[@class='PreparationRequest_FileAttachAddRemoveBtn btn btn-sm  notselected']")
	WebElement Docuploader;
	@FindBy(xpath = "//button[@id='PreparationRequest_FileAttach_selectBtn']")
	WebElement DocAdditem;
	@FindBy(id = "btnConfirm")
	WebElement SubmitDocload;
	@FindBy(xpath = "//button[@id='btnModal_DocumentPublish_SelRepDom']")
	WebElement Distrubution;
	@FindBy(xpath = "//button[@class='DocumentPublish_SelRepDomAddRemoveBtn btn btn-sm  notselected']")
	WebElement Departmentadd;
	@FindBy(xpath = "//button[@id='DocumentPublish_SelRepDom_selectBtn']")
	WebElement Additem;
	@FindBy(xpath = "//input[@id='DocumentPublish_Prtremarks']")
	WebElement printreason;
	@FindBy(xpath = "//input[@id='btnConfirm']")
	WebElement Submitbtn;
	@FindBy(xpath = "//input[@id='txtESignPassword']")
	WebElement Passwordtxt;
	@FindBy(id = "SelectedDecision_2")
	WebElement Approvebtn;
	@FindBy(id = "Remarks")
	WebElement Remarks;
	@FindBy(id = "btnSubmit")
	WebElement Submit;
	@FindBy(xpath = "//input[@id='btnSubmitTmp']")
	WebElement Submitanx;
	

//	Main Document
	public void MytaskDocumentTask(String Unicodeval, String ChangeDetails, String Docpath, String Passwordval,
			String Documentcodeval, String DocumentDescval) {

		click(docsicon, "Icon");
//		sendText(Searchbox, "docs-iq -> Document Manager -> Transactions -> Document Preparation Request", "Search box");
		jsClick(documentmanagermenu, "Doc Managermenu");
		click(TransactionMenu, "TransactionMenu");
		click(MytaskMenu, "MytaskMenu");
		switchToBodyFrame(driver);
		click(MyDocumenttaskMenu, "MyDocumenttaskMenu");
		click(Btnsearch, "Btnsearch");
		click(Selectbtn, "Selectbtn");
		sendText(SearchField, "Doc. unique Code", "Doc. unique Code val");
		click(Selectoption, "Selectoption");
		sendText(Unicode, Unicodeval, "Doc. unique Code val");
		click(Applybtn, "Apply btn");
		click(DisplayedRecord, "Displayed Record");
		TimeUtil.mediumWait();
		click(DisplayedUnicodeRecord, "Displayed Record");
		TimeUtil.shortWait();
		sendText(RevisionDetails, ChangeDetails, "ChangeDetails val");
		click(ApproverAdditem, "Approver Additem");
		SelectRadioBtnAndCheckbox(driver, AssigntoGroup);
		click(Addbtn, "Add btn");
		click(ExternalDocument, "Add btn");
		TimeUtil.shortWait();
		uploadDocument(Docpath);
		click(submit, "submit btn");
		sendText(Password, Passwordval, "Passwordtxt val");
		click(SubmitEsign, "submit Esign");
	}

//Associate document Preparation Request
	public void MytaskDocumentTaskAnnexure(String Unicodeval, String ChangeDetails, String Docpath, String Passwordval,
			String Documentcodeval, String DocumentDescval) {

		String s = "";
		String s1 = "";
		prop = ConfigsReader.readProperties("./configs/configuration.properties");
		if (prop.getProperty("EnableAppendRandomValue").equalsIgnoreCase("YES")) {
			s = TextUtils.randomvalue(3);
			s1 = TextUtils.randomAlphaNumeric(2);

			System.out.println("Generated S Value is: " + s);
		}
		String DocReqVal = DocumentPreReq.getDocPreReq();
		click(docsicon, "Icon");
//		sendText(Searchbox, "docs-iq -> Document Manager -> Transactions -> Document Preparation Request", "Search box");
		jsClick(documentmanagermenu, "Doc Managermenu");
		click(TransactionMenu, "TransactionMenu");
		click(MytaskMenu, "MytaskMenu");
		switchToBodyFrame(driver);
		click(MyDocumenttaskMenu, "MyDocumenttaskMenu");
		click(Btnsearch, "Btnsearch");
		click(Selectbtn, "Selectbtn");
		sendText(SearchField, "Doc. unique Code", "Doc. unique Code val");
		click(Selectoption, "Selectoption");
		sendText(Unicode, DocReqVal+"-000", "Doc. unique Code val");
		click(Applybtn, "Apply btn");
		click(DisplayedRecord, "Displayed Record");
		TimeUtil.mediumWait();
		click(DisplayedUnicodeRecord, "Displayed Record");
		TimeUtil.shortWait();
		sendText(RevisionDetails, ChangeDetails, "ChangeDetails val");
		click(ApproverAdditem, "Approver Additem");
		SelectRadioBtnAndCheckbox(driver, AssigntoGroup);
		click(Addbtn, "Add btn");
		click(ExternalDocument, "Add btn");
		TimeUtil.shortWait();
		uploadDocument(Docpath);
		click(submit, "submit btn");
		sendText(Password, Passwordval, "Passwordtxt val");
		click(SubmitEsign, "submit Esign");
		TimeUtil.shortWait();
		click(Anexxure, "Anexxure btn");
		TimeUtil.shortWait();
		sendText(Documentcode, Documentcodeval + s1, "Document Code val");
		sendText(DocumentDesc, DocumentDescval + s1, "DocumentDesc Code val");
		click(Repository, "Repository btn");
		click(Repositorybtn, "Repository btn");
		SelectRadioBtnAndCheckbox(driver, Section);
		click(SectionAddbtn, "FileAttach btn");
		click(FileAttach, "FileAttach btn");
		TimeUtil.shortWait();
		click(Docuploader, "Docuploader btn");
		click(DocAdditem, "DocAdditem btn");
		click(SubmitDocload, "SubmitDocload btn");
		click(DisplayedRecord2, "Displayed Record");
		TimeUtil.shortWait();
		sendText(RevisionDetails, ChangeDetails, "ChangeDetails val");
		click(ExternalDocument, "Add btn");
		TimeUtil.shortWait();
		uploadDocument(Docpath);
		click(submit, "submit btn");
		sendText(Password, Passwordval, "Passwordtxt val");
		click(SubmitEsign, "submit Esign");
		click(Anexxure, "Anexxure btn");
		TimeUtil.shortWait();
		sendText(Documentcode, "Doccodeval" + s, "Document Code val");
		sendText(DocumentDesc, "DocDescval" + s, "DocumentDesc Code val");
		click(Repository, "Repository btn");
		click(Repositorybtn, "Repository btn");
		SelectRadioBtnAndCheckbox(driver, Section);
		click(SectionAddbtn, "FileAttach btn");
		click(FileAttach, "FileAttach btn");
		TimeUtil.shortWait();
		click(Docuploader, "Docuploader btn");
		click(DocAdditem, "DocAdditem btn");
		click(SubmitDocload, "SubmitDocload btn");
		click(DisplayedRecord3, "Displayed Record");
		TimeUtil.shortWait();
		sendText(RevisionDetails, ChangeDetails, "ChangeDetails val");
		click(ExternalDocument, "Add btn");
		TimeUtil.shortWait();
		uploadDocument(Docpath);
		click(submit, "submit btn");
		sendText(Password, Passwordval, "Passwordtxt val");
		click(SubmitEsign, "submit Esign");
		TimeUtil.shortWait();
		click(Submitanx, "submit Esign");
		click(Submitanx, "submit Esign");
		sendText(Password, Passwordval, "Passwordtxt val");
		click(SubmitEsign, "submit Esign");
	}

//Approve Document Preparation
	public void DocumentPreparationApproval(String Unicodeval, String Passwordval) {
		String DocReqVal = DocumentPreReq.getDocPreReq();
		click(docsicon, "Icon");
//		sendText(Searchbox, "docs-iq -> Document Manager -> Transactions -> Document Preparation Request", "Search box");
		jsClick(documentmanagermenu, "Doc Managermenu");
		click(TransactionMenu, "TransactionMenu");
		click(MytaskMenu, "MytaskMenu");
		switchToBodyFrame(driver);
		click(MyDocumenttaskMenu, "MyDocumenttaskMenu");
		click(Btnsearch, "Btnsearch");
		click(Selectbtn, "Selectbtn");
		sendText(SearchField, "Doc. unique Code", "Doc. unique Code val");
		click(Selectoption, "Selectoption");
		sendText(Unicode, DocReqVal+"-000", "Doc. unique Code val");
		click(Applybtn, "Apply btn");
		click(DisplayedRecord, "Displayed Record");
		TimeUtil.mediumWait();
		click(DisplayedUnicodeRecord, "Displayed Record");
		TimeUtil.shortWait();
		SelectRadioBtnAndCheckbox(driver, Approvebtn);
		sendText(Remarks, "Approved", "Remarks val");
		click(Submit, "Submit btn");
		sendText(Password, Passwordval, "Passwordtxt val");
		click(SubmitEsign, "submit Esign");
	}

//	Annexure Document Preparation 
	public void MytaskDocumentTask2Annexure(String Unicodeval, String ChangeDetails, String Docpath, String Passwordval,
			String Documentcodeval, String DocumentDescval) {
		String DocReqVal = DocumentPreReq.getDocPreReq();
		click(docsicon, "Icon");
//		sendText(Searchbox, "docs-iq -> Document Manager -> Transactions -> Document Preparation Request", "Search box");
		jsClick(documentmanagermenu, "Doc Managermenu");
		click(TransactionMenu, "TransactionMenu");
		click(MytaskMenu, "MytaskMenu");
		switchToBodyFrame(driver);
		click(MyDocumenttaskMenu, "MyDocumenttaskMenu");
		click(Btnsearch, "Btnsearch");
		click(Selectbtn, "Selectbtn");
		sendText(SearchField, "Doc. unique Code", "Doc. unique Code val");
		click(Selectoption, "Selectoption");
		sendText(Unicode, DocReqVal+"-000", "Doc. unique Code val");
		click(Applybtn, "Apply btn");
		click(DisplayedRecord, "Displayed Record");
		TimeUtil.mediumWait();
		click(DisplayedUnicodeRecord, "Displayed Record");
		TimeUtil.shortWait();
		sendText(RevisionDetails, ChangeDetails, "ChangeDetails val");
		click(ApproverAdditem, "Approver Additem");
		SelectRadioBtnAndCheckbox(driver, AssigntoGroup);
		click(Addbtn, "Add btn");
		click(ExternalDocument, "Add btn");
		TimeUtil.shortWait();
		uploadDocument(Docpath);
		click(submit, "submit btn");
		sendText(Password, Passwordval, "Passwordtxt val");
		click(SubmitEsign, "submit Esign");
		TimeUtil.shortWait();
		click(Anexxure, "Anexxure btn");
		TimeUtil.shortWait();
		sendText(Documentcode, Documentcodeval, "Document Code val");
		sendText(DocumentDesc, DocumentDescval, "DocumentDesc Code val");
		click(Repository, "Repository btn");
		click(Repositorybtn, "Repository btn");
		SelectRadioBtnAndCheckbox(driver, Section);
		click(SectionAddbtn, "FileAttach btn");
		click(FileAttach, "FileAttach btn");
		TimeUtil.shortWait();
		click(Docuploader, "Docuploader btn");
		click(DocAdditem, "DocAdditem btn");
		click(SubmitDocload, "SubmitDocload btn");
		TimeUtil.shortWait();
		click(DisplayedRecord2, "Displayed Record");
		TimeUtil.shortWait();
		sendText(RevisionDetails, ChangeDetails, "ChangeDetails val");
		click(ApproverAdditem, "Approver Additem");
		SelectRadioBtnAndCheckbox(driver, AssigntoGroup);
		click(Addbtn, "Add btn");
		click(ExternalDocument, "Add btn");
		TimeUtil.shortWait();
		uploadDocument(Docpath);
		click(submit, "submit btn");
		sendText(Password, Passwordval, "Passwordtxt val");
		click(SubmitEsign, "submit Esign");

	}

	// Document Publish
	public void DocumentPublish(String Unicodeval, String ChangeDetails, String Docpath, String Passwordval,
			String Documentcodeval, String DocumentDescval, String printreasonval, String password) {
		String DocReqVal = DocumentPreReq.getDocPreReq();
		click(docsicon, "Icon");
//		sendText(Searchbox, "docs-iq -> Document Manager -> Transactions -> Document Preparation Request", "Search box");
		jsClick(documentmanagermenu, "Doc Managermenu");
		click(TransactionMenu, "TransactionMenu");
		click(MytaskMenu, "MytaskMenu");
		switchToBodyFrame(driver);
		click(MyDocumenttaskMenu, "MyDocumenttaskMenu");
		click(Btnsearch, "Btnsearch");
		click(Selectbtn, "Selectbtn");
		sendText(SearchField, "Doc. unique Code", "Doc. unique Code val");
		click(Selectoption, "Selectoption");
		sendText(Unicode, DocReqVal+"-000", "Doc. unique Code val");
		click(Applybtn, "Apply btn");
		click(DisplayedRecord, "Displayed Record");
		TimeUtil.mediumWait();
		click(DisplayedUnicodeRecord, "Displayed Record");
		TimeUtil.shortWait();
		click(Distrubution, "Distrubution additem");
		click(Departmentadd, "Departmentadd btn");
		click(Additem, "Additem btn");
		sendText(printreason, printreasonval, "printreason field");
		click(Submitbtn, "Submit btn");
		sendText(Passwordtxt, password, "Passwordtxt field");
		click(SubmitEsign, "submit Esign");
		click(Submit, "submit Esign");
		click(Submit, "submit Esign");
		sendText(Password, Passwordval, "Passwordtxt val");
		click(SubmitEsign, "submit Esign");

	}
}