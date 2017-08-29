package hr.fer.zemris.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import org.junit.Assert;
import org.junit.Test;

public class ValueWrapperTest {

	@Test
	public void getValue_True_TestGetter() {
		ValueWrapper wrapper = new ValueWrapper(null);
		Assert.assertEquals(null, wrapper.getValue());
	}

	@Test
	public void getValue_True_TestGetter2() {
		ValueWrapper wrapper = new ValueWrapper("5");
		Assert.assertEquals("5", wrapper.getValue());
	}

	@Test
	public void setValue_True_TestSetter() {
		ValueWrapper wrapper = new ValueWrapper(-5);
		wrapper.setValue(null);
		Assert.assertEquals(null, wrapper.getValue());
	}

	@Test
	public void increment_True_NewValueIsDouble() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.increment(1.5);
		Assert.assertEquals(1.5, wrapper.getValue());
		Assert.assertTrue(wrapper.getValue() instanceof Double);
	}

	@Test
	public void decrement_True_NewValueIsInteger() {
		ValueWrapper wrapper = new ValueWrapper("5");
		wrapper.decrement(1);
		Assert.assertEquals(4, wrapper.getValue());
		Assert.assertTrue(wrapper.getValue() instanceof Integer);
	}

	@Test
	public void multiply_True_NewValueIsInteger() {
		ValueWrapper wrapper = new ValueWrapper(5);
		wrapper.multiply(5);
		Assert.assertEquals(25, wrapper.getValue());
		Assert.assertTrue(wrapper.getValue() instanceof Integer);
	}

	@Test
	public void divide_True_NewValueIsDouble() {
		ValueWrapper wrapper = new ValueWrapper("5.0");
		wrapper.divide(2);
		Assert.assertEquals(2.5, wrapper.getValue());
		Assert.assertTrue(wrapper.getValue() instanceof Double);
	}

	@Test
	public void numCompare_True_EqualNumbers() {
		ValueWrapper wrapper = new ValueWrapper(5);
		Assert.assertTrue(wrapper.numCompare(5) == 0);
	}

	@Test
	public void numCompare_True_EqualNulls() {
		ValueWrapper wrapper = new ValueWrapper(null);
		Assert.assertTrue(wrapper.numCompare(null) == 0);
	}

	@Test
	public void numCompare_True_SmallerNumberFromString() {
		ValueWrapper wrapper = new ValueWrapper("5");
		Assert.assertTrue(wrapper.numCompare("67") < 0);
	}

	@Test
	public void numCompare_True_EqualValueWrappers() {
		ValueWrapper wrapper = new ValueWrapper("5");
		Assert.assertTrue(wrapper.numCompare(new ValueWrapper("5")) == 0);
	}

	// exceptions
	@Test(expected = RuntimeException.class)
	public void increment_RuntimeException_UnsupportedType() {
		ValueWrapper wrapper = new ValueWrapper("number");
		wrapper.increment(1);
	}

	@Test(expected = RuntimeException.class)
	public void decrement_RuntimeException_UnsupportedType() {
		ValueWrapper wrapper = new ValueWrapper(5);
		wrapper.decrement("number");
	}
	
	@Test(expected= IllegalArgumentException.class)
	public void divide_IllegalArgumentExcetpion_DivideByZero() {
		ValueWrapper wrapper = new ValueWrapper("5.0");
		wrapper.divide(0);
	}
}
