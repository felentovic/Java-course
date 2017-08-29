package hr.fer.zemris.java.fractals.Newton;

import org.junit.Assert;
import org.junit.Test;

public class ComplexPolynomialTest {

	@Test
	public void order_ValidResult_Test() {
		Complex[] factors = new Complex[] { new Complex(7, 2), Complex.ONE,
				Complex.ONE_NEG };
		ComplexPolynomial polynom = new ComplexPolynomial(factors);
		Assert.assertEquals(2, polynom.order());
	}

	@Test
	public void multiply_ValidResult_Test() {
		Complex[] factors = new Complex[] { new Complex(7, 2), Complex.ONE,
				Complex.ONE_NEG };
		ComplexPolynomial polynom = new ComplexPolynomial(factors);
		Complex[] factors2 = new Complex[] { new Complex(2, 0), Complex.ONE };
		ComplexPolynomial polynom2 = new ComplexPolynomial(factors2);
		Assert.assertEquals("-z^3-z^2+(9+2i)z+(14+4i)",
				polynom2.multiply(polynom).toString());
	}

	@Test
	public void derive_ValidResult_Test() {
		Complex[] factors = new Complex[] { Complex.ONE_NEG, Complex.ONE,
				new Complex(7, 0) };
		ComplexPolynomial polynom = new ComplexPolynomial(factors);
		Complex[] factors2 = new Complex[] { Complex.ONE, new Complex(14, 0) };
		Assert.assertEquals(new ComplexPolynomial(factors2), polynom.derive());
	}
	
	@Test
	public void deriveTest_True_DeriveZeroOrder(){
		ComplexPolynomial polynom = new ComplexPolynomial(Complex.ONE);
		Assert.assertEquals(new ComplexPolynomial(Complex.ZERO), polynom.derive());
	}
	@Test
	public void apply_ValidResult_Test() {
		Complex[] factors = new Complex[] { Complex.ONE, new Complex(14, 0) };
		ComplexPolynomial polynom = new ComplexPolynomial(factors);
		Assert.assertEquals(new Complex(15, 0), polynom.apply(Complex.ONE));
	}
}
