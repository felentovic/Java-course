package hr.fer.zemris.java.gui.charts;

/**
 * Class represents x and y position in XY coordination system.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class XYValue {
	private int x;
	private int y;

	/**
	 * Constructs xy value with given parametes
	 * 
	 * @param x
	 *            value on X axis
	 * @param y
	 *            value on Y axis
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x value.
	 * 
	 * @return value of x.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y value.
	 * 
	 * @return value of y.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Parses given string into {@link XYValue}.
	 * 
	 * @param str
	 *            xy value in string
	 * @return {@link XYValue} from string
	 * @throws IllegalArgumentException
	 *             if string is not parsable into {@link XYValue}
	 */
	public static XYValue parseString(String str) {
		str = str.replaceAll("\\s+", "");

		String[] xy = str.split(",");
		if (xy.length != 2) {
			throw new IllegalArgumentException("Unparsable to XYValue string: "
					+ str);
		}
		try {
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);
			return new XYValue(x, y);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Unparsable to XYValue string: "
					+ str);
		}
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof XYValue))
			return false;
		XYValue other = (XYValue) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
