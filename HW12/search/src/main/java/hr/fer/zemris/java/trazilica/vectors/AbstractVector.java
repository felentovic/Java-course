package hr.fer.zemris.java.trazilica.vectors;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;

/**
 * Class implements IVector interface and offers methods for vectors algebra.
 * Unimplemented methods are copy(), get(), newInstance(), set(), getDimension()
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public abstract class AbstractVector implements IVector {

	/**
	 * Adds given vector to this vector, and returns this vector.
	 * 
	 * @param other
	 *            vector that is added to this vector
	 * @return this vector
	 * @throws IncompatibleOperandException
	 *             if vectors are not same dimension.
	 */
	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		sameDimensions(other);
		for (int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}

	/**
	 * Creates new vector with given dimension. If dimension is smaller than
	 * this dimension elements are copied till dimension, otherwise elements are
	 * zero.
	 * 
	 * @param dimension
	 *            dimension of new vector
	 * @return new vector with given dimension and copied elements
	 * @throws IllegalArgumentException
	 *             if dimension is smaller than zero
	 */
	@Override
	public IVector copyPart(int dimension) {
		IVector vector = newInstance(dimension);
		for (int i = 0; i < dimension; i++) {
			if (i < this.getDimension()) {
				vector.set(i, this.get(i));
			} else {
				vector.set(i, 0);
			}
		}
		return vector;
	}

	/**
	 * Returns cosine from given vector and this vector.
	 * 
	 * @param other
	 *            other vector
	 * @return cosine from given vector and this vector.
	 * @throws IncompatibleOperandException
	 *             if vectors are not same dimension
	 */
	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		sameDimensions(other);

		double thisMagnitude = this.norm();
		double otherMagnitude = other.norm();
		if (thisMagnitude == 0 || otherMagnitude == 0) {
			throw new IncompatibleOperandException(
					"This or other vector norm is zero");
		}
		return this.scalarProduct(other) / (thisMagnitude * otherMagnitude);
	}

	/**
	 * Addes given vector to this vector and returns new vector. This vector
	 * stayes unchanged.
	 * 
	 * @param other
	 *            other vector
	 * @return new vector
	 * @throws IncompatibleOperandException
	 *             if vectors are not same dimension
	 */
	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		return this.copy().add(other);

	}

	/**
	 * Returns new homogenus vector with new dimension= this dimension -1. All
	 * elements are divided with the last element from this vector.
	 * 
	 * @return new vector
	 */
	@Override
	public IVector nFromHomogeneus() {
		if (this.getDimension() == 1) {
			throw new IncompatibleOperandException("Elements dimension is one.");
		}
		if (this.get(getDimension() - 1) == 0) {
			throw new IncompatibleOperandException(
					"Last element of vector is zero");
		}
		IVector vector = newInstance(this.getDimension() - 1);
		double lastElement = this.get(this.getDimension() - 1);

		for (int i = 0; i < vector.getDimension(); i++) {
			vector.set(i, this.get(i) / lastElement);
		}

		return vector;
	}

	/**
	 * Normalizes this vector to unit vector and returns new vector. This vector
	 * stays unchanged.
	 * 
	 * @return new vector
	 * @throws UnsupportedOperationException
	 *             if vectors norm is zero
	 */
	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}

	/**
	 * Multiplies each element of this vector with given scalar and returns new
	 * vector. This vector stays unchanged.
	 * 
	 * @param byValue
	 *            scalar
	 * @return this vector
	 */
	@Override
	public IVector nScalarMultiply(double byValue) {
		return this.copy().scalarMultiply(byValue);
	}

	/**
	 * Subs this vector and other vector. Returns new vector. This vector stays
	 * unchanged.
	 * 
	 * @param other
	 *            other vector
	 * @return new vector
	 * @throws IncompatibleOperandException
	 *             if vectors are not same dimension
	 */
	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		return this.copy().sub(other);
	}

	/**
	 * Returns new vector which is result of vector product on this vector and
	 * given vector.
	 * 
	 * @param other
	 *            other vector
	 * @return new vector
	 * @throws IncompatibleOperandException
	 *             if this or other vector are not three-dimensional
	 */
	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		sameDimensions(other);

		if (this.getDimension() != 3) {
			throw new IncompatibleOperandException(
					"Vectors are not three-dimensional.");
		}

		IVector vector = newInstance(3);
		double this1 = this.get(0);
		double this2 = this.get(1);
		double this3 = this.get(2);

		double other1 = other.get(0);
		double other2 = other.get(1);
		double other3 = other.get(2);
		vector.set(0, this2 * other3 - this3 * other2);
		vector.set(1, -this1 * other3 + this3 * other1);
		vector.set(2, this1 * other2 - this2 * other1);

		return vector;
	}

	/**
	 * Returns vectors norm.
	 * 
	 * @return vectors norm
	 */
	@Override
	public double norm() {
		double norm = 0;
		for (int i = 0; i < this.getDimension(); i++) {
			norm += Math.pow(this.get(i), 2);
		}
		return Math.sqrt(norm);
	}

	/**
	 * Normalizes this vector to unit vector.
	 * 
	 * @return this vector
	 * @throws UnsupportedOperationException
	 *             if vectors norm is zero
	 */
	@Override
	public IVector normalize() {
		double norm = this.norm();
		if (norm == 0) {
			throw new IncompatibleOperandException(
					"You cant normalize vector whose norm is zero.");
		}
		for (int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) / norm);
		}

		return this;
	}

	/**
	 * Multiplies each element of this vector with given scalar and returns this
	 * vector.
	 * 
	 * @param byValue
	 *            scalar
	 * @return this vector
	 */
	@Override
	public IVector scalarMultiply(double byValue) {
		for (int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) * byValue);
		}
		return this;
	}

	/**
	 * Multiplies elements of this vector with given vector. First this element
	 * * first other element..
	 * 
	 * @param other
	 *            other vector
	 * @return scalar product of vectors
	 * @throws IncompatibleOperandException
	 *             if vectors are not same dimension
	 */
	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		sameDimensions(other);

		double scalar = 0;
		for (int i = 0, dimension = this.getDimension(); i < dimension; i++) {
			scalar += this.get(i) * other.get(i);
		}

		return scalar;
	}

	/**
	 * Subs this vector and other vector. Returns this vector.
	 * 
	 * @param other
	 *            other vector
	 * @return this vector
	 * @throws IncompatibleOperandException
	 *             if vectors are not same dimension
	 */
	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		sameDimensions(other);

		for (int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}

	/**
	 * Returns elements of vector in array
	 * 
	 * @return array of vector elements.
	 */
	@Override
	public double[] toArray() {
		double[] array = new double[this.getDimension()];
		for (int i = 0; i < getDimension(); i++) {
			array[i] = this.get(i);
		}

		return array;
	}

	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns string representation of vector.
	 */
	@Override
	public String toString() {
		return toString(3);
	}

	/**
	 * Returns string representation of vector.
	 * 
	 * @param n
	 *            number of element decimals
	 * @return string representation of vector
	 */
	public String toString(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(
					"Decimals number is smaller than zero :" + n);
		}
		StringBuilder str = new StringBuilder();
		str.append("(");
		for (int i = 0; i < this.getDimension(); i++) {
			str.append(String.format("%." + n + "f,", this.get(i)));
		}
		str.deleteCharAt(str.length() - 1);
		str.append(")");
		return str.toString();
	}

	/**
	 * Throws {@link IncompatibleOperandException} if this vector and other
	 * vector are not same dimension.
	 * 
	 * @param other
	 *            other vector
	 */
	private void sameDimensions(IVector other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector is null");
		}
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Vectors are not same dimension.");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IVector))
			return false;
		IVector other = (IVector) obj;
		if (getDimension() != other.getDimension()) {
			return false;
		}
		int dimension = getDimension();

		for (int i = 0; i < dimension; i++) {
			if (Math.abs(this.get(i) - other.get(i)) > 0.00000001) {
				return false;
			}
		}

		return true;
	}
}
