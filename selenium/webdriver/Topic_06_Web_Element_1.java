package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;          //bọn này là đc import từ thư viện testng
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element_1 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");   //file thực thi này luôn luôn nằm trước khi setting driver như ở dòng dưới
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
// các khai báo như ở topic 5 ko nên khai báo gán biến sớm trước khi khởi chạy url (driver.get) ở phần @Test vì chưa có page thì đã có cm gì đâu để mà gán
	@Test
	public void TC_01_() {
		driver.get("https://demo.nopcommerce.com/login?returnUrl=%2Fregister");
		// muốn tương tác đc ele thì phải tìm đc ele thông qua các locator của nó thông qua biến By, vd: By.id/class/name/xpath/css/tagname/linhtext/partiallinktext
		
		
		
		WebElement Element = driver.findElement(By.className(""));
		//hàm clear dùng cho các dạng có thể edit đc như textbox/textarea/dropdown - hàm này dùng để clear dữ liệu, làm sạch sẽ trước khi nhập text
		// cho nên hàm clear (xóa dữ liệu) hay đi cùng với hàm sendkeys (nhập dữ liệu)
		Element.clear();
		Element.sendKeys("");
		// hàm click
		Element.click();
		
		//hàm getAttribute //ngoài ra do hàm attribute này là String nên ta có thể gán vào String searchAttribute = Element.getAttribute();
		Element.getAttribute("placeholder"); // ví dụ dùng hàm này để cho ra kết quả trong cái attribute này là Search Store trong placeholder='Search Store' 
		// ví dụ này là để lấy cái chữ search store trong cái placeholder của 1 web mua sắm //placeholder là gì có thể search gg
		
		//getcssvalue, gán string //hàm này để tìm mấy cái như cỡ chữ, màu, độ rộng, vị trí bla bla, nhìn cái tên là hiểu html css
		//nằm trong test giao diện GUI (test này khá ít dùng)
		Element.getCssValue("background-color"); //vi du: verify màu nền của 1 cái button
		
		//getlocation, lấy vị trí của element so với cái web (bên ngoài)
		Point point = Element.getLocation(); //thằng này hậu tố point nên gán = point
		
		//getsize lấy kích thước của element (bên trong)
		Dimension di = Element.getSize(); // thằng này hậu tố dimension nên gán = dimension
		di.getHeight();
		di.getWidth();
		
		//getrect là sự kết hợp của size + location
		Rectangle rec = Element.getRect(); // thằng này hậu tố Rectangle nên gán = Rectangle

		
		//chụp hình khi testcase fail
		Element.getScreenshotAs(OutputType.FILE); //thường dùng outputtype.file hoặc .base64 
		
		//gettagname dùng khi muốn lấy cái tên thẻ html của element đó (hiếm dùng)
		
		//gettext từ error msg/success msg/label/...
		Element.getText();
		//ví dụ lấy text trong đoạn báo lỗi như Please enter your email
		//gettext dùng khi đoạn text nằm ngoài tagname vi du <span id='emai'> Please enter your email </span>
		//trong khi getattribute là thuộc tagname đó có attribute như <input id='email'> </input>
		
		//isdisplayed dùng để verify element có hiển thị ko, phạm vi tất cả các ele
		//nếu màn hình hiển thị (tức là true) ta dùng:
		Assert.assertTrue(Element.isDisplayed());
		//màn hình ko hiển thị
		Assert.assertFalse(Element.isDisplayed());
		
		//isenabled xem element có thao tác đc hay ko, cách dùng giống như trên, phạm vi tất cả các ele
		Assert.assertTrue(Element.isEnabled());
		Assert.assertFalse(Element.isEnabled());
		
		//isselected, verify xem element đc chọn hay chưa, phạm vi checkbox/radio
		Assert.assertTrue(Element.isSelected());
		Assert.assertFalse(Element.isSelected());
		
		//các element phải trong thẻ form, hàm này thực hiện chức năng giống như ng dùng bth ấn click chọn, enter 
		//tuy nhiên cách này ít đc sử dụng, bth dùng .click nhiều hơn
		Element.submit();
		
	}
	

	@Test
	public void TC_02_() {
		
	}

	@Test
	public void TC_03_() {
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
