package hr.fer.zemris.java.fractals.Newton;

import org.junit.Assert;
import org.junit.Test;

public class ComplexRootedPolynomialTest {

	@Test
	public void apply_ValidResult_Test() {
		Complex[] roots = new Complex[] { new Complex(1, 0), new Complex(-1, 0) };
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(
				roots);
		Assert.assertEquals(new Complex(0, 0),
				rootedPolynomial.apply(Complex.ONE));
	}

	@Test
	public void toComplexPolynom_ValidResult_Test() {
		Complex[] roots = new Complex[] { new Complex(1, 0), new Complex(-1, 0) };
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(
				roots);
		Assert.assertEquals("+z^2-1", rootedPolynomial.toComplexPolynom()
				.toString());
	}

	@Test
	public void toString_ValidResult_Test() {
		Complex[] roots = new Complex[] { new Complex(1, 0), new Complex(-1, 0) };
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(
				roots);
		Assert.assertEquals("(z-1)(z+1)", rootedPolynomial.toString());
	}

	@Test
	public void indexOfClosestRoot_True_NoRootFound() {
		Complex[] roots = new Complex[] { new Complex(1, 0), new Complex(-1, 0) };
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(
				roots);
		Assert.assertEquals(-1,
				rootedPolynomial.indexOfClosestRootFor(new Complex(7, 8), 1e-3));
	}

	@Test
	public void indexOfClosestRoot_True_RootFound() {
		Complex[] roots = new Complex[] { new Complex(1, 0), new Complex(-1, 0) };
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(
				roots);
		Assert.assertEquals(0,
				rootedPolynomial.indexOfClosestRootFor(Complex.ONE, 1e-3));
	}
}
