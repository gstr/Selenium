

//package com.example.tests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class csl1 {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome C:\\Firefox\\firefox.exe", "http://www.chasestudentloans.com");
		selenium.start();
	}

	@Test
	public void testCsl1() throws Exception {
		selenium.open("/");
		selenium.click("link=Budget calculators");
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='tuition' and @type='text']", "400");
		selenium.type("tuition", "350");
		selenium.type("food", "200");
		selenium.type("creditcards", "400");
		selenium.type("job", "550");
		selenium.type("grants", "350");
		selenium.click("//input[@class='calcbutton1']");
		String vExp = selenium.getValue("totexp");
		System.out.println("Expense total is " + vExp);
		String vInc = selenium.getValue("totinc");
		System.out.println("Income total is " + vInc);
		String vLoan = selenium.getValue("balance");
		System.out.println("Loan required is " + vLoan);
		String vText = selenium.getText("//html/body/form/div/div[3]/div/div/div/h2");
		System.out.println(vText);
	}

	@After
	public void tearDown() throws Exception {
		//selenium.stop();
	}
}
