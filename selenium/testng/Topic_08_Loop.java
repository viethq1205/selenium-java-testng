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
import org.testng.annotations.Test;

public class Topic_08_Loop { //nghe cái tên thì m biết tác dụng gì rồi đó, ví dụ như tạo liên tiếp 1000 cái acc để đăng ký app
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
   

    @BeforeClass
    public void beforeClass() {
    	System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test (invocationCount = 5) //loop chức năng tạo acc 5 lần
    public void TC_01_Register() {
            driver.get("http://live.techpanda.org/index.php/customer/account/create/");

            driver.findElement(By.id("firstname")).sendKeys("taoditconmemay");
            driver.findElement(By.id("lastname")).sendKeys("nhulontrauloncho");
            driver.findElement(By.id("email_address")).sendKeys("lonbomangnhen" + getrandomnumber() + "@hotmail.net");
            driver.findElement(By.id("password")).sendKeys("malonbenlakhoai");
            driver.findElement(By.id("confirmation")).sendKeys("malonbenlakhoai");
            driver.findElement(By.cssSelector("button[title='Register']")).click();
            
            Assert.assertTrue(driver.findElement(By.xpath("//li[@class='success-msg']//span[text()='Thank you for registering with Main Website Store.']")).isDisplayed());
            
            driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
            driver.findElement(By.xpath("//a[text()='Log Out']")).click();
    }

    public int getrandomnumber() {
    	Random rand = new Random();
    	return rand.nextInt(9999);
    }
   
    

    //thực tế ít người dùng cách như trên
    
    @AfterClass
    public void afterClass() {
            //driver.quit();
    }

}
