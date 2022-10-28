package initializingWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.aventstack.extentreports.ExtentReports;
import io.github.bonigarcia.wdm.WebDriverManager;

public class InitializingWebDriver {
    private WebDriver driver;
    private Properties prop = new Properties();
    public WebDriver initiate (String browserName,String url) throws IOException{
        if (browserName.equalsIgnoreCase("Chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        else {
            System.out.println("Please check browser");

        }
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        prop = loadData();
        driver.get(url);
        driver.manage().window().maximize();
        return driver;
    }

    public Properties loadData() throws IOException{
        File path = new File("dealx.properties");
        FileInputStream files = new FileInputStream(path);
        prop.load(files);
        return prop;
    }
    public void quitDriver(ExtentReports extent, WebDriver driver){
        //extent.flush();
        driver.close();
    }
}
