package hr.fer.zemris.linearna;

/**
 * Class provides static factory methods for linear algebra.
 * 
 * @author Borna
 *
 */
public class LinsAlgDefaults {

	/**
	 * Factory method for IMatrix implementation
	 * 
	 * @param rows
	 *            number of rows
	 * @param cols
	 *            number of columns
	 * @return IMatrix with given rows and columns
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}

	/**
	 * Factory method for IVector implementation
	 * 
	 * @param dimension
	 *            vector dimension
	 * @return IVector with given dimension.
	 */
	public static IVector defaultVector(int dimension) {
		return new Vector(dimension);
	}

}
