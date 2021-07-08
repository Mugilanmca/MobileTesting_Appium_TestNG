package com.appium.android.mobile;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

public class AutomationPracticeMobile {
    public static AppiumDriver<MobileElement> driver = null;

    public static String[] customerData=new String[18];
    String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
    String regexName = "^(?=.*[a-zA-Z]).{2,10}$";
    String regexPassword = "^(?=.*[a-zA-Z])(?=.*[0-9]).{5,}$";
    String regexPostCode = "^[0-9]{5}$";
    String regexMobile = "^07[0-9]{9}$";

    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        AutomationPracticeMobile mobileTesting = new AutomationPracticeMobile();
 //       assertTrue(mobileTesting.validate_Regex_Pattern("07440035509",mobileTesting.regexMobile),"incorrect password Pattern");
        customerData = readExcelData();

        mobileTesting.setDeviceCapability();
        mobileTesting.launch_URL();
        mobileTesting.click_on_sign_in();

        //Customer Registration
//        mobileTesting.navigate_to_register_page();
//        mobileTesting.fill_registration_form();

        //Customer Sign in
       mobileTesting.sign_in_existing_customers();
       mobileTesting.purchange_items();
       mobileTesting.check_cart();
       mobileTesting.checkout_shopping();
    }

    public void setDeviceCapability()
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
    }

    public void launch_URL()
    {
        //Launch URL in mobile browser
        driver.get("http://automationpractice.com/");
    }

    public void scroll_to_element(WebElement elementNav) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementNav);
        Thread.sleep(500);
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

    public void click_on_sign_in() throws InterruptedException {
        //acceptCookies();
        Thread.sleep(2000);
        WebElement sign_in_menu = driver.findElement(By.xpath("//*[contains(text(),'Sign in')]"));
        sign_in_menu.click();
        System.out.println("Move to Sign in Page");
    }


    public void navigate_to_register_page() throws InterruptedException {
        Thread.sleep(2000);
        WebElement elementNavigateReg = driver.findElement(By.xpath("//h3[contains(text(),'Create an account')]"));
        scroll_to_element(elementNavigateReg);
        String eMail_Address = customerData[3];
        assertTrue(validate_Regex_Pattern(eMail_Address,regexEmail),"Invalid email ID provided");
        WebElement txt_email_Reg = driver.findElement(By.id("email_create"));
        enter_email_address(txt_email_Reg,eMail_Address);
        System.out.println("Email Id provided");
        Thread.sleep(2000);
        //  driver.findElement(By.xpath("//*[@resource-id = 'SubmitCreate']")).click();
        WebElement btn_submit_details_Reg =   driver.findElement(By.id("SubmitCreate"));
        btn_submit_details_Reg.click();
        System.out.println("Submitted the email address");
    }

    public void sign_in_existing_customers() throws InterruptedException {
        Thread.sleep(2000);
        WebElement elementNavigateLogin = driver.findElement(By.xpath("//h3[contains(text(),'Already registered?')]"));
        scroll_to_element(elementNavigateLogin);
        WebElement txt_email_Login = driver.findElement(By.id("email"));
        enter_email_address(txt_email_Login,customerData[3]);
        enter_cust_Password(customerData[4]);
        submit_customer_sign_in();
        Thread.sleep(2000);
    }

    public void purchange_items() throws InterruptedException {
        search_item();
        select_product();
        Thread.sleep(2000);
        WebElement btn_add_to_cart = driver.findElement(By.xpath("//p[@id = 'add_to_cart']/button/span"));
        scroll_to_element(btn_add_to_cart);
        select_quantity();
        select_size();
        select_color();
        add_cart();
    }

    public void check_cart() throws InterruptedException {
        continue_shopping();
        Thread.sleep(2000);
        click_on_cart();
        Thread.sleep(2000);
        validate_cart_product_count();
    }

    public void checkout_shopping() throws InterruptedException {
        click_checkout_from_cart();
        Thread.sleep(2000);
        proceed_to_checkout_Summary();
        Thread.sleep(2000);
        add_shopping_note();
        proceed_to_checkout_AddConfirm();
        Thread.sleep(2000);
        select_delivery_option();
        select_terms_and_conditions();
        proceed_to_checkout_Shipping();
        Thread.sleep(2000);
        select_payment_method();
        Thread.sleep(2000);
        select_confirm_order();
        Thread.sleep(2000);
        validate_booking_confirmation();
    }

    public void click_checkout_from_cart(){
        WebElement btn_cart_checkout= driver.findElement(By.xpath("//*[@title= 'Continue shopping']"));
        System.out.println("btn_cart_checkout identified");
        btn_cart_checkout.click();
    }

    public void proceed_to_checkout_Summary() throws InterruptedException {
        WebElement btn_checkout= driver.findElement(By.xpath("//*[@title= 'Proceed to checkout']"));
        System.out.println("btn_checkout identified");
        scroll_to_element(btn_checkout);
        btn_checkout.click();
    }

    public void add_shopping_note()
    {
        WebElement txt_shopping_message = driver.findElement(By.name("message"));
        System.out.println("txt_shopping_message identified");
        txt_shopping_message.sendKeys("Purchange finishe ready for billing");
    }

    public void proceed_to_checkout_AddConfirm() throws InterruptedException {
        WebElement btn_checkout_AddConfirm= driver.findElement(By.xpath("//*[@title= 'processAddress']"));
        System.out.println("btn_checkout_AddConfirm identified");
        scroll_to_element(btn_checkout_AddConfirm);
        btn_checkout_AddConfirm.click();
    }

    public void select_delivery_option(){
        WebElement btn_select_shipping_option = driver.findElement(By.id("delivery_option_535280_0"));
        System.out.println("btn_select_shipping_option identified");
        btn_select_shipping_option.click();
    }

    public void select_terms_and_conditions(){
        WebElement chk_terms_and_condition = driver.findElement(By.id("cgv"));
        System.out.println("chk_terms_and_condition identified");
        chk_terms_and_condition.click();
    }

    public void proceed_to_checkout_Shipping() throws InterruptedException {
        WebElement btn_checkout_shipping= driver.findElement(By.xpath("//*[@title= 'processCarrier']"));
        System.out.println("btn_checkout_shipping identified");
        scroll_to_element(btn_checkout_shipping);
        btn_checkout_shipping.click();
    }

    public void select_payment_method() throws InterruptedException {
        WebElement btn_checkout_payment_method= driver.findElement(By.xpath("//*[@title= 'Pay by bank wire']/span[1]"));
        System.out.println("btn_checkout_payment_method identified");
        scroll_to_element(btn_checkout_payment_method);
        btn_checkout_payment_method.click();
    }

    public  void select_confirm_order() throws InterruptedException {
        WebElement btn_confirm_order= driver.findElement(By.xpath("//*[@type = 'submit']"));
        System.out.println("btn_confirm_order identified");
        scroll_to_element(btn_confirm_order);
        btn_confirm_order.click();
    }

    public void validate_booking_confirmation(){
        WebElement lbl_confirm_booking = driver.findElement(By.xpath("//div[@class='box']//p/strong"));
        System.out.println("lbl_confirm_booking identified");
        assertTrue(lbl_confirm_booking.getText().equalsIgnoreCase("//div[@class='box']//p/strong"),"Purchange failed");
    }

    public void search_item() throws InterruptedException {
        Thread.sleep(1000);
        WebElement lbl_search_product = driver.findElement(By.id("search_query_top"));
        lbl_search_product.sendKeys("Dress");
        System.out.println("Entered string for search");
        Thread.sleep(1000);
        WebElement btn_submit_search = driver.findElement(By.name("submit_search"));
        btn_submit_search.click();
        System.out.println("Submitted search request");
        Thread.sleep(5000);
    }

    public void select_product() throws InterruptedException {
        WebElement lnk_top_seller = driver.findElement(By.xpath("//*[contains(text(),'Top sellers')]"));
        lnk_top_seller.click();
        System.out.println("Top sellers selected");
        //       WebElement lnk_categories = driver.findElement(By.xpath("//div[@id = 'left_column']//*[contains(text(),'Categories')]"));
        WebElement lnk_select_item = driver.findElement(By.xpath("//div[@id = 'left_column']//a[contains(text(),'Printed Summer Dress')]"));
      //  scroll_to_element(lnk_select_item);
        lnk_select_item.click();
        System.out.println("Click on item to be purchased");
        Thread.sleep(5000);
    }

    public void continue_shopping(){
        WebElement btn_continue_shopping= driver.findElement(By.xpath("//div[@class = 'button-container']//*[@title= 'Continue shopping']"));
        System.out.println("btn_continue_shopping identified");
        btn_continue_shopping.click();
        System.out.println("btn_continue_shopping clicked");
    }

    public void click_on_cart() {
        WebElement lbl_cart_details = driver.findElement(By.xpath("//*[@title= 'View my shopping cart']"));
        System.out.println("lbl_cart_details identified");
        lbl_cart_details.click();
    }
    public void validate_cart_product_count(){
        WebElement lbl_cart_product_count = driver.findElement(By.xpath("//*[@title= 'View my shopping cart']/span[1]"));
        System.out.println("lbl_cart_product_count identified");
        assertTrue(lbl_cart_product_count.getText().equalsIgnoreCase("1"),"Incorrect product count");
    }

    public void select_quantity(){
        WebElement img_increase_product = driver.findElement(By.cssSelector("i[class='icon-plus']"));
        System.out.println("img_increase_product identified");
        img_increase_product.click();
        //        WebElement img_deccrease_product = driver.findElement(By.cssSelector("i[class='icon-minus']"));
    }

    public void select_color(){
        WebElement img_select_color = driver.findElement(By.xpath("//*[@id = 'color_to_pick_list']/li"));
        System.out.println("img_select_color identified");
        img_select_color.click();
    }

    public void select_size(){
        Select selecSize = new Select(driver.findElement(By.id("group_1")));
        System.out.println("selecSize identified");
        selecSize.selectByVisibleText("S");
    }

    public  void add_cart(){
        WebElement btn_add_to_cart = driver.findElement(By.xpath("//p[@id = 'add_to_cart']/button/span"));
        System.out.println("btn_add_to_cart identified");
        btn_add_to_cart.click();
    }

    public void validate_Login_confirmation(){
        WebElement lbl_login_account = driver.findElement(By.xpath("//a[@class='account']/span"));
        System.out.println("lbl_login_account identified");
        assertTrue(lbl_login_account.getText().equalsIgnoreCase(customerData[1]+" "+customerData[2]),
                "Incorrect login details");
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
        //assertTrue(validate_firstName(customerData[1]));
        //assertTrue(validate_lastName(customerData[2]));
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

    public void select_title(String title) {
        driver.findElement(By.id(title)).click();

    }

    public void enter_email_address(WebElement elementEmail, String emailAdd) {
        elementEmail.sendKeys(emailAdd);
        System.out.println("Email address \t" +emailAdd);
    }

    public void enter_fName(String fName) {
        assertTrue(validate_Regex_Pattern(fName,regexName),"incorrect name Pattern");
        WebElement txt_customer_firstname = driver.findElement(By.id("customer_firstname"));
        txt_customer_firstname.sendKeys(fName);
        System.out.println("Customer First Name \t" +fName);
    }

    public void enter_lName(String lName) {
        assertTrue(validate_Regex_Pattern(lName,regexName),"incorrect name Pattern");
        WebElement txt_customer_lastname = driver.findElement(By.id("customer_lastname"));
        txt_customer_lastname.sendKeys(lName);
        System.out.println("Customer Last Name \t" +lName);
    }

    public void enter_cust_Password(String custPwd) {
        assertTrue(validate_Regex_Pattern(custPwd,regexPassword),"incorrect password Pattern");
        WebElement txt_passwd = driver.findElement(By.id("passwd"));
        txt_passwd.sendKeys(custPwd);
        System.out.println("Customer has entered the password");
    }

    public void select_days_DOB(String day) {
        Select selectDay = new Select(driver.findElement(By.id("days")));
        selectDay.selectByValue(String.valueOf((int)Double.parseDouble(day)));
        System.out.println("Customer Birth day  \t" +(int)Double.parseDouble(day));
    }

    public void select_mon_DOB(String month) {
        Select selectMonth = new Select(driver.findElement(By.id("months")));
        selectMonth.selectByValue(String.valueOf((int)Double.parseDouble(month)));
        System.out.println("Customer Birth month  \t" +(int)Double.parseDouble(month));
    }

    public void select_year_DOB(String year) {
        Select selectYear = new Select(driver.findElement(By.id("years")));
        selectYear.selectByValue(String.valueOf((int)Double.parseDouble(year)));
        System.out.println("Customer Birth year \t" +(int)Double.parseDouble(year));
    }

    public boolean validate_firstName(String fName) {
        WebElement txt_firstname = driver.findElement(By.id("firstname"));
        boolean checkfName = false;
        if(txt_firstname.getText().equalsIgnoreCase(fName)) {
            checkfName = true;
            System.out.println("Correct first name populated");
        }
        else
            System.out.println("Incorrect first name populated");
            return checkfName;
    }

    public boolean validate_lastName(String lName) {
        WebElement txt_lastname = driver.findElement(By.id("lastname"));
        boolean checklName = false;
        if(txt_lastname.getText().equalsIgnoreCase(lName)) {
            checklName = true;
            System.out.println("Correct last name populated");
        }
        else
            System.out.println("Incorrect last name populated");
        return checklName;
    }

    public void enter_cust_company(String companyName) {
        WebElement txt_company = driver.findElement(By.id("company"));
        txt_company.sendKeys(companyName);
        System.out.println("Customer company name \t" +companyName);
    }

    public void enter_cust_Address1(String address1) {
        WebElement txt_address1 = driver.findElement(By.id("address1"));
        txt_address1.sendKeys(address1);
        System.out.println("Customer address line1 \t" +address1);
    }

    public void enter_cust_Address2(String address2) {
        WebElement txt_address2 = driver.findElement(By.id("address2"));
        txt_address2.sendKeys(address2);
        System.out.println("Customer address line2 \t"+address2);
    }

    public void enter_cust_City(String cityName) {
        WebElement txt_city = driver.findElement(By.id("city"));
        txt_city.sendKeys(cityName);
        System.out.println("Customer city is \t" +cityName);
    }

    public void select_cust_State(String stateName) {
        Select selectState = new Select(driver.findElement(By.id("id_state")));
        selectState.selectByVisibleText(stateName);
        System.out.println("Customer State name is \t" +stateName);
    }

    public void enter_cust_Postcode(String postcode) {
        assertTrue(validate_Regex_Pattern(postcode,regexPostCode),"incorrect post code Pattern");
        WebElement txt_postcode = driver.findElement(By.id("postcode"));
        txt_postcode.sendKeys(postcode);
        System.out.println("Customer postcode is \t" + postcode);
    }

    public void select_cust_Country(String country) {
        Select selectCountry = new Select(driver.findElement(By.id("id_country")));
        selectCountry.selectByVisibleText(country);
        System.out.println("Customer country name is \t" +country);
    }

    public void enter_cust_AdditionalInfo(String additionalInfo) {
        WebElement txt_other_details = driver.findElement(By.id("other"));
        txt_other_details.sendKeys(additionalInfo);
        System.out.println("Customer provided additional info as \t" + additionalInfo);
    }

    public void enter_cust_ContactInfo(String mobileNumber) {
        assertTrue(validate_Regex_Pattern(mobileNumber,regexMobile),"incorrect mobile number Pattern");
        WebElement txt_phone_mobile = driver.findElement(By.id("phone_mobile"));
        txt_phone_mobile.sendKeys(mobileNumber);
        System.out.println("Customer mobile number \t" + mobileNumber);
    }
    public void enter_cust_FutureReference(String futureRef) {
        WebElement txt_alias = driver.findElement(By.id("alias"));
        txt_alias.sendKeys(futureRef);
        System.out.println("Customer future reference details \t" + futureRef);
    }

    public void submitForm() {
        WebElement btn_submit_reg_acc_details = driver.findElement(By.id("submitAccount"));
        btn_submit_reg_acc_details.click();
        System.out.println("Form Sumbitted \t");
    }

    public void submit_customer_sign_in(){
        WebElement btn_submit_details_Login =  driver.findElement(By.id("SubmitLogin"));
        btn_submit_details_Login.click();
        System.out.println("Sign in details Sumbitted \t");
    }

    public void validate_Registration_Successful() {
        WebElement lbl_page_heading = driver.findElement(By.className("page-heading"));
        assertTrue(lbl_page_heading.isDisplayed(),"Registration not Successful");
        System.out.println(lbl_page_heading.getText());
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
                       // System.out.print(cell.getNumericCellValue()+ j+ "\t");
                        customerData[j] = String.valueOf(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                       // System.out.print(cell.getStringCellValue()+j+ "\t");
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

    public void validate_alert_for_registration() {
        driver.findElement(By.xpath("//*[@resource-id='auth-email-missing-alert']")).isDisplayed();
        assertTrue(driver.findElement(By.xpath("//*[@resource-id='auth-alert-window']")).isDisplayed());
        System.out.println("Alert is visible to the customer");
    }



}
