package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Button {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void TC_01_Web_Fahasa() {
		driver.get("https://www.fahasa.com/customer/account/create");
		sleepInSecond(5);
		//switch qua iframe trc khi close pop up
		driver.switchTo().frame("preview-notification-frame");
		//close popup
		driver.findElement(By.cssSelector("a#NC_CLOSE_ICON>img")).click();
		//từ iframe chuyển về trang login
//phải qua các bước này vì cái popup giống như là 1 trang html khác, khi ta đóng nó phải code trở về trang lúc đầu nếu ko vẫn sẽ đang ở trang iframe popup quảng cáo, chạy code ko đc = lỗi
		driver.switchTo().defaultContent();
		//chuyển qua tab đăng nhập
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		//verify login button disable (dùng hàm isenabled - hàm này tức là xem nút đang đc enable hay inenable)
		//vì hàm này đang tìm cái gì đó có đang đc kích hoạt ko? mà ta đang verify cái nút ở trang này nó ko đc kích hoạt nên ta dùng assertfalse thay vì true
		Assert.assertFalse(driver.findElement(By.cssSelector("button.fhs-btn-register")).isEnabled());
		//nhập giá trị để đăng ký
		driver.findElement(By.cssSelector("input#login_username")).sendKeys("dam@gmail.com");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("123456");
		//note: sau khi mình điền 2 thông tin này vào thì lút đăng nhập đc kích hoạt, mình sẽ vegify nó bằng true
		Assert.assertTrue(driver.findElement(By.cssSelector("button.fhs-btn-register")).isEnabled());
		//verify nút đăng nhập có màu red//dùng hàm gét css vanue
		//muốn lấy đúng màu thì phải xổ cái background xuống để nấy background-color
		String rgbacolor = driver.findElement(By.cssSelector("button.fhs-btn-register")).getCssValue("background-color"); 
		//nhưng mỗi trình duyệt lại hiển thị chỗ màu khác nhau như chrome hiện rgb nhưng ff có thể hiện rgba nên h ta cần convert qua mã hexa để đồng bộ lại
		//cái mã #c92126 kế bên ô màu đỏ của mục color chính là mã hexa
		String hexaColor = Color.fromString(rgbacolor).asHex().toUpperCase();
		


		
	}

	@Test
	public void TC_02_() {
		
	}

	@Test
	public void TC_03_() {
		
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
