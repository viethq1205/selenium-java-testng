package testng;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Topic_05_Parameter_Multiple_Browser { 
	//chạy test 1 lúc trên nhiều browser, môi trường (dev, staging) 
	//dùng runtestcase.xml để chạy nha, ko run test trên đây (phần này thì có chạy chung với cái envi, chạy lại mỗi browser thôi thì xóa phần global đi là đc)
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
    By emailTextbox = By.xpath("//*[@id='email']");
    By passwordTextbox = By.xpath("//*[@id='pass']");
    By loginButton = By.xpath("//*[@id='send2']");

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
    	//đây là cách if else set chạy nhiều trình duyệt (cũng đc nhưng ko nên dùng vì viết dài và dễ bị lỗi lặp - ví dụ tức là sai sót khi set trong ifelse tận 2 cái firefox nhưng vẫn chạy bth)
    	//if (browserName.equals("firefox")) {
    		//System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
            //driver = new FirefoxDriver();
		//} else if (browserName.equals("chrome")) {
    	//System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
    	// driver = new ChromeDriver();
            //} else if (browserName.equals("edge")) {
    	//System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
    	//driver = new EdgeDriver();
    	//} else {
    	//throw new RuntimeException("Please input with correct browser name.");  //cái này dùng để nếu mình có nhập sai tên cái browser nào đó trong code thì chạy fail nó sẽ show lỗi ra
    	//}

    	//switch-case gọn hơn ifelse và sẽ báo lỗi khi driver duplicate
    	switch (browserName) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
            driver = new FirefoxDriver();
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
            driver = new ChromeDriver();
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
            driver = new EdgeDriver();
			break;
		default:
			throw new RuntimeException("Please input with correct browser name.");
		}
    	
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            
    }

    @Test
    public void TC_01_LoginToSystem() {
            driver.get("http://live.techpanda.org/index.php/customer/account/login/");

            driver.findElement(emailTextbox).sendKeys("selenium_11_01@gmail.com");
            driver.findElement(passwordTextbox).sendKeys("111111");
            driver.findElement(loginButton).click();
            Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//p")).getText().contains("selenium_11_01@gmail.com"));
        
    }

    
    @AfterClass
    public void afterClass() {
            driver.quit();
    }

}
