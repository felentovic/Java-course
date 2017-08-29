package hr.fer.zemris.linearna;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

	@Test
	public void parseString_True_NewVector() {
		double[] elements = { 1, 2, 3, 4 };
		String str = " 1 2 3 4 ";
		IVector vector = Vector.parseSimple(str);
		Assert.assertEquals(new Vector(elements), vector);
	}
	
	@Test
	public void set_True_NewValue() {
		double[] elements = { 1, 2, 3, 4 };
		IVector vector = new Vector(true, true, elements);
		vector.set(2, -4);
		Assert.assertTrue(vector.get(2) == -4);
	}
	// exceptions
	@Test(expected = IllegalArgumentException.class)
	public void parseString_IllegalArgumentException_EmptyString() {
		double[] elements = { 1, 2, 3, 4 };
		String str = "  ";
		IVector vector = Vector.parseSimple(str);
		Assert.assertEquals(new Vector(elements), vector);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseString_IllegalArgumentException_NotDouble() {
		double[] elements = { 1, 2, 3, 4 };
		String str = " 1 2 gr 4 ";
		IVector vector = Vector.parseSimple(str);
		Assert.assertEquals(new Vector(elements), vector);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_IllegalArgumentException_NegativeDimension() {
		@SuppressWarnings("unused")
		IVector vector = new Vector(-1);
	}

	@Test(expected = UnmodifiableObjectException.class)
	public void set_UnmodifiableObjectException_NegativeDimension() {
		double[] elements = { 1, 2, 3, 4 };
		IVector vector = new Vector(true, true, elements);
		vector.set(2, -4);
	}

}
