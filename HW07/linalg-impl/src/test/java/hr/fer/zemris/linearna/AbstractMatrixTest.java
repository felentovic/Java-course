package hr.fer.zemris.linearna;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class AbstractMatrixTest {

	@Test
	public void addTest_True_AddMatrx() {
		double[][] elements = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		double[][] expected = new double[][] { { 2, 4, 6 }, { 8, 10, 12 } };
		IMatrix matrix1 = new Matrix(2, 3, elements, true);
		Assert.assertTrue(matrix1.add(matrix1).equals(
				new Matrix(2, 3, expected, false)));
	}

	@Test
	public void nAddTest_True_OriginalMatrixUnchanged() {
		double[][] elements = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		double[][] expected = new double[][] { { 2, 4, 6 }, { 8, 10, 12 } };
		IMatrix matrix1 = new Matrix(2, 3, elements, false);
		Assert.assertTrue(matrix1.nAdd(matrix1).equals(
				new Matrix(2, 3, expected, false)));
		Assert.assertTrue(matrix1.equals(new Matrix(2, 3, elements, false)));
	}

	@Test
	public void subTest_True_SubMatrix() {
		double[][] expected = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		double[][] elements = new double[][] { { 2, 4, 6 }, { 8, 10, 12 } };
		IMatrix matrix1 = new Matrix(2, 3, elements, true);
		Assert.assertTrue(matrix1.sub(new Matrix(2, 3, expected, true)).equals(
				new Matrix(2, 3, expected, false)));
	}

	@Test
	public void nSubTest_True_OriginalMatrixUnchanged() {
		double[][] expected = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		double[][] elements = new double[][] { { 2, 4, 6 }, { 8, 10, 12 } };
		IMatrix matrix1 = new Matrix(2, 3, elements, false);
		Assert.assertTrue(matrix1.nSub(new Matrix(2, 3, expected, true)).equals(
				new Matrix(2, 3, expected, false)));

		Assert.assertTrue(matrix1.equals(new Matrix(2, 3, elements, false)));
	}

	@Test
	public void determinantTest_True_Calculate() {
		double[][] elements = new double[][] { { 1, 5, -2, 6 }, { 0, 1, 2, 2 },
				{ 0, 0, 1, 3 }, { 0, -1, 1, 1 } };
		double determinant = new Matrix(4, 4, elements, true).determinant();
		Assert.assertEquals(-6, determinant, 0.1);
	}

	@Test
	public void scalarMultiply_True_Multiply() {
		double[][] elements = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		double[][] expected = new double[][] { { 2, 4, 6 }, { 8, 10, 12 } };
		IMatrix matrix1 = new Matrix(2, 3, elements, true);
		Assert.assertTrue(matrix1.scalarMultiply(2).equals(
				new Matrix(2, 3, expected, false)));
	}

	@Test
	public void nScalarMultiply_True_OriginalUnchanged() {
		double[][] elements = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		double[][] expected = new double[][] { { 2, 4, 6 }, { 8, 10, 12 } };
		IMatrix matrix1 = new Matrix(2, 3, elements, true);
		Assert.assertTrue(matrix1.scalarMultiply(2).equals(
				new Matrix(2, 3, expected, false)));
		Assert.assertTrue(matrix1.equals(new Matrix(2, 3, elements, false)));

	}

	@Test
	public void toArray_True_EqualArrays() {
		double[][] elements = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		IMatrix matrix1 = new Matrix(2, 3, elements, false);
		Assert.assertTrue(Arrays.deepEquals(elements, matrix1.toArray()));
	}

	@Test
	public void toVectorTest_True_EqualVector() {
		double[][] elements = new double[][] { { 1, 2, 3, 4 } };
		double[] elems = new double[] { 1, 2, 3, 4 };
		IMatrix matrix = new Matrix(1, 4, elements, true);

		Assert.assertTrue(new Vector(elems).equals(matrix.toVector(false)));
	}

	@Test
	public void makeIdentity_True_Identity() {
		double[][] elements = new double[][] { { 1, 2, }, { 3, 4 } };
		double[][] expected = new double[][] { { 1, 0 }, { 0, 1 } };
		IMatrix matrix = new Matrix(2, 2, elements, true);
		Assert.assertTrue(matrix.makeIdentity().equals(
				new Matrix(2, 2, expected, true)));

	}

	@Test
	public void nInvert_True_Inverz() {

		double[][] elements = new double[][] { { 1, 1, 1, 1 }, { 1, 2, 2, 2 },
				{ 1, 2, 3, 3 }, { 1, 2, 3, 4 } };
		double[][] expected = new double[][] { { 2, -1, 0, 0 },
				{ -1, 2, -1, 0 }, { 0, -1, 2, -1 }, { 0, 0, -1, 1 } };
		IMatrix matrix = new Matrix(4, 4, elements, true);
		Assert.assertTrue(matrix.nInvert().equals(
				new Matrix(4, 4, expected, true)));

	}

	@Test
	public void nMultiply_True_Multiply() {
		double[][] elements = new double[][] { { 1, 0, 2 }, { -1, 3, 1 } };
		double[][] elements2 = new double[][] { { 3, 1, }, { 2, 1, }, { 1, 0 } };
		IMatrix matrix1 = new Matrix(2, 3, elements, true);
		IMatrix matrix2 = new Matrix(3, 2, elements2, true);
		double[][] expected = new double[][] { { 5, 1 }, { 4, 2 } };
		Assert.assertEquals(new Matrix(2, 2, expected, true),
				matrix1.nMultiply(matrix2));
	}

	// -------------------exceptions--------------------

	@Test(expected = IncompatibleOperandException.class)
	public void determinantTest_IncompatibleOperandException_NotSquaredMatrix() {
		double[][] elements = new double[][] { { 3, 1, }, { 2, 1, }, { 1, 0 } };
		IMatrix matrix1 = new Matrix(3, 2, elements, true);
		matrix1.determinant();
	}

	@Test(expected = IncompatibleOperandException.class)
	public void identityTest_IncompatibleOperandException_NotSquaredMatrix() {
		double[][] elements = new double[][] { { 3, 1, }, { 2, 1, }, { 1, 0 } };
		IMatrix matrix1 = new Matrix(3, 2, elements, true);
		matrix1.makeIdentity();
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nInvertTest_IncompatibleOperandException_NotSquaredMatrix() {
		double[][] elements = new double[][] { { 1, 2, 3 }, { 4, 5, 6 },
				{ 5, 7, 9 } };
		IMatrix matrix1 = new Matrix(3, 3, elements, true);
		matrix1.nInvert();
	}
	
	@Test(expected=IncompatibleOperandException.class )
	public void nMultiply_IncompatibleOperandException_NotSameRowsAndColsLength(){
		double[][] elements = new double[][] { { 1, 2, 3 }, { 4, 5, 6 },
				{ 5, 7, 9 } };
		IMatrix matrix1 = new Matrix(3, 2, elements, true);
		double[][] elements2 = new double[][] { { 3, 1, }, { 2, 1, }, { 1, 0 } };
		IMatrix matrix2 = new Matrix(3, 2, elements2, true);
		matrix1.nMultiply(matrix2);
	}
}
