package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_TextBox_TestArea {
	WebDriver driver;
	Random rand = new Random();
	String projectPath = System.getProperty("user.dir");
	String employeeID = String.valueOf(rand.nextInt(99999)); //convert thằng string sang int
	String passportNumber = "40517-302-96-6899";
	String commentInput = "Passport\ncon mẹ mày";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void TC_01_Create_New_Employee() {
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.findElement(By.name("username")).sendKeys("Admin");
		driver.findElement(By.name("password")).sendKeys("admin123");
		driver.findElement(By.cssSelector("button.orangehrm-login-button")).click(); //dùng css cho tiện vì nên nhớ css có thể lấy 1 khúc trong 1 đoạn dài
		sleepInSecond(2);
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		sleepInSecond(2);
		driver.findElement(By.name("firstName")).sendKeys("vit");
		driver.findElement(By.name("lastName")).sendKeys("bau");
		driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).sendKeys(Keys.DELETE);
		driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).sendKeys(employeeID);
		sleepInSecond(2);
		driver.findElement(By.xpath("//p[text()='Create Login Details']/parent::div//span")).click(); //ở đây input là con div nên dùng // tới luôn ko phải dùng sibling gì hết
		sleepInSecond(2);
		//tạo acc
		driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys("afc" + employeeID);
		driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys("Afc123..");
		driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys("Afc123..");
		driver.findElement(By.xpath("//button[contains(string(),'Save')]")).click(); //do bị chặn node trên nếu dùng contains text() ko lấy đc, còn dùng string ở bất cứ đâu cũng lấy đc
		sleepInSecond(3);
		//verify các thông tin đã điền
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), "vit");
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), "bau");
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), employeeID);
		
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/folloing-sibling::button")).click();
		driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys(passportNumber);
		driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).sendKeys(commentInput);
		driver.findElement(By.xpath("//button[contains(string(),'Save']")).click();
		sleepInSecond(2);
		driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getAttribute("value"), passportNumber);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).getAttribute("value"), commentInput);
		//logout
		driver.findElement(By.cssSelector("p.oxd-userdropdown-name")).click();
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		//đăng nhập lại
		driver.findElement(By.name("username")).sendKeys("afc" + employeeID);
		driver.findElement(By.name("password")).sendKeys("Afc123.");
		driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), "vit");
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), "bau");
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), employeeID);
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getAttribute("value"), passportNumber);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).getAttribute("value"), commentInput);

		


		

		
		



		


		
		


		
	}

	@Test
	public void TC_02_Verify_Employee() {
		
		
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
