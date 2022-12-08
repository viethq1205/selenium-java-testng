package webdriver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_18_Upload_File {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;
	
	//tạo ra mấy cái biến file tên hình ảnh
	String matcuoiFileName = "3b4de8c29174b2f8a544c6b8cfa39a4a.png";
	String rolexFileName = "25f1f94e8d524d0c1443.jpg";
	String cheemsFileName = "21c1cce02ecf2585c4e196a4951683b4.jpg";
	//giờ thì tạo biến cho cái patch (đường dẫn), cái đường dẫn này là cái properties của seleniumjavatestng (projectpath) + đuôi của cái properties hình ảnh ấy
	String matcuoiFilePatch = projectPath + "\\uploadfiles\\" + matcuoiFileName;
	String rolexFilePatch = projectPath + "\\uploadfiles\\" + rolexFileName;
	String cheemsFilePatch = projectPath + "\\uploadfiles\\" + cheemsFileName;
	
	String autoItFirefox1TimePath = projectPath + "\\autoITscript\\firefoxUploadOneTime.exe";
	String autoItFirefoxMultipleTimePath = projectPath + "\\autoITscript\\firefoxUploadMultiple.exe";
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		jsExecutor = (JavascriptExecutor) driver;
		
	}

	//@Test
	public void TC_01_() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		sleepInSecond(1);
		
		//load file lên site = cách sendkeys cái đường dẫn file (location) + tên file như dưới đây //lưu ý phải có cái thẻ input type file
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(matcuoiFilePatch);
		sleepInSecond(1);
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(rolexFilePatch);
		sleepInSecond(1);
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(cheemsFilePatch);
		sleepInSecond(1);
	
		//verify file đã đc load lên site thành công 
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class = 'name' and text() = '3b4de8c29174b2f8a544c6b8cfa39a4a.png']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class = 'name' and text() = '25f1f94e8d524d0c1443.jpg']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class = 'name' and text() = '21c1cce02ecf2585c4e196a4951683b4.jpg']")).isDisplayed());
		
		//click upload //dùng vòng lặp vì mình click 3 hình nên dùng cách này cho nhanh, chữ lỡ >10 hình mà mình ngồi viết từng cái dòng code click thì ko nên
		List<WebElement> buttonUpLoad = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : buttonUpLoad) {
			button.click();
			sleepInSecond(2);	
		}
		
		//verify đã upload thành công (đây là verify cái đường link - khi mình up lên site này nó sẽ cho ra 2 phần - 1 cái link mình nhấp vào sẽ mở ra 1 window xem đc hình đầy đủ fullsize và 1 cái kế bên là hình ảnh cỡ nhỏ thể hiện đã loadfile thành công )
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '3b4de8c29174b2f8a544c6b8cfa39a4a.png']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '25f1f94e8d524d0c1443.jpg']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '21c1cce02ecf2585c4e196a4951683b4.jpg']")).isDisplayed());
		
		//giờ là verify cái hình thu nhỏ thể hiện đã đc up thành công //dùng cái isimageloaded đã đc học ở bài trc javaexecutor
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'3b4de8c29174b2f8a544c6b8cfa39a4a.png')]"));
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'25f1f94e8d524d0c1443.jpg')]"));
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'21c1cce02ecf2585c4e196a4951683b4.jpg')]"));	
	}

	//@Test
	public void TC_02_() {//case này test upload nhiều file 1 lúc, khác nhau chỗ sendkeys mà thôi (multiple)
		//theo cách làm manual thì khi ta chọn file để up, như trên ta muốn chọn cùng lúc 3 file thì chỗ file name của cái open file dialog sẽ hiện tên cả 3 file như sau "21c1cce02ecf2585c4e196a4951683b4.jpg" "25f1f94e8d524d0c1443.jpg" "3b4de8c29174b2f8a544c6b8cfa39a4a.png" 
		//vậy khi sendkeys sau mỗi cái "" thì ta thêm cái \n (xuống dòng) là đc
		//Lưu ý: cũng tùy site cơ chế nó chỉ cho up 1 file 1 lượt, ko cho up multiple (hoặc kiểm tra attribute add file của nó có multiple hay ko)
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		sleepInSecond(1);
		
		//load file lên site = cách sendkeys cái đường dẫn file (location) + tên file như dưới đây
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(matcuoiFilePatch + "\n" + rolexFilePatch + "\n" +cheemsFilePatch); 
		sleepInSecond(3);

		//verify file đã đc load lên site thành công 
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class = 'name' and text() = '3b4de8c29174b2f8a544c6b8cfa39a4a.png']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class = 'name' and text() = '25f1f94e8d524d0c1443.jpg']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class = 'name' and text() = '21c1cce02ecf2585c4e196a4951683b4.jpg']")).isDisplayed());
		
		//click upload //dùng vòng lặp vì mình click 3 hình nên dùng cách này cho nhanh, chữ lỡ >10 hình mà mình ngồi viết từng cái dòng code click thì ko nên
		List<WebElement> buttonUpLoad = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : buttonUpLoad) {
			button.click();
			sleepInSecond(2);	
		}
		
		//verify đã upload thành công (đây là verify cái đường link - khi mình up lên site này nó sẽ cho ra 2 phần - 1 cái link mình nhấp vào sẽ mở ra 1 window xem đc hình đầy đủ fullsize và 1 cái kế bên là hình ảnh cỡ nhỏ thể hiện đã loadfile thành công )
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '3b4de8c29174b2f8a544c6b8cfa39a4a.png']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '25f1f94e8d524d0c1443.jpg']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '21c1cce02ecf2585c4e196a4951683b4.jpg']")).isDisplayed());
		
		//giờ là verify cái hình thu nhỏ thể hiện đã đc up thành công //dùng cái isimageloaded đã đc học ở bài trc javaexecutor
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'3b4de8c29174b2f8a544c6b8cfa39a4a.png')]"));
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'25f1f94e8d524d0c1443.jpg')]"));
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'21c1cce02ecf2585c4e196a4951683b4.jpg')]"));	
	}

	//@Test
	public void TC_03_Update_1_file_voi_autoIT() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		sleepInSecond(3);
		
		//cách dùng thằng autoIT thì khi muốn load file lên mình phải có bước mở open file dialog chứ ko truyền thẳng dễ dàng như thằng sendkeys
		driver.findElement(By.cssSelector("span.btn-success")).click();
		sleepInSecond(1);
		
		//load file lên site nhưng dùng autoIT
		Runtime.getRuntime().exec(new String[] {autoItFirefox1TimePath, matcuoiFilePatch});
		sleepInSecond(1);
		
		//thằng autoIT bất tiện ở cái nếu mình muốn truyền tiếp file và tiếp file ... nhiều file khác thì lại phải lặp đi lặp lại bước mở open file dialog rồi nhét cái run time vào (cần nhiều script)
		//đã nói nhược điểm ở trong zalo
		//nên bài tập này truyền 1 cái để test thôi
		
		//verify file đã đc load lên site thành công 
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class = 'name' and text() = '3b4de8c29174b2f8a544c6b8cfa39a4a.png']")).isDisplayed());
			
				
		//click upload
		List<WebElement> buttonUpLoad = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : buttonUpLoad) {
		button.click();
		sleepInSecond(5);	
	}
				
		//verify đã upload thành công (đây là verify cái đường link - khi mình up lên site này nó sẽ cho ra 2 phần - 1 cái link mình nhấp vào sẽ mở ra 1 window xem đc hình đầy đủ fullsize và 1 cái kế bên là hình ảnh cỡ nhỏ thể hiện đã loadfile thành công )
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '3b4de8c29174b2f8a544c6b8cfa39a4a.png']")).isDisplayed());
				
		//giờ là verify cái hình thu nhỏ thể hiện đã đc up thành công //dùng cái isimageloaded đã đc học ở bài trc javaexecutor
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'3b4de8c29174b2f8a544c6b8cfa39a4a.png')]"));
				
	}
	
	@Test
	public void TC_04_Update_nhieu_file_voi_autoIT() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		sleepInSecond(3);
		
		//cách dùng thằng autoIT thì khi muốn load file lên mình phải có bước mở open file dialog chứ ko truyền thẳng dễ dàng như thằng sendkeys
		driver.findElement(By.cssSelector("span.btn-success")).click();
		sleepInSecond(3);
		
		//load file lên site nhưng dùng autoIT
		Runtime.getRuntime().exec(new String[] {autoItFirefoxMultipleTimePath, matcuoiFilePatch, rolexFilePatch});
		sleepInSecond(3);
		//đến đây lại có nhược điểm đã nói trong zalo, đó là chọn nhiều đường dẫn sẽ dẫn đến quá giới hạn từ trong phần file name của open file dialog (>255 từ)
		//nên đường dẫn của hình thứ 3 trở lên thường sẽ bị lỗi ko load lên đc
		//cách duy nhất chỉ có mấy cái đường dẫn mình ráng đặt tên gọn lại
		//nên case này mình up 2 cái để test đc rồi
		
		
		//verify file đã đc load lên site thành công 
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class = 'name' and text() = '3b4de8c29174b2f8a544c6b8cfa39a4a.png']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class = 'name' and text() = '25f1f94e8d524d0c1443.jpg']")).isDisplayed());
			
				
		//click upload
		List<WebElement> buttonUpLoad = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : buttonUpLoad) {
		button.click();
		sleepInSecond(5);	
	}
				
		//verify đã upload thành công (đây là verify cái đường link - khi mình up lên site này nó sẽ cho ra 2 phần - 1 cái link mình nhấp vào sẽ mở ra 1 window xem đc hình đầy đủ fullsize và 1 cái kế bên là hình ảnh cỡ nhỏ thể hiện đã loadfile thành công )
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '3b4de8c29174b2f8a544c6b8cfa39a4a.png']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text() = '25f1f94e8d524d0c1443.jpg']")).isDisplayed());
				
		//giờ là verify cái hình thu nhỏ thể hiện đã đc up thành công //dùng cái isimageloaded đã đc học ở bài trc javaexecutor
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'3b4de8c29174b2f8a544c6b8cfa39a4a.png')]"));
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'25f1f94e8d524d0c1443.jpg')]"));
		
		
	}
	//phần này đc thêm vào vì phần implicitlywait đôi khi trong 30s nếu driver.get url lâu có thể gây ra fail test case
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);   //vì sleep nhận là milisecond nên 3s*1000 mới ra ms
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getElement(locator));
		return status;
	}
	
	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}

	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
}
