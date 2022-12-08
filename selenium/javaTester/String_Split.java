package javaTester;

public class String_Split {

	public static void main(String[] args) {
		String basicauthenURL = "http://the-internet.herokuapp.com/basic_auth";
		
		String[] authenURLArray = basicauthenURL.split("//");
		
		basicauthenURL = authenURLArray[0] + "//" + "admin" + ":" + "admin" + "@" + authenURLArray[1];
		System.out.println(basicauthenURL);
		
		//driver.get(basicauthenURL);
		

	}

}
