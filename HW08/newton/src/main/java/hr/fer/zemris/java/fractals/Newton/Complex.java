package hr.fer.zemris.java.fractals.Newton;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class gives basic calculation functions to work with complex numbers.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Complex {

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Real part of ComplexNumber.
	 */
	final private double real;
	/**
	 * Imaginary part of ComplexNUmber.
	 */
	final private double imaginary;
	/**
	 * Value of angle in ComplexNumber.
	 */
	final private double angle;
	/**
	 * Value of magnitude in ComplexNumber.
	 */
	final private double magnitude;

	public Complex() {
		this(0, 0);
	}

	/**
	 * Consturcts new Complex number with real and imaginary part.
	 * 
	 * @param real
	 *            real part of complex number
	 * @param imaginary
	 *            imaginary part of complex number
	 */
	public Complex(double real, double imaginary) {
		this.imaginary = imaginary;
		this.real = real;
		angle = Math.atan2(imaginary, real);
		magnitude = Math.hypot(real, imaginary);
	}

	/**
	 * Constructs new Complex number from real, imaginary part, angle and
	 * magnitude.
	 * 
	 * @param real
	 *            real part of complex number
	 * 
	 * @param imaginary
	 *            imaginary part of complex number
	 * @param angle
	 *            complex number angle
	 * @param magnitude
	 *            complex number module
	 */
	private Complex(double real, double imaginary, double angle,
			double magnitude) {
		this.real = real;
		this.imaginary = imaginary;
		this.angle = angle;
		this.magnitude = magnitude;
	}

	/**
	 * Returns module of complex number
	 * 
	 * @return module of complex number
	 */
	public double module() {
		return magnitude;
	}

	/**
	 * Adds up this Complex to number and returns new one.
	 * 
	 * @param number
	 *            complex number that adds to this complex number
	 * @return new Complex
	 */
	public Complex add(Complex number) {
		return new Complex(number.real + this.real, number.imaginary
				+ this.imaginary);
	}

	/**
	 * Substract this Complex and number, returns new one.
	 * 
	 * @param number
	 *            subtrahend
	 * @return new Complex
	 */
	public Complex sub(Complex number) {
		return new Complex(this.real - number.real, this.imaginary
				- number.imaginary);
	}

	/**
	 * Multiplies this complex number and given number. Returns new one.
	 * 
	 * @param number
	 *            multiplier
	 * @return new Complex
	 */
	public Complex multiply(Complex number) {
		double newMagnitude = this.magnitude * number.magnitude;
		double newAngle = this.angle + number.angle;
		double real = newMagnitude * Math.cos(newAngle);
		double imaginary = newMagnitude * Math.sin(newAngle);

		return new Complex(real, imaginary, newAngle, newMagnitude);
	}

	/**
	 * Division this complex number with given number. Returns new one.
	 * 
	 * @param number
	 *            divisor
	 * @return new Complex
	 */
	public Complex divide(Complex number) {
		double newMagnitude;
		if (doubleDifferentFromZero(number.magnitude, 1e-16)) {
			newMagnitude = this.magnitude / number.magnitude;
		} else {
			newMagnitude = 0;
		}
		double newAngle = this.angle - number.angle;
		double real = newMagnitude * Math.cos(newAngle);
		double imaginary = newMagnitude * Math.sin(newAngle);

		return new Complex(real, imaginary, newAngle, newMagnitude);
	}

	/**
	 * Returns this complex number with real and imaginary part negated.
	 * 
	 * @return negated complex number
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Returns the new ComplexNumber of the this ComplexNumber raised to the
	 * power of the argument.
	 * 
	 * @param n
	 *            exponent
	 * @return new ComplexNumber
	 * @throws IllegalArgumentException
	 *             if exponent is smaller than zero.
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(
					"Exponent can't be smaller than zero.");
		}
		double newMagnitude = Math.pow(magnitude, n);
		double newAngle = n * this.angle;
		double real = newMagnitude * Math.cos(newAngle);
		double imaginary = newMagnitude * Math.sin(newAngle);

		return new Complex(real, imaginary, newAngle, newMagnitude);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		if (Math.abs(imaginary - other.imaginary) > 1e-12)
			return false;
		if (Math.abs(real - other.real) > 1e-12)
			return false;
		return true;
	}
	
	/**
	 * Returns new Complex number parsing the string. Parse method accepts entry
	 * like "2.14+i3","2","i3". If in string is given real and imaginary part,
	 * real part must be first one.
	 * 
	 * @param str
	 *            complex number in string
	 * @return new ComplexNumber
	 */
	public static Complex parseFromString(String str) {
		final String regex = "(?<realAndImg>(?<real>[\\+\\-]?[\\d]+\\.?[\\d]*)"
				+ "(?<img>[\\+\\-]{1}i[\\d]*\\.?[\\d]*))"
				+ "|(?<onlyImg>[\\+\\-]?i[\\d]*\\.?[\\d]*)"
				+ "|(?<onlyReal>[\\+\\-]?[\\d]+\\.?[\\d]*)"
				+ "|(?<other>.*)";
		

		str = str.replaceAll(" ", "");
		if(str.isEmpty()){
			throw new IllegalArgumentException(
					"Given string is not parseable to Complex number");
		}
		final Matcher match = Pattern.compile(regex).matcher(str);
		double real = 0;
		double imaginary = 0;
		match.find();
		if (!match.group().isEmpty()) {
			if (match.group("realAndImg") != null) {
				real = Double.parseDouble(match.group("real"));
				imaginary = parseImaginary(match.group("img"));
			} else if (match.group("onlyImg") != null) {
				imaginary = parseImaginary((match.group()));
			} else if (match.group("onlyReal") != null) {
				real = Double.parseDouble(match.group());

			} else if (match.group("other") != null){
				throw new IllegalArgumentException(
						"Given string is not parseable to Complex number");
			}

		}
		match.find();
		if (!match.group().isEmpty()) {
			throw new IllegalArgumentException(
					"Given string is not parseable to Complex number");
		}

		return new Complex(real, imaginary);
	}

	private static double parseImaginary(String group) {
		double imaginary;
		if (group.endsWith("i")) {
			group = group.substring(0, group.length() - 1) + "1";
			imaginary = Double.parseDouble(group);
			return imaginary;
		}
		group = group.replaceFirst("i", "");
		imaginary = Double.parseDouble(group);

		return imaginary;

	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		DecimalFormat formatter = new DecimalFormat("0.####");
		if (doubleDifferentFromZero(real, 1e-6)) {
			s.append(formatter.format(real));
		}
		if (doubleDifferentFromZero(imaginary, 1e-6)) {
			if (imaginary > 0 && s.length() != 0) {
				s.append("+");
			}
			s.append(formatter.format(imaginary) + "i");
		}

		if (s.toString().isEmpty()) {
			s.append("+0");
		}

		if (doubleDifferentFromZero(real, 1e-6)
				&& doubleDifferentFromZero(imaginary, 1e-6)) {
			s.insert(0, "(");
			s.append(")");
		}
		return s.toString();
	}

	public boolean doubleDifferentFromZero(double d, double tresHold) {
		return d > tresHold || d < -tresHold;
	}
}
