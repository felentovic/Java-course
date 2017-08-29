package hr.fer.zemris.linearna;

/**
 * Class provides live view on vector as one column or one row matrix.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class MatrixVectorView extends AbstractMatrix {
	private IVector originalVector;
	private boolean asRowMatrix;
	private int colsCount;
	private int rowsCount;

	/**
	 * Stores given reference on originalVector for liveView.
	 * 
	 * @param originalVector
	 *            reference to original matrix
	 * @param asRowMatrix
	 *            if true given matrix is has one row, otherwise one column
	 */
	public MatrixVectorView(IVector originalVector, boolean asRowMatrix) {
		this.originalVector = originalVector;
		this.asRowMatrix = asRowMatrix;
		if (asRowMatrix) {
			rowsCount = 1;
			colsCount = originalVector.getDimension();
		} else {
			rowsCount = originalVector.getDimension();
			colsCount = 1;
		}
	}

	@Override
	public IMatrix copy() {
		return new MatrixVectorView(originalVector.copy(), asRowMatrix);
	}

	@Override
	public double get(int row, int col) {
		checkIndex(row, col);
		if (asRowMatrix) {
			return originalVector.get(col);
		} else {
			return originalVector.get(row);
		}
	}

	@Override
	public int getColsCount() {
		return colsCount;
	}

	@Override
	public int getRowsCount() {
		return rowsCount;
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		if (rows != 1 && cols != 1) {
			throw new IllegalArgumentException(
					"Columns or rows must be size one");
		}

		return LinsAlgDefaults.defaultMatrix(rows, cols);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		checkIndex(row, col);
		if (asRowMatrix) {
			originalVector.set(col, value);
		} else {
			originalVector.set(row, value);
		}
		return this;
	}

	/**
	 * Throws {@link IllegalArgumentException} if matrix has one row and given
	 * row is bigger than zero or if matrix has one column and given column is
	 * bigger than zero
	 * 
	 * @param row
	 *            given row
	 * @param col
	 *            given column
	 */
	private void checkIndex(int row, int col) {
		if (asRowMatrix && row != 0) {
			throw new IllegalArgumentException("Matrix has only one row.");

		} else if (!asRowMatrix && col != 0) {
			throw new IllegalArgumentException("Matrix has only one column");

		}

	}
}
