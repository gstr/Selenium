import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class myJUnit1 {
	
	@BeforeClass
	public static void beforeEveryTestRun() {
		System.out.println("Runs before every test run");
	}
	
	@Before
	public void beforeEveryTest() {
		System.out.println("Runs before each @Test method");
	}
//Gets executed when we run the program
	@Test
	public void test1() {
		if (mMultiply(10,30)==300) {
			System.out.println("Multiply Pass");
		} 
		else {
			System.out.println("Multiply Fail");
			fail("Multiply failed for 10 and 30");
		}
	}
	
	//multiple test example
	@Test
	public void test2() {
		if (mAdd(10,30)==300) {
			System.out.println("Add Pass");
		} 
		else {
			System.out.println("Add Fail");
			fail("Add failed for 10 and 30");
		}
	}
	
	@Test
	public void test3() {
		if (mDivide(10,30)==.3) {
			System.out.println("Divide Pass");
		} 
		else {
			System.out.println("Divide Fail");
			fail("Divide failed for 10 and 30");
		}
	}
	
	@After
	public void afterEveryTest() {
		System.out.println("Runs after each @Test method");
	}
	
	@AfterClass
	public static void afterEveryTestRun() {
		System.out.println("Runs after each test run");
	}
	
	public int mMultiply(int x, int y) {
		return x*y;
	}
	
	public int mAdd(int x, int y) {
		return x+y;
	}
	
	public double mDivide(int x, int y) {
		return x/y;
	}
	
	
	
	
}
