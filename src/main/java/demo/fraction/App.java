package demo.fraction;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String args[]) {
		Fraction fraction1 = new Fraction(6, 7);
		Fraction fraction2 = new Fraction(0, 0);
		Fraction result;
		try {
			result = fraction1.multiply(fraction2);
			result.display();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
