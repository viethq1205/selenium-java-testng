package testng;

import org.testng.Assert;

public class Topic_01_Assert {

	public static void main(String[] args) {
    //Assert
		String fullname = "Automation Testing";
		//
		Assert.assertTrue(fullname.contains("Manual")); //mong đợi là true nhưng run test ra false do đối tượng tìm sai
		//
		Assert.assertTrue(fullname.contains("Automation")); //run test ko báo lỗi = true
		
		// 2 điều kiện bằng nhau, kết quả mong đợi = kết quả thực tế
		Assert.assertEquals(fullname, "Automation Testing");
	}

}
