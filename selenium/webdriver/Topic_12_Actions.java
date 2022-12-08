package webdriver;

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
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Actions {
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
	public void TC_01_Tooltips() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		sleepInSecond(2);
		//để hover vào cái element làm như sau
		action.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();
		sleepInSecond(1);
		//verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
		
	}

	//@Test
	public void TC_02_Myntra() {
		driver.get("http://www.myntra.com/");
		sleepInSecond(1);
		//hover vào kids
		action.moveToElement(driver.findElement(By.xpath("//a[@data-group='kids']"))).perform();
		sleepInSecond(1);
		//bth ít dùng thằng action click, dùng webelement click là đc r
		action.click(driver.findElement(By.xpath("//a[text()='Home & Bath']"))).perform();
		//verify
		Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(), "Kids Home Bath");
		
	}

	//@Test
	public void TC_03_Fahasa() {
		driver.get("https://www.fahasa.com/");
		sleepInSecond(1);
		//hover vào menu
		action.moveToElement(driver.findElement(By.cssSelector("span.icon_menu"))).perform();
		sleepInSecond(1);
		
		driver.findElement(By.xpath("//div[@class='fhs_column_stretch']//a[text()='Kỹ Năng Sống']")).click();
		sleepInSecond(1);
		//verify
		//hàm gettext nó lấy cái text trên chính cái UI (giao diện web) mà user nhìn thấy, nên ở đây trên UI từ KY NANG SONG đc viết hoa mới đúng chứ ko phải cái Kỹ Năng Sống trong inspect html sẽ verify sai
		Assert.assertEquals(driver.findElement(By.xpath("//ol[@class='breadcrumb']/li/strong")).getText(),"KỸ NĂNG SỐNG");
		//hàm isdisplayed thì dùng text html nên kỹ năng sống lại đúng
		Assert.assertTrue(driver.findElement(By.xpath("//ol[@class='breadcrumb']/li/strong[text()='Kỹ Năng Sống']")).isDisplayed());
	}
	
	//@Test
	public void TC_04_Click_and_hold() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		//đầu tiên trên web này có 12 số, thì mình phải chọn hết 12 cái element này đã, dùng findelements
		//rồi lưu cái đống này vào 1 cái list web giống như gom đc 12 trái táo bỏ vào 1 cái túi
		//khai báo list
		List<WebElement> listNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		//bài tập yêu cầu kéo chọn từ số 1 đến số 4, thì ta làm từng bước như sau cho dễ hiểu
		//nhấp vào số 1 giữ chuột trái
		action.clickAndHold(listNumbers.get(0)) //hàm get của biến listnumber để lấy thứ tự số của web theo index, ở đây ta muốn lấy số đầu tiên thì theo index vị trí đầu tiên là số 0
		//kéo chuột đến số bài yêu cầu là số 4
		.moveToElement(listNumbers.get(3)) //tương tự như ở đây ta cần kéo đến số 4, thì theo index = số 3
		// và thả chuột ra
		.release().perform();
		//có thể viết gọn mấy dòng code trên thành 1 dòng nhưng bài này viết thế này cho dễ hiểu
		sleepInSecond(2);
		
		//khi test ta nhấp vào các số thì trong html của nó sẽ hiện thêm chữ là selected, nên để verify các số đã đc chọn thành công ta làm như sau
		List<WebElement> listNumbersSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		//verify các element đã chọn, ở đây là tổng 4 số
		Assert.assertEquals(listNumbersSelected.size(), 4); //note: note này đã từng ghi ở bài trc, nhớ là số 4 này là int khác với số 4 trong ngoặc "4" là String, và thằng size cũng là giá trị int nên nếu số 4 mình để trong ngoặc là sai giá trị và khi test sẽ fail
		
		//đây chính là verify từng số nên giá trị của nó là string
		//ít ai verify từng cái như này vì rối vãi L, tham khảo trước đc rồi
		String[] listNumberSelectedActual = {"1", "2", "3", "4"};
		//khai báo 1 array list để lưu các giá trị đc get bên trên
		ArrayList<String> listNumbersSelectedExpected = new ArrayList<String>();
		//và tạo vòng lặp để duyệt các số ở trên
		for (WebElement number : listNumbersSelected) {
			listNumbersSelectedExpected.add(number.getText());
		}
		//ép kiểu array qua list =
		Assert.assertEquals(listNumbersSelectedExpected, Arrays.asList(listNumberSelectedActual));
		
		
		
		
	}
	//@Test
	public void TC_05_Click_and_select_Random_number() {
		//chọn ngẫu nhiên từng số (chính là nhấn ctrl+click chuột)
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		List<WebElement> listNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		
		//bước dùng ctrl
		action.keyDown(Keys.CONTROL).perform();
		
		//thao tác ctrl+click chuột chọn các số theo yêu cầu 1 3 6 11
		action.click(listNumbers.get(0))
		.click(listNumbers.get(2))
		.click(listNumbers.get(5))
		.click(listNumbers.get(10)).perform();
		//nhả ctrl
		action.keyDown(Keys.CONTROL).perform();		
		
		//verify
		List<WebElement> listNumbersSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(listNumbersSelected.size(), 4);
		
	}
	
	@Test
	public void TC_06_Double_click() { 
		//case này khi thực hiện trên ff sẽ bị lỗi vì nằm ngoài viewport để hàm thao tác (chrome lại ko bị vấn đề này, nó tự động kéo xuống)
		//tức là khi test, web hiện default chỉ có khúc đầu, còn phần cần test lại nằm tuốt phía dưới
		driver.get("https://automationfc.github.io/basic-form/index.html");
		sleepInSecond(1);
		
		//nên sau khi vào tới site phải scroll tới chỗ cần test, dùng hàm như dưới + nhét đúng cái locator vào hàm
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[text()='Double click me']")));
		sleepInSecond(1);
		
		//double click
		action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		sleepInSecond(1);
		
		//verify
		Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
		
		
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
