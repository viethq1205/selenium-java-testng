package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_P1 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Tương tác với browser thì sẽ thông qua biến webdriver driver
		// Tương tác với element thì sẽ thông qua biến webelement element
	}

	@Test
	public void TC_01_() {
		//java document cách dùng hàm ntn, f3 hoặc chuột phải chọn open declaration
		
		// trong trường hợp >= 2 tab nó sẽ đóng tab hoặc cửa sổ mà nó đang đứng, 1 tab thì sẽ đóng browser luôn
		driver.close();
		// đóng browser luôn ko kể bao nhiêu tab
		driver.quit();
		// Tìm 1 element
		//gán driver.find... cho weblement đặt thành email textbox cho ngắn gọn
		WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='email']"));
		//với như trên mình có thể ghi ngắn gọn hơn, ví dụ emailTextbox.clear(); thay vì phải ghi cả câu driver.findElement(By.xpath("//input[@id='email']")).clear();	
		// còn đoạn code nào chỉ dùng 1 lần thì thôi khỏi gán webelement
		
		// Tìm nhiều elements
		//element có thêm s sẽ tìm ra all elements hoặc 1 list element nên phải khai báo List <WebElement> như ví dụ dưới
		List<WebElement> checkboxes = driver.findElements(By.xpath(""));
		// dùng cách gán biến này code sẽ clean hơn với các dòng trùng
		
		// Trả về sourcode của page hiện tại
		// verify tương đối (chứa 1 đoạn text trong sourcode như đoạn dưới, ngắn hơn hoặc dài hơn đều đc)
		driver.getPageSource();
		Assert.assertTrue(driver.getPageSource().contains("Facebook giúp bạn kết nối và chia sẻ với mọi người trong cuộc sống của bạn."));
		
		// Trả về title của page hiện tại 
		Assert.assertEquals(driver.getTitle(),"facebook - Đăng nhập hoặc đăng ký");
		
		
		// 2 cái dưới đc sử dụng trong webdriver API - window/tabs
		// lấy ra đc id của window/tab mà driver đang đứng
		//có thể gán string
		driver.getWindowHandle();
		String loginWindowID = driver.getWindowHandle();
		//lấy ra tất cả id của window/tab, vì là số nhiều nên gán kiểu khác
		driver.getWindowHandles();
		Set<String> allIDs = driver.getWindowHandles();
		
		// chú ý cookie/cache
		Options Opt = driver.manage(); // manage trả về options, có thể gán biến options
		//login thành công
		Opt.getCookies();
		//testcase khác -> set cookie vào lại -> ko cần phải login lần nữa (sẽ nhanh hơn so với việc mỗi lần test là load lại, ví dụ nếu server đặt ở xa có thể gây ra tình trạng load lâu)
		// sẽ học trong phần framework
		Timeouts time = Opt.timeouts();
		
		//bài implicit wait và depend on: findelement và findelements
		// khoảng thời gian chờ element xuất hiện trong x giây
		time.implicitlyWait(5000, TimeUnit.MILLISECONDS); //nếu chọn time unit second thì là 5s, còn ms sẽ ra 5000 còn microsecond sẽ là 5tr
		// khoảng thời gian chờ page load trong x giây
		time.pageLoadTimeout(5, TimeUnit.SECONDS);
		// khoảng thời gian chờ đoạn script đc thực thi trong x giây
		// bài webdriver API - javascript executor
		time.setScriptTimeout(5, TimeUnit.SECONDS);
		
		
		//phần window
		Window Win = Opt.window(); 
		// lưu ý:trong các biến (ctrl space) của window thì fullscreen ko phải là full màn mà maximize mới là full màn
	
		Navigation Nav = driver.navigate();
		//navigate có 3 cái tiêu biểu
		Nav.back();
		Nav.refresh();
		Nav.forward();
		// còn nav.to(); tương tự như driver.get();
		
		TargetLocator tar = driver.switchTo();
		tar.alert();
		tar.frame("");
		tar.window("");




	
		
	
		
			
	}

	@Test
	public void TC_02_() {
		
	}

	@Test
	public void TC_03_() {
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
