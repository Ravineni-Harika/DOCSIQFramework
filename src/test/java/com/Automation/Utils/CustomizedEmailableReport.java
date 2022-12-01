package com.Automation.Utils;

import org.testng.*;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlSuite;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomizedEmailableReport implements IReporter {
	String productname = "LearnIQ";
	String version = "epIQ100BL";
	String productcode = "epIQ100";
	String URL = "http://10.10.3.1/epIQ100BL_TST";
	String environment= "Testing";
	String systemname= "Cal4013";
	String ipaddress = "10.10.4.13";
	String browserversion= "Chrome Browser";
	String ram= "8GB";
	
	
	
	
	

	//This is the customize emailabel report template file path.
		private static final String emailableReportTemplateFile = System.getProperty("user.dir") +"/src/test/java/customReport/CustomReportTemplate.html";
		
		public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
			
			try
			{
				// Get content data in TestNG report template file.
				String customReportTemplateStr = this.readEmailabelReportTemplate();
				
				// Create custom report title.
				String customReportTitle = this.getCustomReportTitle("Test Summary");
				
				// Create test suite summary data.
				String customSuiteSummary = this.getTestSuiteSummary(suites);
				
				// Create test suite whole summary data.
				String customSuiteWholeSummary = this.getTestSuiteWholeSummary(suites);
				
				// Create Test Environment Details data.
				String testEnvironmentDetails = this.getTestEnvironmentDetails(suites);
				
				// Create Test Environment Details data.
				String testProductDetails = this.getProductDetails(suites);
				
				// Replace report title place holder with custom title.
				customReportTemplateStr = customReportTemplateStr.replaceAll("\\$TestNG_Custom_Report_Title\\$", customReportTitle);
				
				// Replace test suite place holder with custom test suite summary.
				customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Summary\\$", customSuiteSummary);
				
				//Replace test suite place holder with custom summarize suite summary (Gaurav).
				customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_WholeSummary\\$", customSuiteWholeSummary);
				
				// Replace test methods place holder with Test Environment Details
				customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_EnvironmentDetails\\$", testEnvironmentDetails);
				
				// Replace test methods place holder with Test Product Details
				customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_ProductDetails\\$", testProductDetails);
				
				// Write replaced test report content to custom-emailable-report.html.
				File targetFile = new File(outputDirectory + "/custom-emailable-report.html");
				
				FileWriter fw = new FileWriter(targetFile);
				fw.write(customReportTemplateStr);
				fw.flush();
				fw.close();
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		/* Read template content. */
		private String readEmailabelReportTemplate()
		{
			StringBuffer retBuf = new StringBuffer();
			
			try {
			
				File file = new File(this.emailableReportTemplateFile);
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				String line = br.readLine();
				while(line!=null)
				{
					retBuf.append(line);
					line = br.readLine();
				}
				
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}finally
			{
				return retBuf.toString();
			}
		}
		
		/* Build custom report title. */
		private String getCustomReportTitle(String title)
		{
			StringBuffer retBuf = new StringBuffer();
			retBuf.append(title);
			//retBuf.append(title + " " + this.getDateInStringFormat(new Date()));
			return retBuf.toString();
		}
		
		/* Build test suite summary data. */
		private String getTestSuiteSummary(List<ISuite> suites)
		{
			StringBuffer retBuf = new StringBuffer();
			
			try
			{
				int totalTestCount = 0;
				int totalTestPassed = 0;
				int totalTestFailed = 0;
				int totalTestSkipped = 0;
				
				for(ISuite tempSuite: suites)
				{
					//retBuf.append("<tr><td colspan=11><center><b>" + tempSuite.getName() + "</b></center></td></tr>");
					
					Map<String, ISuiteResult> testResults = tempSuite.getResults();
					
					for (ISuiteResult result : testResults.values()) {
						
						retBuf.append("<tr>");
						
						ITestContext testObj = result.getTestContext();
						
						totalTestPassed = testObj.getPassedTests().getAllMethods().size();
						totalTestSkipped = testObj.getSkippedTests().getAllMethods().size();
						totalTestFailed = testObj.getFailedTests().getAllMethods().size();
						
						totalTestCount = totalTestPassed + totalTestSkipped + totalTestFailed;
						
						/* Test name. */
						retBuf.append("<td>");
						retBuf.append(testObj.getName());
						retBuf.append("</td>");
						
						/* Total method count. */
						retBuf.append("<td>");
						retBuf.append(totalTestCount);
						retBuf.append("</td>");
						
						/* Passed method count. */
						retBuf.append("<th style=color:green>");
						retBuf.append(totalTestPassed);
						retBuf.append("</th>");
						
						
						/* Failed method count. */
						retBuf.append("<th style=color:red>");
						retBuf.append(totalTestFailed);
						retBuf.append("</th>");
						
						/* Skipped method count. */
						retBuf.append("<th style=color:orange>");
						retBuf.append(totalTestSkipped);
						retBuf.append("</th>");
						
						
						
//						/* Get browser type. */
//						String browserType = tempSuite.getParameter("browserType");
//						if(browserType==null || browserType.trim().length()==0)
//						{
//							browserType = "Chrome";
//						}
//						
//						/* Append browser type. */
//						retBuf.append("<td>");
//						retBuf.append(browserType);
//						retBuf.append("</td>");
//						
						/* Start Date*/
						Date startDate = testObj.getStartDate();
						retBuf.append("<td>");
						retBuf.append(this.getDateInStringFormat(startDate));
						retBuf.append("</td>");
						
						/* End Date*/
						Date endDate = testObj.getEndDate();
						retBuf.append("<td>");
						retBuf.append(this.getDateInStringFormat(endDate));
						retBuf.append("</td>");
						
						/* Execute Time */
						long deltaTime = endDate.getTime() - startDate.getTime();
						String deltaTimeStr = this.convertDeltaTimeToString(deltaTime);
						retBuf.append("<td>");
						retBuf.append(deltaTimeStr);
						retBuf.append("</td>");
						
						/* Include groups. */
//						retBuf.append("<td>");
//						retBuf.append(this.stringArrayToString(testObj.getIncludedGroups()));
//						retBuf.append("</td>");
//						
//						/* Exclude groups. */
//						retBuf.append("<td>");
//						retBuf.append(this.stringArrayToString(testObj.getExcludedGroups()));
//						retBuf.append("</td>");
						
						retBuf.append("</tr>");
					}
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}finally
			{
				return retBuf.toString();
			}
		}

		
		/* Build test suite whole summary data. */
		private String getTestSuiteWholeSummary(List<ISuite> suites)
		{
			StringBuffer retBuf = new StringBuffer();
			
			try
			{   int testmethodcount = 0;
				int wholecount = 0;
				int totalTestCount = 0;
				int totalTestPassed = 0;
				int totalTestFailed = 0;
				int totalTestSkipped = 0;
				ITestContext testObj=null;
				String startdate = null;
				String enddate = null;
				String deltaTimeStr = null;
				String executiontime = null;
				long time = 0;
				int totalfailledtest = 0;
				int totalskipedtest = 0;
				int totalpassedtest = 0;
				
				
				
				
				
				for(ISuite tempSuite: suites)
				{
					//retBuf.append("<tr><td colspan=11><center><b>" + tempSuite.getName() + "</b></center></td></tr>");
					
					Map<String, ISuiteResult> testResults = tempSuite.getResults();
					
					for (ISuiteResult result : testResults.values()) {
						
						retBuf.append("<tr>");
						
					 testObj = result.getTestContext();
					
						totalTestPassed = testObj.getPassedTests().getAllMethods().size();
						totalTestSkipped = testObj.getSkippedTests().getAllMethods().size();
						totalTestFailed = testObj.getFailedTests().getAllMethods().size();
						
						totalTestCount = totalTestPassed + totalTestSkipped + totalTestFailed;
						
						++wholecount;
						testmethodcount+=totalTestCount;
						Date startDate = testObj.getStartDate();
						startdate = this.getDateInStringFormat(startDate);
						
						
						Date endDate = testObj.getEndDate();
						enddate = this.getDateInStringFormat(endDate);
						
						long deltaTime = endDate.getTime() - startDate.getTime();
						time += deltaTime;
					    deltaTimeStr = this.convertDeltaTimeToString(time);
					    
					    if (totalTestFailed>0){
					    	totalfailledtest++;
					    }
						
					    if (totalskipedtest>0){
					    	
					    	totalskipedtest++;
					    }
						
						
						
						
						
						
//						/* Passed method count. */
//						retBuf.append("<td bgcolor=forestgreen>");
//						retBuf.append(totalTestPassed);
//						retBuf.append("</td>");
//						
//						/* Skipped method count. */
//						retBuf.append("<td bgcolor=yellow>");
//						retBuf.append(totalTestSkipped);
//						retBuf.append("</td>");
//						
//						/* Failed method count. */
//						retBuf.append("<td bgcolor=red>");
//						retBuf.append(totalTestFailed);
//						retBuf.append("</td>");
						
//						/* Get browser type. */
//						String browserType = tempSuite.getParameter("browserType");
//						if(browserType==null || browserType.trim().length()==0)
//						{
//							browserType = "Chrome";
//						}
//						
//						/* Append browser type. */
//						retBuf.append("<td>");
//						retBuf.append(browserType);
//						retBuf.append("</td>");
//						
						
						
						/* Include groups. */
//						retBuf.append("<td>");
//						retBuf.append(this.stringArrayToString(testObj.getIncludedGroups()));
//						retBuf.append("</td>");
//						
//						/* Exclude groups. */
//						retBuf.append("<td>");
//						retBuf.append(this.stringArrayToString(testObj.getExcludedGroups()));
//						retBuf.append("</td>");
						
						
					}
					
				}
				
				totalpassedtest = (totalfailledtest+totalskipedtest) - wholecount;
					
					/* Test count. */
					retBuf.append("<td>");
					retBuf.append(wholecount);
					retBuf.append("</td>");
					
					
					/* Total method count. */
//					retBuf.append("<td>");
//					retBuf.append(testmethodcount);
//					retBuf.append("</td>");
					
					
	                  /* End Date*/
					retBuf.append("<th style=color:green>");
					retBuf.append(totalpassedtest);
					retBuf.append("</th>");
					
					/* Start Date*/
					//Date startDate = testObj.getStartDate();
					retBuf.append("<th style=color:red>");
					retBuf.append(totalfailledtest);
					retBuf.append("</th>");
					
				
					
					/* Execute Time */
					retBuf.append("<th style=color:orange>");
					retBuf.append(totalskipedtest);
					retBuf.append("</th>");
					
                    /* Execute Time */
					
					retBuf.append("<td>");
					retBuf.append(deltaTimeStr);
					retBuf.append("</td>");
					
					retBuf.append("</tr>");
					
					
					
					
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}finally
			{
				return retBuf.toString();
			}
		}
		
		
		
		
		private String getProductDetails(List<ISuite> suites)
		{
			StringBuffer retBuf = new StringBuffer();
			
			try
			{   
		      
		          
		      
		     
		     
		     
		
				
				
	
				
				
						
						
						
//						/* Passed method count. */
//						retBuf.append("<td bgcolor=forestgreen>");
//						retBuf.append(totalTestPassed);
//						retBuf.append("</td>");
//						
//						/* Skipped method count. */
//						retBuf.append("<td bgcolor=yellow>");
//						retBuf.append(totalTestSkipped);
//						retBuf.append("</td>");
//						
//						/* Failed method count. */
//						retBuf.append("<td bgcolor=red>");
//						retBuf.append(totalTestFailed);
//						retBuf.append("</td>");
						
//						/* Get browser type. */
//						String browserType = tempSuite.getParameter("browserType");
//						if(browserType==null || browserType.trim().length()==0)
//						{
//							browserType = "Chrome";
//						}
//						
//						/* Append browser type. */
//						retBuf.append("<td>");
//						retBuf.append(browserType);
//						retBuf.append("</td>");
//						
						
						
						/* Include groups. */
//						retBuf.append("<td>");
//						retBuf.append(this.stringArrayToString(testObj.getIncludedGroups()));
//						retBuf.append("</td>");
//						
//						/* Exclude groups. */
//						retBuf.append("<td>");
//						retBuf.append(this.stringArrayToString(testObj.getExcludedGroups()));
//						retBuf.append("</td>");
						
						
					
					
				
				
				
					
					/* Test count. */
					retBuf.append("<td>");
					retBuf.append(productname);
					retBuf.append("</td>");
					
					
					/* Total method count. */
//					retBuf.append("<td>");
//					retBuf.append(testmethodcount);
//					retBuf.append("</td>");
					
					
	                  /* End Date*/
					retBuf.append("<td>");
					retBuf.append(version);
					retBuf.append("</td>");
					
					/* Start Date*/
					//Date startDate = testObj.getStartDate();
					retBuf.append("<td>");
					retBuf.append(productcode);
					retBuf.append("</td>");
					
				
					
					/* Execute Time */
					retBuf.append("<td >");
					retBuf.append(URL);
					retBuf.append("</td>");
					
					retBuf.append("</tr>");
					
					
					
					
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}finally
			{
				return retBuf.toString();
			}
		}
		
		
		
		
		
		private String getTestEnvironmentDetails(List<ISuite> suites)
		{
			StringBuffer retBuf = new StringBuffer();
			
			try
			{   
		      
		          InetAddress addr;
		          addr = InetAddress.getLocalHost();
		          ipaddress = addr.toString();
		          String ip[]= ipaddress.split("/");
		          
		          systemname = addr.getHostName();
		      
		     
		     
		     
		
				
				
	
				
				
						
						
						
//						/* Passed method count. */
//						retBuf.append("<td bgcolor=forestgreen>");
//						retBuf.append(totalTestPassed);
//						retBuf.append("</td>");
//						
//						/* Skipped method count. */
//						retBuf.append("<td bgcolor=yellow>");
//						retBuf.append(totalTestSkipped);
//						retBuf.append("</td>");
//						
//						/* Failed method count. */
//						retBuf.append("<td bgcolor=red>");
//						retBuf.append(totalTestFailed);
//						retBuf.append("</td>");
						
//						/* Get browser type. */
//						String browserType = tempSuite.getParameter("browserType");
//						if(browserType==null || browserType.trim().length()==0)
//						{
//							browserType = "Chrome";
//						}
//						
//						/* Append browser type. */
//						retBuf.append("<td>");
//						retBuf.append(browserType);
//						retBuf.append("</td>");
//						
						
						
						/* Include groups. */
//						retBuf.append("<td>");
//						retBuf.append(this.stringArrayToString(testObj.getIncludedGroups()));
//						retBuf.append("</td>");
//						
//						/* Exclude groups. */
//						retBuf.append("<td>");
//						retBuf.append(this.stringArrayToString(testObj.getExcludedGroups()));
//						retBuf.append("</td>");
						
						
					
					
				
				
				
					
					/* Test count. */
					retBuf.append("<td>");
					retBuf.append(environment);
					retBuf.append("</td>");
					
					
					/* Total method count. */
//					retBuf.append("<td>");
//					retBuf.append(testmethodcount);
//					retBuf.append("</td>");
					
					/* Start Date*/
					//Date startDate = testObj.getStartDate();
					retBuf.append("<td>");
					retBuf.append(systemname);
					retBuf.append("</td>");
					
					
					 /* End Date*/
					retBuf.append("<td>");
					retBuf.append(ip[1]);
					retBuf.append("</td>");
					
					
	                  /* End Date*/
					retBuf.append("<td>");
					retBuf.append(System.getProperty("os.name"));
					retBuf.append("</td>");
					
                      /* Execute Time */
					
					retBuf.append("<td>");
					retBuf.append(ram);
					retBuf.append("</td>");
					
								
					
					/* Execute Time */
					retBuf.append("<td>");
					retBuf.append(browserversion);
					retBuf.append("</td>");
					
					retBuf.append("</tr>");
					
                  
					
					
					
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}finally
			{
				return retBuf.toString();
			}
		}
		
		
		
		
		
		
		
		
		/* Get date string format value. */
		private String getDateInStringFormat(Date date)
		{
			StringBuffer retBuf = new StringBuffer();
			if(date==null)
			{
				date = new Date();
			}
			DateFormat df = new SimpleDateFormat("d-MMM-yyyy HH:mm:ss");
			retBuf.append(df.format(date));
			return retBuf.toString();
		}
		
		/* Convert long type deltaTime to format hh:mm:ss:mi. */
		private String convertDeltaTimeToString(long deltaTime)
		{
			StringBuffer retBuf = new StringBuffer();
			
			long milli = deltaTime;
			
			long seconds = deltaTime / 1000;
			
			long minutes = seconds / 60;
			
			long hours = minutes / 60;
			
			retBuf.append(hours + ":" + minutes + ":" + seconds + ":" + milli);
			
			return retBuf.toString();
		}
		
//		/* Get test method summary info. */
//		private String getTestMehodSummary(List<ISuite> suites)
//		{
//			StringBuffer retBuf = new StringBuffer();
//			
//			try
//			{
//				for(ISuite tempSuite: suites)
//				{
//					retBuf.append("<tr><td colspan=7><center><b>" + tempSuite.getName() + "</b></center></td></tr>");
//					
//					Map<String, ISuiteResult> testResults = tempSuite.getResults();
//					
//					for (ISuiteResult result : testResults.values()) {
//						
//						ITestContext testObj = result.getTestContext();
//
//						String testName = testObj.getName();
//						
//						/* Get failed test method related data. */
//						IResultMap testFailedResult = testObj.getFailedTests();
//					//	String failedTestMethodInfo = this.getTestMethodReport(testName, testFailedResult, false, false);
//					//	retBuf.append(failedTestMethodInfo);
//						
//						/* Get skipped test method related data. */
//						IResultMap testSkippedResult = testObj.getSkippedTests();
//					//	String skippedTestMethodInfo = this.getTestMethodReport(testName, testSkippedResult, false, true);
//					//	retBuf.append(skippedTestMethodInfo);
//						
//						/* Get passed test method related data. */
//						IResultMap testPassedResult = testObj.getPassedTests();
//				//		String passedTestMethodInfo = this.getTestMethodReport(testName, testPassedResult, true, false);
//					//	retBuf.append(passedTestMethodInfo);
//					}
//				}
//			}catch(Exception ex)
//			{
//				ex.printStackTrace();
//			}finally
//			{
//				return retBuf.toString();
//			}
//		}
		
		/* Get failed, passed or skipped test methods report. */
//		private String getTestMethodReport(String testName, IResultMap testResultMap, boolean passedReault, boolean skippedResult)
//		{
//			StringBuffer retStrBuf = new StringBuffer();
//			
//			String resultTitle = testName;
//			
//			String color = "green";
//			
//			if(skippedResult)
//			{
//				resultTitle += " - Skipped ";
//				color = "yellow";
//			}else
//			{
//				if(!passedReault)
//				{
//					resultTitle += " - Failed ";
//					color = "red";
//				}else
//				{
//					resultTitle += " - Passed ";
//					color = "green";
//				}
//			}
//			
//			retStrBuf.append("<tr bgcolor=" + color + "><td colspan=7><center><b>" + resultTitle + "</b></center></td></tr>");
//				
//			Set<ITestResult> testResultSet = testResultMap.getAllResults();
//				
//			for(ITestResult testResult : testResultSet)
//			{
//				String testClassName = "";
//				String testMethodName = "";
//				String startDateStr = "";
//				String executeTimeStr = "";
//				String paramStr = "";
//				String reporterMessage = "";
//				String exceptionMessage = "";
//				
//				//Get testClassName
//				testClassName = testResult.getTestClass().getName();
//					
//				//Get testMethodName
//				testMethodName = testResult.getMethod().getMethodName();
//					
//				//Get startDateStr
//				long startTimeMillis = testResult.getStartMillis();
//				startDateStr = this.getDateInStringFormat(new Date(startTimeMillis));
//					
//				//Get Execute time.
//				long deltaMillis = testResult.getEndMillis() - testResult.getStartMillis();
//				executeTimeStr = this.convertDeltaTimeToString(deltaMillis);
//					
//				//Get parameter list.
//				Object paramObjArr[] = testResult.getParameters();
//				for(Object paramObj : paramObjArr)
//				{
//					paramStr += (String)paramObj;
//					paramStr += " ";
//				}
//					
//				//Get reporter message list.
//				List<String> repoterMessageList = Reporter.getOutput(testResult);
//				for(String tmpMsg : repoterMessageList)				
//				{
//					reporterMessage += tmpMsg;
//					reporterMessage += " ";
//				}
//					
//				//Get exception message.
//				Throwable exception = testResult.getThrowable();
//				if(exception!=null)
//				{
//					StringWriter sw = new StringWriter();
//					PrintWriter pw = new PrintWriter(sw);
//					exception.printStackTrace(pw);
//					
//					exceptionMessage = sw.toString();
//				}
//				
//				retStrBuf.append("<tr bgcolor=" + color + ">");
//				
//				/* Add test class name. */
//				retStrBuf.append("<td>");
//				retStrBuf.append(testClassName);
//				retStrBuf.append("</td>");
//				
//				/* Add test method name. */
//				retStrBuf.append("<td>");
//				retStrBuf.append(testMethodName);
//				retStrBuf.append("</td>");
//				
//				/* Add start time. */
//				retStrBuf.append("<td>");
//				retStrBuf.append(startDateStr);
//				retStrBuf.append("</td>");
//				
//				/* Add execution time. */
//				retStrBuf.append("<td>");
//				retStrBuf.append(executeTimeStr);
//				retStrBuf.append("</td>");
//				
//				/* Add parameter. */
//				retStrBuf.append("<td>");
//				retStrBuf.append(paramStr);
//				retStrBuf.append("</td>");
//				
//				/* Add reporter message. */
//				retStrBuf.append("<td>");
//				retStrBuf.append(reporterMessage);
//				retStrBuf.append("</td>");
//				
//				/* Add exception message. */
//				retStrBuf.append("<td>");
//				retStrBuf.append(exceptionMessage);
//				retStrBuf.append("</td>");
//				
//				retStrBuf.append("</tr>");
//
//			}
//			
//			return retStrBuf.toString();
//		}
//		
//		/* Convert a string array elements to a string. */
//		private String stringArrayToString(String strArr[])
//		{
//			StringBuffer retStrBuf = new StringBuffer();
//			if(strArr!=null)
//			{
//				for(String str : strArr)
//				{
//					retStrBuf.append(str);
//					retStrBuf.append(" ");
//				}
//			}
//			return retStrBuf.toString();
		}

	
