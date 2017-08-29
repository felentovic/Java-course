package hr.fer.zemris.linearna;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class AbstractVectorTest {
	@Test
	public void addTest_True_AddVector() {
		double[] elements = new double[] { 1, 2, 3 };
		double[] expected = new double[] { 2, 4, 6 };
		IVector vector = new Vector(elements);
		Assert.assertEquals(new Vector(expected), vector.add(vector));
	}

	@Test
	public void nAddTest_True_OriginalVectorUnchanged() {
		double[] elements = new double[] { 1, 2, 3 };
		double[] expected = new double[] { 2, 4, 6 };
		IVector vector = new Vector(elements);
		Assert.assertEquals(new Vector(expected), vector.nAdd(vector));
		Assert.assertTrue(vector.equals(new Vector(elements)));
	}

	@Test
	public void subTest_True_SubVector() {
		double[] expected = new double[] { 1, 2, 3 };
		double[] elements = new double[] { 2, 4, 6 };
		IVector vector = new Vector(elements);
		IVector vector2 = new Vector(expected);
		Assert.assertEquals(vector2, vector.sub(vector2));
	}

	@Test
	public void nSubTest_True_OriginalVectorUnchanged() {
		double[] expected = new double[] { 1, 2, 3 };
		double[] elements = new double[] { 2, 4, 6 };
		IVector vector = new Vector(elements);
		IVector vector2 = new Vector(expected);
		Assert.assertEquals(vector2, vector.nSub(vector2));
		Assert.assertTrue(vector.equals(new Vector(elements)));

	}

	@Test
	public void copyPart_True_CopiedValue() {
		double[] elements = new double[] { 2, 4, 6 };
		IVector vector = new Vector(elements);
		IVector vector2 = vector.copyPart(5);
		double[] expected = { 2, 4, 6, 0, 0 };
		Assert.assertEquals(new Vector(expected), vector2);
	}

	@Test
	public void cosine_True_Value() {
		double[] elements = new double[] { 1, 2, 3 };
		IVector vector = new Vector(elements);
		double[] elements2 = new double[] { 4, 5, 6 };
		IVector vector2 = new Vector(elements2);

		Assert.assertEquals(0.9746, vector.cosine(vector2), 0.0001);
	}

	@Test
	public void nFromHomogenus_True_Value() {
		double[] elements = new double[] { 1, 2, 3, 2 };
		IVector vector = new Vector(elements);
		double[] expected = new double[] { 0.5, 1, 1.5 };

		Assert.assertEquals(vector.nFromHomogeneus(), new Vector(expected));
	}

	@Test
	public void normalize_True_UnitVector() {
		double[] elements = { 3, 3, 3 };
		double[] expected = { 0.57735026, 0.57735026, 0.57735026 };
		Assert.assertEquals(new Vector(elements).normalize(), new Vector(
				expected));
	}

	@Test
	public void nNormalize_True_UnitVector() {
		double[] elements = { 3, 3, 3 };
		double[] expected = { 0.57735026, 0.57735026, 0.57735026 };
		IVector vector = new Vector(elements);
		Assert.assertEquals(vector.nNormalize(), new Vector(expected));
		Assert.assertEquals(new Vector(elements), vector);
	}

	@Test
	public void scalarMultiply_True_Value() {
		double[] elements = new double[] { 1, 2, 3 };
		IVector vector = new Vector(elements);
		double[] elements2 = new double[] { 2, 4, 6 };

		Assert.assertEquals(vector.scalarMultiply(2), new Vector(elements2));
	}

	@Test
	public void scalar_True_Value() {
		double[] elements = new double[] { 1, 2, 3 };
		IVector vector = new Vector(elements);

		Assert.assertEquals(vector.scalarProduct(vector), 14, 00001);
	}

	@Test
	public void toArray_True_Value() {
		double[] elements = new double[] { 1, 2, 3 };
		IVector vector = new Vector(elements);
		double[] elements2 = new double[] { 1, 2, 3 };

		Assert.assertTrue(Arrays.equals(vector.toArray(), elements2));
	}

	@Test
	public void toRowMatrix_True_RowMatrix() {
		double[] elements = new double[] { 1, 2, 3 };
		IVector vector = new Vector(elements);
		double[][] elements2 = new double[][] { { 1, 2, 3 } };

		Assert.assertEquals(vector.toRowMatrix(false), new Matrix(1, 3,
				elements2, true));
	}

	@Test
	public void toColumnMatrix_True_RowMatrix() {
		double[] elements = new double[] { 1, 2, 3 };
		IVector vector = new Vector(elements);
		double[][] elements2 = new double[][] { { 1 }, { 2 }, { 3 } };

		Assert.assertEquals(vector.toColumnMatrix(false), new Matrix(3, 1,
				elements2, true));
	}

	@Test
	public void nVectorPorduct_True_Value() {
		double[] elements = new double[] { 3, -3, 1 };
		IVector vector = new Vector(elements);
		double[] elements2 = new double[] { 4, 9, 2 };
		double[] expected = { -15, -2, 39 };
		Assert.assertEquals(vector.nVectorProduct(new Vector(elements2)),
				new Vector(expected));
	}

	@Test
	public void toString_True_Output() {
		double[] elements = new double[] { 3, -3, 1 };
		IVector vector = new Vector(elements);
		String output = "(3.000,-3.000,1.000)";
		Assert.assertEquals(output, vector.toString());
	}

	// --------------------exceptions--------------
	@Test(expected = IncompatibleOperandException.class)
	public void cosine_IncompatibleOperandException_Norm0() {
		double[] elements = new double[] { 1, 2, 3 };
		IVector vector = new Vector(elements);
		double[] elements2 = new double[] { 0, 0, 0 };
		IVector vector2 = new Vector(elements2);
		vector.cosine(vector2);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nFromGomogenus_IncompatibleOperandException_Dimension1() {
		double[] elements = { 3 };
		IVector vector = new Vector(elements);
		vector.nFromHomogeneus();
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nFromGomogenus_IncompatibleOperandException_LastElement0() {
		double[] elements = { 3, 0 };
		IVector vector = new Vector(elements);
		vector.nFromHomogeneus();
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nVectorPorduct_IncompatibleOperandException_VectorsDimensionNot3() {
		double[] elements = new double[] { 3, -3, 1 };
		IVector vector = new Vector(elements);
		double[] elements2 = new double[] { 4, 9, 2, 5 };
		vector.nVectorProduct(new Vector(elements2));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void normalize_IncompatibleOperandException_NormIsZero() {
		double[] elements = { 0, 0, 0 };
		double[] expected = { 0.57735026, 0.57735026, 0.57735026 };
		Assert.assertEquals(new Vector(elements).normalize(), new Vector(
				expected));
	}

	@Test
	public void toString_IllegalArgumentException_NegativeDecimals() {
		double[] elements = new double[] { 3, -3, 1 };
		Vector vector = new Vector(elements);
		vector.toString(-1);
	}
}
