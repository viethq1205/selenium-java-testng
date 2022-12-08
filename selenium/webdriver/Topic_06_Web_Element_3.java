package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_3 {
	WebDriver driver;
	Random rand;
	String projectPath = System.getProperty("user.dir");
	String emailAddress, firstname, lastname, fullname, password;
	By emailTextbox = By.id("mail");
	By ageUnder18Radio = By.cssSelector("#under_18");
	By educationTextArea = By.cssSelector("#edu");
	By nameUser5Text = By.xpath("//h5[text()='Name: User5']");
	By passwordTextbox = By.cssSelector("#disable_password");
	By biographyTextarea = By.cssSelector("#bio");
	By developmentCheckbox = By.cssSelector("#development");


	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		rand = new Random();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		emailAddress = "automation" + rand.nextInt(9999) + "@gmail.com";
		firstname = "Automation";
		lastname = "FC";
		fullname = firstname + " " + lastname;
		password = "123456";
	}

	//@Test
	public void TC_01_Empty_Mail_and_Password() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(),"This is a required field.");//ô email bỏ trống hiện ra rquire field
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),"This is a required field.");// như trên mà ô password
		
	}

	//@Test
	public void TC_02_invalid_Email() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("1231234124@1235564");
		driver.findElement(By.id("pass")).sendKeys("123456");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(),"Please enter a valid email address. For example johndoe@domain.com.");
	}

	//@Test
	public void TC_03_Less_than_6_Characters() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(),"Please enter 6 or more characters without leading or trailing spaces.");
	}
	
	//@Test
	public void TC_04_Incorrect_Email() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys(emailAddress); //chú ý đặc điểm nó ko nằm trong "", vì dòng code này nó đang ko truyền 1 chuỗi (string) mà truyền 1 biến emailAddress (trong biến này có chuỗi "automation" + rand.nextInt(9999) + "gmail.com")  
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");
	}
	
	@Test
	public void TC_05_Create_Account() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.findElement(By.xpath("//input[@title='First Name']")).sendKeys(firstname);
		driver.findElement(By.xpath("//input[@title='Last Name']")).sendKeys(lastname);
		driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys(emailAddress);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='confirmation']")).sendKeys(password);
		driver.findElement(By.cssSelector("button[title='Register']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(),"Thank you for registering with Main Website Store.");
		//do khó lấy chính xác cả 1 box text theo cách bth nên dùng cách xpath axes dưới đây là tối ưu nhất
		String contactinformationboxtext = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText(); //dùng hàm gettext vì nó mới lấy đc chính xác tất cả text trong textbox
		//vì gettext là string nên mình khai báo ra cho nhanh gọn, lấy 1 lấy sẽ đc cả 1 textbox gồm nhiều dòng trong đó như text fullname, email, đường link change password 
		//ở cái bước verify boxtext này dùng assert tuyệt đối (chính xác) là ko thể nên ta dùng tương đối asserttrue vì tương đối có thể tìm chỉ với vài từ (contains - chứa)
		Assert.assertTrue(contactinformationboxtext.contains(fullname));
		Assert.assertTrue(contactinformationboxtext.contains(emailAddress));
		//bước tiếp theo là logout (nhiều bước vl)
		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();
		//Verify đăng xuất thành công về trang home = cách check hiển thị đúng trang này ko
		Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@src,'logo.png')]")).isDisplayed());
	}
	
	
	@Test
	public void TC_06_Valid_Info() {
			driver.get("http://live.techpanda.org/");
			driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
			driver.findElement(By.id("email")).sendKeys(emailAddress);
			driver.findElement(By.id("pass")).sendKeys(password);
			driver.findElement(By.xpath("//button[@title='Login']")).click();
			String contactinformationboxtext = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
			Assert.assertTrue(contactinformationboxtext.contains(fullname));
			Assert.assertTrue(contactinformationboxtext.contains(emailAddress));
			

		
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
