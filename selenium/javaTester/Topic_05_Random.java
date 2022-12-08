package javaTester;

import java.util.Random;

public class Topic_05_Random {

	public static void main(String[] args) {
		//đây là cách tạo ra các dãy số random để mình làm email test
		//đây là ultilities - thư viện tiện ích của Java, ta đang dùng thư viện này để tạo class random, dựa vào biến random để tạo ra random
		//rand đại diện cho Random
		Random rand = new Random(); 
		//ta dùng nextInt vì nó ra các số nguyên chứ ko ra số thập phân hoặc kiểu số như 10.5 11.2
		System.out.println("automation" + rand.nextInt(9999) + "gmail.com");  
		//(9999) là để random 4 số từ 9999 đổ lại, ta cũng có thể để 5 số hoặc 3 số 999 nếu muốn
		//note: ko nên để random nextInt() như này mà nên thêm phạm vi như (9999) như trên vì nếu ko sẽ random ra cả số âm

	}

}
