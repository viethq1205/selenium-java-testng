package testng;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listener.ExtentReportListener;

//@Listeners(ExtentReportListener.class) //đây là cách đầu tiên của Listener (chỉ chạy cho 1 specific class nào đó), cách 2 là đưa dòng này vào runtestcase2.xml (chạy all class)
public class Topic_10_Listener { 
	public static WebDriver driver;
	String projectPath = System.getProperty("user.dir");
   

    @BeforeClass
    public void beforeClass() {
    	System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void TC_01_Register() {
            driver.get("http://live.techpanda.org/index.php/customer/account/create/");

            driver.findElement(By.id("firstname")).sendKeys("automation");
            driver.findElement(By.id("lastname")).sendKeys("fc");
            driver.findElement(By.id("email_address")).sendKeys("afc" + getrandomnumber() + "@hotmail.net");
            driver.findElement(By.id("password")).sendKeys("123456");
            driver.findElement(By.id("confirmation")).sendKeys("123456");
            driver.findElement(By.cssSelector("button[title='Register']")).click();
            
            Assert.assertFalse(driver.findElement(By.xpath("//li[@class='success-msg']//span[text()='Thank you for registering with Main Website Store.']")).isDisplayed());
            //cố tình đổi thành false để làm bài tập về Listener, do false nó mới chụp hình còn pass nó ko chụp
            //nó sẽ chụp hình khúc false lại theo đoạn code mình đã liên kết bên package listener (nhớ chỉnh cái driver thành public static như trên kia thì mới liên kết đc nhé)
    }

    public int getrandomnumber() {
    	Random rand = new Random();
    	return rand.nextInt(9999);
    }
   
    
    @AfterClass
    public void afterClass() {
            //driver.quit();
    }

}
