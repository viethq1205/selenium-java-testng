package javaTester;

public class Topic_04_And_Or {

	public static void main(String[] args) {
		boolean statusA;
		boolean statusB;
		// And (trong lập trình and ghi bằng &&)
		// Nếu 1 trong 2 điều kiện sai (ví dụ ghi sai chính tả điều kiện 1) => thì kết quả ra sai
		// Nếu 1 trong 2 điều kiện đúng => thì kết quả cũng ra sai		
		// Chỉ có cả 2 đúng mới ra đúng
		statusA = true;
		statusB = false;
		System.out.println("Kết quả = " + (statusA && statusB));

		statusA = true;
		statusB = true;
		System.out.println("Kết quả = " + (statusA && statusB));
		
		// Or (trong lập trình or ghi bằng ||)
		// Nếu 1 trong 2 điều kiện sai => thì kết quả ra đúng (ví dụ ghi sai chính tả đk2 nhưng đk1 ghi đúng thì vẫn cho ra kết quả dựa theo điều kiện 1)
		// Nếu 1 trong 2 điều kiện đúng => thì kết quả cũng ra đúng		
		// Chỉ có cả 2 sai mới ra sai
		statusA = true;
		statusB = false;
		System.out.println("Kết quả = " + (statusA || statusB));

		statusA = false;
		statusB = false;
		System.out.println("Kết quả = " + (statusA || statusB));		

	}

}
