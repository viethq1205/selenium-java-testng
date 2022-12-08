package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_Dropdown {
	//đây là khai báo còn như cái String project dưới là vừa khai báo vừa khởi tạo biến, do nó lấy dữ liệu ra và gán vào String projectPath
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	WebDriverWait explicitWait;
	Select select;
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		//cách khởi tạo javaexecutor //note thằng này ko new vì new là dành cho class, còn nó là interface, nên để chuột vào chữ driver sẽ hiện ra add to cast để add thằng js vào
		jsExecutor = (JavascriptExecutor) driver;
		//khởi tạo wait
		explicitWait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}
	//đây là 1 bài khá nà lâng cao
	//custom dropdown khác default như nào, nó đc build = các thư viện bên ngoài trên nền tảng javascript
	//default là thư viện select của sele để handle, custom thì thư viện sele ko sp nên mình phải tự viết hàm để xử lý

	//@Test
	public void TC_01_Vi_du_custom_dropdown_site_Jquery() {
		//1-click vào 1 phần tử để dropdown xổ ra
		driver.findElement(By.cssSelector("span#number-button")).click();
		//2-chờ các item trong dropdown đc load ra để lấy đc cái mình cần 
		//note: ko nên sleep cứng (sleepinsecond) vì nó ko đc linh động, nên dùng 1 hàm wait nào đó vì nếu chưa có đối tượng nó sẽ tìm lại hoặc có rồi sẽ đi tiếp bước tiếp theo luôn, ko có chờ nốt time đã set
		//dùng webdriverwait (thằng này cũng phải khai báo)
		//nhớ các bước khai báo + khởi tạo trên kia nếu ko sẽ bị null
		//presenceOfAllElementsLocatedBy thằng này dùng oke hơn thằng visibilityOfAllElementsLocatedBy 
		//vì presence chỉ cần các item có trong cây html là đc ko phụ thuộc vào giao diện đang có gì 
		//còn thằng visibility nó phụ thuộc vào giao diện dropdown hiện ra những item gì thì mới wait 
		//vi du: trên UI có tận 100 item dropdown nhưng lúc click vào cái dropdown thì khung hiện ra chỉ có từ 1->9, những item còn lại phải chờ kéo xuống (load) nên ko xài đc, ko hiệu quả = presence
		//presence hiện tất cả các item có trong cây html nên nó cần bắt 1 cái locator hiển thị tất cả các item
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#number-menu div")));
		//ở đây ta chọn thẻ ul là chuẩn chỉ rồi vì chọn đúng box dropdown 
		//chọn tiếp thẻ li là bao trọn 19 cái item, nhưng ta chọn luôn thẻ con của li là div vì khi find ele sẽ đỡ gây confuse hơn thằng li
		//3.1 là hành vi người dùng nếu cái mình cần chọn đã có sẵn (đã hiển thị) thì chọn luôn là qua bước 4
		//nếu nó ko có và đang chờ load thì sang bước 3.2 vì đôi khi có 1 số dropdown chứa rất nhiều dữ liệu
		//4-kiểm tra text của item - đúng cái mình cần là click vào chọn
		//viết code duyệt item - kiểm tra theo từng điều kiện cụ thể - gettext đúng cái mình cần thì click vào
		//lưu trữ các item thì mới duyệt qua đc
		//findele là listele nên mình khai báo là listwebelement
		List<WebElement> allItem = driver.findElements(By.cssSelector("ul#number-menu div"));//cái list<webelement> này giống như là 1 cái túi chứa hết item, muốn dùng hàm for each thì ta phải gán 1 cái biến tạm để duyệt
		
		//duyệt qua từng item ta dùng vòng lặp foreach
		//ở đây ta dùng biến item cho cái vòng nặp for
		for (WebElement item : allItem) {
			//dùng biến item để thao tác trong vòng lặp foreach
			//lấy ra text
			String textItem = item.getText(); //nhớ ghi nhớ (hay quên vl) vì gettext là string nên khi ta muốn khai báo cũng phải kb kiểu string
			//kiểm tra nếu nó = với text mình mong muốn thì click vào
			if (textItem.equals("7")) {//condition ở đây nó nhận vào 1 cái boolean (T/F), đúng thì mới xài đc
				//vậy nếu đúng 7 thì click vào
				item.click(); //item mới là cái để thao tác, cái textitem chỉ là cái khai báo gettext	
			}			
		}
		sleepInSecond(3);
		///tất cả các bước trên chỉ để chọn 1 thằng số 7 

		//thử chọn 1 số khác
		driver.findElement(By.cssSelector("span#number-button")).click();
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#number-menu div")));
		allItem = driver.findElements(By.cssSelector("ul#number-menu div"));
		for (WebElement item : allItem) {
			String textItem = item.getText();
			if (textItem.equals("5")) {
				item.click();
			}
		}
	}

	//@Test
	public void TC_02_Vi_du_custom_dropdown_site_Jquery() {//đây là test case 1 user thao tác đc với cái cứt tôm dropdown
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		//sau các bước dài đằng đẵng trên kia ông thầy cho thêm 1 cách này đc thiết lập sau chỗ afterclass
		//1 hàm có thể gọi 1 hàm khác để dùng chung cùng 1 class
		//cái nào có đóng mở ngoặc () là hàm, ko có mà chỉ có {} là class, ở đây hàm là TC_01 () đó
		selectItemInCustomDropdown("span#number-button", "ul#number-menu div", "7"); // xong thì cho sleep
		sleepInSecond(2);
		//rồi từ giờ chọn mấy số khác trong drop down cũng tương tự, copy lại r đổi số
		//verify nó cũng đơn giản dùng asertequals gettext
		
		//trên là chọn number thì giờ ta thử chọn dropdown speed cùng site ví dụ này
		selectItemInCustomDropdown("span#speed-button", "ul#speed-menu div", "Fast"); // xong thì cho sleep
		sleepInSecond(2);
		
		//chọn dropdown title cũng y chang
		selectItemInCustomDropdown("span#salutation-button", "ul#salutation-menu div", "Mr."); // xong thì cho sleep
		sleepInSecond(2);
		
	}

	//@Test
	//TC này ban đầu viết đúng nhưng sẽ bị lỗi khi chạy test, màn hình window bị thu nhỏ dẫn đến 1 số ele bị che ko thao tác đc
	//cách khắc phục như sau: ta tạo hàm scrolltoelement để nó tự kéo xuống element đang tìm, nhớ khai báo và khởi tạo jsexecutor
	public void TC_03_web_honda() {
		driver.get("https://www.honda.com.vn/o-to/du-toan-chi-phi");
		//trang này chọn mẫu xe là cứt tôm còn chọn vị trí thì trong inspec hiên là thẻ option = default dorpdown
		//thằng scroll này nên chọn thằng element nào vị trí đẹp để khi kéo xuống nếu đc sẽ hiện ra hết mấy cái dropdown mà ko bị che
		//còn ko thì sẽ phải dùng scroll nhiều lần để chọn mà ko bị che
		//ở đây cũng tùy máy, như máy vịt đẹp trai thì 1 lần là đc nhưng ông thầy phải scroll 2 lần, thôi theo ông thầy cho đỡ sai xót
		scrollToElement("div.carousel-item");
		sleepInSecond(2);
		//như ở cái scroll này thì sẽ chọn đc dropdown xe nhưng 2 cái vị trí, khu vực bị che nên làm xong ta xuống kia thêm 1 cái scroll nữa
		//chọn xe
		selectItemInCustomDropdown("button#selectize-input", "div.dropdown-menu>a", "CITY L (Đỏ)"); // xong thì cho sleep
		sleepInSecond(2);
		//scroll này để hiện ra đc dropdown vị trí, khu vực
		scrollToElement("div.container");
		sleepInSecond(2);
		//chọn vị trí, khu vực //do đây là default dropdown nên mình dùng new select của thư viện selenium
		Select select = new Select(driver.findElement(By.cssSelector("select#province")));
		select.selectByVisibleText("TP. Hồ Chí Minh");
		//thằng này là df dropdown nên mình dùng getselected
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "TP. Hồ Chí Minh");
		//chọn khu vực hcm, hn 1, các chỗ khác 2
		new Select(driver.findElement(By.cssSelector("select#registration_fee"))).selectByVisibleText("Khu vực I");
		
		//đây là testcase có thể xảy ra lỗi ở các màn hình nhỏ, còn màn hình lớn thêm dòng code này ở beforeclass là ổn áp ngay driver.manage().window().maximize();
		
	}
	//@Test
	public void TC_04_Web_react() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemInCustomDropdown("div.dropdown", "div.menu span", "Jenny Hess");
		sleepInSecond(2);
		//sau khi chọn con Jenny này (nó hiện lên đầu vì đã đc chọn trong dropdown) thì ta sẽ verify theo inspect của nó
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Jenny Hess");
		//mấy thằng khác trong web này cũng tương tự
		
	}
	//@Test
	public void TC_05_Web_Vuejs() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInCustomDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "Second Option");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Second Option");
		//mấy thằng khác trong web này cũng tương tự	
	}
	
	//@Test
	//cách này chỉ có chọn chứ ko edit viết chữ gì cả
	//để ý thằng này đc viết = react nên có thể copy từ tc04 vì cùng thư viện react
	public void TC_06_Web_Dropdown_Editable_1() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		selectItemInCustomDropdown("div.dropdown", "div.menu span", "Albania");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Albania");
		//mấy thằng khác trong web này cũng tương tự	
	}
	
	@Test
	//đây là cách có thể nhập, tức là nếu chọn sẽ phải kéo tìm item mình cần, đây chỉ cần viết chính xác hoặc vài chữ sẽ filter ra kết quả đang cần
		public void TC_06_Web_Dropdown_Editable_2() {
			driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
			//vì nó có bước nhập để filter ra cái mình cần nên phần locator sẽ khác
			editItemInCustomDropdown("input.search", "div.menu span", "Albania");
			sleepInSecond(2);
			Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Albania");
			//mấy thằng khác trong web này cũng tương tự	
		}
	
	
	//@Test
	//này là cách tìm thủ công từng bước nè, ko nhanh như cái trên hihi
	public void TC_06_Web_Dropdown_Editable_Manual() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		driver.findElement(By.cssSelector("input.search")).sendKeys("Alba");
		sleepInSecond(2);
		driver.findElement(By.xpath("//span[text()='Albania']")).click();
	
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
	public void scrollToElement(String cssLocator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector(cssLocator)));
	}
	//đây là mẫu ko dùng cho 1 cái dropdown cụ thể nào, thích copy paste lấy làm mẫu
	//mấy cái locator kia mốt fix lại thành dự án khác là đc
	public void selectItemInCustomDropdown(String parentLocator, String childLocator, String textExpectedItem) {
		//giải thích đơn giản lại các bước như sau và tổng hợp đc cách ngắn gọn như TC 02 
		//click vào thẻ dropdown để nó xổ tất cả item ra hay còn gọi là parentLocator
		driver.findElement(By.cssSelector(parentLocator)).click();
		//wait để các item xuất hiện đầy đủ trong cây HTML/DOM
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator))); 
		//tìm lưu lại tất cả item vào list để chuẩn bị cho step tiếp theo
		List<WebElement> allItem = driver.findElements(By.cssSelector(childLocator)); 
		//duyệt qua từng phần tử (element) trong cái list trên
		for (WebElement item : allItem) {
			//lấy text ra
			String textActualItem = item.getText();
			//và so sánh kết quả thực tế với kết quả mình mong muốn
			if (textActualItem.equals(textExpectedItem)) {
				//và click vào xem chạy có chuẩn ko
				item.click();
//khi đã tìm thấy kq và thỏa mãn đk thì ko cần tìm chạy hết danh sách để duyệt tiếp nữa, tốc độ load sẽ nhanh hơn
//và break cũng có tác dụng khi các trang sử dụng ngôn ngữ khác nhau, có trang khi đã chọn item rồi thì các item khác biến mất (trong inspect), và nếu ko break nó code sẽ duyệt tiếp và biến thành lỗi
				break;
			}
		}
	}		
		
		//đây là mẫu với dropdown có thể edit
		public void editItemInCustomDropdown(String parentLocator, String childLocator, String textExpectedItem) {
			driver.findElement(By.cssSelector(parentLocator)).clear();
			driver.findElement(By.cssSelector(parentLocator)).sendKeys(textExpectedItem);
			sleepInSecond(1);
			explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator))); 
			List<WebElement> allItem = driver.findElements(By.cssSelector(childLocator)); 
			for (WebElement item : allItem) {
				String textActualItem = item.getText();
				if (textActualItem.equals(textExpectedItem)) {
					item.click();
					break;
				}
			}
	}
}
