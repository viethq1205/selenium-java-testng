package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_ImplicitWait {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
	}

	//topic này đang test xem mình set cái thời gian như nào là hợp lý nếu khi page có 1 cái feature load mất vài s
	
	@Test
	public void TC_01_Not_Enough() { //test mình để implicitwait chỉ có 2s mà 1 chức năng cần test load hơn 3s thì xảy ra vấn đề gì
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
		
		//như ở đây gettext là nó đi tìm cái text xuất hiện sau khi nhấn start liền nhưng khổ cái sau 5s loading cái text mới xuất hiện nên case fail, dù có set wait 2s nhưng như thế là ko đủ
	}

	@Test
	public void TC_02_Enough() { //set Wait vừa đủ để text xuất hiện (5s)
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
		
		//Pass
	}

	@Test
	public void TC_03_More_time() { //set Wait dư thì càng thoải mái, nhưng tất nhiên là sau 5s loading thì get đc cái text luôn nên ko cần phải chạy hết 10s, sẵn sàng chuyển qua step tiếp theo
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
		
		//Pass 
	}
	
	//Lưu ý: Ngoài ra cần 1 điều đáng lưu tâm là thay vì mình gán ImplicitWait ở mỗi test case thì mình có thể gán Wait vào cái BeforeClass nó sẽ áp dụng cho toàn bộ các case luôn
	//nhưng khi viết các case mình gán lại Wait vào script, thì lúc này test case khi chạy sẽ áp dụng cái Wait mới viết chứ ko bị ảnh hưởng bởi cái Wait đã gán trên beforeclass
	//nếu trong cùng 1 script mình gán tận 2-3 cái wait thì nó wait theo thứ tự thôi (nhưng tất nhiên bth viết code méo ai set 1 script mấy cái wait)
	//nên set 1 cái implicitWait ở BeforeClass là chuẩn nhất
	
	
	//thằng sleepinsecond dưới là sleep cứng, nó ko linh hoạt như thằng implicitwait nhưng khi viết script mình nên hiểu mình cần gì để sử dụng linh hoạt 2 thằng này
	
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
