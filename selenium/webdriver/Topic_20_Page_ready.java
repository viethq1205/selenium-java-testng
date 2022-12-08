package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Page_ready {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	Actions action;
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 30);
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	//ứng dụng wait để chờ 1 page ready để sử dụng (có viết note trong zalo)
	//cách này thì dùng mấy cái wait kia cũng đc, nhưng cái này có cái hay của nó, gọi 1 lần ở dưới là dùng đc khá hiệu quả ngắn gọn, dùng chung wait cho cả page
	//ví dụ như explicit thì viết nhiều hơn, mỗi cái ele đều phải wait do thằng này làm theo element
	
	//@Test
	public void TC_01_() {
		driver.get("https://opensource-demo.orangehrmlive.com");
		
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		driver.findElement(By.id("btnLogin")).click();
		
		//verify sau khi đăng nhập vào thì page hrm đã load xong chưa
		Assert.assertTrue(isPageLoadedSuccess()); //dùng true vì cái này ở dưới mình dùng boolean mà
		
		//verify mình đang ở trang dashboard (verify 1 thành phần nào đó để chứng minh là đc)
		Assert.assertEquals(driver.findElement(By.cssSelector("div#task-list-group-panel-container span")).getText(), "3 month(s)");
		
		
		//nhấn vào Menu Pim
		driver.findElement(By.cssSelector("a#menu_pim_viewPimModule")).click();
		
		//do chuyển sang trang mới nên mình cũng wait cho trang Pim này load xong như trên
		Assert.assertTrue(isPageLoadedSuccess());

		//search tên thằng nhân viên nào đó
		driver.findElement(By.id("empsearch_employee_name_empName")).sendKeys("Peter Mac Anderson");
		driver.findElement(By.id("btnSearch")).click();
		
		//thằng này tuy ko chuyển qua trang mới nhưng khi bấm search nó load lại 1 phần trang để search nên mình cũng wait (giống ajax loading)
		Assert.assertTrue(isPageLoadedSuccess());
		
		//verify thằng peter mac được search thành công (nó hiển thị tên)
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Peter Mac']")).isDisplayed());

	}

	@Test
	public void TC_02_() {
		driver.get("https://blog.testproject.io/");
	
		
		//web này sẽ thường có popup hiện lên, nếu có thì close đi còn ko thì qua bước tiếp theo luôn nên dùng if
		//note: do mình học bài cũ từ khóa 25 nên web này giờ ko còn popup nữa nên bỏ qua bước này, nhưng thôi vẫn ghi lại đề phòng
		//if (driver.findElement(By.cssSelector("div.mailch-wrap")).isDisplayed()) {
			//driver.findElement(By.cssSelector("div#close-mailch")).click();}

		//và cái web này hài hước ở chỗ nếu mình wait thì chắc chắn sẽ fail Assert.assertTrue(isPageLoadedSuccess());
		//vì dù cái page này đã load xong nhưng trong cái console nếu mình chạy mấy cái code t có ghi chú ở chỗ public boolean sẽ thấy chạy ra kq toàn false => sau 30s wait sẽ fail case 
		//chỉ khi nào mình thao tác vào 1 cái chức năng nào đó thì nó mới chuyển sang true
		
		//nên giờ mình phải có cái thao tác gì đó-page sẽ load thêm 1 đống thứ nữa (xem trong phần network của console mới thấy đc)
		//chỗ network này nhìn giống lúc test API có get, post, lúc mới load lên có 50 request thôi và hover vào trang mới nhảy lên hơn trăm request, lúc này trang mới load đủ ko bị false nữa
		action.moveToElement(driver.findElement(By.cssSelector("section#search-2 input.search-field"))).perform();
		//rồi wait xác nhận page cuối cùng đã oke rồi
		Assert.assertTrue(isPageLoadedSuccess());
		
		//nhập nội dung vào ô search
		driver.findElement(By.cssSelector("section#search-2 input.search-field")).sendKeys("Selenium");
		
		//click search
		driver.findElement(By.cssSelector("section#search-2 span.glass")).click();
		
		Assert.assertTrue(driver.findElement(By.cssSelector("h2.page-title>span")).isDisplayed()); //chỗ này kết hợp với implicitwait hoặc explicit cũng đc (visible)
		
		Assert.assertTrue(isPageLoadedSuccess());
		
		//ra được 1 đống post về selenium (8 title) thì mình verify hết tụi nó theo yêu cầu bài tập
		List<WebElement> posttitle = driver.findElements(By.cssSelector("h3.post-title>a"));
		for (WebElement title : posttitle) {
			System.out.println(title.getText()); //in ra xem tên các post là gì
			//verify
			Assert.assertTrue(title.getText().contains("Selenium"));
		}
		
		//trang này thật sự khá nhiều vấn đề rắc rối dù là 1 case thực tế khá hay
	}

	//@Test
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
	
    public boolean isPageLoadedSuccess() {
    explicitWait = new WebDriverWait(driver, 30);
    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
    @Override
    public Boolean apply(WebDriver driver) {
            return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);"); //cái jquery này trong mục console, code này chạy trên đó thấy đc load xong thì ra kq true còn chưa là false
    }
    };

    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
    @Override
    public Boolean apply(WebDriver driver) {
            return jsExecutor.executeScript("return document.readyState").toString().equals("complete"); //thằng document (DOM) này cũng giống trên kia nhưng thay vì true/false là complete
            }
    };
    return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
}
}
