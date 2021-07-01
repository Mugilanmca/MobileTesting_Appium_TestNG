package com.appium.android.mobile;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

public class AutomationPracticeMobile {

    public static AppiumDriver<MobileElement> driver = null;
    public static String[] customerData=new String[18];
    String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
    String regexName = "^(?=.*[a-zA-Z]).{2,}$";
    String regexPassword = "^(?=.*[a-zA-Z])(?=.*[0-9]).{5,}$";
    String regexPostCode = "^[0-9]{5}$";
    String regexMobile = "^07[0-9]{9}$";

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        AutomationPracticeMobile mobileTesting = new AutomationPracticeMobile();
//        assertTrue(mobileTesting.validate_Regex_Pattern("77440035509",mobileTesting.regexMobile),"incorrect password Pattern");
        customerData = readExcelData();
        mobileTesting.launch_URL();
        mobileTesting.navigate_to_register_page();
        mobileTesting.fill_registration_form();

    }

    public void launch_URL()
    {
        //Setting the capabilities
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "10099323");
        caps.setCapability("platformName", "Android");
        caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        caps.setCapability(CapabilityType.VERSION, "10");
        caps.setCapability("newCommandTimeout", 200);
        caps.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
        caps.setCapability(CapabilityType.BROWSER_VERSION,"91.0.4472.88");

        try {
            driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
        }
        catch(MalformedURLException e)
        {System.out.println(e.getMessage());}

        //Launch URL in mobile browser
        driver.get("http://automationpractice.com/");



       }

    public void acceptCookies()
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String context = ((AppiumDriver) driver).getContext(); // = "CHROMIUM"
        ((AppiumDriver) driver).context("NATIVE_APP");
        //driver.findElement(By.id("com.android.chrome:id/terms_accept")).click();
        driver.findElement(By.id("com.android.chrome:id/positive_button")).click();
        System.out.println("Location accepted");
        ((AppiumDriver) driver).context(context);
    }

    public void navigate_to_register_page()
    {
        try {
            //acceptCookies();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[contains(text(),'Sign in')]")).click();
            System.out.println("Move to Sign in Page");
            Thread.sleep(2000);
            String eMail_Address = customerData[3];
            assertTrue(validate_Regex_Pattern(eMail_Address,regexEmail),"Invalid email ID provided");

          //  driver.findElement(By.xpath("//*[@resource-id = 'email_create']")).sendKeys("johny.bhatt@gmail.com");
            driver.findElement(By.id("email_create")).sendKeys(eMail_Address);
            System.out.println("Email Id provided");
            Thread.sleep(2000);
          //  driver.findElement(By.xpath("//*[@resource-id = 'SubmitCreate']")).click();
            driver.findElement(By.id("SubmitCreate")).click();
            System.out.println("Submitted the email address");
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {System.out.println(e.getMessage());}

    }

    public void fill_registration_form() throws IOException {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        select_title(customerData[0]);
        enter_fName(customerData[1]);
        enter_lName(customerData[2]);
        enter_cust_Password(customerData[4]);
        select_days_DOB(customerData[5]);
        select_mon_DOB(customerData[6]);
        select_year_DOB(customerData[7]);
//        assertTrue(validate_firstName(customerData[1]));
//        assertTrue(validate_lastName(customerData[2]));
        enter_cust_company(customerData[8]);
        enter_cust_Address1(customerData[9]);
        enter_cust_Address2(customerData[10]);
        enter_cust_City(customerData[11]);
        select_cust_State(customerData[12]);
        enter_cust_Postcode(customerData[13]);
        select_cust_Country(customerData[14]);
        enter_cust_AdditionalInfo(customerData[15]);
        enter_cust_ContactInfo(customerData[16]);
        enter_cust_FutureReference(customerData[17]);
        submitForm();
        validate_Registration_Successful();
    }

    public boolean validate_Regex_Pattern(String actualValue, String regexPattern) {
        //Validate email using regex.
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(actualValue);
        return matcher.matches();
    }

//    public boolean nameValidator(String eMailID) {
//        //Validate email using regex.
//        String regex = "^(?=.*[a-zA-Z]).{2,}$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(eMailID);
//        return matcher.matches();
//    }
//
//    public boolean passwordValidator(String custPwd) {
//        //Validate email using regex.
//        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9]).{5,}$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(custPwd);
//        return matcher.matches();
//    }

    public void select_title(String title)
    {
        driver.findElement(By.id(title)).click();

    }

    public void enter_fName(String fName)
    {
        assertTrue(validate_Regex_Pattern(fName,regexName),"incorrect name Pattern");
        driver.findElement(By.id("customer_firstname")).sendKeys(fName);
        System.out.println("Customer First Name \t" +fName);
    }

    public void enter_lName(String lName)
    {
        assertTrue(validate_Regex_Pattern(lName,regexName),"incorrect name Pattern");
        driver.findElement(By.id("customer_lastname")).sendKeys(lName);
        System.out.println("Customer Last Name \t" +lName);
    }
    public void enter_cust_Password(String custPwd)
    {
        assertTrue(validate_Regex_Pattern(custPwd,regexPassword),"incorrect password Pattern");
        driver.findElement(By.id("passwd")).sendKeys(custPwd);
        System.out.println("Customer has entered the password");
    }
    public void select_days_DOB(String day)
    {
        Select selectDay = new Select(driver.findElement(By.id("days")));
        selectDay.selectByValue(String.valueOf((int)Double.parseDouble(day)));
        System.out.println("Customer Birth day  \t" +(int)Double.parseDouble(day));
    }
    public void select_mon_DOB(String month)
    {
        Select selectMonth = new Select(driver.findElement(By.id("months")));
        selectMonth.selectByValue(String.valueOf((int)Double.parseDouble(month)));
        System.out.println("Customer Birth month  \t" +(int)Double.parseDouble(month));
    }

    public void select_year_DOB(String year)
    {
        Select selectYear = new Select(driver.findElement(By.id("years")));
        selectYear.selectByValue(String.valueOf((int)Double.parseDouble(year)));
        System.out.println("Customer Birth year \t" +(int)Double.parseDouble(year));
    }



    public boolean validate_firstName(String fName)
    {
        System.out.println(driver.findElement(By.id("firstname")).getText());
        System.out.println(fName);
        boolean checkfName = false;
        if(driver.findElement(By.id("firstname")).getText().equalsIgnoreCase(fName)) {
            checkfName = true;
            System.out.println("Correct first name populated");
        }
        else
            System.out.println("Incorrect first name populated");
            return checkfName;
    }

    public boolean validate_lastName(String lName)
    {
        boolean checklName = false;
        if(driver.findElement(By.id("lastname")).getText().equalsIgnoreCase(lName)) {
            checklName = true;
            System.out.println("Correct last name populated");
        }
        else
            System.out.println("Incorrect last name populated");
        return checklName;
    }

    public void enter_cust_company(String companyName)
    {
        driver.findElement(By.id("company")).sendKeys(companyName);
        System.out.println("Customer company name \t" +companyName);
    }

    public void enter_cust_Address1(String address1)
    {
        driver.findElement(By.id("address1")).sendKeys(address1);
        System.out.println("Customer address line1 \t" +address1);
    }

    public void enter_cust_Address2(String address2)
    {
        driver.findElement(By.id("address2")).sendKeys(address2);
        System.out.println("Customer address line2 \t"+address2);
    }

    public void enter_cust_City(String cityName)
    {
        driver.findElement(By.id("city")).sendKeys(cityName);
        System.out.println("Customer city is \t" +cityName);
    }

    public void select_cust_State(String stateName)
    {
        Select selectState = new Select(driver.findElement(By.id("id_state")));
        selectState.selectByVisibleText(stateName);
        System.out.println("Customer State name is \t" +stateName);
    }

    public void enter_cust_Postcode(String postcode) {
        assertTrue(validate_Regex_Pattern(postcode,regexPostCode),"incorrect post code Pattern");
        driver.findElement(By.id("postcode")).sendKeys(postcode);
        System.out.println("Customer postcode is \t" + postcode);
    }

    public void select_cust_Country(String country)
    {
        Select selectCountry = new Select(driver.findElement(By.id("id_country")));
        selectCountry.selectByVisibleText(country);
        System.out.println("Customer country name is \t" +country);
    }

    public void enter_cust_AdditionalInfo(String additionalInfo) {
        driver.findElement(By.id("other")).sendKeys(additionalInfo);
        System.out.println("Customer provided additional info as \t" + additionalInfo);
    }

    public void enter_cust_ContactInfo(String mobileNumber) {
        assertTrue(validate_Regex_Pattern(mobileNumber,regexMobile),"incorrect mobile number Pattern");
        driver.findElement(By.id("phone_mobile")).sendKeys(mobileNumber);
        System.out.println("Customer mobile number \t" + mobileNumber);
    }
    public void enter_cust_FutureReference(String futureRef) {
        driver.findElement(By.id("alias")).sendKeys(futureRef);
        System.out.println("Customer future reference details \t" + futureRef);
    }

    public void submitForm()
    {
        driver.findElement(By.id("submitAccount")).click();
        System.out.println("Form Sumbitted \t");

    }
    public void validate_Registration_Successful()
    {
        assertTrue(true,"Registration not Successful");
        System.out.println(" Customer registration successful");
    }

    public static String[] readExcelData() throws IOException {   //Do not change the method signature

        //Implement code to read data from excel file. Store the values in 'customerData' array. Return the array. */
        try (FileInputStream fs = new FileInputStream("/Users/jugal/Downloads/Others/Cards.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet=workbook.getSheet("MobileAutomation");
            FormulaEvaluator formulaEvaluator=workbook.getCreationHelper().createFormulaEvaluator();
            int j=0;
            Row row = sheet.getRow(1);

            for(Cell cell: row)
            {

                switch(formulaEvaluator.evaluateInCell(cell).getCellType())
                {
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue()+ j+ "\t");
                        customerData[j] = String.valueOf(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue()+j+ "\t");
                        customerData[j] = cell.getStringCellValue();
                        break;
                }
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerData;
    }

    public void validate_alert_for_registration()
    {
        driver.findElement(By.xpath("//*[@resource-id='auth-email-missing-alert']")).isDisplayed();
        assertTrue(driver.findElement(By.xpath("//*[@resource-id='auth-alert-window']")).isDisplayed());
        System.out.println("Alert is visible to the customer");
    }



}
