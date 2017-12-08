package demo.fraction;

/**
 * This Fraction class supports operations Add, Subtract, Multiply, Divide,
 * Equality/Non-Equality, Greater Than/Less Than and Display
 * 
 * @author Kalyan Sikdar
 * @version 1.0
 */
public class Fraction implements Comparable<Fraction> {
	private int numerator;
	private int denominator;

	private static final String ILLEGAL_ARGUMENT_EXCEPTION = "Illegal or inappropriate argument cannot be passed";
	private static final String ARITHMETIC_EXCEPTION = "Denominator cannot be Zero";
	private static final String INTEGER_OVERFLOW_EXCEPTION = "Integer value overflow";

	/**
	 * Creates a new Fraction object that represents the fraction x/y Zero
	 * fraction is represented by 0/0
	 * 
	 * @param numerator
	 *            numerator of the fraction
	 * @param denominator
	 *            denominator of the fraction
	 * @exception ArithmeticException
	 *                If the denominator is zero & numerator is not zero
	 */

	public Fraction(int numerator, int denominator) throws ArithmeticException {
		// handle zero fraction
		if (numerator != 0 && denominator == 0)
			throw new ArithmeticException(ARITHMETIC_EXCEPTION);

		// handle positive fraction
		if ((numerator < 0 && denominator < 0) || (numerator > 0 && denominator > 0)) {
			this.setDenominator(Math.abs(denominator));
			this.setNumerator(Math.abs(numerator));
		}

		// handle negative fraction
		else if (numerator < 0 || denominator < 0) {
			this.setDenominator(Math.abs(denominator));
			this.setNumerator(-1 * Math.abs(numerator));
		}

	}

	public int getNumerator() {
		return numerator;
	}

	public void setNumerator(int numerator) throws ArithmeticException {
		this.numerator = numerator;
		if (numerator == 0)
			this.setDenominator(numerator);
	}

	public int getDenominator() {
		return denominator;
	}

	// todo
	public void setDenominator(int denominator) throws ArithmeticException {
		// only checks nonZeroNum/0
		if (this.getNumerator() != 0 && denominator == 0)
			throw new ArithmeticException(ARITHMETIC_EXCEPTION);

		if (denominator < 0)
			this.setNumerator(this.getNumerator() * -1);
		this.denominator = Math.abs(denominator);
	}

	/**
	 * Adds parameter fraction with current fraction and returns another
	 * fraction as their sum.
	 * 
	 * @param fraction
	 *            A Fraction Object
	 * @return sum A fraction object that represents the sum of current fraction
	 *         and parameter fraction
	 * @exception IllegalArgumentException
	 *                If the parameter is null.
	 */
	public Fraction add(Fraction fraction) throws IllegalArgumentException, ArithmeticException {
		if (fraction == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION);

		// for scenario n1/d1 + 0/0, where n1 & d1 is non zero
		if (fraction.isZero())
			return new Fraction(this.getNumerator(), this.getDenominator());

		// for scenario 0/0 + n2/d2, where n2 & d2 is non zero
		if (this.isZero())
			return new Fraction(fraction.getNumerator(), fraction.getDenominator());

		int lcm = getLcm(this.getDenominator(), fraction.getDenominator());

		int calculatedSum = addTwoNums(multiplyTwoNums(this.getNumerator(), lcm / this.getDenominator()),
				multiplyTwoNums(fraction.getNumerator(), lcm / fraction.getDenominator()));

		Fraction sum = new Fraction(calculatedSum, lcm);
		return sum.reduceFraction();
	}

	/**
	 * Subtracts parameter fraction from current fraction and returns another
	 * fraction as their difference.
	 * 
	 * @param fraction
	 *            A Fraction Object
	 * @return subtract A fraction that represents the difference between
	 *         current fraction and parameter fraction
	 * @exception IllegalArgumentException
	 *                If the parameter is null.
	 */

	public Fraction subtract(Fraction fraction) throws IllegalArgumentException, ArithmeticException {
		if (fraction == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION);

		// for scenario n1/d1 + 0/0, where n1 & d1 is non zero
		if (fraction.isZero())
			return new Fraction(this.getNumerator(), this.getDenominator());

		// for scenario 0/0 + n2/d2, where n2 & d2 is non zero
		if (this.isZero())
			return new Fraction(-1 * fraction.getNumerator(), fraction.getDenominator());

		int lcm = getLcm(this.getDenominator(), fraction.getDenominator());
		int calculatedDiff = this.getNumerator() * (lcm / this.getDenominator())
				- fraction.getNumerator() * (lcm / fraction.getDenominator());
		Fraction difference = new Fraction(calculatedDiff, lcm);
		return difference.reduceFraction();
	}

	/**
	 * Multiplies parameter fraction with current fraction and returns another
	 * fraction as their product.
	 * 
	 * @param fraction
	 *            A Fraction Object
	 * @return product A fraction object that represents the product of current
	 *         fraction and parameter fraction
	 * @exception IllegalArgumentException
	 *                If the parameter is null.
	 */

	public Fraction multiply(Fraction fraction) throws IllegalArgumentException, ArithmeticException {
		if (fraction == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION);

		// product of the numerator of both fractions
		int multipliedNumerator = multiplyTwoNums(this.getNumerator(), fraction.getNumerator());
		// product of the denominator of both fractions
		int multipliedDenominator = multiplyTwoNums(this.getDenominator(), fraction.getDenominator());

		Fraction product = new Fraction(multipliedNumerator, multipliedDenominator);
		return product.reduceFraction();
	}

	/**
	 * Divides current fraction by parameter fraction and returns another
	 * fraction as their result.
	 * 
	 * @param fraction
	 *            A Fraction Object
	 * @return product A fraction object that represents the division result of
	 *         current fraction and parameter fraction
	 * @exception IllegalArgumentException
	 *                If the parameter is null. ArithmeticException If the
	 *                denominator is zero & numerator is not zero
	 */

	public Fraction divide(Fraction fraction) throws IllegalArgumentException, ArithmeticException {
		if (fraction == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION);
		if (fraction.isZero())
			throw new ArithmeticException(ARITHMETIC_EXCEPTION);

		int resultNumerator = multiplyTwoNums(this.getNumerator(), fraction.getDenominator());
		int resultDenominator = multiplyTwoNums(this.getDenominator(), fraction.getNumerator());
		// if resultDenominator is zero, the creation of result would throw
		// Divide by zero exception
		Fraction result = new Fraction(resultNumerator, resultDenominator);
		return result.reduceFraction();
	}

	/**
	 * Checks if the current fraction is of the form 0/0
	 *
	 * @return true If numerator and denominator of current fraction is both 0
	 *         false If numerator and denominator of current fraction is both
	 *         not 0
	 * @exception IllegalArgumentException
	 *                If the parameter is null.
	 */

	private boolean isZero() {
		if (this.getNumerator() == 0 && this.getDenominator() == 0)
			return true;
		return false;
	}

	/**
	 * Checks if numerator of the current fraction is less than 0
	 * 
	 * @return true If the current fraction is negative false If the current
	 *         fraction is positive
	 * @exception IllegalArgumentException
	 *                If the parameter is null.
	 */

	private boolean isNegative() {
		return this.getNumerator() < 0;
	}

	/**
	 * Adds two integer number and returns the summation
	 * 
	 * @param number1
	 *            first integer value
	 * @param number2
	 *            second integer value
	 * @return summedValue An integer that represents sum of two input integers
	 * @exception ArithmeticException
	 *                If the summation exceeds the maximum size of the Integer
	 *                type
	 */
	private int addTwoNums(int number1, int number2) throws ArithmeticException {
		long summedValue = (long) number1 + (long) number2;
		// check if the sum of both the numbers is outside the range of a int
		if (summedValue < Integer.MIN_VALUE || summedValue > Integer.MAX_VALUE) {
			throw new ArithmeticException(INTEGER_OVERFLOW_EXCEPTION);
		}
		return (int) summedValue;
	}

	/**
	 * Multiples two integer number and returns the product integer
	 * 
	 * @param number1
	 *            first integer value
	 * @param number2
	 *            second integer value
	 * @return multipliedValue An integer that represents product of two input
	 *         integers
	 * @exception ArithmeticException
	 *                If the product exceeds the maximum size of the Integer
	 *                type
	 */

	private int multiplyTwoNums(int number1, int number2) throws ArithmeticException {
		long multipliedValue = (long) number1 * (long) number2;
		// check if the product of both the numbers is outside the range of a
		// int
		if (multipliedValue < Integer.MIN_VALUE || multipliedValue > Integer.MAX_VALUE) {
			throw new ArithmeticException(INTEGER_OVERFLOW_EXCEPTION);
		}
		return (int) multipliedValue;
	}

	/**
	 * Simplifies the numerator and denominator of the current fraction if there
	 * is a common factor
	 * 
	 * @param fraction
	 *            A Fraction Object
	 * @return reducedFraction A simplified form of the parameter fraction
	 */

	private Fraction reduceFraction() {
		if (this.isZero())
			return new Fraction(0, 0);

		int gcd = getGcd(this.getNumerator(), this.getDenominator());
		int numerator = this.getNumerator() / gcd;
		int denominator = this.getDenominator() / gcd;
		Fraction reducedFraction = new Fraction(numerator, denominator);
		return reducedFraction;
	}

	/**
	 * Finds lowest common multiple of two numbers
	 * 
	 * @param number1
	 *            first integer value
	 * @param number2
	 *            Second integer value
	 * @return number1 lowest common multiple of two the numbers
	 */

	private int getLcm(int number1, int number2) {
		int factor = number1;
		while ((number1 % number2) != 0)
			number1 = number1 + factor;
		return number1;
	}

	/**
	 * Finds greatest common divisor of two numbers
	 * 
	 * @param number1
	 *            first integer value
	 * @param number2
	 *            second integer value
	 * @return number1 greatest common divisor of two the numbers
	 */
	private int getGcd(int number1, int number2) {
		if (number2 != 0)
			return (getGcd(number2, number1 % number2));
		else
			return number1;
	}

	/**
	 * Displays the fraction
	 * 
	 * @param @return
	 * 			@exception
	 */

	public void display() {
		System.out.println("The Fraction is: " + this.getNumerator() + "/" + this.getDenominator());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + denominator;
		result = prime * result + numerator;
		return result;
	}

	/**
	 * Finds equality between parameter fraction and current fraction
	 * 
	 * @param obj
	 * 
	 * @return true If parameter fraction and current fraction are equal
	 * @return false If parameter fraction and current fraction are different
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Fraction))
			return false;
		Fraction other = (Fraction) obj;
		if (denominator != other.denominator)
			return false;
		if (numerator != other.numerator)
			return false;
		return true;
	}

	/**
	 * Compares parameter fraction and current fraction
	 * 
	 * @param arg0
	 *            A Fraction object
	 * @return 0 If parameter fraction and current fraction are equal
	 * @return -1 If parameter fraction is lesser than current fraction
	 * @return 1 If parameter fraction is greater than current fraction
	 * @exception IllegalArgumentException
	 *                If parameter fraction is null
	 */


	public int compareTo(Fraction arg0) {
		if (arg0 == null) {
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION);
		}
		if (this.equals(arg0))
			return 0;
		if (this.subtract(arg0).isNegative())
			return -1;
		else
			return 1;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return numerator + "/" + denominator;
	}

	/**
	 * Checks if parameter fraction is lesser than current fraction
	 * 
	 * @param arg0
	 *            A Fraction object
	 * @return true If parameter fraction is lesser than current fraction
	 * @return false If parameter fraction is not lesser than current fraction
	 * @exception IllegalArgumentException
	 *                If parameter fraction is null
	 */

	public boolean lessThan(Fraction fraction) {
		if (fraction == null) {
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION);
		}
		return compareTo(fraction) == -1 ? true : false;
	}

	/**
	 * Checks if parameter fraction is greater than current fraction
	 * 
	 * @param arg0
	 *            A Fraction object
	 * @return true If parameter fraction is greater than current fraction
	 * @return false If parameter fraction is not greater than current fraction
	 * @exception IllegalArgumentException
	 *                If parameter fraction is null
	 */

	public boolean greaterThan(Fraction fraction) {
		if (fraction == null) {
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION);
		}
		return compareTo(fraction) == -1 ? true : false;
	}

	
}
