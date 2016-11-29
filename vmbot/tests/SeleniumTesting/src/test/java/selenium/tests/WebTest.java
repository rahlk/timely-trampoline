package selenium.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class WebTest
{
	private static WebDriver driver;
	
	@BeforeClass
	public static void setUp() throws Exception 
	{
		//driver = new HtmlUnitDriver();
		ChromeDriverManager.getInstance().setup();
		driver = new ChromeDriver();
	}
	
	@AfterClass
	public static void  tearDown() throws Exception
	{
		driver.close();
		driver.quit();
	}

	
	@Test
	public void googleExists() throws Exception
	{
		driver.get("http://www.google.com");
        assertEquals("Google", driver.getTitle());		
	}
	

	//To test number of closed studies
	@Test
	public void Closed() throws Exception
	{
		driver.get("http://www.checkbox.io/studies.html");
		
		// http://geekswithblogs.net/Aligned/archive/2014/10/16/selenium-and-timing-issues.aspx
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='status']/span[.='CLOSED']")));
		List<WebElement> spans = driver.findElements(By.xpath("//a[@class='status']/span[.='CLOSED']"));

		assertNotNull(spans);
		assertEquals(5, spans.size());
	}
	
	@Test
	public void participationCount() throws Exception
	{
		driver.get("http://www.checkbox.io/studies.html");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='row']/div[@class='span8']/h3/span[.='Frustration of Software Developers']/../../../div[@class='span4']/p/span[.='55']")));
		List<WebElement> count = driver.findElements(By.xpath("//div[@class='row']/div[@class='span8']/h3/span[.='Frustration of Software Developers']/../../../div[@class='span4']/p/span[.='55']"));
		
		assertNotNull(count);
		assertEquals(1,count.size());
		
	}
	//shashwath.basics@gmail.com
	@Test
	public void openStudyParticipate() throws Exception
	{
		driver.get("http://www.checkbox.io/studies.html");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='row']/div[@class='span4']/a/span[.='OPEN']/../../div/button")));
		//List<WebElement> count =
		int count=0;
		for(WebElement i: driver.findElements(By.xpath("//div[@class='row']/div[@class='span4']/a/span[.='OPEN']/../../div/button"))){
			if(i.isEnabled()){
				count=count+1;
			}
			i.click();
		}
		
		assertNotNull(count);
		assertEquals(7,count);
	}
	
	@Test
	public void amazonRewardImage() throws Exception
	{
		driver.get("http://www.checkbox.io/studies.html");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='row']/div[@class='span8']/h3/span[.='Software Changes Survey']/../../div[@class='award']/div/span/img")));
		WebElement element = driver.findElement(By.xpath("//div[@class='row']/div[@class='span8']/h3/span[.='Software Changes Survey']/../../div[@class='award']/div/span/img"));
		String path = element.getAttribute("src");
		//assertNotNull(count);
		assertEquals("http://www.checkbox.io/media/amazongc-micro.jpg",path);
		
	}
}
