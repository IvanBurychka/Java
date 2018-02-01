import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
	
//		int x = 98;
//		int y  = 0;
//		
//		System.out.println(divideLBYL(x, y));
//		System.out.println(divideEAFP(x, y));
//		System.out.println(divide(x, y));
		
//		int x = getInt();
//		System.out.println("x is "+ x);
		
//		int x = getIntLBYL();
//		System.out.println("x is " + x);
		
		int x = getInt();
		System.out.println("x is " + x);
	}
	
	private static int getInt() {
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}
	
	private static int getIntLBYL() {
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		boolean isValid = true;
		
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isDigit(input.charAt(i))) {
				isValid = false;
				break;
			}
		}
		
		if (isValid) {
			return Integer.parseInt(input);
		} else {
			return 0;
		}
		
		
	}
	
	private static int getIntEAFP() {
		try {
			Scanner sc = new Scanner(System.in);
			return sc.nextInt();
		} catch (InputMismatchException e) {
			return 0;
		}
		
	}
	
	private static int divideLBYL(int x, int y) {
		
		if (y != 0) {
			return x / y; 
		}
		return 0;
	}
	
	private static int divideEAFP(int x, int y) {
		try {
			return x / y;
		} catch (ArithmeticException e) {
//			System.out.println("Divide to zero");
//			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static int divide(int x, int y) {
		
		return x / y; 
	}
}
