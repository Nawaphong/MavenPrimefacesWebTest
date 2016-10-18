package selenium.web.test;

import static org.testng.AssertJUnit.assertEquals;

import java.awt.AWTException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class Webtest {
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected WebElement element = null;
	protected JavascriptExecutor jsExecutor;

	// protected Wait<WebDriver> fluentWait = new
	// FluentWait<WebDriver>(driver).withTimeout(60,
	// TimeUnit.SECONDS).pollingEvery(10,
	// TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
	public void assertTestUpdate(String name, String login, String pass) {
		assertEquals("test2", name);
		assertEquals("test2", login);
		assertEquals("test2", pass);
	}

	public void assertTestDelete(String name) {
		AssertJUnit.assertTrue(!"test02".equals(name));
	}

	@Parameters("browser")
	@BeforeClass
	public void initWebDriver(String browser) throws MalformedURLException {
		if (browser.equalsIgnoreCase("firefox")) {
			String Node = "http://hostjboss:5555/wd/hub";
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setBrowserName("firefox");
			driver = new RemoteWebDriver(new URL(Node), cap);
			driver.get("http://hostjboss:8080/MavenPrimefacesWebTest/");
		} else if (browser.equalsIgnoreCase("chrome")) {
			String Node = "http://hostjboss:5556/wd/hub";
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
			driver = new RemoteWebDriver(new URL(Node), cap);
			driver.get("http://hostjboss:8080/MavenPrimefacesWebTest/");
		}else if (browser.equalsIgnoreCase("internet explorer")) {
			String Node = "http://hostjboss:5557/wd/hub";
			DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
			cap.setBrowserName("internet explorer");
			driver = new RemoteWebDriver(new URL(Node), cap);
			driver.get("http://hostjboss:8080/MavenPrimefacesWebTest/");
		}
	}

	@Test
	public void TestA_insert() throws InterruptedException {
		element = getFluentWait().until(getFinderByLocatorID("form:userName"));
		element.sendKeys("test01");
		driver.findElement(By.id("form:userLogin")).sendKeys("test01");
		driver.findElement(By.id("form:userPassword")).sendKeys("pass01");
		driver.findElement(By.id("form:j_idt11")).click();
		element = getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='form:tableUser_data']/tr[1]")));
		AssertJUnit.assertNotNull(element);
	}

	@Ignore
	@Test
	public void TestB_update() throws InterruptedException, AWTException {
		element = getFluentWait().until(getFinderByLocatorID("form:userName"));
		element.clear();
		element.sendKeys("test02");
		element = driver.findElement(By.id("form:userLogin"));
		element.clear();
		element.sendKeys("test02");
		element = driver.findElement(By.id("form:userPassword"));
		element.clear();
		element.sendKeys("pass02");
		element = getWebDriverWait()
				.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("form:j_idt12"))));
		element.click();
		Thread.sleep(500);
		String name = driver.findElement(By.xpath("//*[@id='form:tableUser_data']/tr[3]/td[2]")).getText();
		String login = driver.findElement(By.xpath("//*[@id='form:tableUser_data']/tr[3]/td[3]")).getText();
		String pass = driver.findElement(By.xpath("//*[@id='form:tableUser_data']/tr[3]/td[4]")).getText();
		assertTestUpdate(name, login, pass);
	}

	@Test
	public void TestC_delete() throws InterruptedException, AWTException {
		element = getWebDriverWait()
				.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("form:j_idt13"))));
		element.click();
		driver.findElement(By.id("form:confirmRemoveBtn")).click();
		element = getWebDriverWait()
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='form:tableUser_data']/tr[1]")));
		String name = element.getText();
		assertTestDelete(name);
	}

	public WebDriverWait getWebDriverWait() {
		if (wait == null) {
			wait = new WebDriverWait(driver, 60);
		}
		return wait;
	}
	
	public FluentWaitElementFinder fluentFinder = new FluentWaitElementFinder(null, driver);
	public FluentWaitElementFinder getFinderByLocatorXpath(String idOfComponent) {
		fluentFinder.setLocator(By.xpath(idOfComponent));
		return fluentFinder;
	}
	public FluentWaitElementFinder getFinderByLocatorID(String idOfComponent) {
		fluentFinder.setLocator(By.id(idOfComponent));
		return fluentFinder;
	}
	public class FluentWaitElementFinder implements Function<WebDriver,WebElement>{
		private By locator;
		private WebDriver driver;
		private int count = 0;
		public FluentWaitElementFinder() {
			super();
		}

		public FluentWaitElementFinder(By locator,WebDriver driver) {
			super();
			this.locator = locator;
			this.driver = driver;
		}

//		@Override
		public WebElement apply(WebDriver driver) {
			return driver.findElement(this.locator);
		}

		public By getLocator() {
			return locator;
		}

		public void setLocator(By locator) {
			this.locator = locator;
		}

		public WebDriver getDriver() {
			return driver;
		}

		public void setDriver(WebDriver driver) {
			this.driver = driver;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	}
	
	@AfterTest
	public void afterTest(){
		driver.quit();
	}
	
	@SuppressWarnings("rawtypes")
	public Wait<WebDriver> getFluentWait(){
		@SuppressWarnings("unchecked")
		Wait<WebDriver> fluentWait = new FluentWait(driver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.SECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
		return fluentWait;
	}
}
