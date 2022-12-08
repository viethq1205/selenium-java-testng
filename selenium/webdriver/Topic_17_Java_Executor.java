package webdriver;

import java.util.Random;
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

public class Topic_17_Java_Executor {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		jsExecutor = (JavascriptExecutor) driver; //thằng driver (webdriver) và javaexe đều kế thừa từ thằng remote webdriver (trong ggdocs có nói) nên 2 thằng này éo về 1 kiểu với nhau đc
		
	}

	//@Test
	public void TC_01_Tech_Panda() { 
//thằng javascript có 1 cái ko tối ưu chính là việc phải sleep nó để chờ load, ví dụ như cái navigate ta phải sleep nó khá lâu để nó load xong mới làm đc nếu ko sẽ bị lỗi, còn thằng seleniumjava driver.get nó sẽ chờ
//cái bất cập này cũng tương tự như các câu lệnh khác, cái này đc gọi chuyên ngành là lập trình bất đồng bộ
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(4);
		//su dung je de get cai domain cua page, verify
		Assert.assertEquals(getDomainName(), "live.techpanda.org");
		//như trên lấy URL
		Assert.assertEquals(executeForBrowser("return document.URL;"), "http://live.techpanda.org/"); //tương tự như trên mà thay đổi chút thôi
		//nhấp vào mở mục Mobile
		clickToElementByJS("//a[text()='Mobile']");
		sleepInSecond(2); //ngoài ra mình cũng có thể màu mè hơn nếu có ai cần xem test như nào, thì mình có thể dùng highlightelement để nó có khung màu cái chỗ cần bấm để ng xem dễ hiểu
		
		//nhấp vào add to card cái đt samsung
		hightlightElement("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div/button");
		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div/button");
		sleepInSecond(2);
		
		//verify đã add thành công sp = getinnettext
		Assert.assertTrue(getInnerText().contains("Samsung Galaxy was added to your shopping cart.")); 
		//Lưu ý: vì thằng getinnertext này trên console nó sẽ lấy text của cả trang web nên mình ko dùng assertequals đc (hay còn gọi là verify tuyệt đối) nên mình dùng True tương đối thôi để sử dụng đc lệnh contains
		
		//mở link customer service
		hightlightElement("//a[text()='Customer Service']");
		clickToElementByJS("//a[text()='Customer Service']");
		sleepInSecond(2);
		
		//scroll tới element Newsletter textbox ở cuối page Customer Service
		hightlightElement("//input[@id='newsletter']");
		scrollToElementOnDown("//input[@id='newsletter']"); //thằng này sẽ scroll tới đúng ngay cái cạnh dưới cùng của ô textbox newsletter vì nó là Down, còn scroll top thì dừng ngày cái cạnh trên cùng
		sleepInSecond(1);
		
		//nhập vào ô newsletter và nhấn subscribe
		sendkeyToElementByJS("//input[@id='newsletter']", "afc" + getrandomnumber() + "@gmail.net");
		hightlightElement("//button[@title='Subscribe']");
		clickToElementByJS("//button[@title='Subscribe']");
		sleepInSecond(2);
		
		//verify subscribe thành công
		Assert.assertTrue(getInnerText().contains("Thank you for your subscription."));
		
		//sau đó thì chuyển tới (navigate) sang cái domain này http://demo.guru99.com/v4/
		navigateToUrlByJS("http://demo.guru99.com/v4/");
		sleepInSecond(4);
		
		//verify đã chuyển tới http://demo.guru99.com/v4/ thành công
		Assert.assertEquals(executeForBrowser("return document.domain;"), "demo.guru99.com");
	}

	@Test
	public void TC_02_HLTM5_Validation_Message() {
		driver.get("https://warranty.rode.com/");
		String registerButton = "//button[contains(text(),'Register')]";
		//vì cái link vào web đăng ký này có tận 2 form, 1 đăng nhập 1 đăng ký nên chú ý lấy đúng cái locator ko khi test sẽ sai
		String firstnameTextbox = "//div[contains(text(),'Register')]/following-sibling::div//input[@id='firstname']";
		String surnameTextbox = "//div[contains(text(),'Register')]/following-sibling::div//input[@id='surname']";
		String emailTextbox = "//div[contains(text(),'Register')]/following-sibling::div//input[@id='email']";
		String passwordTextbox = "//div[contains(text(),'Register')]/following-sibling::div//input[@id='password']";
		String confirmpassTextbox = "//div[contains(text(),'Register')]/following-sibling::div//input[@id='password-confirm']";

		//click register mà ko nhập gì hết
		clickToElementByJS(registerButton);
		sleepInSecond(1);
		
		//verify cái tin nhắn báo lỗi ko nhập gì mà nhấn đăng ký (mấy cái này gọi là Validation Message)
		Assert.assertEquals(getElementValidationMessage(firstnameTextbox), "Please fill out this field.");
		
		//thực hiện hành động nhập firstname nhưng mấy cái còn lại ko nhập gì hết để ô surname báo lỗi và mình lại verify như trên
		sendkeyToElementByJS(firstnameTextbox, "John");
		clickToElementByJS(registerButton);
		sleepInSecond(1);
		
		//verify surname báo lỗi 
		Assert.assertEquals(getElementValidationMessage(surnameTextbox), "Please fill out this field.");
		
		//rồi lại nhập tiếp vào ô surname để ô thứ 3 là email cũng báo lỗi
		sendkeyToElementByJS(surnameTextbox, "conmemay");
		clickToElementByJS(registerButton);
		sleepInSecond(1);
		
		//verify lỗi ở ô email
		Assert.assertEquals(getElementValidationMessage(emailTextbox), "Please fill out this field.");
		
		//nhập vào email ko hợp lệ nó sẽ hiện ra 1 cái báo lỗi khác ở mục email
		sendkeyToElementByJS(emailTextbox, "helohelo@net@com.net");
		clickToElementByJS(registerButton);
		sleepInSecond(1);
		
		//verify thông báo nhập 1 email hợp lệ
		Assert.assertEquals(getElementValidationMessage(emailTextbox), "Please enter an email address.");
		
		//giờ thì nhập 1 cái email hợp lệ để ô password bị bỏ trống sẽ báo lỗi
		sendkeyToElementByJS(emailTextbox, "helohelo@gmail.com");
		clickToElementByJS(registerButton);
		sleepInSecond(1);
		
		//verify
		Assert.assertEquals(getElementValidationMessage(passwordTextbox), "Please fill out this field.");
		
		//giờ nhập vào ô password còn cái confirm bỏ trống 
		sendkeyToElementByJS(passwordTextbox, "anhlavodich");
		clickToElementByJS(registerButton);
		sleepInSecond(1);
		
		//verify báo lỗi ở ô confirm pass
		Assert.assertEquals(getElementValidationMessage(confirmpassTextbox), "Please fill out this field.");

		

	}

	@Test
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
	public String getDomainName() {
		return (String) jsExecutor.executeScript("return document.domain;");
	}

	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
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
	
	public int getrandomnumber() {
		return new Random().nextInt(9999);
	}
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
}
