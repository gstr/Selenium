

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

public class csl8 {
	private Selenium selenium;

	//Global variables
	public String vExp, vInc, vLoan;
	public double vTExp, vTInc, vTLoan;
	public String[][] xData;
	public int xRows, xCols;
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
		int vET, vIT, vLT, vR; //Column numbers for the output values
		vET = 11;
		vIT = 12;
		vLT = 13;
		vR = 14;
		for (int i = 1; i < xRows; i++) {
		myprint("Row # " +i);
		//Read from each row in Excel into variables
		vTui = xData[i][1];
		vFood = xData[i][4];
		vCC = xData[i][7];
		vJob = xData[i][8];
		vGrants = xData[i][9];
		myprint("Tuition is " + vTui + " Job is " + vJob);
		
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
		if (ts_005() == "Pass") {
			xData[i][vR] = "Pass";
			
		}
		
		else {
			xData[i][vR] = "Fail";
		}
		
		// Value from the app back into the array
		xData[i][vET] = vExp;
		xData[i][vIT] = vInc;
		xData[i][vLT] = vLoan;
		}
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
		String res_path= "C:/testing/excel/csl_data1.xls";
		xlwrite(res_path, xData);
	}
	
	//Custom Functions
	public void myprint(String mymessage){
		System.out.println(mymessage);
		System.out.println("-----");
		
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
			return "Pass";
		}
		else {
			return "Fail";
		}

	}

	public void xlRead(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);
		
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		//HSSFSheet mySheet = new HSSFSheet(myWB);
		//HSSFSheet mySheet = myWB.getSheetAt(0);	// Referring to 1st sheet
		HSSFSheet mySheet = myWB.getSheetAt(2);	// Referring to 3rd sheet
		//int xRows = mySheet.getLastRowNum()+1;
		//int xCols = mySheet.getRow(0).getLastCellNum();
		xRows = mySheet.getLastRowNum()+1;
		xCols = mySheet.getRow(0).getLastCellNum();
		myprint("Rows are " + xRows);
		myprint("Cols are " + xCols);
		// String[][] xData = new String[xRows][xCols];
		xData = new String[xRows][xCols];
		  for (int i = 0; i < xRows; i++) {
			  HSSFRow row = mySheet.getRow(i);
			  for (int j = 0; j < xCols; j++) {
				  HSSFCell cell = row.getCell(j);
				  String value = cellToString(cell);
				  xData[i][j] = value;
				 // System.out.print(value);
				 // System.out.print("  ");
			  }
            // System.out.println("");
		  }
		
	}

	
	public static String cellToString(HSSFCell cell) {
		// This function will convert an object of type excel cell to a string value
	        int type = cell.getCellType();
	        Object result;
	        switch (type) {
	            case HSSFCell.CELL_TYPE_NUMERIC: //0
	                result = cell.getNumericCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_STRING: //1
	                result = cell.getStringCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_FORMULA: //2
	                throw new RuntimeException("We can't evaluate formulas in Java");
	            case HSSFCell.CELL_TYPE_BLANK: //3
	                result = "-";
	                break;
	            case HSSFCell.CELL_TYPE_BOOLEAN: //4
	                result = cell.getBooleanCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_ERROR: //5
	                throw new RuntimeException ("This cell has an error");
	            default:
	                throw new RuntimeException("We don't support this cell type: " + type);
	        }
	        return result.toString();
	    }
	
	public void xlwrite(String xlPath, String[][] xldata) throws Exception {
		System.out.println("Inside XL Write");
    	File outFile = new File(xlPath);
        HSSFWorkbook wb = new HSSFWorkbook();
           // Make a worksheet in the XL document created
        /*HSSFSheet osheet = wb.setSheetName(1,"TEST");*/
        HSSFSheet osheet = wb.createSheet("TESTRESULTS");
        // Create row at index zero ( Top Row)
    	for (int myrow = 0; myrow < xRows; myrow++) {
    		//System.out.println("Inside XL Write");
	        HSSFRow row = osheet.createRow(myrow);
	        // Create a cell at index zero ( Top Left)
	        for (int mycol = 0; mycol < xCols; mycol++) {
	        	HSSFCell cell = row.createCell(mycol);
	        	// Lets make the cell a string type
	        	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	// Type some content
	        	cell.setCellValue(xldata[myrow][mycol]);
	        	//System.out.print("..." + xldata[myrow][mycol]);
	        }
	        //System.out.println("..................");
	        // The Output file is where the xls will be created
	        FileOutputStream fOut = new FileOutputStream(outFile);
	        // Write the XL sheet
	        wb.write(fOut);
	        fOut.flush();
//		    // Done Deal..
	        fOut.close();
    	}
    }
}

