package hr.fer.zemris.linearna;

import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

	@Test
	public void parseString_True_EqualMatrix() {
		String str = "1 2 3| 4 5 6 |";
		IMatrix matrix = Matrix.parseString(str);
		double[][] elements = { { 1, 2, 3 }, { 4, 5, 6 } };

		Assert.assertEquals(new Matrix(2, 3, elements, true), matrix);
	}

	@Test
	public void toString_True_Output() {
		String str = "1 2 3| 4 5 6 |";
		IMatrix matrix = Matrix.parseString(str);
		String output = "[1,000, 2,000, 3,000]\n" + "[4,000, 5,000, 6,000]\n";
		Assert.assertEquals(output, matrix.toString());
	}

	// ------------------exceptions-----------
	@Test(expected = IllegalArgumentException.class)
	public void parseString_IllegalArgumentException_NotDouble() {
		String str = "1 2 3| 4 zr 6 |";
		@SuppressWarnings("unused")
		IMatrix matrix = Matrix.parseString(str);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseString_IllegalArgumentException_NotSameRowsLength() {
		String str = "1 2 3| 4 6 |";
		@SuppressWarnings("unused")
		IMatrix matrix = Matrix.parseString(str);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_IllegalArgumentException_NegativeArguments() {
		@SuppressWarnings("unused")
		IMatrix matrix = new Matrix(-1, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_IllegalArgumentException_BiggerRowsSizeThanRealElementsSize() {
		double[][] elements = { { 1, 2, 3 }, { 4, 5, 6 } };
		@SuppressWarnings("unused")
		IMatrix matrix = new Matrix(2, 4, elements, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor2_IllegalArgumentException_BiggerRowsSizeThanRealElementsSize() {
		double[][] elements = { { 1, 2, 3 }, { 4, 5, 6 } };
		@SuppressWarnings("unused")
		IMatrix matrix = new Matrix(2, 4, elements, false);
	}

}
