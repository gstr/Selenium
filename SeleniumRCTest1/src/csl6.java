

//package com.example.tests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

import java.math.BigInteger;
import java.util.regex.Pattern;
import java.io.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class csl6 {
	private Selenium selenium;

	//Global variables
	public String vExp, vInc, vLoan;
	public double vTExp, vTInc, vTLoan;
	
	@Before
	public void setUp() throws Exception {
		String xPath = "C:/testing/excel/csl_data.xls";
		xlRead(xPath);
		selenium = new DefaultSelenium("localhost", 4444, "*chrome C:\\Firefox\\firefox.exe", "http://www.chasestudentloans.com");
		selenium.start();
		myprint("Testing function");
	}

	@Test
	public void testCsl1() throws Exception {
		String vTui, vFood, vCC; // Declare expenses
		String vJob, vGrants; // Declare income
		int i;
		for (i=0;i<5;i++){
		myprint("Iteration # " +i);
		//Initialize test parameters
		vTui = "350"; 
		vFood = "200";
		vCC = "350";
		vJob = "500";
		vGrants ="450";
		
		//Simulate application functionality
		vTExp = Double.parseDouble(vTui) + Double.parseDouble(vFood) + Double.parseDouble(vCC);
		vTInc = Double.parseDouble(vJob) + Double.parseDouble(vGrants);
		vTLoan = vTInc - vTExp;
		myprint("Expense total from script is " + vTExp);
		myprint("Income total from script is " + vTInc);
		myprint("Loan total from script is " + vTLoan);
		
		//Test Step 1 - Open website, click budget calculator, wait for page to load
		myprint(ts_001("Budget calculators", "25000"));
		
		//Test Step 2 - Expenses starts here
		myprint(ts_002(vTui, vFood, vCC));
		
		//Test Step 3 - Income starts here
		myprint(ts_003(vJob, vGrants));
		
		//Test Step 4 - Click Calculate
		myprint(ts_004());
		
		//Test Step 5 - Compare results
		myprint(ts_005());
		
		}
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
	
	//Custom Functions
	public void myprint(String mymessage){
		System.out.println(mymessage);
		
	}
	
	public String ts_001(String LinkName, String waitTime){
		//Test Step 1 - Open website, click budget calculator, wait for page to load
				selenium.open("/");
				selenium.click("link=" + LinkName);
				selenium.waitForPageToLoad(waitTime);
				return "ts_001 Pass";
	}
	
	public String ts_002(String vFTui, String vFFood, String vFCC){
		//Test Step 2 - Expenses start here
				selenium.type("tuition", vFTui);
				selenium.type("food", vFFood);
				selenium.type("creditcards", vFCC);
				return "ts_002 Pass";
	}
	
	public String ts_003(String vFJob, String vFGrants){
		//Income starts here
		selenium.type("job", vFJob);
		selenium.type("grants", vFGrants);
		return "ts_003 Pass";
	}
	
	public String ts_004(){
		//Clicking on calculate
				selenium.click("//input[@class='calcbutton1']");
				vExp = selenium.getValue("totexp");
				myprint("Expense total is " + vExp);
				vInc = selenium.getValue("totinc");
				myprint("Income total is " + vInc);
				vLoan = selenium.getValue("balance");
				myprint("Loan required is " + vLoan);
				return "ts_004 Pass";
	}
	
	public String ts_005(){
		//Compare results
		String vE, vI, vL;
		if (vTExp==Double.parseDouble(vExp)) {
			myprint("Expenses Match");
			vE = "Pass";
		}
		else {
			myprint("Expenses Do Not Match");
			vE = "Fail";
		}
		
		if (vTInc==Double.parseDouble(vInc)) {
			myprint("Income Match");
			vI = "Pass";
		}
		else {
			myprint("Income Does Not Match");
			vI = "Fail";
		}
		
		if (vTLoan==Double.parseDouble(vLoan)) {
			myprint("Loan Match");
			vL = "Pass";
		}
		else {
			myprint("Loan Does Not Match");
			vL = "Fail";
		}
		if (vE=="Pass" && vI=="Pass" && vL=="Pass"){
			return "ts_005 Pass";
		}
		else {
			return "ts_005 Fail";
		}

	}

	public void xlRead(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);
		
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		HSSFSheet mySheet = myWB.getSheetAt(1);
		int xRows = mySheet.getLastRowNum()+1;
		int xCols = mySheet.getRow(0).getLastCellNum()+1;
		myprint("Rows are " + xRows);
		myprint("Cols are " + xCols);
	}
}
