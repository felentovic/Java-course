package hr.fer.zemris.java.gui.charts;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents barchart informations.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class BarChart {
	private List<XYValue> values;
	private String xDesc;
	private String yDesc;
	private int yMin;
	private int yMax;
	private int space;
	private int numberOfValues;

	/**
	 * Constructs new {@link BarChart} from given parameters. If
	 * {@code Ymax - Ymin % space !=0} than yMax is first bigger number that
	 * divided with space gives zero as rest.
	 * 
	 * @param values
	 *            {@link XYValue} as coordinates
	 * @param xDesc
	 *            descrption below X Axis
	 * @param yDesc
	 *            description on left side of Y Axis
	 * @param minY
	 *            first number on Y Axis
	 * @param maxY
	 *            maximum number on Y Axis
	 * @param space
	 *            step between two numbers on Y Axis
	 */
	public BarChart(List<XYValue> values, String xDesc, String yDesc, int minY,
			int maxY, int space) {
		if (maxY <= minY) {
			throw new IllegalArgumentException("MaxYy: " + maxY
					+ " cant be smaller" + "or equal to minY:" + minY);
		}

		if (space < 1) {
			throw new IllegalArgumentException(
					"Space can not be smaller than one.");
		}
		this.values = new LinkedList<XYValue>();
		this.values.addAll(values);
		numberOfValues = values.size();
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		this.yMax = maxY;
		this.yMin = minY;
		if ((maxY - minY) % space == 0) {
			this.space = space;
		} else {
			int y = yMax;
			while (true) {
				y++;
				if ((y - yMin) % space == 0) {
					break;
				}
			}
		}
	}

	/**
	 * Getter for values
	 * 
	 * @return list of values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Getter for description below X Axis
	 * 
	 * @return description below X Axis
	 */
	public String getxDescription() {
		return xDesc;
	}

	/**
	 * Getter for description beside Y Axis
	 * 
	 * @return description beside Y Axis
	 */
	public String getyDescription() {
		return yDesc;
	}

	/**
	 * Getter for minimal y
	 * 
	 * @return minimal y
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Getter for maximal y
	 * 
	 * @return maximal y
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Getter for space between two numbers on Y Axis
	 * 
	 * @return space between two numbers on Y Axis
	 */
	public int getSpace() {
		return space;
	}

	/**
	 * Return number of values
	 * 
	 * @return number of values
	 */
	public int getnumberOfValues() {
		return numberOfValues;
	}
}
