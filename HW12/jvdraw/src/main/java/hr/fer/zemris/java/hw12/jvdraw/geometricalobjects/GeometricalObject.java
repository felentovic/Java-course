package hr.fer.zemris.java.hw12.jvdraw.geometricalobjects;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Interface represents geometrical object intended for painting on
 * {@link Graphics}
 * 
 * @author Borna
 *
 */
public interface GeometricalObject {

	/**
	 * Returns JPanel with object properties and buttons for setting new one.
	 * 
	 * @return JPanel with object properties.
	 */
	public JPanel getProperty();

	/**
	 * Paint component on graphics
	 * 
	 * @param g
	 *            given graphics
	 */
	public void paint(Graphics g);

	/**
	 * Geometrical object sets new parameters depending on mouse position
	 * 
	 * @param x
	 *            x coordinate of mouse
	 * @param y
	 *            y coordinate of mouse
	 */
	public void mouseMoved(int x, int y);

	/**
	 * Sets offset for painting on {@link Graphics} used for exporting as image
	 * 
	 * @param x
	 *            initial x coordinate for painting
	 * @param y
	 *            initial y coordinate for painting
	 */
	public void setOffset(int x, int y);

	/**
	 * Returns object as string for exporting in file
	 * 
	 * @return object as string
	 */
	public String export();

	/**
	 * Returns maximum x coordinate
	 * 
	 * @return maximum x coordinate
	 */
	public int getMaxX();

	/**
	 * Returns maximum y coordinate
	 * 
	 * @return maximum y coordinate
	 */
	public int getMaxY();

	/**
	 * Returns minimum x coordinate
	 * 
	 * @return minimum x coordinate
	 */
	public int getMinX();

	/**
	 * Returns minimum y coordinate
	 * 
	 * @return minimum y coordinate
	 */
	public int getMinY();

	/**
	 * Return color as string with R G B components
	 * 
	 * @param color
	 *            given color
	 * @return color as string
	 */
	default String colorToString(Color color) {
		return color.getRed() + " " + color.getGreen() + " " + color.getBlue();
	}
}
