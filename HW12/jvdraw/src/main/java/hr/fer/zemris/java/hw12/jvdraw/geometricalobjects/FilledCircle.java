package hr.fer.zemris.java.hw12.jvdraw.geometricalobjects;

import hr.fer.zemris.java.hw12.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw12.jvdraw.components.JColorArea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class extends {@link Circle} and represents circle filled with given color.
 * 
 * @author Borna
 *
 */
public class FilledCircle extends Circle {
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Circle is filled with this color
	 */
	private Color innerColor;
	/**
	 * Areat that is used for setting new inner color
	 */
	private JColorArea innerArea;

	/**
	 * Constructs new circle with given parameters
	 * 
	 * @param x
	 *            x coordinate of center
	 * @param y
	 *            y coordinate of center
	 * @param radius
	 *            circle radius
	 * @param outerColor
	 *            outer circle color
	 * @param innerColor
	 *            inner circle color
	 * @param name
	 *            circle name
	 */
	public FilledCircle(int x, int y, int radius, Color outerColor,
			Color innerColor, String name) {
		super(x, y, radius, outerColor, name);
		this.innerColor = innerColor;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(innerColor);
		g2d.fillOval(xCenter - radius - offsetX, yCenter - radius - offsetY,
				radius * 2, radius * 2);
		g2d.setColor(outerColor);
		g2d.drawOval(xCenter - radius - offsetX, yCenter - radius - offsetY,
				radius * 2, radius * 2);
		g2d.dispose();
	}

	@Override
	public JPanel getProperty() {
		JPanel panel = new JPanel(new GridLayout(4, 2));

		panel.add(new JLabel("Outer Color:"));
		JColorArea outerArea = new JColorArea(outerColor);
		outerArea.addColorChangeListener(this);
		panel.add(outerArea);

		panel.add(new JLabel("Inner Color:"));
		innerArea = new JColorArea(innerColor);
		innerArea.addColorChangeListener(this);
		panel.add(innerArea);

		panel.add(new JLabel("Center:"));
		panel.add(getCenterPanel());

		panel.add(new JLabel("Radius:"));
		panel.add(getRadiusPanel());
		return panel;
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		if (innerArea.equals(source)) {
			innerColor = newColor;
		} else {
			outerColor = newColor;
		}
	}

	@Override
	public String export() {
		return "F" + super.export() + " " + colorToString(innerColor);
	}

	/**
	 * Returns new FilledCircle parsed from string.
	 * 
	 * @param str
	 *            string that is parsed
	 * @param name
	 *            FilledCircle name
	 * @return new FilledCircle parsed from string
	 * @throws IllegalArgumentException
	 *             if FilledCircle cannot be parsed form string
	 */
	public static GeometricalObject parseFromString(String str, String name) {
		String[] elements = str.trim().split("\\s+");
		if (elements.length != 10) {
			throw new IllegalArgumentException(
					"Invalid number of arguments for filled circle: " + str);
		}

		if (!elements[0].equals("FCIRCLE")) {
			throw new IllegalArgumentException("Not filled circle: " + str);
		}

		FilledCircle circle = null;
		try {
			int xCenter = Integer.parseInt(elements[1]);
			int yCenter = Integer.parseInt(elements[2]);
			int radius = Integer.parseInt(elements[3]);

			int r1 = Integer.parseInt(elements[4]);
			int g1 = Integer.parseInt(elements[5]);
			int b1 = Integer.parseInt(elements[6]);

			int r2 = Integer.parseInt(elements[7]);
			int g2 = Integer.parseInt(elements[8]);
			int b2 = Integer.parseInt(elements[9]);
			Color outer = new Color(r1, g1, b1);
			Color inner = new Color(r2, g2, b2);
			circle = new FilledCircle(xCenter, yCenter, radius, outer, inner,
					name);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Not integer numbers: " + str);
		}
		return circle;
	}
}
