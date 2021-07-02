package com.appium.android.mobile;

import java.awt.RenderingHints.Key;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobilePlatform;

public class MobileChromeBrowser_Payal {

	public static AppiumDriver<MobileElement> driver = null;
	
	public void signIn() throws InterruptedException
	{

		// Open URL in Chrome Browser
		driver.get("https://www.noon.com/uae-en/");
		Thread.sleep(5000);

		driver.findElement(By.xpath("//*[contains(text(),'My Account')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//p[contains(text(),'Sign In')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[contains(@id,'emailInput')]")).click();
		driver.findElement(By.xpath("//input[contains(@id,'emailInput')]")).sendKeys("SweetyTest94@gmail.com");
		driver.findElement(By.xpath("//input[contains(@id,'passwordInput')]")).sendKeys("Password@12345");
		driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();
	}
	
	public void launchBrowser()
	{
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "95c4616ed6a6");
		caps.setCapability("platformName", "Android");
		caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		caps.setCapability(CapabilityType.VERSION, "8.1.0");

		// Instantiate Appium Driver
		
		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);

		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}

	
	}
	
	public void searchProduct(String product) throws InterruptedException
	{
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='searchBar']")).click();
		driver.findElement(By.xpath("//*[@id='searchBar']")).sendKeys(product);
		driver.findElement(By.xpath("//*[@id='searchBar']")).sendKeys(Keys.ENTER);			
		Thread.sleep(5000);	
	}
	
	public void AddtoCart() throws InterruptedException
	{
		driver.findElement(By.xpath("//div[contains(@title,'DELL G3 15 3500, 15.6')]")).click();
		Thread.sleep(2000);
		WebElement ele =driver.findElement(By.xpath("//div[contains(text(),'Add To Cart')]"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", ele);
		
	}
	
	public void checkout() throws InterruptedException
	{
		
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[contains(@class,'sc-1g6zspo-8 bswpPM')]")).click();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// Set the Desired Capabilities
		MobileChromeBrowser_Payal obj = new MobileChromeBrowser_Payal();
		obj.launchBrowser();
		obj.signIn();
		obj.searchProduct("Laptop");
		obj.AddtoCart();
		obj.checkout();
	}

}
