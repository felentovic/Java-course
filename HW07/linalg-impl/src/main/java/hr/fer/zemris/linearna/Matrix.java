package hr.fer.zemris.linearna;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class extendes {@link AbstractMatrix} and implements unimplemented methods.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class Matrix extends AbstractMatrix {
	private double[][] elements;
	private int rows;
	private int cols;

	/**
	 * Constructs empty matrix with given number of rows and columns
	 * 
	 * @param rows
	 *            number of rows
	 * @param columns
	 *            number of columns
	 * @throws IllegalArgumentException
	 *             if rows or columns are smaller than one
	 */
	public Matrix(int rows, int columns) {
		this(rows, columns, getArrayRowsCols(rows, columns), true);

	}

	/**
	 * Constructs matrix from given array. If flag use given is true this
	 * elements and elements share same reference.
	 * 
	 * @param rows
	 *            number of used rows
	 * @param cols
	 *            number of used columns
	 * @param elements2
	 *            elements array
	 * @param useGiven
	 *            if true this elemenents and elements share same reference,
	 *            otherwise array is copied
	 */
	public Matrix(int rows, int cols, double[][] elements2, boolean useGiven) {
		checkRowsCols(rows, cols);
		this.rows = rows;
		this.cols = cols;
		checkArray(rows, cols, elements2);
		if (useGiven) {
			this.elements = elements2;
		} else {
			this.elements = new double[rows][cols];
			for (int i = 0; i < rows; i++) {
				elements[i] = Arrays.copyOf(elements2[i], cols);
			}

		}
	}

	/**
	 * Trows {@link IllegalArgumentException} if rows length or columns length
	 * is bigger than columns and rows lengtth in elements2 array.
	 * 
	 * @param rows2
	 *            used rows length
	 * @param cols2
	 *            used cols length
	 * @param elements2
	 *            elements array
	 */
	private void checkArray(int rows2, int cols2, double[][] elements2) {
		if (rows2 > elements2.length) {
			throw new IllegalArgumentException("Given array of elements has"
					+ " smaller number of elements than given in constructor.");
		}
		for (int i = 0; i < rows2; i++) {
			if (cols2 > elements2[i].length) {
				throw new IllegalArgumentException(
						"Given array of elements has"
								+ " smaller number of elements than given in constructor.");
			}
		}

	}

	@Override
	public IMatrix copy() {
		IMatrix matrix = newInstance(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix.set(i, j, elements[i][j]);
			}
		}
		return matrix;
	}

	/**
	 * @throws IllegalArgumentException
	 *             if row or columns are smaller than 0 or bigger than length-1
	 */
	@Override
	public double get(int row, int col) {
		checkIndex(row, col);
		return elements[row][col];
	}

	@Override
	public int getColsCount() {
		return cols;
	}

	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new Matrix(rows, cols);
	}

	/**
	 * @throws IllegalArgumentException
	 *             if row or columns are smaller than 0 or bigger than length-1
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		checkIndex(row, col);
		elements[row][col] = value;
		return this;
	}

	/**
	 * Parse string into new matrix and returns it. i.e. 1 2 3 | 3 4 5
	 * 
	 * @param str
	 *            string that is parsed
	 * @return new matrix from string
	 * @throws IllegalArgumentException
	 *             if element can not be parsed into double
	 * @throws IllegalArgumentException
	 *             if columns are not same length
	 */
	public static Matrix parseString(String str) {
		String[] rows = str.split("\\|");
		LinkedList<List<Double>> elem = new LinkedList<>();

		for (int row = 0; row < rows.length; row++) {
			String[] cols = rows[row].trim().split(" ");
			List<Double> list = new LinkedList<Double>();

			for (int col = 0; col < cols.length; col++) {
				try {
					list.add(Double.parseDouble(cols[col]));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"Can't parse into double.");
				}
			}

			if (row != 0) {
				if (elem.getLast().size() != list.size()) {
					throw new IllegalArgumentException(
							"Columns are not same length");
				}
			}
			elem.add(list);
		}
		double[][] array = new double[elem.size()][elem.getFirst().size()];
		int i = 0, j = 0;
		for (List<Double> outer : elem) {
			j = 0;
			for (double number : outer) {
				array[i][j] = number;
				j++;
			}
			i++;
		}

		return new Matrix(i, j, array, true);
	}

	/**
	 * Throws {@link IllegalArgumentException} if given row or colums is smaller
	 * than 0 or bigger than length-1
	 * 
	 * @param row
	 *            given row
	 * @param col
	 *            given column
	 */
	private void checkIndex(int row, int col) {
		if (row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new IllegalArgumentException("Given row:" + row + " or col:"
					+ col + " are smaller"
					+ "than zero or equal, bigger than matrix dimension.");
		}
	}

	/**
	 * Method returns double[][] array from given rows and columns length.
	 * 
	 * @param rows
	 *            rows length
	 * @param columns
	 *            columns length
	 * @return double array
	 * @throws IllegalArgumentException
	 *             if rows or columns are smaller than 1
	 */
	private static double[][] getArrayRowsCols(int rows, int columns) {
		checkRowsCols(rows, columns);
		return new double[rows][columns];
	}

	/**
	 * Throws {@link IllegalArgumentException} if rows or columns are smaller
	 * than one
	 * 
	 * @param rows
	 *            rows length
	 * @param columns
	 *            columns length
	 */
	private static void checkRowsCols(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new IllegalArgumentException(
					"Rows and columns cant be smaller than 1.");
		}

	}
}
