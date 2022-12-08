package webdriver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Actions_PartII {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	Actions action;
	JavascriptExecutor jsExecutor;
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		action = new Actions(driver);
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;
	}

	//@Test
	public void TC_01_Right_Click() {
		//context menu: đây là tên gọi cái bảng xuất hiện mà sau khi mình nhấn chuột phải, nên đây cũng là tên hàm khi mình test right click
	
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		sleepInSecond(1);
		
		//click chuột phải vào button
		action.contextClick(driver.findElement(By.cssSelector("span.context-menu-one"))).perform();
		sleepInSecond(1);
		
		//Hover chuột vào context menu, ở đây ta chọn option paste nhưng ko click
		action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();
		
		//nhắc lại: verify mấy thằng như này ta để ý đến html vì ở option ta chọn đoạn code trong html sẽ bị thay đổi, kiểu như ta chọn trên UI là option copy thì html sẽ thay đổi thành copy-selected
		//đây chính là cơ sở để ta verify, như ở dưới đoạn html trên web đã thay đổi có thêm chữ hover, visible
		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-paste.context-menu-hover.context-menu-visible")).isDisplayed());
		
		//click luôn vào chữ paste
		action.click(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();
		sleepInSecond(1);
		
		//handle, verify cái alert luôn
		//accept cái alert
		driver.switchTo().alert().accept();
		sleepInSecond(1);
		
		//sau khi accept alert thì ta verify lại cái đoạn html vì mấy cái như context menu hover, visible đã biến mất, ko hiển thị nữa (invisible)
		//để ý ko dùng true đc vì nó đã biến mất, nên nếu ta dùng true sẽ ra ko có (false) true + false = false
		//nên ta dùng false vì false + false = true
		Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-paste")).isDisplayed());
		
		//cái tính năng này cũng ít gặp vl nên hiếm làm
	}

	//@Test
	public void TC_02_Drag_and_Drop_HTML4() { 
		//đây là dạng case ko nên làm auto vì ko ổn định
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		sleepInSecond(1);
		//kéo thả
		WebElement smallCircle = driver.findElement(By.cssSelector("div#draggable"));
		WebElement bigCircle = driver.findElement(By.cssSelector("div#droptarget"));
		action.dragAndDrop(smallCircle, bigCircle).perform();
		sleepInSecond(2);
		
		//verify thằng bigcircle trước, sau khi kéo thả vào thành công có chữ u did great
		Assert.assertEquals(bigCircle.getText(), "You did great!");
		
		//có cách verify nữa là verify cái màu background vì khi thả thành công nó biến thành màu xanh, đã từng làm ở bài button
		String rgbacolor = bigCircle.getCssValue("background-color");
		String hexaColor = Color.fromString(rgbacolor).asHex().toUpperCase();
	
		//verify
		Assert.assertEquals(hexaColor, "#03A9F4");
	}

	@Test
	public void TC_03_Drag_and_Drop_HTML5() throws IOException {
		//đây là dạng case ko nên làm auto vì ko ổn định
		
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		sleepInSecond(1);
		
		String jsContentFile = getContentFile(projectPath + "/dragandrop/drag_and_drop_helper.js");
		jsExecutor.executeScript(jsContentFile, "$('div#column-a').simulateDragDrop({dropTarget: 'div#column-b'});");
		sleepInSecond(2);
		
		jsExecutor.executeScript(jsContentFile, "$('div#column-a').simulateDragDrop({dropTarget: 'div#column-b'});");
		sleepInSecond(2);
		
		
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
	
	public String getContentFile(String filePath) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(filePath);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}
}
