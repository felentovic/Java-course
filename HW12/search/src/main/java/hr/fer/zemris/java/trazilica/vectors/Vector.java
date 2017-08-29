package hr.fer.zemris.java.trazilica.vectors;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.UnmodifiableObjectException;

/**
 * Vector implementation extends {@link AbstractVector}. Provides basic methods
 * for vector algebra
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class Vector extends AbstractVector {
	
	/**
	 * Elements array
	 */
	private double[] elements;
	/**
	 * Vector dimension
	 */
	private int dimension;
	/**
	 * Readonly flag
	 */
	private boolean readOnly;

	/**
	 * Constructs new vector from given elements array. Vector is not read only.
	 * 
	 * @param components
	 *            elements array
	 */
	public Vector(double... components) {
		this(false, false, components);
	}

	/**
	 * Constucts new vector from given elements array.
	 * 
	 * @param readOnly
	 *            if true this readOnly flag is true, false otherwise
	 * @param useGiven
	 *            if true vector use same reference on elements array, otherwise
	 *            creates new one and copies elements
	 * @param components
	 *            elements array
	 */
	public Vector(boolean readOnly, boolean useGiven, double... components) {
		if (components == null) {
			throw new IllegalArgumentException("Null elements");
		}

		int length = components.length;
		if (useGiven) {
			elements = components;
		} else {
			elements = new double[length];
			System.arraycopy(components, 0, elements, 0, length);

		}
		this.readOnly = readOnly;
		dimension = length;
	}

	/**
	 * Constructs vector with given dimension.
	 * 
	 * @param dimension
	 *            initial dimension
	 */
	public Vector(int dimension) {
		if (dimension < 1) {
			throw new IllegalArgumentException("Given dimension :" + dimension
					+ " is smaller than one.");
		}
		elements = new double[dimension];
		readOnly = false;
		this.dimension = dimension;
	}

	/**
	 * Return this vector with same atributtes but different reference.
	 */
	@Override
	public IVector copy() {
		return copyPart(dimension);
	}

	/**
	 * Returns element on given index.
	 * 
	 * @throws IllegalArgumentException
	 *             if index is smaller than zero or bigger than vetor
	 *             dimension-1
	 */
	@Override
	public double get(int index) {
		checkIndex(index);
		return elements[index];
	}

	/**
	 * Returns vector dimension.
	 */
	@Override
	public int getDimension() {
		return dimension;
	}

	/**
	 * Returns new instance of vector with given dimension.
	 * 
	 * @throws IllegalArgumentException
	 *             if dimesion is smaller than 1
	 */
	@Override
	public IVector newInstance(int dimension) {
		return new Vector(dimension);

	}

	/**
	 * Sets new element value on given index.
	 * 
	 * @throws IllegalArgumentException
	 *             if index is smaller than zero or bigger than vetor
	 *             dimension-1
	 */
	@Override
	public IVector set(int index, double value)
			throws UnmodifiableObjectException {
		if (readOnly) {
			throw new UnmodifiableObjectException("Vector is read only.");
		}
		checkIndex(index);
		elements[index] = value;
		return this;
	}

	/**
	 * Return new vector parsed from string. In string are only vetor elements.
	 * 
	 * @param str
	 *            vector elements
	 * @return new vector
	 * @throws IllegalArgumentException
	 *             if elements can't be parsed into double
	 * @throws IllegalArgumentException
	 *             if string is empty
	 */
	public static Vector parseSimple(String str) {
		if (str == null) {
			throw new IllegalArgumentException("String is null.");
		}

		String[] elems = str.trim().split("\\s+");
		if (elems[0].isEmpty()) {
			throw new IllegalArgumentException("Given empty string.");
		}
		double[] elem = new double[elems.length];

		for (int i = 0; i < elems.length; i++) {
			try {
				elem[i] = Double.parseDouble(elems[i]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Given element cant be parsed into double " + elems[i]);
			}
		}
		return new Vector(elem);
	}
	
	/**
	 * Throws {@link IllegalArgumentException} if index is not valid
	 * @param index given index
	 */
	private void checkIndex(int index) {
		if (index < 0 || index >= dimension) {
			throw new IllegalArgumentException("Invalid index :" + index);
		}
	}
}
