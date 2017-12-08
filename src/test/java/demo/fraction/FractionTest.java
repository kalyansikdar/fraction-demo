package demo.fraction;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import demo.fraction.Fraction;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class FractionTest {

	private static final String ARITHMETIC_EXCEPTION = "Denominator cannot be Zero";
	private static final String INTEGER_OVERFLOW_EXCEPTION = "Integer value overflow";
	private static final String NO_EXCEPTION = "";

	private Fraction fraction1;
	private Fraction fraction2;

	@SuppressWarnings("unused")
	private static final Object[] parametersForFractionTest() {
		return $(
				// Parameters are: (1,2,3,4,5,6,7,8)
				// 1=firstNumerator, 2=firstDenominator, 3=secondNumerator,
				// 4=secondDenominator, 5=resultDenominator, 6=resultNumerator,
				// 7 = operationBeingPerformed, 8 = expectedException

				// Addition Test Cases
				// Test case 0: Add two fractions of form n1/d1, n2/d2
				$(6, 7, 4, 9, 82, 63, Operations.ADD, NO_EXCEPTION),

				// Test Case 1: Add two fraction of form n1/d1, 0/0
				$(6, 7, 0, 0, 6, 7, Operations.ADD, ARITHMETIC_EXCEPTION),

				// Test Case 2: Add two fraction of form n1/d1, 0/d2
				$(6, 7, 0, 9, 6, 7, Operations.ADD, ARITHMETIC_EXCEPTION),

				// Test Case 3: Add two fractions of form n1/d1, -n2/d2
				$(6, 7, -4, 9, 26, 63, Operations.ADD, NO_EXCEPTION),

				// Test Case 4: Add two fractions of form n1/d1, n2/-d2
				$(6, 7, 4, -9, 26, 63, Operations.ADD, NO_EXCEPTION),

				// Test Case 5: Add two fractions of form n1/d1, -n2/-d2
				$(6, 7, -4, -9, 82, 63, Operations.ADD, NO_EXCEPTION),
				
				// Test Case 6: Add two fractions of form n1/d1(greater than integer max value), -n2/-d2
				$(Integer.MAX_VALUE, 1, 2, 1, 0, 0, Operations.ADD, INTEGER_OVERFLOW_EXCEPTION),

				// Subtraction Test Cases
				// Test case 7: Subtract two fractions of form n1/d1, n2/d2
				$(6, 7, 4, 9, 26, 63, Operations.SUBTRACT, NO_EXCEPTION),

				// Test Case 8: Subtract two fraction of form 0/0, n2/d2
				$(0, 0, 4, 9, -4, 9, Operations.SUBTRACT, NO_EXCEPTION),

				// Test Case 9: Subtract two fraction of form 0/d1, n2/d2
				$(0, 7, 4, 9, -4, 9, Operations.SUBTRACT, NO_EXCEPTION),

				// Test Case 10: Subtract two fractions of form -n1/d1, n2/d2
				$(-6, 7, 4, 9, -82, 63, Operations.SUBTRACT, NO_EXCEPTION),

				// Test Case 11: Subtract two fractions of form n1/-d1, n2/d2
				$(6, -7, 4, 9, -82, 63, Operations.SUBTRACT, NO_EXCEPTION),

				// Multiplication Test Cases
				// Test case 12: Multiply two fractions of form n1/d1, n2/d2
				$(6, 7, 4, 9, 8, 21, Operations.MULTIPLY, NO_EXCEPTION),

				// Test Case 13: Multiply two fraction of form n1/d1, 0/0
				$(6, 7, 0, 0, 0, 0, Operations.MULTIPLY, NO_EXCEPTION),

				// Test Case 14: Multiply two fraction of form n1/d1, 0/d2
				$(6, 7, 0, 9, 0, 0, Operations.MULTIPLY, NO_EXCEPTION),

				// Test Case 15: Multiply two fractions of form n1/d1, -n2/d2
				$(6, 7, -4, 9, -8, 21, Operations.MULTIPLY, NO_EXCEPTION),

				// Test Case 16: Multiply two fractions of form n1/d1, n2/-d2
				$(6, 7, 4, -9, -8, 21, Operations.MULTIPLY, NO_EXCEPTION),

				// Test Case 17: Multiply two fractions of form n1/d1, -n2/-d2
				$(6, 7, -4, -9, 8, 21, Operations.MULTIPLY, NO_EXCEPTION),
				
				// Test Case 18: Multiply two fractions of form n1/d1, n2/-d2(greater than integer max value)
				$(6, 7, Integer.MAX_VALUE, -9, 0, 0, Operations.MULTIPLY, INTEGER_OVERFLOW_EXCEPTION),

				// Division Test Cases
				// Test case 19: Divide two fractions of form n1/d1, n2/d2
				$(6, 7, 4, 9, 27, 14, Operations.DIVIDE, NO_EXCEPTION),

				// Test Case 20: Divide two fraction of form n1/d1, 0/0
				$(6, 7, 0, 0, 0, 0, Operations.DIVIDE, ARITHMETIC_EXCEPTION),

				// Test Case 21: Divide two fraction of form n1/d1, 0/d2
				$(6, 7, 0, 9, 0, 0, Operations.DIVIDE, ARITHMETIC_EXCEPTION),

				// Test Case 22: Divide two fractions of form n1/d1, -n2/d2
				$(6, 7, -4, 9, -27, 14, Operations.DIVIDE, NO_EXCEPTION),

				// Test Case 23: Divide two fractions of form n1/d1, n2/-d2
				$(6, 7, 4, -9, -27, 14, Operations.DIVIDE, NO_EXCEPTION),

				// Test Case 24: Divide two fractions of form n1/d1, -n2/-d2
				$(6, 7, -4, -9, 27, 14, Operations.DIVIDE, NO_EXCEPTION));
	}

	@SuppressWarnings("unused")
	private static final Object[] parametersForFractionTest2() {
		return $(
				// Parameters are: (1,2,3,4,5,6)
				// 1=firstNumerator, 2=firstDenominator, 3=secondNumerator,
				// 4=secondDenominator, 5=operation result, 6=operation type

				// Less Than relationship Test Cases
				// Test Case 0: Check less than comparison between two fractions
				$(1, 3, 2, 3, true, Operations.LESSTHAN),

				// Test Case 1: Check less than comparison between two fractions
				$(2, 3, 1, 3, false, Operations.LESSTHAN),

				// Greater Than relationship Test Cases
				// Test Case 2: Check greater than comparison between two
				// fractions
				$(1, 3, 2, 3, true, Operations.GREATERTHAN),
				// Test Case 3: Check greater than comparison between two
				// fractions
				$(2, 3, 1, 3, false, Operations.GREATERTHAN),

				// Equal relationship Test Cases
				// Test Case 4: Check equality between two fractions
				$(1, 3, 1, 3, true, Operations.EQUAL),

				// Test Case 5: Check equality comparison between two fractions
				$(2, 3, 1, 3, false, Operations.EQUAL),

				// Not Equal relationship test cases
				// Test Case 6: Check non- equality comparison between two
				// fractions
				$(1, 3, 2, 3, true, Operations.NOTEQUAL),

				// Test Case 7: Check non- equality comparison between two
				// fractions
				$(1, 3, 1, 3, false, Operations.NOTEQUAL));
		// How to show null pointer exception
	}

	@Before
	public void setUp() throws Exception {
		fraction1 = new Fraction(0, 0);
		fraction2 = new Fraction(0, 0);
	}

	@Test
	@Parameters(method = "parametersForFractionTest")
	public void test(int numerator1, int denominator1, int numerator2, int denominator2, int expectedNumerator,
			int expectedDenominator, Operations op, String exceptionMsg) {
		// Fraction 1
		Fraction result = null;

		fraction1.setNumerator(numerator1);
		fraction1.setDenominator(denominator1);
		// Fraction 2
		fraction2.setNumerator(numerator2);
		fraction2.setDenominator(denominator2);

		try {
			switch (op) {
			case ADD:
				result = fraction1.add(fraction2);
				break;
			case SUBTRACT:
				result = fraction1.subtract(fraction2);
				break;
			case MULTIPLY:
				result = fraction1.multiply(fraction2);
				break;
			case DIVIDE:
				result = fraction1.divide(fraction2);
				break;
			default:
				System.err.println("Invalid Option");
			}
			assertEquals(expectedNumerator, result.getNumerator());
			assertEquals(expectedDenominator, result.getDenominator());
		} catch (Exception ex) {
			assertEquals(exceptionMsg, ex.getMessage());
		}
	}

	@Test
	@Parameters(method = "parametersForFractionTest2")
	public void test2(int numerator1, int denominator1, int numerator2, int denominator2, boolean expectedResult,
			Operations op) {
		boolean result = false;

		// Fraction 1
		fraction1.setNumerator(numerator1);
		fraction1.setDenominator(denominator1);

		// Fraction 2
		fraction2.setNumerator(numerator2);
		fraction2.setDenominator(denominator2);

		switch (op) {
		case GREATERTHAN:
			result = fraction1.greaterThan(fraction2);
			break;
		case LESSTHAN:
			result = fraction1.lessThan(fraction2);
			break;
		case EQUAL:
			result = fraction1.equals(fraction2);
			break;
		case NOTEQUAL:
			result = !fraction1.equals(fraction2);
			break;
			default:
				System.err.println("Invalid Option");
		}
		assertEquals(expectedResult, result);
	}

}
