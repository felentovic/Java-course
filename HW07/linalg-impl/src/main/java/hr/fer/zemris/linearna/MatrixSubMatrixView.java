package hr.fer.zemris.linearna;

/**
 * Class is used to have live view on original matrix that is provided through
 * constructor wtih given column and row escaped.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix {
	private IMatrix originalMatrix;
	private int[] rows;
	private int[] cols;
	private int rowsCount;
	private int colsCount;

	/**
	 * Constructs live view on original matrix with given row and column
	 * escaped.
	 * 
	 * @param originalMatrix
	 *            original matrix
	 * @param row
	 *            escaped row
	 * @param col
	 *            escaped column
	 */
	public MatrixSubMatrixView(IMatrix originalMatrix, int row, int col) {
		this(originalMatrix, getArray(originalMatrix.getRowsCount(), row),
				getArray(originalMatrix.getColsCount(), col));
	}

	/**
	 * Constructs live view on original matrix with rows that are real used
	 * indexes and cols.
	 * 
	 * @param originalMatrix
	 *            original matrix
	 * @param rows
	 *            original used rows indexs
	 * @param cols
	 *            original used columns indexs
	 */
	private MatrixSubMatrixView(IMatrix originalMatrix, int[] rows, int[] cols) {
		this.originalMatrix = originalMatrix;
		this.rows = rows;
		this.cols = cols;
		rowsCount = rows.length;
		colsCount = cols.length;
	}

	@Override
	public IMatrix copy() {
		return new MatrixSubMatrixView(getCopiedViewMatrix(), rows, cols);
	}

	/**
	 * @throws IllegalArgumentException
	 *             if row or column are smaller than 0 or bigger than length -1
	 */
	@Override
	public double get(int row, int col) {
		checkIndex(row, col);

		return originalMatrix.get(rows[row], cols[col]);
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
		return originalMatrix.newInstance(rows, cols);
	}

	/**
	 * @throws IllegalArgumentException
	 *             if row or column are smaller than 0 or bigger than length -1
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		checkIndex(row, col);
		originalMatrix.set(rows[row], cols[col], value);
		return this;
	}

	public IMatrix subMatrix(int row, int col, boolean liveView) {
		int[] newRows = getArray(rows, row);
		int[] newCols = getArray(cols, col);
		IMatrix usedM;
		if (liveView) {
			usedM = originalMatrix;
		} else {
			usedM = originalMatrix.copy();
		}
		return new MatrixSubMatrixView(usedM, newRows, newCols);

	}

	/**
	 * Throws {@link IllegalArgumentException} if row or column are smaller than
	 * 0 or bigger than length -1
	 * 
	 * @param row
	 *            given row index
	 * @param col
	 *            given column index
	 */
	private void checkIndex(int row, int col) {
		if (col < 0 || row < 0 || col >= cols.length || row >= rows.length) {
			throw new IllegalArgumentException(
					"Given row and col are smaller than zero or"
							+ "bigger than originalMatrix dimension. Given row: "
							+ row + " Given col: " + col);
		}
	}

	/**
	 * Returns array of indexes that are real used in original matrix
	 * 
	 * @param originalLength
	 *            original length of rows or columns
	 * @param escaped
	 *            escaped row or column
	 * @return array of indexes that are real used in original matrix
	 */
	private static int[] getArray(int originalLength, int escaped) {
		if (escaped < 0 || escaped >= originalLength) {
			throw new IllegalArgumentException(
					"Given row or column are smaller than zero or"
							+ "bigger than originalMatrix dimension.");
		}
		int[] array = new int[originalLength - 1];
		for (int i = 0, j = 0; i < originalLength; i++) {
			if (escaped != i) {
				array[j] = i;
				j++;
			}
		}
		return array;
	}

	/**
	 * Returns array of real used indexes; escapes index from given array.
	 * 
	 * @param existingArray
	 *            existing array of indexes
	 * @param escaped
	 *            escaped array
	 * @return array of used indexes
	 */
	private int[] getArray(int[] existingArray, int escaped) {
		if (escaped < 0 || escaped >= existingArray.length) {
			throw new IllegalArgumentException(
					"Given row or column are smaller than zero or"
							+ "bigger than matrix dimension.");
		}
		int[] array = new int[existingArray.length - 1];
		for (int i = 0, j = 0; i < existingArray.length; i++) {
			if (escaped != i) {
				array[j] = existingArray[i];
				j++;
			}
		}
		return array;
	}

	/**
	 * Returns copied elements from matrix that are real used in this view.
	 * 
	 * @return real used matrix
	 */
	private IMatrix getCopiedViewMatrix() {
		IMatrix matrix = originalMatrix.newInstance(rowsCount, colsCount);
		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < colsCount; j++) {
				matrix.set(i, j, get(i, j));
			}
		}

		return matrix;
	}

}
