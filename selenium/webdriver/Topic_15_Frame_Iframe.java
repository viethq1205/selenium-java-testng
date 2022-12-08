package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Frame_Iframe {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void TC_01_() {
		driver.get("https://kyna.vn/");
		
		//frame/iframe nhớ dùng hàm switch, kiểu như m đang test ở trang web của m, muốn dùng code thao tác với cái iframe thì phải switch qua web đó, nếu ko test case sẽ bị lỗi, rồi muốn quay lại web mình để thao tác thì phải switch 1 lần nữa để trở về
		//verify fb iframe hiển thị, lúc này nó vẫn là 1 element của trang kyna nên verify bth
		Assert.assertTrue(driver.findElement(By.xpath("//iframe[contains(@src,'kyna.vn')]")).isDisplayed());
		
		//bước verify fb có 165k like thì phải switch vì mấy cái element lúc này nó thuộc về fb, tức là 1 dòng code của trang khác chứ ko phải của thằng kyna viết ra
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'kyna.vn')]")));
		
		//verify nó có 165k lượt nike
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='_1drq']")).getText(), "165K likes");
		
		//bước tiếp theo là chat với bên web - ở đây cái phần mềm wechat của web kyna cũng là 1 iframe chứ ko phải của web
		
		//chú ý ta đang ở bên iframe fb nên muốn qua iframe khung chat mình phải switch về web ban đầu là kyna rồi mới switch qua iframe mới đc, chứ ko thể nhảy từ iframe này qua iframe kia khác loại
		
		//note:nhưng ví dụ như cái iframe fb nó lại chứa 1 cái iframe nữa và mình cũng cần thao tác trên đó thì sau khi hoàn thành task, muốn trở về main page, thì đầu tiên ta phải dùng
		//cái parentframe để trở về iframe fb, rồi mới dùng default trở về main page đc chứ ko phải nhảy 1 cái là về đc main page luôn
		driver.switchTo().defaultContent();
		
		//rồi giờ nhảy qua cái iframe chat
		driver.switchTo().frame("cs_chat_iframe"); //tại sao cái frame này lại ngắn hơn cái trên vì frame trên tìm theo web element còn frame này là frame tìm theo nameorid (cs_chat_iframe cái này là id trong html)
		
		//click vào chat
		driver.findElement(By.cssSelector("div.button_bar")).click();
		
		//nhập dữ liệu vào khung chat
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("Hitler");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0987654321");
		
		//chỗ chọn dịch vụ hỗ trợ nà gì, nó là 1 dropdown nên ta khai báo select
		new Select(driver.findElement(By.id("serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
		
		//nhập vào khung chat
		driver.findElement(By.name("message")).sendKeys("alo khỏe không cu");
		sleepInSecond(1);
		
		//search từ khóa học excel, ở đây thao tác search là ở main page nên ta phải switch về default hehehehehe
		driver.switchTo().defaultContent();
		driver.findElement(By.id("live-search-bar")).sendKeys("Excel");
		driver.findElement(By.cssSelector("button.search-button")).click();
		//verify các kết quả search ra từ excel
		List<WebElement> coursename = driver.findElements(By.cssSelector("div.content>h4")); //cái driver find theo cái css này ra đc 9 cái, gọi chuyên ngành là mảng, mảng này có 9 cái nhét vào cái túi list webelement
		
		//vì nó có 9 thằng nên mình dùng vòng lặp foreach để verify hết 9 thằng mà ko phải làm từng cái 
		for (WebElement course : coursename) {
			Assert.assertTrue(course.getText().contains("Excel"));
		}
		
		
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
