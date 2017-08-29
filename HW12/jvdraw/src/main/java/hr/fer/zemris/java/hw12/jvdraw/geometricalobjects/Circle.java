package hr.fer.zemris.java.hw12.jvdraw.geometricalobjects;

import hr.fer.zemris.java.hw12.jvdraw.components.ColorChangeListener;
import hr.fer.zemris.java.hw12.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw12.jvdraw.components.JColorArea;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class extends {@link JComponent} and implements {@link GeometricalObject} and
 * {@link ColorChangeListener}. Represents circle component.
 * 
 * @author Borna
 *
 */
public class Circle extends JComponent implements GeometricalObject,
		ColorChangeListener {
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * X coordinate of center
	 */
	protected Integer xCenter;
	/**
	 * Y coordinate of center
	 */
	protected Integer yCenter;
	/**
	 * Circle radius
	 */
	protected Integer radius;
	/**
	 * Outer circle color
	 */
	protected Color outerColor;
	/**
	 * Circle name
	 */
	private String name;
	/**
	 * Offset from where x center coordinate is painted
	 */
	protected int offsetX;
	/**
	 * Offest from where y center coordinate is painted
	 */
	protected int offsetY;

	/**
	 * Constructs new circle with given parameters
	 * 
	 * @param x
	 *            x coordinate of center
	 * @param y
	 *            y coordinate of center
	 * @param radius
	 *            circle radius
	 * @param color
	 *            outer circle color
	 * @param name
	 *            circle name
	 */
	public Circle(int x, int y, int radius, Color color, String name) {
		super();
		this.xCenter = x;
		this.yCenter = y;
		this.radius = radius;
		this.outerColor = color;
		this.name = name;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(outerColor);
		g2d.drawOval(xCenter - radius - offsetX, yCenter - radius - offsetY,
				radius * 2, radius * 2);
		g2d.dispose();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		outerColor = newColor;

	}

	@Override
	public JPanel getProperty() {
		JPanel panel = new JPanel(new GridLayout(3, 2));

		panel.add(new JLabel("Color:"));
		JColorArea area = new JColorArea(outerColor);
		area.addColorChangeListener(this);
		panel.add(area);

		panel.add(new JLabel("Center:"));
		panel.add(getCenterPanel());

		panel.add(new JLabel("Radius:"));
		panel.add(getRadiusPanel());
		return panel;
	}

	/**
	 * Returns radius panel with button to set circle radius.
	 * 
	 * @return radius panel with button to set circle radius
	 */
	protected JPanel getRadiusPanel() {
		JPanel radiusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		JTextField radiusField = new JTextField(radius.toString(), 5);
		radiusField.setPreferredSize(radiusField.getPreferredSize());
		radiusPanel.add(radiusField);
		JButton setStart = new JButton("Set");
		setStart.addActionListener((e) -> {
			int radiusSet;
			try {
				radiusSet = Integer.parseInt(radiusField.getText());
			} catch (NumberFormatException e1) {
				JOptionPane.showConfirmDialog(null, "Invalid input!", "Error",
						JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
				radiusField.setText(radius.toString());
				return;
			}
			if (radiusSet < 0) {
				JOptionPane.showConfirmDialog(null,
						"Radius smaller than zero!", "Error",
						JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
				radiusField.setText(radius.toString());
				return;
			}
			repaint();
			radius = radiusSet;

		});
		radiusPanel.add(setStart);
		return radiusPanel;
	}

	/**
	 * Returns center panel with button to set circle center x and y coordinate.
	 * 
	 * @return center panel with button to set circle center x and y coordinate.
	 */
	protected JPanel getCenterPanel() {
		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(new JLabel("X:"));
		JTextField endFieldX = new JTextField(xCenter.toString(), 5);
		centerPanel.add(endFieldX);
		centerPanel.add(new JLabel("Y:"));
		JTextField endFieldY = new JTextField(yCenter.toString(), 5);
		centerPanel.add(endFieldY);
		JButton setStart = new JButton("Set");
		setStart.addActionListener((e) -> {
			int xSet;
			int ySet;
			try {
				xSet = Integer.parseInt(endFieldX.getText());
				ySet = Integer.parseInt(endFieldY.getText());
			} catch (NumberFormatException e1) {
				JOptionPane.showConfirmDialog(null, "Invalid input", "Error",
						JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
				endFieldX.setText(xCenter.toString());
				endFieldY.setText(yCenter.toString());
				return;
			}
			repaint();
			xCenter = xSet;
			yCenter = ySet;

		});
		centerPanel.add(setStart);
		return centerPanel;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void mouseMoved(int x, int y) {
		radius = (int) Math.round(Math.sqrt(Math.pow((x - xCenter), 2)
				+ Math.pow(y - yCenter, 2)));
	}

	@Override
	public String export() {
		return "CIRCLE " + xCenter + " " + yCenter + " " + radius + " "
				+ colorToString(outerColor);
	}

	/**
	 * Returns new circle parsed from string.
	 * 
	 * @param str
	 *            string that is parsed
	 * @param name
	 *            circle name
	 * @return new circle parsed from string
	 * @throws IllegalArgumentException
	 *             if circle cannot be parsed form string
	 */
	public static GeometricalObject parseFromString(String str, String name) {
		String[] elements = str.trim().split("\\s+");
		if (elements.length != 7) {
			throw new IllegalArgumentException(
					"Invalid number of arguments for circle: " + str);
		}
		if (!elements[0].equals("CIRCLE")) {
			throw new IllegalArgumentException("Not circle: " + str);
		}
		Circle circle = null;
		try {
			int xCenter = Integer.parseInt(elements[1]);
			int yCenter = Integer.parseInt(elements[2]);
			int radius = Integer.parseInt(elements[3]);

			int r = Integer.parseInt(elements[4]);
			int g = Integer.parseInt(elements[5]);
			int b = Integer.parseInt(elements[6]);
			Color color = new Color(r, g, b);
			circle = new Circle(xCenter, yCenter, radius, color, name);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Not integer numbers: " + str);
		}
		return circle;
	}

	@Override
	public int getMaxX() {
		return radius + xCenter;
	}

	@Override
	public int getMaxY() {
		return radius + yCenter;
	}

	@Override
	public int getMinX() {
		return xCenter - radius;
	}

	@Override
	public int getMinY() {
		return yCenter - radius;
	}

	@Override
	public void setOffset(int x, int y) {
		offsetX = x;
		offsetY = y;
	}
}
