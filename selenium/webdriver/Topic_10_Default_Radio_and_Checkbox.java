package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Default_Radio_and_Checkbox {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	//@Test
	public void TC_01_radio_checkbox_1() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		//chọn vài check box
		driver.findElement(By.xpath("//input[@value='Diabetes']")).click();
		driver.findElement(By.xpath("//input[@value='Fainting Spells']")).click();
		//verify đã chọn thành công
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Diabetes']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Fainting Spells']")).isSelected());
		//chọn thêm radio
		driver.findElement(By.xpath("//input[@value='5+ days']")).click();
		driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).click();
		//verify 
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='5+ days']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).isSelected());
		//bỏ chọn check box-với checkbox thì chọn lại nó = bỏ chọn
		driver.findElement(By.xpath("//input[@value='Diabetes']")).click();
		driver.findElement(By.xpath("//input[@value='Fainting Spells']")).click();
		//verify đã chọn thành công
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='Diabetes']")).isSelected());
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='Fainting Spells']")).isSelected());
		//bỏ chọn radio (thằng này éo bỏ chọn đc, chỉ có chọn qua thằng khác, nên click vào bỏ chọn kết quả vẫn y như cũ)
		driver.findElement(By.xpath("//input[@value='5+ days']")).click();
		driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).click();
		//verify 
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='5+ days']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).isSelected());	}
 public void checktocheckboxorradio(String xpathLocator) {
	 //kiểm tra trc nó đã chọn hay chưa
	 //nếu chọn r thì ko cần lick nữa, nếu chưa chọn thì lick vào
	 if (!driver.findElement(By.xpath(xpathLocator)).isSelected()) {
		 driver.findElement(By.xpath(xpathLocator)).click();
	}
 }
 public void unchecktocheckboxorradio(String xpathLocator) {
	 if (driver.findElement(By.xpath(xpathLocator)).isSelected()) {
		 driver.findElement(By.xpath(xpathLocator)).click();
	}
 }
 public boolean iselementselected(String xpathLocator) {
	 return driver.findElement(By.xpath(xpathLocator)).isSelected();
 }
	//@Test
	public void TC_02_radio_checkbox_cach_2() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		//chọn vài check box
		checktocheckboxorradio("//input[@value='Diabetes']");
		checktocheckboxorradio("//input[@value='Fainting Spells']");
				//verify đã chọn thành công
		Assert.assertTrue(iselementselected("//input[@value='Diabetes']"));
		Assert.assertTrue(iselementselected("//input[@value='Fainting Spells']"));
				//chọn thêm radio
		checktocheckboxorradio("//input[@value='5+ days']");
		checktocheckboxorradio("//input[@value='1-2 cups/day']");
				//verify 
				Assert.assertTrue(iselementselected("//input[@value='5+ days']"));
				Assert.assertTrue(iselementselected("//input[@value='1-2 cups/day']"));
				//bỏ chọn check box-với checkbox thì chọn lại nó = bỏ chọn
				unchecktocheckboxorradio("//input[@value='Diabetes']");
				unchecktocheckboxorradio("//input[@value='Fainting Spells']");
				//verify đã bỏ chọn thành công
				Assert.assertFalse(iselementselected("//input[@value='Diabetes']"));
				Assert.assertFalse(iselementselected("//input[@value='Fainting Spells']"));
				//bỏ chọn radio (thằng này éo bỏ chọn đc, chỉ có chọn qua thằng khác, nên click vào bỏ chọn kết quả vẫn y như cũ)				
				driver.findElement(By.xpath("//input[@value='5+ days']")).click();
				driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).click();
				//verify 
				Assert.assertTrue(iselementselected("//input[@value='5+ days']"));
				Assert.assertTrue(iselementselected("//input[@value='1-2 cups/day']"));
		
	}

	//@Test
	public void TC_03_Jotform_Select_All() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		List<WebElement> Allcheckboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
		//dùng vòng lặp
		for (WebElement checkbox : Allcheckboxes) {
			checktocheckboxorradio(checkbox);
	
			
		}
		//dùng vòng lặp để duyệt qua và kiểm tra
				for (WebElement checkbox : Allcheckboxes) {
				Assert.assertTrue(iselementselected(checkbox));					
				}
		//bỏ chọn hết
				for (WebElement checkbox : Allcheckboxes) {
					unchecktocheckboxorradio(checkbox);
			
				}
			//dùng vòng lặp để duyệt qua và kiểm tra cái bỏ chọn
				for (WebElement checkbox : Allcheckboxes) {
				Assert.assertFalse(iselementselected(checkbox));		
				}
	}
	@Test
	public void TC_04_Jotform_Select_All() {
		//bài tập này là default custom
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		//check
		checktocheckboxorradio("//label[text()='Luggage compartment cover']/preceding-sibling::input");
		Assert.assertTrue(iselementselected("//label[text()='Luggage compartment cover']/preceding-sibling::input"));
		//uncheck
		unchecktocheckboxorradio("//label[text()='Luggage compartment cover']/preceding-sibling::input");
		Assert.assertFalse(iselementselected("//label[text()='Luggage compartment cover']/preceding-sibling::input"));
	}
	//phần này đc thêm vào vì phần implicitlywait đôi khi trong 30s nếu driver.get url lâu có thể gây ra fail test case
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);   //vì sleep nhận là milisecond nên 3s*1000 mới ra ms
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	public void checktocheckboxorradio(WebElement element) {
		 if (!element.isSelected()) {
			element.click();
		}
	 }
	public boolean iselementselected(WebElement element) {
		 return element.isSelected();
	 }
	public void unchecktocheckboxorradio(WebElement element) {
		 if (element.isSelected()) {
			element.click();
		 }
	}
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
}
