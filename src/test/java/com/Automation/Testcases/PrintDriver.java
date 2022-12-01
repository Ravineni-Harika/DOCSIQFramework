package com.Automation.Testcases;

import java.awt.AWTException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Automation.Base.ActionEngine;
import com.Automation.Utils.ConfigsReader;
import com.Automation.Utils.Excelutil;
import com.Automation.Utils.MyScreenRecorder;

public class PrintDriver extends ActionEngine {
String Excelpath = "./TestData/printdriver.xlsx";
    
    
    
    public PrintDriver () {
            
        super(ConfigsReader.getPropValue("applicationUrl"));
            
            
        }



   @DataProvider(name = "PrintDriver")
    public Object[][] getCCLgn13() {
       Object getCCLgn13[][] = Excelutil.getTestData(Excelpath, "Printdriver");
        return getCCLgn13;//
    }
        @Test(priority = 1, enabled = true)
        public void Printdriverdocs() throws AWTException {
            
        	Docsloginpage.docsiqlogin("Epiq320",
                    "Prasannad2",
                    "password2");
        	Docsloginpage.Masterplantselection();
           
            print1.Printdriver1("PDFliteprint","ss");
            
            
    }
        
        @AfterTest
        public void afterTest() {
            extent.flush();
            MyScreenRecorder.stopRecording();
}
}
