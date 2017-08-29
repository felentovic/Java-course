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
 * {@link ColorChangeListener}. Represents line filled with color
 * 
 * @author Borna
 *
 */
public class Line extends JComponent implements GeometricalObject,
		ColorChangeListener {
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Start x coordinate
	 */
	private Integer x1;
	/**
	 * Start y coordinate
	 */
	private Integer y1;
	/**
	 * End x coordinate
	 */
	private Integer x2;
	/**
	 * End y coordinate
	 */
	private Integer y2;
	/**
	 * Line is painted with this color
	 */
	private Color color;
	/**
	 * Line name
	 */
	private String name;
	/**
	 * X offset
	 */
	private int offsetX;
	/**
	 * Y offset
	 */
	private int offsetY;

	/**
	 * Constructs new line with given parameters
	 * 
	 * @param x1
	 *            start x coordinate
	 * @param y1
	 *            start y coordinate
	 * @param x2
	 *            end x coordinate
	 * @param y2
	 *            end y coordinate
	 * @param color
	 *            color for painting
	 * @param name
	 *            line name
	 */
	public Line(int x1, int y1, int x2, int y2, Color color, String name) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
		this.name = name;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(color);
		g2d.drawLine(x1 - offsetX, y1 - offsetY, x2 - offsetX, y2 - offsetY);
		g2d.dispose();
	}

	@Override
	public JPanel getProperty() {
		JPanel panel = new JPanel(new GridLayout(3, 2));

		panel.add(new JLabel("Color:"));
		JColorArea area = new JColorArea(color);
		area.addColorChangeListener(this);
		panel.add(area);

		panel.add(new JLabel("Start:"));
		panel.add(getStartPanel());

		panel.add(new JLabel("End"));
		panel.add(getEndPanel());
		return panel;
	}

	/**
	 * Returns end panel with button to set line end x and y coordinate.
	 * 
	 * @return end panel with button to set line end x and y coordinate.
	 */
	private JPanel getEndPanel() {
		JPanel endPanel = new JPanel(new FlowLayout());
		endPanel.add(new JLabel("X:"));
		JTextField endFieldX = new JTextField(x2.toString(), 5);
		endPanel.add(endFieldX);
		endPanel.add(new JLabel("Y:"));
		JTextField endFieldY = new JTextField(y2.toString(), 5);
		endPanel.add(endFieldY);
		JButton setEnd = new JButton("Set");
		setEnd.addActionListener((e) -> {
			int xSet;
			int ySet;
			try {
				xSet = Integer.parseInt(endFieldX.getText());
				ySet = Integer.parseInt(endFieldY.getText());
			} catch (NumberFormatException e1) {
				JOptionPane.showConfirmDialog(null, "Invalid input", "Error",
						JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
				endFieldX.setText(x2.toString());
				endFieldY.setText(y2.toString());
				return;
			}
			repaint();
			x2 = xSet;
			y2 = ySet;

		});
		endPanel.add(setEnd);
		return endPanel;
	}

	/**
	 * Returns start panel with button to set line start x and y coordinate.
	 * 
	 * @return start panel with button to set line start x and y coordinate.
	 */
	private JPanel getStartPanel() {
		JPanel startPanel = new JPanel(new FlowLayout());
		startPanel.add(new JLabel("X:"));
		JTextField startFieldX = new JTextField(x1.toString(), 5);
		startPanel.add(startFieldX);
		startPanel.add(new JLabel("Y:"));
		JTextField startFieldY = new JTextField(y1.toString(), 5);
		JButton setStart = new JButton("Set");
		startPanel.add(startFieldY);
		startPanel.add(setStart);
		setStart.addActionListener((e) -> {
			int xSet;
			int ySet;
			try {
				xSet = Integer.parseInt(startFieldX.getText());
				ySet = Integer.parseInt(startFieldY.getText());
			} catch (NumberFormatException e1) {
				JOptionPane.showConfirmDialog(null, "Invalid input", "Error",
						JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
				startFieldX.setText(x1.toString());
				startFieldY.setText(y1.toString());
				return;
			}
			repaint();
			x1 = xSet;
			y1 = ySet;

		});
		startPanel.setPreferredSize(startPanel.getPreferredSize());
		return startPanel;
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		color = newColor;

	}

	@Override
	public void mouseMoved(int x, int y) {
		x2 = x;
		y2 = y;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String export() {
		return "LINE " + x1 + " " + y1 + " " + x2 + " " + y2 + " "
				+ colorToString(color);
	}

	/**
	 * Returns new Line parsed from string.
	 * 
	 * @param str
	 *            string that is parsed
	 * @param name
	 *            Line name
	 * @return new Line parsed from string
	 * @throws IllegalArgumentException
	 *             if Line cannot be parsed form string
	 */
	public static GeometricalObject parseFromString(String str, String name) {
		String[] elements = str.trim().split("\\s+");
		if (elements.length != 8) {
			throw new IllegalArgumentException(
					"Invalid number of arguments for line: " + str);
		}

		if (!elements[0].equals("LINE")) {
			throw new IllegalArgumentException("Not line: " + str);
		}

		Line line = null;
		try {
			int x1 = Integer.parseInt(elements[1]);
			int y1 = Integer.parseInt(elements[2]);
			int x2 = Integer.parseInt(elements[3]);
			int y2 = Integer.parseInt(elements[4]);

			int r = Integer.parseInt(elements[5]);
			int g = Integer.parseInt(elements[6]);
			int b = Integer.parseInt(elements[7]);
			Color color = new Color(r, g, b);
			line = new Line(x1, y1, x2, y2, color, name);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Not integer numbers: " + str);
		}
		return line;
	}

	@Override
	public int getMaxX() {
		return Math.max(x1, x2);
	}

	@Override
	public int getMaxY() {
		return Math.max(y1, y2);

	}

	@Override
	public int getMinX() {
		return Math.min(x1, x2);

	}

	@Override
	public int getMinY() {
		return Math.min(y1, y2);

	}

	@Override
	public void setOffset(int x, int y) {
		offsetX = x;
		offsetY = y;

	}
}
