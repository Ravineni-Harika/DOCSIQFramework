package com.Automation.Testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.Automation.Base.ActionEngine;
import com.Automation.Utils.ConfigsReader;
import com.Automation.Utils.Excelutil;
import com.Automation.Utils.MyScreenRecorder;

public class DocumentPreReq_Test extends ActionEngine {
	String ExcelPath = "./TestData/DOCRequestPreparation.xlsx";

	@DataProvider(name = "DPRequest")
	public Object[][] getDPRequestData() {
		Object getDPRequestData[][] = Excelutil.getTestData(ExcelPath, "DocsPreReq");
		return getDPRequestData;
	}

	@DataProvider(name = "DocTask")
	public Object[][] getDocTaskData() {
		Object getDocTaskData[][] = Excelutil.getTestData(ExcelPath, "DocumentTask");
		return getDocTaskData;
	}

	@DataProvider(name = "Anexxure")
	public Object[][] getDocAnexxureData() {
		Object getDocTaskData[][] = Excelutil.getTestData(ExcelPath, "Anexxure");
		return getDocTaskData;
	}

	public DocumentPreReq_Test() {

		super(ConfigsReader.getPropValue("applicationUrl"));
	}

	// method is for Document Preparation request
	@Test(priority = 1, dataProvider = "DPRequest", enabled = false)
	public void DocumentPreparationRequest(String DPR, String Description, String CCnumber) {

		Docsloginpage.docsiqlogin("ctplhyd", "satheesh1", "pass1");
		Docsloginpage.dociqplantselection();
		DocPreReq.Document_preparation_Registration(DPR, Description, CCnumber);
	}

	// method is for Document Task
	@Test(priority = 2, dataProvider = "DocTask", enabled = false)
	public void DocumentTask(String Unicodeval, String ChangeDetails, String Documentcodeval, String DocumentDescval) {

		Docsloginpage.docsiqlogin("ctplhyd", "satheesh1", "pass1");
		Docsloginpage.dociqplantselection();
		DocumentTask.MytaskDocumentTask(Unicodeval, ChangeDetails, "C:\\cal_public\\Documents\\1.pdf", "pass1",
				Documentcodeval, DocumentDescval);

	}

	// method is for Anexxure
	@Test(priority = 3, dataProvider = "DocTask", enabled = true)
	public void DocumentAnexxureTask(String Unicodeval, String ChangeDetails, String Documentcodeval,
			String DocumentDescval) {

		Docsloginpage.docsiqlogin("ctplhyd", "satheesh1", "pass2");
		Docsloginpage.dociqplantselection();
		DocPreReq.Document_preparation_Registration("DPR Performance testing_120013-00013", "DPR testing", "CCNO");
		DocumentTask.MytaskDocumentTaskAnnexure(Unicodeval, ChangeDetails, "C:\\cal_public\\Documents\\1.pdf", "pass2",
				Documentcodeval, DocumentDescval);
		Logout.signOutPage();
		Docsloginpage.docsiqlogin("ctplhyd", "sanju1", "pass2");
		Docsloginpage.dociqplantselection();
		DocumentTask.DocumentPreparationApproval(Unicodeval, "pass2");
		Logout.signOutPage();
		Docsloginpage.docsiqlogin("ctplhyd", "satheesh1", "pass2");
		Docsloginpage.dociqplantselection();
		DocumentTask.DocumentPublish(Unicodeval, ChangeDetails, "C:\\cal_public\\Documents\\1.pdf", "pass2",
				Documentcodeval, DocumentDescval, "Printed", "pass2");
		
	}

	@AfterTest
	public void afterTest() {
		extent.flush();
		MyScreenRecorder.stopRecording();

	}
}
