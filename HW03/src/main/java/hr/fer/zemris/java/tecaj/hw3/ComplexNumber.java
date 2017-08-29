package hr.fer.zemris.java.tecaj.hw3;

import java.text.DecimalFormat;

/**
 * Class gives basic calculation functions to work with complex numbers.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ComplexNumber {
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

	/**
	 * Consturcts new ComplexNumber with real and imaginary part.
	 * 
	 * @param real
	 *            real part of complex number
	 * @param imaginary
	 *            imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this(real, imaginary, false);
	}

	/**
	 * If fromAngle is true constructs ComplexNumber from angle and magnitude,
	 * otherwise constructs ComplexNumber from real and imaginary part .If
	 * fromAngle is true than arg1 means angle, and arg2 means magnitude,
	 * otherwise arg1 means real and arg2 means imaginary.
	 * 
	 * @param arg1
	 *            If fromAngle is true than means angle, real otherwise
	 * @param arg2
	 *            If fromAngle is true than means magnitude, imaginary otherwise
	 * @param fromAngle
	 *            if true constructs ComplexNumber from angle and magnitude,
	 *            otherwise constructs ComplexNumber from real and imaginary
	 *            part
	 */
	private ComplexNumber(double arg1, double arg2, boolean fromAngle) {
		super();
		if (fromAngle) {
			this.angle = arg1;
			this.magnitude = arg2;
			this.real = magnitude * Math.cos(angle);
			this.imaginary = magnitude * Math.sin(angle);
		} else {
			this.real = arg1;
			this.imaginary = arg2;
			angle = Math.atan(imaginary / real);
			magnitude = Math.hypot(real, imaginary);
		}
	}

	// -------------------getters--------------

	/**
	 * Getter for real part.
	 * 
	 * @return value of real part
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter for imaginary part.
	 * 
	 * @return value of imaginary part
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Getter for angle. Angle value is from 0 to 2PI
	 * 
	 * @return value of angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Getter for magnitude. Magnitude is calculated by formula
	 * sqrt(realPart^2+imaginaryPart^2).
	 * 
	 * @return sqrt(realPart^2+imaginaryPart^2)
	 */
	public double getMagnitude() {
		return magnitude;
	}

	// ------------------static methods------------

	/**
	 * Returns new ComplexNumber, imaginary part is 0.
	 * 
	 * @param real
	 *            value of real part in ComplexNumber
	 * @return new ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Returns new ComplexNumber, real part is 0.
	 * 
	 * @param imaginary
	 *            value of imaginary part in ComplexNumber
	 * @return new ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Returns new ComlexNumber created from magnitude and angle.
	 * 
	 * @param magnitude
	 *            magnitude of new ComplexNumber
	 * @param angle
	 *            angle of new ComplexNumber
	 * @return new ComplexNuumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,
			double angle) {
		return new ComplexNumber(magnitude, angle, true);
	}

	/**
	 * Returns new ComplexNumber parsing the string. Parse method accepts entry
	 * like "2.14+3i","2","3i". If in string is given real and imaginary part,
	 * real part must be first one. Spaces are not allowed.
	 * 
	 * @param string
	 *            complex number in string
	 * @return new ComplexNumber
	 */
	public static ComplexNumber parse(String string) {

		final String doubleRegex = "(\\+|\\-)?(\\d+)(\\.\\d+)?.*";

		if (!string.matches(doubleRegex) && !string.equals("i")) {
			throw new IllegalArgumentException("Invalid complex number");
		}

		double newImaginary = 0;
		double newReal = 0;
		StringBuilder part = new StringBuilder();
		while (!string.isEmpty()) {
			part.append(string.charAt(0));
			string = string.substring(1);
			if (string.isEmpty()) {
				if (newReal != 0) {// real part is allready set
					throw new IllegalArgumentException("Invalid complex number");
				}
				if (part.toString().equals("i")) {
					newImaginary = 1;
				} else if (part.toString().equals("-i")) {
					newImaginary = -1;
				} else {
					newReal = Double.parseDouble(part.toString());
				}
				break;
			}
			if (string.charAt(0) == '.') {
				continue;
			} else if (!string.matches("[0-9].*")
					&& !string.matches("i||[\\+\\-].*")) {
				throw new IllegalArgumentException("Invalid complex number");

			} else if (!string.matches("[0-9].*")) {
				if (string.charAt(0) == 'i') {// we have 2 possibillites.
												// It's real or imaginary
												// part.
					newImaginary = Double.parseDouble(part.toString());
					break;
				} else {
					if (newReal != 0) {// real part is allready set
						throw new IllegalArgumentException(
								"Invalid complex number");
					}
					newReal = Double.parseDouble(part.toString());
				}
				part.setLength(0);
			}
		}
		return new ComplexNumber(newReal, newImaginary);
	}

	// ---------------calculation methods--------------

	/**
	 * Adds up this ComplexNumber to number and returns new one.
	 * 
	 * @param number
	 *            complex number that adds to this complex number
	 * @return new ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber number) {
		return new ComplexNumber(number.getReal() + this.real,
				number.getImaginary() + this.imaginary);
	}

	/**
	 * Substract this ComplexNumber and number, returns new one.
	 * 
	 * @param number
	 *            subtrahend
	 * @return new ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber number) {
		return new ComplexNumber(this.real - number.getReal(), this.imaginary
				- number.getImaginary());
	}

	/**
	 * Multiplies this complex number and given number. Returns new one.
	 * 
	 * @param number
	 *            multiplier
	 * @return new ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber number) {
		double newMagnitude = this.magnitude * number.getMagnitude();
		double newAngle = this.angle + number.getAngle();
		double real = newMagnitude * Math.cos(newAngle);
		double imaginary = newMagnitude * Math.sin(newAngle);

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Division this complex number with given number. Returns new one.
	 * 
	 * @param number
	 *            divisor
	 * @return new ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber number) {
		double newMagnitude = this.magnitude / number.getMagnitude();
		double newAngle = this.angle - number.getAngle();
		double real = newMagnitude * Math.cos(newAngle);
		double imaginary = newMagnitude * Math.sin(newAngle);

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Returns the new ComplexNumber of the this ComplexNumber raised to the
	 * power of the argument.
	 * 
	 * @param n
	 *            exponent
	 * @return new ComplexNumber
	 * @throws IllegalArgumentException
	 *             if exponent is smaller than one.
	 */
	public ComplexNumber power(int n) {
		if (n < 1) {
			throw new IllegalArgumentException(
					"Exponent can't be smaller than zero.");
		}
		double newMagnitude = Math.pow(magnitude, n);
		double newAngle = n * this.angle;
		double real = newMagnitude * Math.cos(newAngle);
		double imaginary = newMagnitude * Math.sin(newAngle);

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Returns array of ComplexNumbers with value of roots inside.
	 * 
	 * @param n
	 *            number of roots
	 * @return array of ComplexNumbers
	 * @throws IllegalArgumentException if number of roots is smaller than one.
	 */
	public ComplexNumber[] root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException(
					"Root can't be smaller than one.");
		}
		ComplexNumber[] complexNumbers = new ComplexNumber[n];
		double newMagnitude = Math.pow(magnitude, 1. / n);
		double newAngle, real, imaginary;
		for (int k = 0; k < n; k++) {
			newAngle = (this.angle + 2 * k * Math.PI) / n;
			real = newMagnitude * Math.cos(newAngle);
			imaginary = newMagnitude * Math.sin(newAngle);
			complexNumbers[k] = new ComplexNumber(real, imaginary);
		}
		return complexNumbers;
	}

	/**
	 * Returns ComplexNumber as string. Realpart + ImaginaryPart.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		DecimalFormat formatter = new DecimalFormat("0.####");
		if (real != 0) {
			s.append(formatter.format(real));
		}
		if (imaginary != 0) {
			if (imaginary > 0) {
				s.append("+");
			}
			s.append(formatter.format(imaginary) + "i");
		}

		if (s.toString().isEmpty()) {
			s.append("0");
		}

		return s.toString();
	}

}
