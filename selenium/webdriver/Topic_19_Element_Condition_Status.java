package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_19_Element_Condition_Status {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}

	//@Test
	public void TC_01_Visible_Displayed() {//trong thư viện code chuẩn thì dùng từ visibility 
		driver.get("https://www.facebook.com/");
		
		//yeu cau test case nay thoa man dk1 o phan element condition trong topic 15/16 ggdocs, vừa có element trong html/dom vừa hiển thị trên UI (visible)
		//tức là mắt mình nhìn thấy đc cái input trong html phải xuất trên UI (visible) như input textbox thì thấy đc cái textbox, img thì thấy hình ảnh ...
		
		//wait cho email address textbox hien thi (visible) trong vong 10s
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		
		//=> test này thành công thì đk1 đc thỏa mãn 2 cái dưới đây: trên UI có hiển thị (visible) ô email và html/dom cũng có element
		
		//sau 10s chờ email text box xuất hiện thành công r thì mình thử thao tác với nó
		driver.findElement(By.id("email")).sendKeys("automationfc@gmail.net");
	}

	//@Test
	public void TC_02_Invisible_Unisplayed_1() { //test phần nhìn trên UI, liệu ko có trên UI thì có code trong HTML hay ko
		driver.get("https://www.facebook.com/");
		
	//TC này thì mình làm theo cái dk2, chia ra 2 trường hợp
	//TH1: có element trong HTML nhưng ko xuất hiện trên UI (invisible - code đang ẩn)
	//ví dụ cho dễ hiểu (cũng có giải thích trên zalo): 
//khi mình nhập 1 cái email hợp lệ vào textbox, dòng code bị ẩn này mới sáng lên và hiện lên textbox có thể thao tác (textbox yêu cầu mình nhập lại email để confirm), xóa hết các email đã ghi thì dòng này lại bị ẩn và textbox confirm email này biến mất
	
		//vào fb r thì click vào create account
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		
		//ở đây cái textbox nhập lại email đã nói trong ví dụ trên nó ko xuất hiện nhưng có trong html, mình sẽ chờ cái nhập lại email textbox ko hiển thị trong 10s
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation__")));
	
	}

	//@Test
	public void TC_02_Invisible_Unisplayed_2() {
		
		//TH2: ko có element trong HTML và cũng ko xuất hiện trên UI
		driver.get("https://www.facebook.com/");
		//ở đây cái textbox nhập lại email đã nói trong ví dụ trên nó ko xuất hiện và cũng ko có trong html, mình sẽ chờ cái nhập lại email textbox ko hiển thị trong 10s
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation__")));
	}
	//phần này đc thêm vào vì phần implicitlywait đôi khi trong 30s nếu driver.get url lâu có thể gây ra fail test case
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);   //vì sleep nhận là milisecond nên 3s*1000 mới ra ms
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}

	//@Test
	public void TC_03_Presence_1() {
		//cái condition này thì khá giống cái visible displayed ở cái có element trong HTML/DOM 
		driver.get("https://www.facebook.com/");
		
		//nhưng khác ở cái thằng visible nó có nghĩa là mắt mình phải nhìn thấy đc trên UI, ví dụ trong html input 1 cái textbox thì trên UI phải nhìn thấy đc cái textbox này vì nó visible
		
		//còn thằng presence thì chỉ cần thỏa đk element xuất hiện trong html/dom là đc (bắt buộc) - tức dòng code - element này có tồn tại trong html, còn trên UI có hiển thị hay ko ko quan trọng
	
	//ví dụ: trong cái dropdown chọn stt đúng ko thì nó nhiều số phải kéo xuống, case visible tức mắt mình chỉ nhìn thấy từ 1 đến 9 nhưng như vậy cũng là thỏa đk vì mắt mình thấy nó có trên UI và có element trong html
		
		//còn case presence nó check hết các element có trong html từ 1->99 đầy đủ là ok, còn việc có hiển thị hay ko trên Ui thì méo quan trọng
		
		//nên cũng chia ra 2 trường hợp. TH1: có trong HTML/dom (presence) và cũng có hiển thị trên UI
		
		//chờ check cái element email có presence trong html/dom là xong và thành công tức là nó có trên UI luôn
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
		
	}
	
	//@Test
	public void TC_03_Presence_2() {
		driver.get("https://www.facebook.com/");
		
		//TH2: element có trong html/dom (presence) nhưng ko hiển thị trên UI (kiểu dòng code đang ẩn giống cái trường hợp ô nhập lại để confirm email trên ấy)
		
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		
		//chờ check cái element nhập lại email có presence trong html/dom nhưng trên Ui ko hiển thị cái textbox này là xong
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.name("reg_email_confirmation__")));
	}
	
	@Test
	public void TC_04_Staleness() {
		//condition này khá ít dùng: element trước đó có trong html/dom và sau đó biến mất (kiểu như tắt popup, tắt gì đó đi), ko có trong html/dom nữa và cũng chắc chắc méo hiển thị trên UI
		//ở bài tập này thì mình thực hiện yêu cầu khi mở popup lên thì cái element của ô reenter email đang xuất hiện nhưng close popup đi thì element này biến mất
		
		driver.get("https://www.facebook.com/");
		//mở cái create account lên
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		
		//lúc này ko cần nhập email gì để cho nó hiện cái textbox "nhập lại email" hiện lên đâu (vì sau khi mở popup create lên thì code đang xuất hiện rồi nhưng bị ẩn đi thôi)
		WebElement reenterEmail = driver.findElement(By.name("reg_email_confirmation__"));
		
		//giờ thao tác với element khác để mất cái element "nhập lại email" này đi, ở đây là hành động close popup create
		driver.findElement(By.cssSelector("img._8idr")).click();
		
		//giờ mình check nó méo còn trong HTML/DOM nữa với lệnh staleness
		explicitWait.until(ExpectedConditions.stalenessOf(reenterEmail));
		
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
