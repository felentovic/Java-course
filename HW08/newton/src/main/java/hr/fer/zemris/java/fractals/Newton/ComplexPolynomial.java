package hr.fer.zemris.java.fractals.Newton;

import java.util.Arrays;

/**
 * Class is representation of polynom which factors are complex numbers.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class ComplexPolynomial {
	private final Complex[] factors;
	private final short order;

	/**
	 * Constructor creates {@link ComplexPolynomial} from factors.
	 * 
	 * @param factors
	 *            complex factors
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = new Complex[factors.length];
		System.arraycopy(factors, 0, this.factors, 0, factors.length);
		order = (short) (factors.length - 1);
	}

	/**
	 * Returns order of this polynom
	 * 
	 * @return order of this polynom
	 */
	public short order() {
		return order;
	}

	/**
	 * Computes a new polynomial this multiply p
	 * 
	 * @param p
	 *            given complex polynom
	 * @return new complex polynom, this multiply p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[order + p.order + 1];
		int pOrder = p.order;
		for (int i = 0; i < order + 1; i++) {
			for (int j = 0; j < pOrder + 1; j++) {
				if (newFactors[i + j] == null) {
					newFactors[i + j] = factors[i].multiply(p.factors[j]);
				} else {
					newFactors[i + j] = newFactors[i + j].add(factors[i]
							.multiply(p.factors[j]));
				}
			}
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes first derivative of this polynomial
	 * 
	 * @return new {@link ComplexPolynomial}, derivation of this polynom
	 */
	public ComplexPolynomial derive() {
		if (order == 0) {
			return new ComplexPolynomial(new Complex[] { new Complex(0, 0) });
		}
		Complex[] newFactors = new Complex[order];
		// multiply every factor
		for (int i = 0; i < order; i++) {
			newFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes polynomial value at given point z
	 * 
	 * @param z
	 *            {@link Complex} point
	 * @return value of this {@link ComplexPolynomial} at given point z
	 */
	public Complex apply(Complex z) {
		Complex number = new Complex();
		for (int i = 0; i < order + 1; i++) {
			number = number.add(z.power(i).multiply(factors[i]));
		}
		return number;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = order; i >= 0; i--) {
			Complex factor = factors[i];
			if (factor.equals(Complex.ZERO)) {
				continue;
			}
			String factorString = factors[i].toString();
			if (!factorString.startsWith("-")) {
				str.append("+");

			}
			if ((factors[i].equals(Complex.ONE) || factors[i]
					.equals(Complex.ONE_NEG)) && i != 0) {
				str.append(factorString.substring(0, factorString.length() - 1));
			} else {
				str.append(factor.toString());
			}
			if (i != 0) {
				str.append("z");
				if (i > 1) {
					str.append("^" + i);
				}
			}

		}
		if (str.length() == 0) {
			str.append("0");
		}
		return str.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexPolynomial))
			return false;
		ComplexPolynomial other = (ComplexPolynomial) obj;
		if (order != other.order)
			return false;
		if (!Arrays.equals(factors, other.factors))
			return false;

		return true;
	}

}
