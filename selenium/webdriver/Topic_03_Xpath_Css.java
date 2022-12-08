package webdriver;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class Topic_03_Xpath_Css {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	public void TC_00_() {
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");
// nhớ driver. get là click vào link my account ở footer chứ ko phải phần account trên header, phần trên header sẽ tốn 2 bước mới vào đc đường link so với footer 1 bước nên khi viết script test sẽ bị lỗi
// dùng KT Xpath parent node để làm phần này		
// cơ chế của selenium: nếu tìm ra nhiều hơn 1 node (kết quả) thì luôn thao tác với element đầu tiên
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	}

	public void TC_01_Empty_Data() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		//action
		driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
//assert.asserttrue kiểm tra 1 đk trả về là đúng, tương tự là false kiểm tra 1 đk trả về là sai, và equals kiểm tra thực tế (actual result) và mong đợi (expect result)
		//verify, có sẵn id thì mình dùng id luôn, đang tìm kq mong muốn nên dùng equals
		Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(),"Vui lòng nhập họ tên");
		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(),"Vui lòng nhập email");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(),"Vui lòng nhập lại địa chỉ email");
		Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(),"Vui lòng nhập mật khẩu");
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Vui lòng nhập lại mật khẩu");
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Vui lòng nhập số điện thoại.");
	}

	public void TC_02_Invalid_Email() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		//action
		driver.findElement(By.id("txtFirstname")).sendKeys("Jonh Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("123@456@678");
		driver.findElement(By.id("txtCEmail")).sendKeys("123@456@678");
		driver.findElement(By.id("txtPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
	
		driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
// verify
		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(),"Vui lòng nhập email hợp lệ");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(),"Vui lòng nhập email hợp lệ");
	}

    public void TC_03_Incorrect_Email() {
    	driver.get("https://alada.vn/tai-khoan/dang-ky.html");
    	//action
		driver.findElement(By.id("txtFirstname")).sendKeys("Jonh Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("John@Wick.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("John@Wick.net");
		driver.findElement(By.id("txtPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
	
		driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
// verify
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(),"Email nhập lại không đúng");
		
	}
    public void TC_04_Invalid_Password() {
    	driver.get("https://alada.vn/tai-khoan/dang-ky.html");
    	//action
		driver.findElement(By.id("txtFirstname")).sendKeys("Jonh Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("123@456@678");
		driver.findElement(By.id("txtCEmail")).sendKeys("123@456@678");
		driver.findElement(By.id("txtPassword")).sendKeys("123");
		driver.findElement(By.id("txtCPassword")).sendKeys("123");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
	
		driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
// verify
		Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự");
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự");
    }
    public void TC_05_Incorrect_Password() {
    	driver.get("https://alada.vn/tai-khoan/dang-ky.html");
// action    	
		driver.findElement(By.id("txtFirstname")).sendKeys("Jonh Wick");
		driver.findElement(By.id("txtEmail")).sendKeys("John@Wick.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("John@Wick.net");
		driver.findElement(By.id("txtPassword")).sendKeys("123456789");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456777");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
	
		driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
// verify
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Mật khẩu bạn nhập không khớp");
    }
    public void TC_06_Invalid_Phone() {
    	driver.get("https://alada.vn/tai-khoan/dang-ky.html");
    	// action 1: chỉ nhập sđt gồm 9 số thay vì 10~11 số hợp lệ   	
    			driver.findElement(By.id("txtFirstname")).sendKeys("Jonh Wick");
    			driver.findElement(By.id("txtEmail")).sendKeys("John@Wick.com");
    			driver.findElement(By.id("txtCEmail")).sendKeys("John@Wick.net");
    			driver.findElement(By.id("txtPassword")).sendKeys("123456789");
    			driver.findElement(By.id("txtCPassword")).sendKeys("123456777");
    			driver.findElement(By.id("txtPhone")).sendKeys("098712345");
    		
    			driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
    	// verify
    			Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Số điện thoại phải từ 10-11 số.");
    			
    	// action 2: chỉ nhập sđt gồm 9 số thay vì 10~11 số hợp lệ   	
    	//cơ chế của selenium nếu mình làm cùng 1 hành động đến lần thứ 2 thì khi nhập lại dữ liệu sẽ bị lỗi, chỉ chạy phần phone thôi cũng đc
    	//nên dùng hàm clear để xóa dữ liệu cũ
    			driver.findElement(By.id("txtPhone")).clear();
    			driver.findElement(By.id("txtPhone")).sendKeys("098712345125"); 
    			driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
    	// verify 2
    			Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Số điện thoại phải từ 10-11 số.");
    			
    	// action 3: Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08   	
    	//cơ chế của selenium nếu mình làm cùng 1 hành động đến lần thứ 2 thì khi nhập lại dữ liệu sẽ bị lỗi, chỉ chạy phần phone thôi cũng đc
    	//nên dùng hàm clear để xóa dữ liệu cũ
    	    driver.findElement(By.id("txtPhone")).clear();
    	    driver.findElement(By.id("txtPhone")).sendKeys("98712345125"); 
    	    driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
    	// verify 2
    	    Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08");
    			
    	    
    }

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
