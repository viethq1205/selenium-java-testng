package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_ExplicitWait {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	WebDriverWait explicitWait;
	
		//tạo ra mấy cái biến file tên hình ảnh
		String matcuoiFileName = "3b4de8c29174b2f8a544c6b8cfa39a4a.png";
		String rolexFileName = "25f1f94e8d524d0c1443.jpg";
		String cheemsFileName = "21c1cce02ecf2585c4e196a4951683b4.jpg";
		//giờ thì tạo biến cho cái patch (đường dẫn), cái đường dẫn này là cái properties của seleniumjavatestng (projectpath) + đuôi của cái properties hình ảnh ấy
		String matcuoiFilePatch = projectPath + "\\uploadfiles\\" + matcuoiFileName;
		String rolexFilePatch = projectPath + "\\uploadfiles\\" + rolexFileName;
		String cheemsFilePatch = projectPath + "\\uploadfiles\\" + cheemsFileName;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		
		//để ý 1 điều nhé, là trong các hàm của explicitWait thì dùng thẳng By. gì đó luôn thì mới áp dụng đc cái explicit
		//chứ nếu mình dùng cái hàm nào mà phải có driver.findelement rồi mới đến By. thì case khi chạy test sẽ bị fail do nó liên quan đến implicit
		
	}

	//@Test
	public void TC_01_Not_Enough_Time() { 
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		explicitWait = new WebDriverWait(driver, 3);
		
		//click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		//wait cho đến khi cái element của text hello world hiện ra
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#finish>h4"))); 
		//dòng này chính là dòng thể hiện sự khác nhau giữa impli và expli, khi expli cần xác định ele cụ thể nhưng về tính năng thì cũng giống implicit
		
		//nhưng explicit vẫn thể hiện đc nhưng tính năng vượt trội ở khoản thực hiện các trò visible, invisible, clickable ... của element
		
		//verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
		
	}

	//@Test
	public void TC_02_Vua_du_time() {
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		explicitWait = new WebDriverWait(driver, 5);
		
		//click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		//wait cho đến khi cái element của text hello world hiện ra
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#finish>h4"))); 
		//dòng này chính là dòng thể hiện sự khác nhau giữa impli và expli, khi expli cần xác định ele cụ thể nhưng về tính năng thì cũng giống implicit
		
		//verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
		
	}

	//@Test
	public void TC_03_Dư_time() {
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		explicitWait = new WebDriverWait(driver, 50);
		
		//click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		//wait cho đến khi cái element của text hello world hiện ra
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#finish>h4"))); 
		//dòng này chính là dòng thể hiện sự khác nhau giữa impli và expli, khi expli cần xác định ele cụ thể nhưng về tính năng thì cũng giống implicit
		
		//verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
		
	}
	
	//@Test
	public void TC_04_dùng_invisibility() {
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		explicitWait = new WebDriverWait(driver, 50);
		
		//click start
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		//thay vì wait cho đến khi cái element của text hello world hiện ra (visibility), thì mình có thể đổi thành wait cho đến khi cái loading biến mất (invisibility) cũng đc
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading"))); 
		//dòng này chính là dòng thể hiện sự khác nhau giữa impli và expli, khi expli cần xác định ele cụ thể nhưng về tính năng thì cũng giống implicit
		
		//verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
		
	}
	
	//@Test
	public void TC_05_Ajax_Loading() {
		
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		explicitWait = new WebDriverWait(driver, 15);
		
		//wait cho datepicker hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.RadCalendar")));
		
		//verify cái phần Selected Dates chưa có ngày nào đc chọn (phần này là phần hiển thị thông tin đầy đủ thứ ngày tháng năm đi, về)
		Assert.assertEquals(driver.findElement(By.cssSelector("span.label")).getText(), "No Selected Dates to display.");
		
		//wait cho đến khi chỗ ngày có thể nhấn chọn, ở đây chọn ngày 19 đi
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='19']"))); 
		
		//click chọn ngày 19
		driver.findElement(By.xpath("//a[text()='19']")).click();
		
		//wait cho cái ajax loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id*='RadCalendar1']>div.raDiv"))); 
		
		//wait cho cái ngày đã chọn đc phép click trở lại (bước này t cũng thấy khó hiểu)
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class='rcSelected']/a[text()='19']")));
		
		//verify ngày tháng năm đã chọn đc hiển thị ở khung selected dates
		Assert.assertEquals(driver.findElement(By.cssSelector("span.label")).getText(), "Saturday, November 19, 2022");
		
	}
	
	@Test
	public void TC_06_Upload_File() {
		driver.get("https://gofile.io/uploadFiles");
		
		explicitWait = new WebDriverWait(driver, 15);
		
		//làm bài tập này copy vài cái từ bài upload file sang
		
		//wait cho nút add files button hiển thị đầy đủ
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.col-sm-auto>button")));
		
		//giờ load file lên (nên nhớ dùng sendkeys truyền file thì trong html phải có input[type='file'], nên trước khi làm nhớ tìm trên console check coi có hay ko)
		//chứ ta ko mở cái button addfiles lên rồi truyền path vào open file dialog như cách của thằng autoIT
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(matcuoiFilePatch + "\n" + rolexFilePatch + "\n" +cheemsFilePatch);//truyền multiple file
		
		//Wait cho loading icon của từng file biến mất sau khi up thành công
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div#rowUploadProgress-list div.progress"))));
		
		//wait cho đến khi hiện chữ bạn đã upnaod thành công
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Your files have been successfully uploaded']")));
		
		//verify message up thành công này displayed
		Assert.assertTrue(driver.findElement(By.xpath("//h5[text()='Your files have been successfully uploaded']")).isDisplayed());
		
		//wait thằng show files có thể nhấn chọn
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#rowUploadSuccess-showFiles"))).click();
		
		//click vào show file nhưng như thế này là dài dòng, mình có thể tích hợp vào trên kia luôn vì thằng wait phần visi, clickable ... gì đó cũng là 1 webelement
		//driver.findElement(By.cssSelector("button#rowUploadSuccess-showFiles")).click();
		
		//wait + verify cho file name và button down load hiển thị (tích hợp như trên)
Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='3b4de8c29174b2f8a544c6b8cfa39a4a.png']/parent::a/parent::div/following-sibling::div//button[@id='contentId-download']"))).isDisplayed());
Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='25f1f94e8d524d0c1443.jpg']/parent::a/parent::div/following-sibling::div//button[@id='contentId-download']"))).isDisplayed());
Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='21c1cce02ecf2585c4e196a4951683b4.jpg']/parent::a/parent::div/following-sibling::div//button[@id='contentId-download']"))).isDisplayed());
	
		//giờ wait + verify cho file name và button play hiển thị
Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='3b4de8c29174b2f8a544c6b8cfa39a4a.png']/parent::a/parent::div/following-sibling::div//button[contains(@class,'contentPlay')]"))).isDisplayed());
Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='25f1f94e8d524d0c1443.jpg']/parent::a/parent::div/following-sibling::div//button[contains(@class,'contentPlay')]"))).isDisplayed());
Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='21c1cce02ecf2585c4e196a4951683b4.jpg']/parent::a/parent::div/following-sibling::div//button[contains(@class,'contentPlay')]"))).isDisplayed());
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
