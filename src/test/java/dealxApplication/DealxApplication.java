package dealxApplication;

import com.codoid.products.exception.FilloException;
import dataApplication.GetData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import initializingWebDriver.InitializingWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class DealxApplication {

    Properties prop = new Properties();
    GetData getData = new GetData();

    public void searchItems(WebDriver driver) throws IOException, InterruptedException {
        String[] searchCriteria = {"Printed Dress","Blouse","Faded Short Sleeve T-shirts"};
        InitializingWebDriver init = new InitializingWebDriver();
        prop = init.loadData();
        for (int i = 0; i < 3;i++){
            //Clearing the search field before sending the key
            driver.findElement(By.xpath(prop.getProperty("txtSearch"))).clear();
            driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
            driver.findElement(By.xpath(prop.getProperty("txtSearch"))).sendKeys(searchCriteria[i]);
            driver.findElement(By.xpath(prop.getProperty("btnSubmit"))).click();
            String searchName = driver.findElement(By.xpath(prop.getProperty("verifySearchItem"))).getText();
            driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
            System.out.println("Test===============");
            searchName = searchName.replaceAll("\"", "");
            if(searchName.equalsIgnoreCase(searchCriteria[i]))
                System.out.println("Actual value : "+ searchName + " and Expected " + searchCriteria[i] + " result Passed");
            else
                System.out.println("Actual value : "+ searchName + " and Expected " + searchCriteria[i] + " result Failed");
            driver.findElement(By.xpath(prop.getProperty("txtSearch"))).clear();
        }
    }
    public void searchItemsData(XSSFSheet s1,int i,WebDriver driver) throws IOException, InterruptedException, FilloException {
        InitializingWebDriver init = new InitializingWebDriver();
        prop = init.loadData();
            String s_Items = getData.getTestData("items", i, s1);
            System.out.println("Test :" + s_Items);
            driver.findElement(By.xpath(prop.getProperty("txtSearch"))).sendKeys(s_Items);
            driver.findElement(By.xpath(prop.getProperty("btnSubmit"))).click();
            String searchName = driver.findElement(By.xpath(prop.getProperty("verifySearchItem"))).getText();
            driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
            System.out.println("Test===============");
            searchName = searchName.replaceAll("\"", "");
            if(searchName.equalsIgnoreCase(s_Items))
                System.out.println("Actual value : "+ searchName + " and Expected " + s_Items + " result Passed");
            else
                System.out.println("Actual value : "+ searchName + " and Expected " + s_Items + " result Failed");
            driver.findElement(By.xpath(prop.getProperty("txtSearch"))).clear();

    }
    public void signIn(WebDriver driver) throws IOException, InterruptedException {
        InitializingWebDriver init = new InitializingWebDriver();
        prop = init.loadData();
        driver.findElement(By.xpath(prop.getProperty("btnLogin"))).click();
        driver.findElement(By.xpath(prop.getProperty("txtEmail"))).sendKeys("ndlovuwz@yahoo.com");
        driver.findElement(By.xpath(prop.getProperty("txtPassword"))).sendKeys("P@ssword#6");
        driver.findElement(By.xpath(prop.getProperty("bntSignIn"))).click();
    }
    public void hoverOver(WebDriver driver,String xPath){
        //xPath = prop.getProperty("btnLogin");
        WebElement ele = driver.findElement(By.xpath(xPath));
        //Creating object of an Actions class
        Actions action = new Actions(driver);
        //Performing the mouse hover action on the target element.
        action.moveToElement(ele).perform();
    }
    public void addToCart(WebDriver driver) throws IOException, ParseException {
        InitializingWebDriver init = new InitializingWebDriver();
        prop = init.loadData();
        String xpath = prop.getProperty("bntWomenTitle");
        hoverOver(driver,xpath);
        driver.findElement(By.xpath(prop.getProperty("btnShirts"))).click();
        String xpathSelectItem = prop.getProperty("btnSelectItem");
        hoverOver(driver,xpathSelectItem);
        driver.findElement(By.xpath(prop.getProperty("btnAddCart"))).click();
        driver.findElement(By.xpath(prop.getProperty("btnProceed"))).click();
        String sQuantity = driver.findElement(By.xpath(prop.getProperty("txtGetQuantity"))).getText();
        String sPrice = driver.findElement(By.xpath(prop.getProperty("txtGetPrice"))).getText();
        //Removing a dollar sign in string
        NumberFormat format = NumberFormat.getCurrencyInstance();
        Number fNumber = format.parse(sPrice);

        float fPrice = fNumber.floatValue();
        int i =0;
        int randSize = randSize();
        System.out.println("Rand number: " + randSize);
        for(;i <= randSize;i++){
            driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
            driver.findElement(By.xpath(prop.getProperty("btnAddQuantity"))).click();
        }
        sQuantity = driver.findElement(By.xpath(prop.getProperty("txtGetQuantity"))).getText();
        int iQuantity = i+1;
        System.out.println("Array size: " + i);
        System.out.println("Rand number: " + randSize);
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        String sTotalPrice = driver.findElement(By.xpath(prop.getProperty("txtGetTotalPrice"))).getText();
        //Removing a dollar sign in string
        NumberFormat formatTotalPrice = NumberFormat.getCurrencyInstance();
        Number fNumberTotalPrice = formatTotalPrice.parse(sTotalPrice);

        float fTotalPrice = fNumberTotalPrice.floatValue();
        float expectTotalPrice = calculateTotal(fPrice,iQuantity);
        if(fTotalPrice == expectTotalPrice)
            System.out.println("Verification has passed the actual value : " + fTotalPrice+ " is Equals to the expected value : " +
                    expectTotalPrice);
        else
            System.out.println("Verification failed the expected value : "+ expectTotalPrice + " is not equals to actual value: " + fTotalPrice);

    }
    public float calculateTotal(float price,int quantity){
        float totalShipping = 2.00F;
        return (price*quantity+totalShipping);
    }
    public int randSize(){
        Random r = new Random();
        int low = 2;
        int high = 5;
        return (r.nextInt(high-low) + low);
    }
}
