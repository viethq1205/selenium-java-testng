package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Selenium_Locator {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Mở trang register ra
		driver.get("https://demo.nopcommerce.com/register");
		// <input type="text" data-val="true" data-val-required="First name is required." id="FirstName" name="FirstName">
	}

	@Test
	public void TC_01_ID() {
		//Thao tác lên elements nào thì phải tìm ra cái ele đó, dùng hàm findelement
		// find theo cái gì: id, class, name, css, xpath,...
		// tìm thấy ele rồi thì action lên ele đó: click (ví dụ button là nhấp vào chọn, sendkeys là nhập dữ liệu vào, như ô search ta nhập tìm macbook...
	driver.findElement(By.id("FirstName")) .sendKeys("Automation");
	}

	@Test
	public void TC_02_Class() {
		// Mở màn hình search
		driver.get("https://demo.nopcommerce.com/search");
		// Nhập text vào search textbox
		driver.findElement(By.className("search-text")).sendKeys("Lenovo");
	}

	@Test
	public void TC_03_Name() {
		//click vào advanced search checkbox
		driver.findElement(By.name("advs")).click();;
	}
	
	@Test
	public void TC_04_TagName() {
		//tìm bao nhiêu thẻ (tag) input trên màn hình hiện tại
		driver.findElement(By.tagName("input")).getSize();
	}
	
	@Test
	public void TC_05_LinkText() {
		//click vào đường link adresses (tìm tuyệt đối chính xác từng từ)
		driver.findElement(By.linkText("Addresses")).click();
		
	}
	
	@Test
	public void TC_06_PartialLinkText() {
		//click vào đường link Apply for vendor account (tương đối - tìm 1 phần ví dụ như chỉ cần apply for hoặc vendor account gì đó)
				driver.findElement(By.partialLinkText("vendor account")).click();
	}
	
	@Test
	public void TC_07_CSS() {
		// mở lại trang register
		driver.get("https://demo.nopcommerce.com/register");
		//cách 1
		driver.findElement(By.cssSelector("input#FirstName")) .sendKeys("Selenium"); //# là viết tắt của id
		//cách 2
				driver.findElement(By.cssSelector("input[id='LastName']")) .sendKeys("Locator");
		//cách 3
		driver.findElement(By.cssSelector("input[name='Email']")) .sendKeys("automation@gmail.com");

	}
	
	@Test
	public void TC_08_XPath() {
		// mở lại trang register
				driver.get("https://demo.nopcommerce.com/register");
		//cách 1
				driver.findElement(By.xpath("//input[@id='FirstName']")) .sendKeys("Selenium");
		//cách 2
				driver.findElement(By.xpath("//input[@id='LastName']")) .sendKeys("Locator");
		//cách 3
				driver.findElement(By.xpath("//label[text()='Email:']/following-sibling::input")).sendKeys("automation@gmail.com");
		
	}
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
}
