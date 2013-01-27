
import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;



public class myJUnit2 extends SeleneseTestCase {

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome c:\\firefox\\firefox.exe", "http://www.weather.com/");
		selenium.start();
	}

	@Test
	public void testUntitled() throws Exception {
		selenium.open("/");
		selenium.type("id=typeaheadBox", "91367");
		selenium.click("css=div.wx-searchButton");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Woodland Hills, CA (91367) Weather"));
		boolean vResult = selenium.isTextPresent("Woodland Hills, CA (91367) Weather");
		
		if (vResult) {
			System.out.println("Pass");
		}
		else {
			System.out.println("Fail");
			fail("JUnit result is fail");
		}
		
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
