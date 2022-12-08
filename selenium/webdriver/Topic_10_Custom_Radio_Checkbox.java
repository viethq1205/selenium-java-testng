package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Custom_Radio_Checkbox {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor; //khai báo

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		//khởi tạo
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}
	//dấu hiệu nhận biết loại custom là thẻ input của checkbox/radio sẽ bị ẩn đi
	//note: cho nên nếu muốn verify = isselected thì bắt buộc locator phải là thẻ input
	//@Test
	public void TC_01_Custom_Checkbox() {
		driver.get("https://material.angular.io/components/checkbox/examples");
		sleepInSecond(2);
		//trường hợp 1
		//theo đúng như note ta phải tìm đến đúng thẻ input cho chuẩn
		//driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span/input")).click(); 
		//nhưng đáng tiếc vì thẻ input bị ẩn nên hàm click ko thể thao tác đc, nhưng đoạn xpath trên dùng verify thì vẫn đc do theo note có input thì hàm isselect xài đc
		//verify
		//Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span/input")).isSelected());
		
		//trường hợp 2
		//ta dùng 1 thẻ đang được hiển thị của checkbox để làm chứ ko bị đè ẩn đi như thẻ input
		//nhưng vấn đề là sẽ ko verify đc vì nó đéo có thẻ input
		//driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span")).click(); //click đc nhưng đến bước verify = false
		//Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span")).isSelected());
		//ko có thẻ input -> tạch
		
		//trường hợp 3
		//dùng span để click, còn verify = input
		//chạy được nhưng sẽ có vấn đề
		//trong 1 project mà 1 element cần tới 2 locator mới define đc thì sinh ra nhiều code -> mất công maintain nhiều và dễ gây confuse
		
		//cách chuẩn ko thể chỉnh nữa
		//work-around: khi giải pháp thông thường ko thể làm đc thì mình sẽ tìm 1 cách khác nhưng sẽ ko chính thống, cốt làm sao để xử lý đc vấn đề là đc
		//dùng thẻ input để click và verify luôn nhưng như ta đã biết thẻ input bị ẩn thì sao mà lick
		//ta sẽ dùng javascript executor, click bất chấp bị ẩn
		By chekedCheckBox = By.xpath("//span[text()='Checked']/preceding-sibling::span/input"); //khai báo byxpath để viết cho ngắn
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(chekedCheckBox));
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(chekedCheckBox).isSelected());
		
	}

	//@Test
	public void TC_02_Custom_Radio() {
		driver.get("https://material.angular.io/components/radio/examples");
		//nó sẽ bị các trường hợp giống như trên kia nên ta cái cách chuẩn ko thể chỉnh nữa
		By springRadio = By.xpath("//span[contains(text(),'Spring')]/preceding-sibling::span/input"); //spring có khoảng trắng nên ta thêm contains
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(springRadio));
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(springRadio).isSelected());
	}

	//@Test
	public void TC_03_VNdirect() {
		//đây là thằng checkbox có input nhưng vẫn bị ẩn khi dòng element này //input[@name='acceptTerms'] tìm trên trang web chỉ ra 1 chấm nhỏ chứ ko phải cái checkbox = custom checkbox
		//nên cũng dùng cách vip pro trên
		driver.get("https://account-v2.vndirect.com.vn/");
		By termCheckbox = By.xpath("//input[@name='acceptTerms']");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(termCheckbox));
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(termCheckbox).isSelected());
		
	}
	@Test
		public void TC_04_GG_Docs() {
			driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
			//thằng gg nó ko có thẻ input vì nó vip pro, tự design riêng chứ cần theo chuẩn html
			//cách để verify thằng này sẽ hơi khác, sẽ có 1 thẻ có tính năng tương tự như input, cho nên mình phải inspect lên, tương tác/click vào radio của web để quan sát dòng code của nó có element nào thay đổi sau mỗi cú click
			//như cái element aria-checked ='false' khi chưa nhấn vào radio, nhấn vào thì thay đổi thành true
			//dùng isdisplayed
			By canthoRadio = By.xpath("//div[@aria-label= 'Cần Thơ']");
			//verify trước khi click
			//vì attribute này sẽ thay đổi trc khi click và sau khi click như trên đã nói, mình sẽ verify 2 lần
			//getattri trả về false (text), note: thằng này là text của aria checked (chữ false này nằm trong "") chứ ko phải false của boolean nên mình dùng equals
			Assert.assertEquals(driver.findElement(canthoRadio).getAttribute("aria-checked"), "false");
			//click
			driver.findElement(canthoRadio).click();
			sleepInSecond(2);
			//verify sau khi click
			Assert.assertEquals(driver.findElement(canthoRadio).getAttribute("aria-checked"), "true");
			//cách dùng isdisplayed
			//Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label= 'Cần Thơ'" and @aria-checked= 'True'])).isDisplayed();
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
