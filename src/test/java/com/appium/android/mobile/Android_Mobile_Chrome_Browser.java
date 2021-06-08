package com.appium.android.mobile;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Android_Mobile_Chrome_Browser {

	public static void main(String[] args) 
	{
		// Set the Desired Capabilities
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("deviceName", "95c4616ed6a6");
				caps.setCapability("platformName", "Android");
				caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
				caps.setCapability(CapabilityType.VERSION, "10.0");
				//caps.setCapability("W3C","false");
				
				
				// Instantiate Appium Driver
				AppiumDriver<MobileElement> driver = null;
				try {
					driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);

				} catch (MalformedURLException e) {
					System.out.println(e.getMessage());
				}
				
				// Open URL in Chrome Browser
				driver.get("https://www.w3schools.com/");
	}

}
