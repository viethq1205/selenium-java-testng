package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Random_Popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExe;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		jsExe = (JavascriptExecutor) driver;
		
	}

	//@Test
	public void TC_01_() {
		driver.get("https://www.javacodegeeks.com/");
		//random popup: web này vào dạng có popup mở hay close đi thì vẫn có trong html/DOM
		//yêu cầu test case này: TH1 nếu có popup hiện lên thì nhập mail vào để subcribe (popup trang này quảng cáo mình có muốn dùng mail để nhận thông tin khi có sách mới ko)
		//TH2 ko có popup thì qua bước tiếp theo
		WebElement popup = driver.findElement(By.cssSelector("div.lepopup-popup-container>div:not([style*='display:none'])"));
		
		//dùng hàm if để thực hiện TH1
		if (popup.isDisplayed()) {
			driver.findElement(By.cssSelector("input[name='lepopup-20']")).sendKeys(getRandomEmail());
			sleepInSecond(1);
			driver.findElement(By.cssSelector("a[data-label='Get the Books']")).click();
			sleepInSecond(1);
			
		//sau khi nhấn subcribe thì popup biến mất và qua bước tiếp theo là seach 1 cuốn sách
			driver.findElement(By.cssSelector("input#s")).sendKeys("agile testing explained");
			sleepInSecond(1);
			
			driver.findElement(By.cssSelector("button.search-button")).click();
			
			//verify hiển thị đúng tựa sách tìm kiếm
			Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Agile Testing Explained']")).isDisplayed());
			
		}
		
	}

	//@Test
	public void TC_02_() {
		driver.get("https://www.kmplayer.com/home");
		sleepInSecond(2);
		WebElement popup = driver.findElement(By.cssSelector("img#support-home"));
		
		//case này yêu cầu nếu popup xuất hiện thì close nó đi để qua bước tiếp
		//ko có popup thì chuyển qua bước tiếp luôn
		
		if (popup.isDisplayed()) {
			//tắt popup
			//driver.findElement(By.cssSelector("area#btn-r")).click(); cách này méo đc vì dòng này bị ẩn trong html, ta phải dùng javascript
			jsExe.executeScript("arguments[0].click();", driver.findElement(By.cssSelector("area#btn-r"))); // cái argument0 chính là đại diện cho cái driver findelement... area#btn
		} else {
			System.out.println("move to next step");
		}
		
		//click vào pc 64x
		driver.findElement(By.xpath("//li/a[text()='PC 64X']")).click();
		sleepInSecond(1);
		
		//verify đã chuyển vào pc64x
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='KMPlayer 64X']")).isDisplayed());
	}

	@Test
	public void TC_03_Dehieu() {
		//đây là case khi popup mất thì ko còn trong dom (khó handle)
		driver.get("https://dehieu.vn/");
		
		//nên dùng cái findelements như topic fixed popup khi tìm mấy cái ko còn trong dom
		List<WebElement> popups = driver.findElements(By.cssSelector("div.popup-content"));
		
		
		if (popups.size() > 0 && popups.get(0).isDisplayed()) {
			//nếu popup hiển thị thì nhập thông tin
			driver.findElement(By.cssSelector("input#popup-name")).sendKeys("taoditconmemay");
			driver.findElement(By.cssSelector("input#popup-email")).sendKeys(getRandomEmail());
			driver.findElement(By.cssSelector("input#popup-phone")).sendKeys("0987654321");
			sleepInSecond(2);
			driver.findElement(By.cssSelector("button#close-popup")).click();
		}
		
		driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
		sleepInSecond(1);
		driver.findElement(By.cssSelector("input#search-courses")).sendKeys("thiết kế tủ điện");
		sleepInSecond(1);
		driver.findElement(By.cssSelector("i.fa-search")).click();
		sleepInSecond(1);
		//verify
		Assert.assertEquals(driver.findElement(By.cssSelector("h4.name-course")).getText(), "Khóa học Thiết kế tủ điện");
		
		
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


public String getRandomEmail() {
	Random rand = new Random();
	return "sheesh" + rand.nextInt(99999) + "@gmail.com";
}
}