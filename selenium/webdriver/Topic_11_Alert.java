package webdriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Alert {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	// khai báo alert
	Alert alert;
	String firefoxAuthenAutoIT = projectPath + "\\autoITScripts\\" + "authen_firefox.exe";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	// @Test
	public void TC_01_Accept() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		sleepInSecond(2);
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		sleepInSecond(1);
		// switch to alert (khi alert đang xuất hiện)
		alert = driver.switchTo().alert();// tạo thằng biến alert sau khi khai báo ở trên để hứng cái bước này còn dùng
											// cho nhiều việc khác cho tiện, note: làm nhiều rồi mà vẫn hay quên, nhớ
											// phép gán giá trị là dùng dấu =
		// verify alert title trước khi accept
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		// accept 1 alert
		alert.accept();
		// verify accept thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(),
				"You clicked an alert successfully");

	}

	// @Test
	public void TC_02_Confirmation() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		sleepInSecond(2);
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		sleepInSecond(1);
		// switch to alert (khi alert đang xuất hiện)
		alert = driver.switchTo().alert();
		// verify alert title trước khi ok/cancel
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		// bài tập này chọn cancel
		alert.dismiss();
		// verify cancel thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");

	}

	//@Test
	public void TC_03_Prompt() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		sleepInSecond(2);
		String keyword = "Nhâm Sỹ T";
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		sleepInSecond(1);
		// switch to alert (khi alert đang xuất hiện)
		alert = driver.switchTo().alert();
		// verify alert title
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		// bài tập này chọn cancel
		alert.sendKeys(keyword);
		sleepInSecond(2);
		alert.accept();
		// verify cancel thành công
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: " + keyword);
	}

	//@Test
	public void TC_04_Accept_Alert_and_Login_Form() {
		driver.get("https://demo.guru99.com/v4/");
		sleepInSecond(2);
		driver.findElement(By.name("btnLogin")).click();
		sleepInSecond(2);
		alert = driver.switchTo().alert();
		//verify title
		Assert.assertEquals(alert.getText(), "User or Password is not valid");
		//accept
		alert.accept();
		//verify lại url vì cái alert chuyển sang trang khác
		Assert.assertEquals(driver.getCurrentUrl(), "https://demo.guru99.com/v4/index.php");
		

	}
	
	//@Test
	public void TC_05_Authentication_Alert_cach_1() {
		//Pass luôn cái nhập tk/mk để vào luôn màn hình đăng nhập thành công
		// bằng cách nhập thẳng url như sau
		//cách này chạy đc cho tất cả brower/OS
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		sleepInSecond(2);
		//verify
		Assert.assertTrue(driver.findElement(By.cssSelector("div.example p")).getText().contains("Congratulations! You must have the proper credentials."));
	}
	
	//@Test
	public void TC_06_Authentication_Alert_cach_1() {
		driver.get("https://the-internet.herokuapp.com/");
		sleepInSecond(2);
		
		String basicauthenURL = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		
		driver.get(getAuthenURL(basicauthenURL, "admin", "admin"));
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("div.example p")).getText().contains("Congratulations! You must have the proper credentials."));
	}
	
	@Test
		public void TC_07_Authentication_Alert_autoIT() throws IOException {
		//bật script của autoIT trc rồi mở web sau
		Runtime.getRuntime().exec(new String[] {firefoxAuthenAutoIT, "admin", "admin"});

			driver.get("http://the-internet.herokuapp.com/basic_auth");
			sleepInSecond(2);
			//verify
			Assert.assertTrue(driver.findElement(By.cssSelector("div.example p")).getText().contains("Congratulations! You must have the proper credentials."));
		}
	
	// phần này đc thêm vào vì phần implicitlywait đôi khi trong 30s nếu driver.get
	// url lâu có thể gây ra fail test case
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000); // vì sleep nhận là milisecond nên 3s*1000 mới ra ms
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
		// driver.quit();
	}
	
	public String getAuthenURL(String basicauthenURL, String userName, String password) {
		String[] authenURLArray = basicauthenURL.split("//");
		basicauthenURL = authenURLArray[0] + "//" + userName + ":" + password + "@" + authenURLArray[1];
		return basicauthenURL;
	}
}
