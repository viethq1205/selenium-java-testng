package webdriver;

import java.util.List;
import java.util.Random;
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

public class Topic_08_Default_Dropdown {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	Select select; //import select từ thư viện để làm dropdown
	Random rand = new Random();

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	//@Test
	public void TC_01_Dropdown() {
		driver.get("https://demo.nopcommerce.com/");
		driver.findElement(By.xpath("//a[text()='Register']")).click();
		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("Joe");
		driver.findElement(By.cssSelector("input#LastName")).sendKeys("Biden");
		//thao tác với dropdown, đầu tiên phải khởi tạo biến select (làm ở bước public class)
		//khởi tạo select
		//thằng select nó đặc biệt hơn vì khi new nó, select (new Select(null)) đi kèm theo webelement nên khi ta phải có mục driver get url thì mới khởi tạo đc, khác với cái new firefoxdriver ở beforeclass
		//đây là new select cho dropdown day
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']")));
		select.selectByVisibleText("13");
		//đây là new select cho dropdown month
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']")));
		select.selectByVisibleText("May");
		//đây là new select cho dropdown year
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']")));
		select.selectByVisibleText("1965");
		driver.findElement(By.cssSelector("input#Email")).sendKeys("joebidenocheo" + rand.nextInt(9999) + "@hotmail.com");
		driver.findElement(By.cssSelector("input#Company")).sendKeys("Black House");
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123456");
		driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys("123456");		
		driver.findElement(By.cssSelector("button#register-button")).click();
		//verify đã đăng ký thành công //nhớ dùng gettext khi verify 1 câu text, vì ko có get text là đang so sánh webelement và string, phải có gettext string = string (= nhau về giá trị dữ liệu) thì mới dùng hàm assert đc
		Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");
		driver.findElement(By.cssSelector("a.ico-account")).click();
		//verify ngày tháng năm đúng chưa, note: luôn luôn phải new lại vì nó là 1 object chứ ko phải 1 biến
		//note thằng select luôn luôn phải find lại khi verify vì mỗi khi load page nó sẽ bị mất đi nên cứ find lại element là tốt nhất
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(),"13");
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(),"May");
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(),"1965");

	}

	@Test
	public void TC_02_Dropdown() {
		driver.get("https://rode.com/en/support/where-to-buy");
		select = new Select(driver.findElement(By.cssSelector("select#country")));
		select.selectByVisibleText("Vietnam");
		sleepInSecond(2);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Vietnam");
		List<WebElement> dealers = driver.findElements(By.cssSelector("div#map h4"));
		for (WebElement element : dealers) {
			System.out.println(element.getText());
		}
			
			
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
