package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_P2 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
   //bài tập topic webbrowser, webelement
	@Test
	public void TC_01_Url() {
	driver.get("http://live.techpanda.org/");
	driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	sleepInSecond(2);
	//verify url nên dùng assert vì ra kết quả đúng hoặc là sai
	Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/login/");
	//bước tiếp là click vào create account
	driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
	sleepInSecond(2);
	//verify url trang create account
	Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_Title() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(2);
		//verify title login page
		Assert.assertEquals(driver.getTitle(),"Customer Login");
		//verify title register page
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		sleepInSecond(2);
		Assert.assertEquals(driver.getTitle(),"Create New Customer Account");
		}
	

	@Test
	public void TC_03_Navigate() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(2);
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		sleepInSecond(2);
		//verify nút back lại
		driver.navigate().back();
		Assert.assertEquals(driver.getTitle(),"Customer Login");
		// verify nút foward
		driver.navigate().forward();
		Assert.assertEquals(driver.getTitle(),"Create New Customer Account");
		
	}
	
	@Test
	public void TC_04_Page_Source_Html() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(2);
		//verify 1 page source có chứa đoạn text mình mong muốn hay ko
		Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));  //đây là hàm true nên kq trả về true là chuẩn
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		//verify tiếp 1 page source có chứa đoạn text mình mong muốn hay ko
		Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
		
	}
	
	//phần này đc thêm vào vì phần implicitlywait đôi khi trong 30s nếu driver.get url lâu có thể gây ra fail test case
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);   //vì sleep nhận là milisecond nên 3s*1000 mới ra ms
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
