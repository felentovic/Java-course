package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;

/**
 * Class used for constraints in {@link CalcLayout} manager. Defines
 * {@link Component} position in parent {@link Container}
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class RCPosition {
	private int row;
	private int col;

	/**
	 * Constructs new {@link RCPosition} with given parameters.
	 * 
	 * @param row
	 *            given row
	 * @param col
	 *            given column
	 * @throws IllegalArgumentException
	 *             if row or column is smaller than one.
	 */
	public RCPosition(int row, int col) {
		if (row < 1 || col < 1) {
			throw new IllegalArgumentException("Row:" + row + " or col:" + col
					+ " is smaller than one ");
		}
		this.row = row;
		this.col = col;
	}

	/**
	 * Static method used for parsing string into {@link RCPosition}. example
	 * {@code"1,1"}
	 * 
	 * @param str
	 *            given string for parsing
	 * @return {@link RCPosition} from string
	 * @throws IllegalArgumentException
	 *             if string can not be parsed into {@link RCPosition}
	 */
	public static RCPosition parseFromString(String str) {
		String[] position = str.replaceAll("\\s+", "").split(",");
		if (position.length != 2) {
			throw new IllegalArgumentException(
					"RCPosition must have 2 arguments. Rown and"
							+ "column index.");
		}
		int row;
		int col;
		try {
			row = Integer.parseInt(position[0]);
			col = Integer.parseInt(position[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"String can not be parsed into RCPosition: " + str);
		}

		return new RCPosition(row, col);
	}
	
	/**
	 * Getter for row
	 * @return row value
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Getter for column
	 * @return column value
	 */
	public int getCol() {
		return col;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + row + "," + col + ")";
	}

}
