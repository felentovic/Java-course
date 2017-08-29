package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Test;
import org.junit.Assert;

public class ComplexNumberTest {

	// ---------------testGetters--------------------
	@Test
	public void getRealTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);

		Assert.assertEquals(2, c1.getReal(), 0.1);
	}

	@Test
	public void getImaginaryTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);

		Assert.assertEquals(3, c1.getImaginary(), 0.1);
	}

	@Test
	public void getAngleTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);

		Assert.assertEquals(0.9827, c1.getAngle(), 0.001);
	}

	@Test
	public void getMagnitudeTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);

		Assert.assertEquals(3.6055, c1.getMagnitude(), 0.001);
	}

	// --------------test static methods----------------------

	@Test
	public void fromRealTest() {
		ComplexNumber c1 = ComplexNumber.fromReal(3.5);

		Assert.assertEquals(3.5, c1.getReal(), 0.01);
		Assert.assertEquals(0, c1.getImaginary(), 0.01);
	}

	@Test
	public void fromImaginaryTest() {
		ComplexNumber c1 = ComplexNumber.fromImaginary(3.5);

		Assert.assertEquals(0, c1.getReal(), 0.01);
		Assert.assertEquals(3.5, c1.getImaginary(), 0.01);
	}

	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(0.9827, 3.6055);

		Assert.assertEquals(2, c1.getReal(), 0.01);
		Assert.assertEquals(3, c1.getImaginary(), 0.01);

	}

	@Test
	public void testParse() {

		ComplexNumber c1 = ComplexNumber.parse("3.455+45i");
		ComplexNumber c2 = ComplexNumber.parse("3.455");
		ComplexNumber c3 = ComplexNumber.parse("i");

		Assert.assertEquals(3.455, c1.getReal(), 0.001);
		Assert.assertEquals(45, c1.getImaginary(), 0.1);

		Assert.assertEquals(3.455, c2.getReal(), 0.001);
		Assert.assertEquals(0, c2.getImaginary(), 0.1);

		Assert.assertEquals(0, c3.getReal(), 0.1);
		Assert.assertEquals(1, c3.getImaginary(), 0.1);

	}
	// Test exceptions
	@Test(expected = IllegalArgumentException.class)
	public void parseIllegalArgumentExceptionTest() {
		ComplexNumber c5 = ComplexNumber.parse("3.455+4.45+45i");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseIllegalArgumentExceptionTest2() {
		ComplexNumber c5 = ComplexNumber.parse("i+3.45");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseIllegalArgumentExceptionTest3() {
		ComplexNumber c5 = ComplexNumber.parse("3.455+4.67");
	}
	
	// ----------------------test calculation methods---------------------
	@Test
	public void addTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = new ComplexNumber(4, -1);
		ComplexNumber c3 = c1.add(c2);

		Assert.assertEquals(6, c3.getReal(), 0.1);
		Assert.assertEquals(2, c3.getImaginary(), 0.1);
	}

	@Test
	public void subTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = new ComplexNumber(4, -1);
		ComplexNumber c3 = c1.sub(c2);

		Assert.assertEquals(-2, c3.getReal(), 0.1);
		Assert.assertEquals(4, c3.getImaginary(), 0.1);
	}

	@Test
	public void mulTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = new ComplexNumber(4, -1);
		ComplexNumber c3 = c1.mul(c2);

		Assert.assertEquals(11, c3.getReal(), 0.1);
		Assert.assertEquals(10, c3.getImaginary(), 0.1);
	}

	@Test
	public void divTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = new ComplexNumber(4, -1);
		ComplexNumber c3 = c1.div(c2);

		Assert.assertEquals(0.29411, c3.getReal(), 0.0001);
		Assert.assertEquals(0.82352, c3.getImaginary(), 0.0001);
	}

	@Test
	public void powerTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = c1.power(3);

		Assert.assertEquals(-46, c2.getReal(), 0.1);
		Assert.assertEquals(9, c2.getImaginary(), 0.1);
	}

	
	@Test
	public void rootTest() {
		ComplexNumber c = new ComplexNumber(5, 2);
		ComplexNumber c1 = c.root(3)[0];
		ComplexNumber c2 = c.root(3)[1];
		ComplexNumber c3 = c.root(3)[2];

		Assert.assertEquals(1.7387, c1.getReal(), 0.01);
		Assert.assertEquals(0.2217, c1.getImaginary(), 0.01);

		Assert.assertEquals(-1.0614, c2.getReal(), 0.01);
		Assert.assertEquals(1.3949, c2.getImaginary(), 0.01);

		Assert.assertEquals(-0.6773, c3.getReal(), 0.01);
		Assert.assertEquals(-1.6166, c3.getImaginary(), 0.01);
	}	
	
	@Test
	public void toStringTest(){
		ComplexNumber c = new ComplexNumber(5, 2);
		Assert.assertEquals("5+2i",c.toString());
	}
	
	@Test
	public void toStringTest2(){
		ComplexNumber c = new ComplexNumber(0, 0);
		Assert.assertEquals("0",c.toString());
	}
	//Test exceptions
	@Test(expected = IllegalArgumentException.class)
	public void powerIllegalArgumentExceptionTest() {
		ComplexNumber c = new ComplexNumber(5, 2);
		ComplexNumber c1 = c.power(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rootIllegalArgumentExceptionTest() {
		ComplexNumber c = new ComplexNumber(5, 2);
		ComplexNumber c1 = c.root(-3)[0];
	}
}
