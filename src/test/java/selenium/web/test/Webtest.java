package selenium.web.test;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Webtest {
	
	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static WebElement element = null;
	protected static JavascriptExecutor jsExecutor;
//	protected Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(10, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
	public static void assertTestUpdate(String name,String login,String pass){
		assertEquals("test02", name);
		assertEquals("test02", login);
		assertEquals("pass02", pass);
	}

	public static void assertTestDelete(String name){
		AssertJUnit.assertTrue(!"test02".equals(name));
	}
	
	@BeforeClass
	public void initWebDriver() throws MalformedURLException{
		String Node = "http://hostjboss:5556/wd/hub";
		DesiredCapabilities cap = DesiredCapabilities.firefox();
//		cap.setCapability(FirefoxDriver.PROFILE);
		cap.setBrowserName("chrome");
		driver = new RemoteWebDriver(new URL(Node), cap);
	}	
	
	@Test
	public void TestA_insert() throws InterruptedException{
		driver.get("http://hostjboss:8080/MavenPrimefacesWebTest/");
		driver.findElement(By.id("form:userName")).sendKeys("test01");
		driver.findElement(By.id("form:userLogin")).sendKeys("test01");
		driver.findElement(By.id("form:userPassword")).sendKeys("pass01");
		driver.findElement(By.id("form:j_idt11")).click();
		element = getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='form:tableUser_data']/tr[1]")));
		AssertJUnit.assertNotNull(element);
	}
	
	@Ignore
	@Test
	public void TestB_update() throws InterruptedException, AWTException{
		Thread.sleep(2000);
		element = driver.findElement(By.id("form:userName"));
		element.clear();
		element.sendKeys("test02");
		element = driver.findElement(By.id("form:userLogin"));
		element.clear();
		element.sendKeys("test02");
		element = driver.findElement(By.id("form:userPassword"));
		element.clear();
		element.sendKeys("pass02");
		element = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("form:j_idt12"))));
		element.click();
		Thread.sleep(500);
		String name = driver.findElement(By.xpath("//*[@id='form:tableUser_data']/tr/td[2]")).getText();
		String login = driver.findElement(By.xpath("//*[@id='form:tableUser_data']/tr/td[3]")).getText();
		String pass = driver.findElement(By.xpath("//*[@id='form:tableUser_data']/tr/td[4]")).getText();
		assertTestUpdate(name, login, pass);		
	}

	@Test
	public void TestC_delete() throws InterruptedException, AWTException{
		Thread.sleep(500);
		element = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("form:j_idt13"))));
		element.click();
		Thread.sleep(3000);
		driver.findElement(By.id("form:confirmRemoveBtn")).click();
//		Robot rb =new Robot();
//		rb.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
		element = getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='form:tableUser_data']/tr[1]")));
		String name = element.getText();
		assertTestDelete(name);	
	}
	
	@AfterTest
	public void afterTest(){
		driver.quit();
	}
	
	public WebDriverWait getWebDriverWait(){
		if(wait == null){
			wait = new WebDriverWait(driver, 60);
		}
		return wait;
	} 
}
