package testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_03_Priority { //cho test case chạy theo đúng trình tự chứ ko chạy theo bảng chữ cái 

	@Test (description = "Jira_0787 - Create new employee and verify the employee")
	public void TC_01_EndUser_Create_New_Emp() {
		
	}

	@Test 
	public void TC_02_EndUser_View_Emp() {
		
	}

	@Test (enabled = false)
	public void TC_03_EndUser_Edit_Emp() {
		
	}
	
	@Test 
	public void TC_04_EndUser_Move_Emp() {
		
	}
	
	//nhưng cái cách đặt (priority = ?) như này dài dòng và cũng ko ai dùng, trong cái tên đặt theo số thứ tự 01, 02... là nhanh nhất
	//(enabled = false) TC nào set cái false sẽ đc skip, true thì chạy
	//ngoài ra còn có chức năng description thêm ghi chú vào, ví dụ TC01 đọc vô sẽ biết nằm ở Jira mã nào ở feature nào, nếu case fail ta sẽ dễ dàng tạo ra 1 cái ticket trên jira dựa theo mã, ko phải tốn time tìm
}
