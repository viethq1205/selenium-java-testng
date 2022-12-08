package listener;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import testng.Topic_10_Listener;

public class ExtentReportListener implements ITestListener {

	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		 TakesScreenshot  t = (TakesScreenshot) Topic_10_Listener.driver;
		  
	     File srcFile = t.getScreenshotAs(OutputType.FILE);
	  
	     try {
	     File destFile = new File ("./ScreenShot/"+result.getName()+".jpg");  //ngoài ra chỗ này sẽ còn phải chỉnh để làm sao lúc nó lưu cái hình vào screenshot thì có tên khác nhau chứ ko nó lưu đúng mỗi 1 tên
	     																	// nhưng sẽ học vào 1 dịp khác
	     FileUtils.copyFile(srcFile, destFile);
	     Reporter.log("<a href='"+ destFile.getAbsolutePath() + "'> <img src='"+ destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");

	    } 
	    catch (IOException e) {
	    e.printStackTrace();
	   }
		
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}  //đây là bài listener trong testng

}
