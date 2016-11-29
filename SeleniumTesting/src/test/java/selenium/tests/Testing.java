package selenium.tests;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Testing
{
	private static WebDriver driver;
	
	@BeforeClass
	public static void setUp() throws Exception 
	{
		ChromeDriverManager.getInstance().setup();
		driver = new ChromeDriver();
	}
	
	@AfterClass
	public static void  tearDown() throws Exception
	{
		driver.close();
		driver.quit();
	}

	
	String[] inputCommands = {
			"@vmbot create box precise32 with pip install selenium, mkdir myproject",
			"@vmbot create box precise32 with pip install selenium, mkdir myproject",
			"@vmbot Deploy precise32",
			"@vmbot Deploy ruby",
			"@vmbot Processes python",
			"@vmbot Processes ruby",
			"@vmbot Status health",
			"@vmbot Status machine",
	};
	
	String[] outputPassCommands = {
		"A vagrant box for ubuntu-precise32 has been created. Installed python. Installed selenium. Directory myproject created.\n\nBox now ready to be deployed."
	};
	String[] outputFailCommands = {
		"Failed to create precise32. A box with this name has already been created.",
		"Failed to deploy precise32. I did not understand \"ruby\"",
		"Failed to identify process \"ruby\".",
		"Failed to obtain health. I did not understand \"machine\""
	};
	
	@Test
	public void aInputVerify() throws Exception
	{
		driver.get("https://serverbot-project.slack.com/");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signin_btn")));

		WebElement inputEmail = driver.findElement(By.id("email")); 
		inputEmail.sendKeys("agrawal.akash22@gmail.com");
		WebElement inputPassword = driver.findElement(By.id("password"));
		inputPassword.sendKeys("Blahjohnsnow@2208");
		driver.findElement(By.id("signin_btn")).click();
		
		
		wait.until(ExpectedConditions.titleContains("general"));
		
		driver.get("https://serverbot-project.slack.com/messages/serverbot");
		wait.until(ExpectedConditions.titleContains("serverbot"));
		
		WebElement input = driver.findElement(By.id("message-input")); 
		input.sendKeys("@vmbot help");
		input.sendKeys(Keys.RETURN);

		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']")));
		WebElement generatedResponse = driver.findElement(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']"));
		String var= generatedResponse.getText();
		String[] parts = var.split(":");

		assertEquals("Greetings developer. Here's how I can help you-\n\n1. Create a vargrant box", parts[1].trim());
	}
	
	
	@Test
	public void bCreateBox() throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.get("https://serverbot-project.slack.com/messages/serverbot");
		wait.until(ExpectedConditions.titleContains("serverbot"));
		
		WebElement input = driver.findElement(By.id("message-input")); 
		input.sendKeys(inputCommands[0]);
		input.sendKeys(Keys.RETURN);

		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']")));
		WebElement generatedResponse = driver.findElement(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']"));
		String var= generatedResponse.getText();
		String[] parts = var.split(":");
		if(parts[1].trim().equals(outputPassCommands[0])){
			assertEquals(outputPassCommands[0], parts[1].trim());
		}else{
			assertEquals(outputFailCommands[0], parts[1].trim());
		}
		
		input.sendKeys(inputCommands[1]);
		input.sendKeys(Keys.RETURN);

		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']")));
		generatedResponse = driver.findElement(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']"));
		var= generatedResponse.getText();
		parts = var.split(":");
		if(parts[1].trim().equals(outputPassCommands[0])){
			assertEquals(outputPassCommands[0], parts[1].trim());
		}else{
			assertEquals(outputFailCommands[0], parts[1].trim());
		}
		
		
	}
	@Test
	public void cDeploy() throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.get("https://serverbot-project.slack.com/messages/serverbot");
		wait.until(ExpectedConditions.titleContains("serverbot"));
		
		WebElement input = driver.findElement(By.id("message-input")); 
		input.sendKeys(inputCommands[2]);
		input.sendKeys(Keys.RETURN);
		
		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']")));
		WebElement generatedResponse = driver.findElement(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']"));
		//String var= generatedResponse.getText();
		assertNotNull(generatedResponse);
		assertNotEquals(generatedResponse, "Sorry but I didn't understand you");
		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		input.sendKeys(inputCommands[3]);
		input.sendKeys(Keys.RETURN);
		
		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']")));
		generatedResponse = driver.findElement(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']"));
		String var= generatedResponse.getText();
		String[] parts = var.split(":");
		assertEquals(outputFailCommands[1],parts[1].trim());
		
		
	}
	
	@Test
	public void dStatusProcesses() throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.get("https://serverbot-project.slack.com/messages/serverbot");
		wait.until(ExpectedConditions.titleContains("serverbot"));
		
		WebElement input = driver.findElement(By.id("message-input")); 
		input.sendKeys(inputCommands[4]);
		input.sendKeys(Keys.RETURN);
		
		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']")));
		WebElement generatedResponse = driver.findElement(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']"));
		//String var= generatedResponse.getText();
		assertNotNull(generatedResponse);
		assertNotEquals(generatedResponse, "Sorry but I didn't understand you");
		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		input.sendKeys(inputCommands[5]);
		input.sendKeys(Keys.RETURN);
		
		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']")));
		generatedResponse = driver.findElement(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']"));
		String var= generatedResponse.getText();
		String[] parts = var.split(":");
		assertEquals(outputFailCommands[2],parts[1].trim());
	}
	
	@Test
	public void eHealth() throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.get("https://serverbot-project.slack.com/messages/serverbot");
		wait.until(ExpectedConditions.titleContains("serverbot"));
		
		WebElement input = driver.findElement(By.id("message-input")); 
		input.sendKeys(inputCommands[6]);
		input.sendKeys(Keys.RETURN);
		
		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']")));
		WebElement generatedResponse = driver.findElement(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']"));
		//String var= generatedResponse.getText();
		assertNotNull(generatedResponse);
		assertNotEquals(generatedResponse, "Sorry but I didn't understand you");
		
		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		input.sendKeys(inputCommands[7]);
		input.sendKeys(Keys.RETURN);
		
		try {
			 Thread.sleep(5000);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']")));
		generatedResponse = driver.findElement(By.xpath("//div[@id='msgs_div']/div[last()]/div[@class='day_msgs']/ts-message[last()]/div[@class='message_content ']/span[@class='message_body']"));
		String var= generatedResponse.getText();
		String[] parts = var.split(":");
		assertEquals(outputFailCommands[3],parts[1].trim());
	}
	
}
