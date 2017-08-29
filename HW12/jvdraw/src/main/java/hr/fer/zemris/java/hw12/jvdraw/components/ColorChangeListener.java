package hr.fer.zemris.java.hw12.jvdraw.components;

import java.awt.Color;

/**
 * Interface represents listener on {@link ColorChangeSubject}
 * 
 * @author Borna
 *
 */
public interface ColorChangeListener {
	/**
	 * Method is called when color on {@link ColorChangeSubject} is changed
	 * 
	 * @param source
	 *            source
	 * @param oldColor
	 *            old color
	 * @param newColor
	 *            new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor);
}
