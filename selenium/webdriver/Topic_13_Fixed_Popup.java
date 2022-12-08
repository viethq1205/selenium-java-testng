package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Fixed_Popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	//@Test
	public void TC_01_() {
		driver.get("https://ngoaingu24h.vn/");
		sleepInSecond(2);
		
		By loginpopup = By.xpath("(//div[@id='modal-login-v1'])[1]");  //dùng index vì có tận 2 dòng code giống y chang nhau 
		//verify khi popup chưa hiển thị
		//popup login page này thuộc dạng nhấp vào login mới trồi lên nên ta verify lúc nó chưa hiển thị cái đã
		//vì verify ko hiển thị mà dùng hàm True + isdisplayed thì kết quả nó sẽ = false, true + false = false nên fail test
		//nên phải dùng false vì với isdisplayed mà nó đang ko hiển thị thì = false, false + false = true
		Assert.assertFalse(driver.findElement(loginpopup).isDisplayed());
		
		//tiếp theo là nhấp vào đăng nhập để hiện popup
		driver.findElement(By.cssSelector("button.login_")).click();
		sleepInSecond(1);
		
		//verify popup đã hiện lên
		// tất nhiên lúc này đã hiện lên = isdisplayed nên mình dùng hàm True
		Assert.assertTrue(driver.findElement(loginpopup).isDisplayed());
		
		//nhập thông tin ko hợp lệ vào popup
		driver.findElement(By.cssSelector("div#modal-login-v1[style] input#account-input")).sendKeys("automationtesting"); //cái [style] thì [] chính là hàm and bên css
		driver.findElement(By.cssSelector("div#modal-login-v1[style] input#password-input")).sendKeys("automationtesting");
		
		//nhấn đăng nhập
		driver.findElement(By.cssSelector("div#modal-login-v1[style] button.btn-login-v1")).click();
		sleepInSecond(1);
		
		//verify dòng error thông tin login ko hợp lệ
		Assert.assertEquals(driver.findElement(By.cssSelector("div#modal-login-v1[style] div.error-login-panel")).getText(), "Tài khoản không tồn tại!");
		
	}

	//@Test
	public void TC_02_() {
		driver.get("https://kyna.vn/");
		sleepInSecond(2);
		
		By loginKyna = By.cssSelector("div#k-popup-account-login");
		//verify khi popup chưa hiển thị
		Assert.assertFalse(driver.findElement(loginKyna).isDisplayed());
		
		//tiếp theo là nhấp vào đăng nhập để hiện popup
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(1);
			
		//verify popup đã hiện lên
		// tất nhiên lúc này đã hiện lên = isdisplayed nên mình dùng hàm True
		Assert.assertTrue(driver.findElement(loginKyna).isDisplayed());	

		//nhập thông tin vào popup
		driver.findElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com"); 
		driver.findElement(By.cssSelector("input#user-password")).sendKeys("123456");
				
		//nhấn đăng nhập
		driver.findElement(By.cssSelector("button#btn-submit-login")).click();
		sleepInSecond(1);
				
		//verify dòng error thông tin login ko hợp lệ
		Assert.assertEquals(driver.findElement(By.cssSelector("div#password-form-login-message")).getText(), "Sai tên đăng nhập hoặc mật khẩu");
				
		
		
	}

	@Test
	public void TC_03_() {
		driver.get("https://facebook.com/");
		sleepInSecond(1);
		
		//click tạo tk
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		sleepInSecond(1);

		//verify popup hiển thị
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/parent::div")).isDisplayed());
		
		//close popup
		driver.findElement(By.cssSelector("img._8idr")).click();
		sleepInSecond(1);		
		
		//verify popup ko hiển thị nữa
		//lưu ý chỗ này khi popup tắt thì sẽ ko còn trong html nữa, nếu dùng cách như dưới sẽ sai, dùng cách verify theo findelements
		//Assert.assertFalse(driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/parent::div")).isDisplayed());
		
		Assert.assertEquals(driver.findElements(By.xpath("//div[text()='Sign Up']/parent::div/parent::div")).size(), 0); 
//0 ở đây là cái locator element đã biến mất do tắt popup, ta cũng có thể verify việc popup hiển thị = cách thay số 0 bằng 1 vì cái locator element lúc này đang xuất hiện do mình mở popup
		//step verify này hơi lâu thường tốn 10s do liên quan đến cái implicitwait, nó sẽ tìm liên tục cho đến ko tìm đc nữa thì mới thôi qua bước khác
		
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
		//driver.quit();
	}
}
