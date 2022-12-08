package testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class Topic_09_Depend {
  @Test()
  public void TC_01_Create_Product() {
	  Assert.assertTrue(false);
  }
  
  @Test(dependsOnMethods = "TC_01_Create_Product")
  public void TC_02_Read_Product() {
	
  }
  
  @Test(dependsOnMethods = "TC_02_Read_Product")
  public void TC_03_Update_Product() {
	
  }
  
  @Test(dependsOnMethods = "TC_03_Update_Product")
  public void TC_04_Delete_Product() {
	
  }
  
  //thằng sau depend vào thằng trước nên thằng trc mà lỗi thằng sau cũng tịt luôn
  //ví dụ như chỉnh thằng TC1 phải false đi, thì do thằng TC1 fail nên 3 thằng kia dù đúng nhưng vì depend vào nhau nên khi chạy test ra kq TC01 Failure còn 3 thằng kia Skip ko chạy
  //nếu chỉnh tc1 true còn tc2 false thì 2 thằng sau sẽ bị skip, còn tc2 failure, tc1 pass

}
