package com.appium.android.mobile;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class MobileChromeBrowser_Ganesh {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Set the Desired Capabilities
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "36202db07ce5");
		caps.setCapability("platformName", "Android");
		caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		caps.setCapability(CapabilityType.VERSION, "8.1.0");

		// Instantiate Appium Driver
		AppiumDriver<MobileElement> driver = null;
		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);

		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}

		// Open URL in Chrome Browser
		driver.get("https://www.currys.co.uk");
	}

}
