package hr.fer.zemris.linearna;

/**
 * Matrix which is live view of given IMatrix reference. Original matrix columns
 * are this matrix rows, and opposite.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class MatrixTransponseView extends AbstractMatrix {
	private IMatrix originalMatrix;

	/**
	 * Constructor stores given reference for live view.
	 * 
	 * @param originalMatrix
	 *            reference to original matrix
	 */
	public MatrixTransponseView(IMatrix originalMatrix) {
		this.originalMatrix = originalMatrix;

	}

	@Override
	public IMatrix copy() {
		return new MatrixTransponseView(originalMatrix.copy());
	}

	@Override
	public double get(int row, int col) {
		return originalMatrix.get(col, row);
	}

	@Override
	public int getColsCount() {
		return originalMatrix.getRowsCount();
	}

	@Override
	public int getRowsCount() {
		return originalMatrix.getColsCount();
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return originalMatrix.newInstance(rows, cols);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		originalMatrix.set(col, row, value);
		return this;
	}

	@Override
	public double[][] toArray() {
		double[][] array = new double[originalMatrix.getColsCount()][originalMatrix
				.getRowsCount()];
		for (int row = 0; row < originalMatrix.getRowsCount(); row++) {
			for (int col = 0; col < originalMatrix.getColsCount(); col++) {
				array[col][row] = originalMatrix.get(row, col);
			}
		}
		return array;
	}
}
