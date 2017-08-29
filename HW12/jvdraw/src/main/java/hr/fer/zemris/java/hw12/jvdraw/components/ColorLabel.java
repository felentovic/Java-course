package hr.fer.zemris.java.hw12.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Class extends {@link JLabel} and implements {@link ColorChangeListener}. Sets
 * text value on {@code RGB} color components
 * 
 * @author Borna
 *
 */
public class ColorLabel extends JLabel implements ColorChangeListener {
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Current color
	 */
	private Color color;
	/**
	 * Label name
	 */
	private String name;

	/**
	 * Constructs label from given parameters
	 * 
	 * @param name
	 *            given name
	 * @param color
	 *            given color
	 */
	public ColorLabel(String name, Color color) {
		this.color = color;
		this.name = name;
		setText();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		color = newColor;
		setText();
	}

	/**
	 * Sets {@link ColorLabel} text to current color
	 */
	private void setText() {
		setText(name + "(" + color.getRed() + "," + color.getGreen() + ","
				+ color.getBlue() + ")");
	}
}
