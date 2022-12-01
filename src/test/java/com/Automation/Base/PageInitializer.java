package com.Automation.Base;

import com.Automation.Pages.Docs_Loginpage;
import com.Automation.Pages.DocumentPreReq;
import com.Automation.Pages.MytaskDocTask;
import com.Automation.Pages.PrintDriverConfig;
import com.Automation.Pages.Signout;
import com.Automation.Utils.ESign;

public class PageInitializer extends TestEngine {

	
	public static TestEngine Testsetup;
	public static ESign E_sign;
	public static Signout Logout;
	public static Docs_Loginpage Docsloginpage;
	public static DocumentPreReq DocPreReq;
	public static MytaskDocTask DocumentTask;
	public static PrintDriverConfig print1;
	
	public PageInitializer(String url) {

		super(url);
	}
	public PageInitializer() {

	}

	public static void initializePageObjects() {
		
		Testsetup = new TestEngine();
		E_sign = new ESign();
		Logout = new Signout();
		Docsloginpage=new Docs_Loginpage();
		DocPreReq=new DocumentPreReq();
		DocumentTask=new MytaskDocTask();
		print1=new PrintDriverConfig();
	}
}
