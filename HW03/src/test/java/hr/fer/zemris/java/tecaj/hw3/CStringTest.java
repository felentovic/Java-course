package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Test;
import org.junit.Assert;

public class CStringTest {

	// --------------------test constructors-----------------

	/**
	 * Constructor creates new CString using char array, offset and count.
	 */
	@Test
	public void constructor1Test() {
		char[] data = new char[] { 'T', 'E', 'S', 'T' };
		CString string = new CString(data, 1, 3);
	}

	/**
	 * Constructor creates new CString from CString. CStrings don't share char
	 * array.
	 */
	@Test
	public void constructor2Test() {
		CString string = new CString("TEST");
		CString string2 = string.substring(1, 4);

		CString string3 = new CString(string2);
	}

	// Test exceptions
	@Test(expected = IllegalArgumentException.class)
	public void countCantBeBiggerThanLength() {
		char[] data = new char[] { 'T', 'E', 'S', 'T' };
		CString string = new CString(data, 1, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void countCantBeSmallerThenZero() {
		char[] data = new char[] { 'T', 'E', 'S', 'T' };
		CString string = new CString(data, 0, -4);
	}

	// --------------------test methods------------------------
	@Test
	public void lengthTest() {
		CString string = new CString("TEST");

		Assert.assertEquals(4, string.length(), 0.1);
	}

	@Test
	public void charAtTest() {
		CString string = new CString("TEST");

		Assert.assertEquals('E', string.charAt(1));
	}

	@Test
	public void indexOfTest() {
		CString string = new CString("TEST");

		Assert.assertEquals(2, string.indexOfChar('S'), 0.1);
	}

	@Test
	public void startsWithTest() {
		CString string = new CString("TEST");

		Assert.assertTrue(string.startsWith(new CString("TE")));
	}

	@Test
	public void endsWithTest() {
		CString string = new CString("TEST");

		Assert.assertTrue(string.endsWith(new CString("ST")));
	}

	@Test
	public void containsTest() {
		CString string = new CString("TEST");

		Assert.assertTrue(string.contains(new CString("ES")));
	}

	@Test
	public void containsTest2() {
		CString string = new CString("TEST");

		Assert.assertFalse(string.contains(new CString("EP")));
	}

	@Test
	public void substringTest() {
		CString string = new CString("TEST");

		Assert.assertEquals(new CString("ST"), string.substring(2, 4));
	}

	@Test
	public void leftTest() {
		CString string = new CString("TEST");

		Assert.assertEquals(new CString("TES"), string.left(3));
	}

	@Test
	public void rightTest() {
		CString string = new CString("TEST");

		Assert.assertEquals(new CString("ST"), string.right(2));
	}

	@Test
	public void addTest() {
		CString string = new CString("TEST");

		Assert.assertEquals(new CString("TEST123"),
				string.add(new CString("123")));
	}

	@Test
	public void replaceAllTest() {
		CString string = new CString("TEST");

		Assert.assertEquals(new CString("AESA"), string.replaceAll('T', 'A'));
		Assert.assertEquals(new CString("TEST"), string);

	}

	@Test
	public void replaceAllCStringTest() {
		CString string = new CString("TEST CString 123");
		Assert.assertEquals(new CString("TEST 0123"),
				string.replaceAll(new CString("CString "), new CString("0")));
	}

	@Test
	public void equalsTest() {
		CString string = new CString("TEST");
		CString string2 = string.replaceAll('T', 'A');
		CString string3 = new CString("TEST!");

		Assert.assertTrue(string.equals(new CString("TEST")));
		Assert.assertFalse(string.equals(string2));
		Assert.assertFalse(string.equals(string3));
	}

	@Test
	public void toStringTest() {
		CString string = new CString("TEST");
		CString string2 = string.replaceAll('T', 'P');
		Assert.assertEquals("PESP", string2.toString());
	}

	@Test
	public void toCharArrayTest() {
		CString string = new CString("TEST");
		char[] array = string.toCharArray();

		array[0] = 'P';
		Assert.assertEquals(new CString("TEST"), string);
	}

	// Test exceptions
	@Test(expected = IllegalArgumentException.class)
	public void substringIllegalArgumentExceptionTest() {
		CString string = new CString("TEST CString 123").substring(4, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void substringIllegalArgumentExceptionTest2() {
		CString string = new CString("TEST CString 123").substring(-1, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void leftIllegalArgumentExceptionTest() {
		CString string = new CString("TEST").left(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rightIllegalArgumentExceptionTest() {
		CString string = new CString("TEST").right(5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void charAtIllegalArgumentExceptionTest() {
		int index = new CString("TEST").charAt(4);
	}

}
