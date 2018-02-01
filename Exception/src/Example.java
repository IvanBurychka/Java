import java.util.InputMismatchException;
import java.util.Scanner;

public class Example {
	
	public static void main(String[] args) {
		
		try {
			int result = divide();
			System.out.println(result);
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
		} 
		
	}
	
	private static int divide() throws ArithmeticException{
		int x, y;
		try {
			x = getInt();
			y = getInt();
			return x / y;
		} catch (InputMismatchException e) {
			throw new ArithmeticException("my 1 exception"); 
		} catch (ArithmeticException e) {
			throw new ArithmeticException("my 2 exception"); 
		} 
		
	} 
	
	private static int getInt() {
		
		System.out.println("Please enter an integer");
		
		while(true) {
			try (Scanner sc = new Scanner(System.in)){
				return sc.nextInt();
			} catch (InputMismatchException e) {
				Scanner sc = new Scanner(System.in);
				sc.nextLine();
				System.out.println("Input only number from 0 to 9");
				sc.close();
			}
			
		}
		
	}
}
