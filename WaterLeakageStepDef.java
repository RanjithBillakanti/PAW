package Cucumber.StepDef;


import Generics.GenericMethods;
import PageObjects.LoginAPPO;
import PageObjects.MyTask;
import PageObjects.WaterLeakage;
import TestRunner.Hooks;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class WaterLeakageStepDef {
    private WebDriver driver = Hooks.driver; //initialiaztion of hooks driver to local webdriver
    private WebDriverWait wait = new WebDriverWait(driver, 60);//driver wait for 60 seconds
    public String WaterLeakageComplaintNo; //initilization of string variable

    @Given("^Report a water leakage by selecting values([^\"]*),([^\"]*),([^\"]*),([^\"]*),([^\"]*)$")
    public void reportAWaterLeakageBySelectingValuesGovernorateVillayathWaynumberBuildingnumber(String governorate, String villayath, String waynumber, String buildingnumber, String TownSubArea) throws Throwable {
        Thread.sleep(5000);
        WaterLeakage.ReportaWaterLeakage(driver).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOf(WaterLeakage.IsThisYourWaterLeakageLocation(driver))).isDisplayed();//driver will wait till condition becomes true
        WaterLeakage.IsThisYourWaterLeakageLocation(driver).click();// clicking on is this your water leakage location popup
        WebElement G = WaterLeakage.Governorate(driver);

        Select drpGovernorate = new Select(G);//select object creation
        drpGovernorate.selectByVisibleText(governorate);//selecting governorate from drop down list which is visible

        Thread.sleep(3000);
        WebElement v = WaterLeakage.Villayath(driver); //creating local web element v and assigning by calling field from waterleakage class
        Select drpVillayath = new Select(v);
        drpVillayath.selectByVisibleText(villayath);//selecting villayath from the drop down list

        Thread.sleep(3000);
        WebElement sa = WaterLeakage.SubArea(driver);
        Select drpSubArea = new Select(sa);

        drpSubArea.selectByVisibleText(TownSubArea);//selecting town or sub area from the drop down

        WaterLeakage.WayNumber(driver).sendKeys(waynumber);//inputing text to the way number field

        WaterLeakage.BuildingNumber(driver).sendKeys(buildingnumber);//input building number field

        WaterLeakage.Continue(driver).click();//clicking on continue button
        //wait.until(ExpectedConditions.visibilityOf(WaterLeakage.Browse(driver)));
        Thread.sleep(3000);

        WaterLeakage.Browse(driver).click();//clicking on browse button
        String workingDir = System.getProperty("user.dir");//creating local variable and assigning current working drirectory path
        Runtime.getRuntime().exec(workingDir + "\\AutoITexecutables\\uploaddocument.exe");//executing auto it code
        JavascriptExecutor js = (JavascriptExecutor) driver;//java script object creation
        js.executeScript("window.scrollBy(0,200)");//scroll down
        Thread.sleep(3000);

        WaterLeakage.Submit(driver).click();//clicking on submit button
        Thread.sleep(3000);
        //Adding cookie
        String complaintnumber = driver.findElement(By.xpath("//div[@class='inner-modal']//p[2]/h5[1]")).getText();//reading complaint number from the application and storing in the local variable
        WaterLeakage.Ok(driver).click();//clicking on ok button
        Thread.sleep(9000);

        Cookie cookie = new Cookie("Complaint_No", complaintnumber);//creating cookie object
        driver.manage().addCookie(cookie);//adding cookie to driver
        //Retriving cookie
        WaterLeakageComplaintNo = GenericMethods.getValueOfCookieNamed("Complaint_No");
        System.out.println(WaterLeakageComplaintNo);//printing complaint number
    }
//Auraportal Water Leakage Code starts from here

    @Given("^Entering ([^\"]*),([^\"]*)$")
    public void enteringReferenceNumber(String user, String pwd) throws Throwable {
        System.out.println("i am here");
        //Frame1
        String refno;//initialization refo of string type
        WaterLeakageComplaintNo = GenericMethods.getValueOfCookieNamed("Complaint_No");//assigning complaint number stored in the cookie to waterleakage complaint no.
        System.out.println("My Cookie:" + WaterLeakageComplaintNo);//printing complaint number which is stored in the cookie
        String urls = "https://aurauat.diam.om/Login.aspx";//assigning paw test url to string variable
        driver.get(urls);//launching the url
        Thread.sleep(4000);
        driver.switchTo().defaultContent();//After switching to the frame switching back to the default content.
        WebElement ok = wait.until(presenceOfElementLocated(By.xpath("//div[@class='body-e']/div/a")));//driver will wait until presence of OK element
        if (ok.isDisplayed()) {//condition checking if ok button is displayed
            ok.click(); // then clcking on ok button
        }
        wait.until(ExpectedConditions.visibilityOf(LoginAPPO.Username(driver)));//driver will wait till the visibility of user name field
        LoginAPPO.Username(driver).sendKeys(user);//passing values to the username field

        wait.until(ExpectedConditions.visibilityOf(LoginAPPO.Password(driver)));//driver wait unitl the visibility of password field visibil
        LoginAPPO.Password(driver).sendKeys(pwd);//passing values to password field.

        LoginAPPO.Loginbtn(driver).click();//clicking on login button
        refno = WaterLeakageComplaintNo;//storing the value which is stored in the cookie
        System.out.println(refno);//printing reference number
        Thread.sleep(5000);
        WebElement ok1 = wait.until(presenceOfElementLocated(By.xpath("//div[@class='body-e']/div/a")));
        if (ok1.isDisplayed()) {
            ok1.click();
        }
        WebElement fr = wait.until(presenceOfElementLocated(By.id("marcocentral")));
        driver.switchTo().frame(fr);
        System.out.println("i switched to frame 1");

        JavascriptExecutor exe = (JavascriptExecutor) driver;
        Integer numberOfFrames = Integer.parseInt(exe.executeScript("return window.length").toString());
        System.out.println("Number of iframes on the page are " + numberOfFrames);

        //Frame2
        WebElement fr1 = wait.until(presenceOfElementLocated(By.xpath("//iframe[contains(@src,'/BPM_TareasProceso_Lista.aspx?')]")));
        driver.switchTo().frame(fr1);
        System.out.println("i switched to frame 2");
        Thread.sleep(3000);
        JavascriptExecutor exe1 = (JavascriptExecutor) driver;
        Integer numberOfFrames1 = Integer.parseInt(exe1.executeScript("return window.length").toString());
        System.out.println("Number of iframes on the page are " + numberOfFrames1);
        WebElement Refno = driver.findElement(By.xpath("//html[1]/body[1]/form[1]/div[3]/div[3]/div[1]/div[1]/div[7]/span[1]/input[1]"));
        wait.until(ExpectedConditions.visibilityOf(Refno));
        Refno.sendKeys(refno);

    }

    @Then("^Click on Proceed button$")
    public void clickOnProceedButton() throws Throwable {
        MyTask.Proceedbtn(driver).click();
        System.out.println("Clicked on Proceed button");
    }

    @Then("^Select the row & Click$")
    public void selectTheRowClick() throws Throwable {
        Thread.sleep(5000);
        WebElement clik = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//table[@id='dgProcessTask_body_container']/tbody/tr[1]"))));
        clik.click();
        GenericMethods.SwitchingWindow();
        System.out.println("Switched to Child window");
    }

    @Then("^Click on Transaction History$")
    public void clickOnTransactionHistory() throws Throwable {
        System.out.println("Iam Here");
        WebElement fr2 = wait.until(presenceOfElementLocated(By.xpath("//iframe[contains(@src,'/BPM_TareaPersonal.aspx?')]")));
        driver.switchTo().frame(fr2);
        System.out.println("i switched to frame 1");
        Thread.sleep(3000);
        //Code for Clicking on TransactionHistory
        String test = "//div[@id='ctrlElementoAnexo_Frm_7838']/div/div/div/button";
        new WebDriverWait(driver, 300).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(test)));
        WebElement tests = driver.findElement(By.xpath("//div[@id='ctrlElementoAnexo_Frm_7838']/div/div/div/button"));
        tests.click();
        //Code for Close in Transaction history window
        Thread.sleep(3000);
        WebElement close = driver.findElement(By.xpath("//div[@id='ctrlElementoAnexo_Div_Container_599']/div[2]//div/div/div/button"));
        wait.until(ExpectedConditions.visibilityOf(close));
        close.click();
        Thread.sleep(7000);
    }

    public static boolean isloadComplete(WebDriver driver) {
        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("loaded")
                || ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }

    @Then("^Click on Accept$")
    public void clickOnAccept() throws Throwable {
        WebElement acpt1 = driver.findElement(By.xpath("//div[@id='ctrlElementoAnexo_Frm_7839']/div/div/div/button"));
        wait.until(ExpectedConditions.visibilityOf(acpt1));
        acpt1.click();

        //code for selecting yes/no drop down options - Ranjith
        Thread.sleep(3000);
        WebElement LeakageDetailsVerified = driver.findElement(By.xpath("//select[@id='ctrlElementoAnexo_7877']"));
        Select drpLeakageDetailsVerified = new Select(LeakageDetailsVerified);
        drpLeakageDetailsVerified.selectByIndex(1);
        //Click on Submit button
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@id='ctrlElementoAnexo_7882']")).click();
        //code for Inprogress state
        WebElement fr = wait.until(presenceOfElementLocated(By.id("marcocentral")));
        driver.switchTo().frame(fr);
        System.out.println("Switced to proceed to button frame.....");
        //Thread.sleep(3000);
        //driver.switchTo().defaultContent();
        WebElement fr2 = wait.until(presenceOfElementLocated(By.xpath("//iframe[contains(@src,'/BPM_TareasProceso_Lista.aspx?')]")));
        driver.switchTo().frame(fr2);
        Thread.sleep(3000);
        MyTask.Proceedbtn(driver).click();
        System.out.println("Second time clicking on proceed button");

        Thread.sleep(5000);
        WebElement clik = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//table[@id='dgProcessTask_body_container']/tbody/tr[1]"))));
        clik.click();
        GenericMethods.SwitchingWindow();
        System.out.println("Second time clicking on row...");
        //Closing the request
        Thread.sleep(3000);
        GenericMethods.SwitchingWindow();
        WebElement fr3 = wait.until(presenceOfElementLocated(By.xpath("//iframe[contains(@src,'/BPM_TareaPersonal.aspx?')]")));
        driver.switchTo().frame(fr3);
        driver.findElement(By.xpath("//button[@id='ctrlElementoAnexo_7325']")).click();
        System.out.println("...CLOSING THE REQUEST...");

        Thread.sleep(3000);
        driver.findElement(By.xpath("//a[@class='apf-button-icon apf-plus']")).click();
        Thread.sleep(3000);
        System.out.println("...It was Working upto here...");

        GenericMethods.SwitchingWindow();
        System.out.println("Switching to window...");

        WebElement sf = wait.until(presenceOfElementLocated(By.xpath("//iframe[contains(@src,'/OtrosAccesos/MultipleUploadHtml.aspx?')]")));
        driver.switchTo().frame(sf);
        driver.findElement(By.xpath("//a[@id='uploader_browse']")).click();

        Thread.sleep(3000);
        String workingDir = System.getProperty("user.dir");
        Runtime.getRuntime().exec(workingDir + "\\AutoITexecutables\\uploaddocument2.exe");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println("Attached successfully");
        driver.switchTo().defaultContent();
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html[1]/body[1]/form[1]/div[3]/div[3]/div[1]/div[1]/div[2]/div[2]/div[4]/div[1]/div[17]/div[1]/div[1]/div[1]/button[1]")).click();

    }
}
