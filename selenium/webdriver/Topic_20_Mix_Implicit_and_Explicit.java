package webdriver;

import java.util.Date;
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

public class Topic_20_Mix_Implicit_and_Explicit {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	WebDriverWait explicitWait;
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
	}
	
	//Khi làm nhớ ứng dụng tốt implecit và explicit hợp lý

	//@Test
	public void TC_01_Element_Found() {
		//element có xuất hiện tìm đc ngay ko cần chờ hết timeout
		//dù set cả 2 loại timeout đều ko bị ảnh hưởng
		//case này mục đích để chỉ found element thì set mấy cái wait này ko làm ảnh hưởng gì hết, tìm thấy là xong dừng để qua step khác
		
		explicitWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://www.facebook.com/");
		
		//này là explicit
		//cái gettimestamp để thấy đc mốc thời gian chạy của explicit
		System.out.println("Thời gian bắt đầu của explicit:" + getTimeStamp());
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email")));
		System.out.println("Thời gian kết thúc của explicit:" + getTimeStamp());
		
		//này là implicit
		System.out.println("Thời gian bắt đầu của implicit:" + getTimeStamp());
		driver.findElement(By.cssSelector("input#email"));
		System.out.println("Thời gian kết thúc của implicit:" + getTimeStamp());
	}

	//@Test
	public void TC_02_Element_not_found() {
		//chỉ dùng implicit
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://www.facebook.com/");
		
		//ko tìm thấy element nên nó sẽ tìm đi tìm lại hết 10s của implicitwait đã set (cơ chế 0.5s/1 lần tìm)
		//ra kq là báo lỗi nosuchelementexception của element selenium sau hơn 10s tìm kiếm
		//vì cái lỗi này nên mình ko in ra đc để xem cái thời gian chạy đúng 10s hay ko, nên thầy chỉ ta chuột phải vào dòng driver.find, chọn surround rồi chọn try/catch như dưới sẽ in đc
		//lưu ý: khi dùng try catch thì lệnh này nó bắt đc cái exception và nhảy qua step khác nên case sẽ pass chứ ko bị fail
		System.out.println("Thời gian bắt đầu của implicit:" + getTimeStamp());
		try {
			driver.findElement(By.cssSelector("input#selenium"));
		} catch (Exception e) {
			System.out.println("Thời gian kết thúc của implicit:" + getTimeStamp());
		}
		//xem console 2 kết quả in ra cho thấy case chạy đúng 10s để tìm element
		
		
	}

	//@Test
	public void TC_03_Element_not_found_Mix() {
		explicitWait = new WebDriverWait(driver, 5); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.get("https://www.facebook.com/");
		
		//này là explicit
		System.out.println("Thời gian bắt đầu của explicit:" + getTimeStamp());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#selenium")));
		} catch (Exception e1) {
			System.out.println("Thời gian kết thúc của explicit:" + getTimeStamp());
		}
		
		//này là implicit
		System.out.println("Thời gian bắt đầu của implicit:" + getTimeStamp());
		try {
			driver.findElement(By.cssSelector("input#selenium"));
		} catch (Exception e) {
			System.out.println("Thời gian kết thúc của implicit:" + getTimeStamp());
		}
		
		// NOTE nhắc lại: Khi set thằng timeouts.implicitwait dòng code chỉ có thằng driver.findElement(... chỉ chịu ảnh hưởng bởi cái implicit nên explicit có set thành 100s cũng chắng có vấn đề gì
		//nhưng thằng implicit lại có ảnh hưởng đến đoạn code của explicitwait.until(... và fluentwait vì trong 2 thằng này có tìm element/elements (trong cái này là 1 vd visibilityOfElementLocated)
		//vì thằng visibilityOfElementLocated xem trong open decleration, trong code của nó có driver.find chịu ảnh hưởng của implicit và elementIfVisible chịu ảnh hưởng của explicit nên 2 thằng
		//này khi mix implicit/explicit phải chạy dạng async (bất đồng bộ) dẫn đến kết quả có thể 5s hoặc 6s gì đó, chênh lệch tầm nửa s đến 1s
	
		//ngoài ra còn có thêm trường hơp như set thời gian impli>expli và impli<expli
		//tất nhiên là do set mix chạy async nên nó sẽ bị chênh thêm 1 chút s, ví dụ như set impli 5s, expli 8s thì chạy hết sẽ ra kq trong 9s ~ 11s gì đó (chênh lệch loanh quanh 2s đổ lại)
		//nhưng impli 8s, expli 5s thì ko có chênh lệch gì vì khi set mix thằng implicit luôn chạy trước do trong code luôn find element đầu tiên rồi explicit theo ngay sau -> test chạy trong 8s thôi 
	
	}
	
	//@Test
	public void TC_04_Element_not_found_Only_Explicit_By_Locator() { //by của mấy cái visi, invisi, clickable
		
//như đã nói ở trên trong mấy cái code của visi nó có sẵn driver.find nên sẽ chịu ảnh hưởng của implicit nhưng ở đây ta ko set nên implicit = 0, thời gian chạy case trong test này sẽ = explicit (vẫn có thể có chênh lệch nhẹ)
		//khá giống case impli < expli (ở đây là 0 < 5)
		//phải giải thích như trên vì dù ko có implicit nhưng khi ko found đc ele nó vẫn sẽ có thể throw ra exception của implicit, còn explicit là timeoutexception
		explicitWait = new WebDriverWait(driver, 5); 
		
		driver.get("https://www.facebook.com/");
		
		//này là explicit
		System.out.println("Thời gian bắt đầu của explicit:" + getTimeStamp());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#selenium")));
		} catch (Exception e1) {
			System.out.println("Thời gian kết thúc của explicit:" + getTimeStamp());
		}
		
	}
	
	//@Test
	public void TC_05_Element_not_found_Only_Explicit_and_Element() { // TC này thằng visi ko có by (khác tham số với case trên) nên phải thêm driver
		explicitWait = new WebDriverWait(driver, 5); 
		
		driver.get("https://www.facebook.com/");
		
		//này là explicit
		System.out.println("Thời gian bắt đầu của explicit:" + getTimeStamp());
		try {
		explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input#selenium"))));
		//ở đây nó khác với cái visi bth do nó chạy tận 2 bước (đầu tiên là cái driver.find r mới đến visibilityOf)
		//và trong case này ngay bước driver find đã fail (sele ko tồn tại) nên cái visiof 100% ko đc chạy
		} catch (Exception e1) {
			System.out.println("Thời gian kết thúc của explicit:" + getTimeStamp());
		}
		
	}
	
	@Test
	public void TC_06_Real_test_case() { 
		
		explicitWait = new WebDriverWait(driver, 5); 
		
		driver.get("https://www.facebook.com/");
		
		//wait cho đến khi cái email visible
		//explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input#selenium"))));
		
		//action sendkeys
		driver.findElement(By.cssSelector("input#email")).sendKeys("");
		
		//khi chạy dự án thực ngoài việc set implicitwait ở trên beforeclass ta cũng nên kết hợp với explicit
		//đề phòng ví dụ như ta quên cái explicit chờ element visible như ở trên thì vẫn còn cái implicit để wait
		
	}
		//show timestamp thời điểm gọi hàm
		public String getTimeStamp() {
			Date date = new Date();
			return date.toString();
		}
		
		@AfterClass
		public void afterClass() {
			//driver.quit();
		}
}
