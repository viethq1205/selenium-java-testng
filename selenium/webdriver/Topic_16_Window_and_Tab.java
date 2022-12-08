package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Window_and_Tab {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	//@Test
	public void TC_01_() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//có hàm để lấy ID cái window/tab để còn chạy code 
		//có 2 hàm, 1 cái để lấy id cái window/tab đang đứng (active) và hàm lấy hết tất cả id ko phân biệt đang ở đâu: getwindowhandle/getwindowhandles
		//thằng handle thì set theo String bth thôi nhưng thằng handles thì set theo như này Set<String> + đặt tên
		//thằng List, Set, Queue là trong cái phần Collection của java để làm mấy cái tập hợp, sự khác biệt là List cho trùng, Set thì ko
		
		//case 1: 2 window/tab (kiểu như so sánh 2 sp thì nó hiện ra thêm cái window để xem so sánh thông số sp r mình tắt đi thế là xong - đây là kiểu mình thường gặp nhất)
		//case 2: >2 window/tab ít gặp nhưng ở đây làm thử để còn biết
		
		//lấy id window hiện tại
		String parentwindowid = driver.getWindowHandle();
		
		//click vào link gg trong web bên trên để ra tab mới
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(2);
		
		//lấy hết tất cả id của 2 tab để chạy vòng lặp kiểm tra, cái id nào khác với id của cái parentwindowid thì mình switch qua cái này
		Set<String> allWindowID = driver.getWindowHandles();
		
		//oke sau khi lấy đc 2 cái ID, giờ mình dùng vòng lặp để duyệt và dùng if để chuyển qua tab mới
		for (String id : allWindowID) {
			if (!id.equals(parentwindowid)) {  //như ở đây ta có id.equals(parentwindowid) = id của trang đang đứng thì muốn chuyển qua tab kia thì mình phải hiểu như sau - nếu ko phải id của parentwindow muốn chuyển sang tab mới thì cái "ko phải" (phủ định) ta phải thêm dấu ! trc cái id
				//cái condition trên có nghĩa là nếu ko phải id của parent window (web chính mình driver.get ở trên) thì sẽ switch qua cái id còn lại (tức tab mới)
				driver.switchTo().window(id);
				sleepInSecond(2);
				//oke vậy là xong, khi test nó đã switch qua cái tab GG mới mở trên
			}
		}
		
		//verify đã switch sang GG thành công
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
		
		//nhập từ khóa
		driver.findElement(By.cssSelector("input[name='q']")).sendKeys("selenium");
		sleepInSecond(3);
		
		
		//giờ ví dụ mình muốn switch về trang ban đầu thì cũng làm như trên, phải lấy cái id của thằng GG (vì lúc này nó đang giống như là thằng cha) rồi duyệt lại rồi mới về đc trang ban đầu
		String googlewindowid = driver.getWindowHandle();
		
		
		allWindowID = driver.getWindowHandles(); //thằng này đã set trên r nên dưới đây bỏ cái string đi ko sẽ bị lỗi trùng
		
		
		for (String id : allWindowID) {
			if (!id.equals(googlewindowid)) { 
				driver.switchTo().window(id);
				sleepInSecond(2);
				
			}
		}
		
		//verify đã switch về page ban đầu thành công
		Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
		sleepInSecond(2);
		
		//oke, vậy là lúc chạy test đang ở bên GG nó sẽ tự động chuyển về trang github
		//Note: ko nên cop lại đoạn code như trên để switch qua switch lại, ta nên làm ngắn gọn lại, tạo 1 cái public void ở dưới
		
		//như giờ mình từ trang github lại muốn chuyển qua cái google thì dùng đoạn đã set bên dưới cho lẹ //note: nhớ cái phần tử mình truyền vào phải là cái tab mình đang đứng ở đây là parentwindow = github
		switchToWindowByID(parentwindowid);
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
	}

	@Test
	public void TC_02_() {
		//đây là test case của trường hợp 2 đã nói ở trên: >2 window/tab thì ta dựa vào title
		driver.get("https://automationfc.github.io/basic-form/index.html");
		sleepInSecond(1);
		
		//click vào link gg trong web bên trên để ra tab mới
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(2);
		
		//swithc qua GG
		switchtowindowbytitle("Google");
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
		
		//giờ muốn switch lại về main page thì cũng dựa theo title
		switchtowindowbytitle("SELENIUM WEBDRIVER FORM DEMO");
		Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
		
		//khi về trang main page ta click vào fb
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		sleepInSecond(1);
		
		//sau khi nhấp link fb thì giờ ta đã có 3 tab, muốn switch qua tab thứ 3 là fb thì cũng dựa vào cách switch theo title như trên thôi
		//lưu ý vì default language của firefox đang set là tiếng anh nên sẽ vào trang fb tiếng anh, nếu mình dùng title của fb VN sẽ bị lỗi, khi làm chú ý đến ngôn ngữ
		switchtowindowbytitle("Facebook – log in or sign up");
		sleepInSecond(1);
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");
		
		//muốn làm thằng tiki thì cách làm cũng tương tự như fb
		switchtowindowbytitle("SELENIUM WEBDRIVER FORM DEMO");
		sleepInSecond(1);
		Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		sleepInSecond(1);
		switchtowindowbytitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		//thử search 1 sp
		driver.findElement(By.cssSelector("input[class^='FormSearch']")).sendKeys("macbook pro 2022");

		
	}

	//lưu ý khá quan trọng: đừng để bị luồng hoạt động của window đánh lừa, ví dụ như khi đóng 1 window, thì code nó ko tự nhận dạng chạy về main page đâu nhé, vẫn phải switchto về như bth
	@Test
	public void TC_03_() {
		
	}
	
	public void switchToWindowByID(String otherID) { //otherID ở đây tức là 1 cái tab khác ấy
		Set<String> allWindowID = driver.getWindowHandles();
		
		for (String id : allWindowID) {
			if (!id.equals(otherID)) {  
				driver.switchTo().window(id);
				sleepInSecond(2);
			}
		}
	}
	
	public void switchtowindowbytitle (String expectedPagetitle) { // set up dành cho trường hợp trên 2 tab/window (ít gặp)
		Set<String> allWindowID = driver.getWindowHandles();
		
		for (String id : allWindowID) {
			//thay vì if trước như trên kia thì ở đây mình phải switch trước
			driver.switchTo().window(id);
			//lấy ra title của page này
			String actualPagetitle = driver.getTitle();
			//giờ thì if
			if (actualPagetitle.equals(expectedPagetitle)) {
				sleepInSecond(2);
				break;
			}
		}
		
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
