package hr.fer.zemris.java.fractals.Newton;

/**
 * ComplexRootedPolynomial class used for operation with complex number in form (z-root)
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ComplexRootedPolynomial {
	private Complex[] roots;

	/**
	 * Constructor for ComplexRootedPolynomial that is constructed from given
	 * roots
	 * 
	 * @param roots
	 *            polynomial roots
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = new Complex[roots.length];
		System.arraycopy(roots, 0, this.roots, 0, roots.length);
		this.roots = roots;
	}

	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z
	 *            given complex point
	 * @return value of polynom at given point
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;
		int length = roots.length;
		for (int i = 0; i < length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}

	/**
	 * Converts this representation to ComplexPolynomial type.
	 * 
	 * @return this representation as ComplexPolynomial type
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(Complex.ONE);
		int length = roots.length;
		Complex[] rootFactors = new Complex[2];
		rootFactors[1] = Complex.ONE;
		for (int i = 0; i < length; i++) {
			rootFactors[0] = roots[i].negate();
			polynomial = polynomial
					.multiply(new ComplexPolynomial(rootFactors));
		}
		return polynomial;
	}
	

	/**
	 * Finds index of closest root for given complex number z that is within
	 * treshold if there is no such root, returns -1
	 * 
	 * @param z
	 *            given complex number
	 * @param treshold
	 *            treshold
	 * @return index of closest root for given complex number z that is within
	 *         treshold if there is no such root, returns -1
	 */
	public short indexOfClosestRootFor(Complex z, double treshold) {
		short index = -1;
		int length = roots.length;
		for (int i = 0; i < length; i++) {
			if (roots[i].sub(z).module() < treshold) {
				index = (short) i;
				break;
			}
		}
		return index;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		int length = roots.length;
		for (int i = 0; i < length; i++) {
			str.append("(z");
			if (roots[i].toString().startsWith("-")) {
				str.append("+").append(roots[i].toString().substring(1));
			} else {
				str.append("-").append(roots[i]);
			}
			str.append(")");
		}
		return str.toString();
	}
}
