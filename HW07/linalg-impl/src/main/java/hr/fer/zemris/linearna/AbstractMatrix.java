package hr.fer.zemris.linearna;

/**
 * Class implements IMatrix interface and offers methods for matrix algebra.
 * Unimplemented methods are copy(), get(), newInstance(), set(), etColsCunt(),
 * getRowsCount()
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	/**
	 * 
	 * @throws IncompatibleOperandException
	 *             if this matrix and other matrix have not same rows and
	 *             columns length
	 */
	@Override
	public IMatrix add(IMatrix other) {
		sameDimensions(other);

		int rows = getRowsCount();
		int cols = getColsCount();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				this.set(row, col, this.get(row, col) + other.get(row, col));
			}
		}
		return this;
	}

	/**
	 * Return matrix determinant. Algorithm used:
	 * http://www.bigdev.de/2013/04/tutorial-determinant-in-java.html
	 * 
	 * @return determinant of given matrix
	 * @throws IncompatibleOperandException
	 *             if matrix is not squared
	 */
	@Override
	public double determinant() throws IncompatibleOperandException {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException(
					"Determinant can be calculated only on squared matrix");
		}
		int rows = getRowsCount();

		if (rows == 1) {
			return get(0, 0);
		}

		if (rows == 2) {
			return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
		}
		double determinant = 0;
		for (int j = 0; j < rows; j++) {
			determinant += Math.pow(-1, j) * get(0, j)
					* subMatrix(0, j, true).determinant();
		}
		return determinant;

	}

	@Override
	public IMatrix makeIdentity() {
		int rows = getRowsCount();
		int cols = getColsCount();
		if (rows != cols) {
			throw new IncompatibleOperandException(
					"Cant make identity on non squared matrix");
		}
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (row == col) {
					this.set(row, col, 1);
				} else {
					this.set(row, col, 0);
				}
			}
		}
		return this;
	}

	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}

	/**
	 * Return cofactor of this matrix. Used for matrix inverzion.
	 * 
	 * @return cofactor of this matrix.
	 */
	private IMatrix nCofactor() {

		if (getColsCount() != getRowsCount()) {
			throw new IncompatibleOperandException("Matrix must be squared");
		}
		int rows = getRowsCount();
		IMatrix cofMatrix = newInstance(rows, rows);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < rows; col++) {
				cofMatrix.set(row, col,
						Math.pow(-1, row + col)
								* subMatrix(row, col, true).determinant());
			}
		}

		return cofMatrix;
	}

	/**
	 * Algorithm used:
	 * http://mrbool.com/how-to-use-java-for-performing-matrix-operations/26800
	 * 
	 * @return inverted matrix
	 */
	@Override
	public IMatrix nInvert() {
		double determinant = determinant();
		if (determinant < 0.00000001) {
			throw new IncompatibleOperandException("Matrix is not invertable.");
		}
		return nCofactor().nTranspose(true).nScalarMultiply(1 / determinant);
	}

	/**
	 * @throws IncompatibleOperandException
	 *             if this matrix columns and other matrix rows are not same
	 *             length
	 */
	@Override
	public IMatrix nMultiply(IMatrix other) {
		if (this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException(
					"This matrix columns must be equal to "
							+ "other matrix rows");
		}
		IMatrix matrix = newInstance(this.getRowsCount(), other.getColsCount());
		int rows = matrix.getRowsCount();
		int cols = matrix.getColsCount();
		int n = this.getColsCount();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				double oldValue = 0;
				for (int i = 0; i < n; i++) {
					oldValue += this.get(row, i) * other.get(i, col);
				}
				matrix.set(row, col, oldValue);
			}
		}
		return matrix;

	}

	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}

	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}

	@Override
	public IMatrix nTranspose(boolean liveView) {
		if (liveView) {
			return new MatrixTransponseView(this);
		} else {
			return new MatrixTransponseView(this).copy();
		}
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		int rows = getRowsCount();
		int cols = getColsCount();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				set(row, col, get(row, col) * value);
			}
		}

		return this;
	}

	/**
	 * @throws IncompatibleOperandException
	 *             if this and other matrix are not same dimensions.
	 */
	@Override
	public IMatrix sub(IMatrix other) {
		sameDimensions(other);
		int rows = getRowsCount();
		int cols = getColsCount();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				this.set(row, col, this.get(row, col) - other.get(row, col));
			}
		}
		return this;
	}

	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		IMatrix usedM;
		if (liveView) {
			usedM = this;
		} else {
			usedM = this.copy();
		}
		return new MatrixSubMatrixView(usedM, row, col);
	}

	@Override
	public double[][] toArray() {
		double[][] array = new double[getRowsCount()][getColsCount()];
		int rows = getRowsCount();
		int cols = getColsCount();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				array[row][col] = get(row, col);
			}
		}
		return array;
	}

	@Override
	public IVector toVector(boolean liveView) {

		IMatrix usedM;
		if (liveView) {
			usedM = this;
		} else {
			usedM = this.copy();
		}
		return new VectorMatrixView(usedM);

	}

	/**
	 * Throws {@link IncompatibleOperandException} if this and other matrix are
	 * not same dimension
	 * 
	 * @param other
	 *            other matrix
	 */
	private void sameDimensions(IMatrix other) {
		if (this.getColsCount() != other.getColsCount()
				|| this.getRowsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException(
					"Matrixs are not same dimensions.");
		}
	}

	/**
	 * Returns true if all this elements are equal to this matrix elements.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Matrix))
			return false;
		IMatrix other = (IMatrix) obj;
		if (getColsCount() != other.getColsCount()
				|| getRowsCount() != other.getRowsCount())
			return false;
		int rowsCount = getRowsCount();
		int colsCount = getColsCount();
		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < colsCount; j++) {
				if (Math.abs(this.get(i, j) - other.get(i, j)) > 0.00000001) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns this matrix as string. Elements are rounded on 3 decimals
	 */
	@Override
	public String toString() {
		return toString(3);
	}

	/**
	 * Returns this matrix as string. Elements are rounded on n decimals
	 * 
	 * @param n
	 *            number of decimals behined decimal point
	 * @return string representation of this matrix
	 */
	public String toString(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(
					"Decimals number is smaller than zero :" + n);

		}

		StringBuilder str = new StringBuilder();
		int rows = getRowsCount();
		int cols = getColsCount();
		for (int i = 0; i < rows; i++) {
			str.append("[");
			for (int j = 0; j < cols; j++) {
				str.append(String.format("%." + n + "f, ", get(i, j)));
			}
			str.delete(str.length() - 2, str.length());
			str.append("]\n");
		}
		return str.toString();
	}
}
