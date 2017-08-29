package hr.fer.zemris.java.fractals.Newton;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("unused")
public class ComplexTest {

	@Test
	public void parseFromString_ValidResult_Test() {

		Complex c1 = Complex.parseFromString("3.455+i45");
		Complex c2 = Complex.parseFromString("3.455");
		Complex c3 = Complex.parseFromString("i");

		Complex t1 = new Complex(3.455, 45);
		Complex t2 = new Complex(3.455, 0);
		Complex t3 = new Complex(0, 1);
		Assert.assertEquals(t1, c1);
		Assert.assertEquals(t2, c2);
		Assert.assertEquals(t3, c3);

	}

	// Test exceptions
	@Test(expected = IllegalArgumentException.class)
	public void parseFromString_IllegalArgumentException_MoreParts() {
		Complex c5 = Complex.parseFromString("3.455+4.45+i45");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseFromString_IllegalArgumentException_FirstImgThanReal() {
		Complex c5 = Complex.parseFromString("3.45+ii");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseFromString_IllegalArgumentException_TwoRealParts() {
		Complex c5 = Complex.parseFromString("3.455+4.67");
	}

	// ----------------------test calculation methods---------------------
	@Test
	public void add_ValidResult_Test() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(4, -1);
		Complex c3 = c1.add(c2);

		Complex t1 = new Complex(6, 2);
		Assert.assertEquals(t1, c3);
	}

	@Test
	public void sub_ValidResult_Test() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(4, -1);
		Complex c3 = c1.sub(c2);

		Complex t1 = new Complex(-2, 4);
		Assert.assertEquals(t1, c3);
	}

	@Test
	public void multiply_ValidResult_Test() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(4, -1);
		Complex c3 = c1.multiply(c2);

		Complex t1 = new Complex(11, 10);
		Assert.assertEquals(t1, c3);
	}

	@Test
	public void divide_ValidResult_Test() {
		Complex c1 = new Complex(2, -1);
		Complex c2 = new Complex(4, 3);
		Complex c3 = c2.divide(c1);

		Complex t1 = new Complex(1, 2);
		Assert.assertEquals(t1, c3);
	}

	@Test
	public void power_ValidResult_Test() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = c1.power(3);

		Complex t1 = new Complex(-46, 9);
		Assert.assertEquals(t1, c2);
	}

	@Test
	public void toString_ValidResult_Test() {
		Complex c = new Complex(5, 2);
		Assert.assertEquals("(5+2i)", c.toString());
	}

	@Test
	public void toString_ValidResult_Test2() {
		Complex c = new Complex(0, 0);
		Assert.assertEquals("+0", c.toString());
	}

	// Test exceptions
	@Test(expected = IllegalArgumentException.class)
	public void power_IllegalArgumentException_Test() {
		Complex c = new Complex(5, 2);
		Complex c1 = c.power(-1);
	}

}
