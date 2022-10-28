package testRunner;

import java.text.ParseException;
import java.util.Properties;
import java.io.IOException;
import dealxApplication.DealxApplication;
import java.io.*;
import org.apache.poi.xssf.usermodel.*;
import com.aventstack.extentreports.ExtentReports;
import com.codoid.products.exception.FilloException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import initializingWebDriver.InitializingWebDriver;

public class RunTest {
    WebDriver driver;
    Properties prop = new Properties();
    ExtentReports extent = null;
    InitializingWebDriver init = new InitializingWebDriver();
    DealxApplication application = new DealxApplication();

    @BeforeTest
    @Parameters({"browserName","url"})
    public void baseURL(String browser, String URL) throws IOException{
        driver = init.initiate(browser, URL);
        prop = init.loadData();
    }
    @Test
    public void testApp() throws InterruptedException, IOException, FilloException, ParseException {
        //TC1 and TC2
        application.searchItems(driver);
        File file = new File("testData//DealxData.xlsx");
        //checking if the file exist on pre-defined directory
        if(file.isFile() && file.exists()) {
            System.out.println("DealxData.xlsx file open successfully.");
        } else {
            System.out.println("Error to open DealxData.xlsx file.");
        }
        XSSFWorkbook workbook = new XSSFWorkbook("testData//DealxData.xlsx");
        XSSFSheet sheetName = workbook.getSheet("TestData");
        System.out.println(sheetName.getSheetName());
        int countRow= sheetName.getPhysicalNumberOfRows();
        //TC3 – Repeat TC1
        for (int i = 1; i < countRow; i++) {
            application.searchItemsData(sheetName,i,driver);
            }
        //TC4
            application.signIn(driver);
        //TC5 and TC6
            application.addToCart(driver);
    }
    @AfterTest
    public void sessionEnds(){
        init.quitDriver(extent, driver);
    }
}
