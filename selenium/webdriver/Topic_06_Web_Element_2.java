package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_2 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
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
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	//@Test
	public void TC_01_Displayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
        //kiểm tra textbox nếu có hiển thị thì nhập text vào và in ra console 
		if (driver.findElement(emailTextbox).isDisplayed()) {
			driver.findElement(emailTextbox).sendKeys("Selenium Webdriver");
			System.out.println("Email Textbox is Displayed");
		} else {
			System.out.println("Email Textbox is not Displayed");
		}
		
		//kiểm tra textarea nếu có hiển thị thì nhập text vào và in ra console 
		if (driver.findElement(educationTextArea).isDisplayed()) {
			driver.findElement(educationTextArea).sendKeys("Selenium Grid");
			System.out.println("Education Text Area is Displayed");
		} else {
			System.out.println("Education Text Area is not Displayed");
		}
		
		//kiểm tra radio button
		if (driver.findElement(ageUnder18Radio).isDisplayed()) {
			driver.findElement(ageUnder18Radio).click();
			System.out.println("Age Under 18 is Displayed");
		} else {
			System.out.println("Age Under 18 is not Displayed");
		}
		
		//kiểm tra Name user 5 - đây chỉ là 1 đoạn text thôi nên nó ko có action gì như click, sendkeys cả
				if (driver.findElement(nameUser5Text).isDisplayed()) {
					System.out.println("Name User 5 is Displayed");
				} else {
					System.out.println("Name User 5 is not Displayed");
				}
	}

	//@Test
	public void TC_02_Enabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		if (driver.findElement(passwordTextbox).isEnabled()) {
			System.out.println("Password Textbox is Enabled");
		} else {
			System.out.println("Password Textbox is Disabled");
		}
		
		if (driver.findElement(biographyTextarea).isEnabled()) {
			System.out.println("Biography Text Area is Enabled");
		} else {
			System.out.println("Biography Text Area is Disabled");
		}

		if (driver.findElement(emailTextbox).isEnabled()) {
			System.out.println("Email Text Box is Enabled");
		} else {
			System.out.println("Email Text Box is Disabled");
		}
	}

	//@Test
	public void TC_03_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		//verify button vì default trên các trang web là nút chưa đc chọn nên ta dùng assertfalse
		Assert.assertFalse(driver.findElement(ageUnder18Radio).isSelected());
		Assert.assertFalse(driver.findElement(developmentCheckbox).isSelected());
		//bước tiếp là click vào để check
		driver.findElement(ageUnder18Radio).click();
		driver.findElement(developmentCheckbox).click();
		//verify lại button vì các button đã đc chọn nên ta dùng asserttrue
		Assert.assertTrue(driver.findElement(ageUnder18Radio).isSelected());
		Assert.assertTrue(driver.findElement(developmentCheckbox).isSelected());
		
	}
	
	@Test
	public void TC_04_Mail_Chimp() {
		driver.get("https://login.mailchimp.com/signup/");
		driver.findElement(By.id("email")).sendKeys("automationfc@gmail.com");
		
		By passwordText = By.id("new_password");
		//nút signup này dùng nhiều nên mình khai báo nó để làm cho gọn
		//TC này ban đầu có dùng action Click nhưng vì web click signup nhiều lần sẽ bị tính là spam nên đã bỏ click đi
		//trường hợp 1: test pass chỉ có chữ cái thường
		driver.findElement(passwordText).sendKeys("abc");
		sleepInSecond(2);
		//vì abc đã đáp ứng đc yêu cầu có chữ thường nên ta sẽ verify case "one lowercase character"
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());   //khi đáp ứng đúng yêu cầu thì ở inspect sẽ ra dòng completed, những cái ko đạt thì sẽ nót completed
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		//trường hợp 2: chữ hoa
		driver.findElement(passwordText).clear(); //.clear để clear cái case test pass chữ thường ở trường hợp 1
		driver.findElement(passwordText).sendKeys("ABC");
		
		sleepInSecond(2);
		//verify chữ hoa
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());  
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		//trường hợp 3: chỉ nhập số
		driver.findElement(passwordText).clear();
		driver.findElement(passwordText).sendKeys("123");
		 
		sleepInSecond(2);
		//verify pass chỉ nhập số
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());  
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		//trường hợp 4: ký tự đặc biệt
		driver.findElement(passwordText).clear();
		driver.findElement(passwordText).sendKeys("!@#");
		
		sleepInSecond(2);
		//verify pass chỉ nhập số
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());  
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		//trường hợp 5: >=8 chữ
		driver.findElement(passwordText).clear();
		driver.findElement(passwordText).sendKeys("ABCGHZXYM"); //trường hợp pass này thỏa mãn 2 điều kiện đủ 8 chữ và có chữ viết hoa nên sẽ có 2 cái complete
		
		sleepInSecond(2);
		//verify >= 8 chữ và đều viết hoa
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());  
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());
		
		//trường hợp 6: pass đáp ứng đủ tất cả các yêu cầu
		driver.findElement(passwordText).clear();
		driver.findElement(passwordText).sendKeys("123abcAB@#"); 

		sleepInSecond(2);
		//verify >= 8 chữ và đều viết hoa
		//vì pass đã đáp ứng đủ đk nên nó sẽ ko hiện ra msg báo lỗi, đổi assert từ true thành false vì ko hiển thị nên hàm isdisplayed trả về kết quả là false
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());  
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());
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
