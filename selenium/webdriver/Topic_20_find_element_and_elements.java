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

public class Topic_20_find_element_and_elements {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		//set 15s Wait khi tìm element/node
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");
		
	}

	//Bài này học chủ yếu để hiểu rõ hơn về cái find.element/elements khi áp dụng implicitwait
	@Test
	public void TC_01_findelement() {
		// 1. Tìm thấy duy nhất 1 element/node
//=> thì thao tác trực tiếp lên node này luôn, ở đây mình set cái wait timeout 15s nhưng vì tìm thấy 1 node trong vòng 15s (ra k/q lập tức hoặc trong 5~10s gì đó) thì nó ko wait hết 15s để tìm nữa và chuyển qua step tiếp theo
		driver.findElement(By.cssSelector("input#email"));//code này chỉ chạy tìm thôi nhé chứ ko có thao tác gì cả
		
		// 2. Tìm thấy nhiều hơn 1 element/node
		//nó sẽ chỉ thao tác với node đầu tiên tìm thấy, ko quan tâm bọn còn lại vì nó là findelement (ko có s)
		//ở đây giả dụ tìm thấy 2 thằng element email này đi thì mình sendkeys cho nó nhập vào (ở đây tìm thấy 2 cái element email-> nó chỉ nhập vào cái ele tìm thấy đầu tiên)
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("ditconme@gmail.com");
	//Lưu ý: tìm thấy 2 cái trở lên nên nhớ tìm cái locator chuẩn (duy nhất) mà mình cần thao tác, vì ví dụ nó tìm đc 5 cái element thì khả năng fail case cao vì tỷ lệ nó thao tác đúng cái element mình cần chỉ có 20%

		// 3. Không tìm thấy cái nào
		//nó sẽ tìm đi tìm lại trong 15s (cơ chế là 0.5s/1 lần tìm) -> hết 15s vẫn ko có thì test case fail lập tức
		//và throw ra 1 ngoại lê NoSuchElementException (giải thích nôm na nó giống cái sysout - in ra cho mình xem có cái lỗi gì - ở đây là sẽ hiện méo có thằng type check nào cả)
		driver.findElement(By.cssSelector("input[type='check']")); //ko có cái type nào là check trong html hết -> fail
		
	}

	@Test
	public void TC_02_findelements() {
		// 1. Tìm thấy duy nhất 1 element/node
		//Lưu nó vào cái List webelement (tuy là list nhưng chỉ có mỗi 1 ele) 
		List<WebElement> elements = driver.findElements(By.cssSelector("input#email"));
		
		// 2. Tìm thấy nhiều hơn 1 element/node
		//ở đây tìm thấy đc 2 cái element email - lưu vào list 2 cái
		elements = driver.findElements(By.cssSelector("input[type='email']"));
		
		// 3. Không tìm thấy cái nào
//nó sẽ tìm đi tìm lại trong 15s (cơ chế là 0.5s/1 lần tìm) -> hết 15s vẫn ko có thì thằng này nó lại ko đánh fail test case + có thể chuyển qua step tiếp theo
//và trả về 1 cái list rỗng empty = 0 (đây là các sự khác biệt so với findelement - vì thằng findelements này nó tìm mấy cái ele ko có trong html đc, ko có thì nó cho là rỗng và qua bước tiếp theo chứ ko đánh fail)
		elements = driver.findElements(By.cssSelector("input[type='check']"));
		System.out.println("List element number =" + elements.size()); //in ra cho xem kết quả là 0 có element nào nhưng ko đánh fail như thằng trên
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
