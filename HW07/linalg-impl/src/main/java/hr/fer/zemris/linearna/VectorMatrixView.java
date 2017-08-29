package hr.fer.zemris.linearna;

/**
 * Class provides live view on matrix as vector.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class VectorMatrixView extends AbstractVector {
	private IMatrix originalMatrix;
	private int dimension;
	private boolean wasRowMatrix;

	/**
	 * Stores given reference on originalMatrix for liveView and sets flag
	 * wasRowMatrix if matrix have one row, otherwise if matrix have one column
	 * sets false.
	 * 
	 * @param originalMatrix
	 *            reference to original matrix
	 */
	public VectorMatrixView(IMatrix originalMatrix) {
		if (originalMatrix.getColsCount() != 1
				&& originalMatrix.getRowsCount() != 1) {
			throw new UnsupportedOperationException(
					"Cant conver to vector because originalMatrix has"
							+ " not one column or one row.");
		}
		this.originalMatrix = originalMatrix;
		if (originalMatrix.getColsCount() == 1) {
			wasRowMatrix = false;
			dimension = originalMatrix.getRowsCount();
		} else {
			wasRowMatrix = true;
			dimension = originalMatrix.getColsCount();
		}
	}

	@Override
	public IVector copy() {
		return new VectorMatrixView(originalMatrix.copy());
	}

	@Override
	public double get(int index) {

		if (wasRowMatrix) {
			return originalMatrix.get(0, index);
		} else {
			return originalMatrix.get(index, 0);
		}
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public IVector newInstance(int dimension) {
		return LinsAlgDefaults.defaultVector(dimension);

	}

	@Override
	public IVector set(int index, double value)
			throws UnmodifiableObjectException {

		if (wasRowMatrix) {
			originalMatrix.set(0, index, value);
		} else {
			originalMatrix.set(index, 0, value);
		}

		return this;
	}

}
