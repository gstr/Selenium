

//package com.example.tests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class csl3 {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome C:\\Firefox\\firefox.exe", "http://www.chasestudentloans.com");
		selenium.start();
	}

	@Test
	public void testCsl1() throws Exception {
		String vTui, vFood, vCC; // Declare expenses
		String vJob, vGrants; // Declare income
		int i;
		for (i=0;i<5;i++){
		System.out.println("Iteration # " +i);
		//Initialize test parameters
		vTui = "350"; 
		vFood = "200";
		vCC = "350";
		vJob = "500";
		vGrants ="450";
		
		//Simulate application functionality
		double vTExp = Double.parseDouble(vTui) + Double.parseDouble(vFood) + Double.parseDouble(vCC);
		double vTInc = Double.parseDouble(vJob) + Double.parseDouble(vGrants);
		double vTLoan = vTInc - vTExp;
		System.out.println("Expense total from script is " + vTExp);
		System.out.println("Income total from script is " + vTInc);
		System.out.println("Loan total from script is " + vTLoan);
		
		selenium.open("/");
		selenium.click("link=Budget calculators");
		selenium.waitForPageToLoad("30000");
		
		//Expenses start here
		selenium.type("tuition", vTui);
		selenium.type("food", vFood);
		selenium.type("creditcards", vCC);
		
		//Income starts here
		selenium.type("job", vJob);
		selenium.type("grants", vGrants);
		
		
		//Clicking on calculate
		selenium.click("//input[@class='calcbutton1']");
		String vExp = selenium.getValue("totexp");
		System.out.println("Expense total is " + vExp);
		String vInc = selenium.getValue("totinc");
		System.out.println("Income total is " + vInc);
		String vLoan = selenium.getValue("balance");
		System.out.println("Loan required is " + vLoan);
		
		if (vTExp==Double.parseDouble(vExp)) {
			System.out.println("Expenses Match");
		}
		else {
			System.out.println("Expenses Do Not Match");
		}
		
		if (vTInc==Double.parseDouble(vInc)) {
			System.out.println("Income Match");
		}
		else {
			System.out.println("Income Does Not Match");
		}
		
		if (vTLoan==Double.parseDouble(vLoan)) {
			System.out.println("Loan Match");
		}
		else {
			System.out.println("Loan Does Not Match");
		}
		
		}
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
