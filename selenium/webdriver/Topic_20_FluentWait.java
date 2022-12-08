package webdriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.remote.server.handler.FindElements;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class Topic_20_FluentWait {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	FluentWait<WebDriver> fluentDriver;						//FluentWait<T> T là type tham số của hàm apply, ở đây ta apply webdriver
	FluentWait<WebElement> fluentElement;	
	long alltime = 15; //second
	long pollingTime = 100; //milis

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
	}
	
	//sự khác biệt của thằng fluent là mấy cái wait kia có cơ chế mặc định 0.5s tìm 1 lần, thì thằng fluent có thể custom thời gian tìm
	//à thằng explicit cũng có hỗ trợ custom thời gian tìm
	//explicitWait = new WebDriverWait(driver, 15, 1000); //1000 là milis = 1s, ở đây ta đã custom lại cái cơ chế tìm kiếm là 1s/lần
	//Thằng fluentwait viết code rất dài chứ ko ngắn gọn 1 dòng như implicit, explicit
	
	//@Test
	public void TC_01_() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		//khởi tạo
		fluentDriver = new FluentWait<WebDriver>(driver);
		
		//set tổng time và tần suất check
		fluentDriver.withTimeout(Duration.ofSeconds(15)) //dạng viết code nhiều dòng nhiều . như này từng viết ở topic actions rồi, gọi là action chaining, chuỗi hành động
		.pollingEvery(Duration.ofMillis(333))  //1/3s check 1 lần (set milli giay, giay, phut gi cung dc)
		.ignoring(NoSuchElementException.class);
		
		//aplly điều kiện
		fluentDriver.until(new Function<WebDriver, WebElement>() {

			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.cssSelector("div#start>button"));
			}
		});
		
		//từ chỗ khởi tạo đến cái dòng ngay trên dòng này chỉ để chờ wait Start button xuất hiện :D?? (dài dòng vl còn khó hiểu)
		//cùng 1 chức năng wait findele như implicit nhưng thằng fluent wait quá dài dòng, chỉ đc thêm mỗi cái custom cái tần suất check
		//chỉ dùng cho case đặc thù vl cần có các tần suất check theo yêu cầu
		
		driver.findElement(By.cssSelector("div#start>button")).click();
	}

	//có cách viết ngắn hơn, giúp khi test các case khác đc gọn lại mà lười viết quá có hình minh họa trong zalo
	
	@Test
	public void TC_02_() { //như case này thì test thời gian đếm ngược áp dụng fluentwait hợp lý
		driver.get("https://automationfc.github.io/fluent-wait/");
		
		WebElement countdownTime = findElement("//div[@id='javascript_countdown_time']");
		
		fluentElement = new FluentWait<WebElement>(countdownTime);
		fluentElement.withTimeout(Duration.ofSeconds(alltime))
		.pollingEvery(Duration.ofMillis(pollingTime))  
		.ignoring(NoSuchElementException.class);
		
		fluentElement.until(new Function<WebElement, Boolean>() {

			@Override
			public Boolean apply(WebElement element) {
				return element.getText().endsWith("00"); // 00 vì đang kiểm tra đếm ngược từ hơn 12s còn 01:00:00s //do đếm ngược về 00s hơn 12s mà nếu mình set all time 10s thì sẽ fail
			}
		});
		
	}

	public WebElement findElement (String xpathLocator) {
		fluentDriver = new FluentWait<WebDriver>(driver);
		fluentDriver.withTimeout(Duration.ofSeconds(alltime)).pollingEvery(Duration.ofMillis(pollingTime)).ignoring(NoSuchElementException.class);
		return fluentDriver.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath(xpathLocator));
			}
		});
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
		driver.quit();
	}
}
