package com.appium.android.mobile;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;

public class Android_Mobile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "10099323");
		caps.setCapability("platformName", "Android");
		caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		caps.setCapability(CapabilityType.VERSION, "10");
		caps.setCapability("newCommandTimeout", 200);
		caps.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
		caps.setCapability(CapabilityType.BROWSER_VERSION,"91.0.4472.88");
		
		AppiumDriver<MobileElement> driver = null;
		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
		}
		catch(MalformedURLException e)
		{System.out.println(e.getMessage());}
	
	

	// Open URL in Chrome Browser
		try {
				driver.get("https://www.amazon.co.uk/");
				Thread.sleep(1000);
				//driver.switchTo().frame(1);
				driver.findElement(By.id("sp-cc-accept")).click();
				driver.findElement(By.id("nav-hamburger-menu")).click();
				driver.findElement(By.id("hmenu-customer-name")).click();
				Thread.sleep(1000);
				driver.findElement(By.id("register_accordion_header")).click();
				Thread.sleep(1000);
				driver.findElement(By.id("ap_customer_name")).sendKeys("Jugal");
				Thread.sleep(1000);
			//	driver.findElement(By.id("continue")).click();
			//	driver.findElement(By.xpath("//*[contains(text),'There was a problem']"));
				driver.findElement(By.id("ap_email")).sendKeys("07440035509");
				Thread.sleep(1000);
				driver.findElement(By.id("ap_password")).sendKeys("Test@2314");
				Thread.sleep(1000);
				driver.findElement(By.id("continue")).click();
				driver.findElement(By.id("continue")).click();
				
				
		}
		catch (InterruptedException e) 
		{System.out.println(e.getMessage());}
		
}

}
