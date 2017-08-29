package hr.fer.zemris.java.hw12.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Class extends {@link JComponent} and implements {@link ColorChangeSubject}.
 * Represents component with fixed size and paint all his area with selected
 * color. On clik opens {@link JColorChooser}
 * 
 * @author Borna
 *
 */
public class JColorArea extends JComponent implements ColorChangeSubject,
		IColorProvider {
	/**
	 * degault serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Current color on this component
	 */
	private Color selectedColor;
	/**
	 * Color that was before current
	 */
	private Color oldColor;
	/**
	 * List of listeners
	 */
	private List<ColorChangeListener> listeners;

	/**
	 * Constructs JColorArea with given color and sets mouse listener.
	 * 
	 * @param selectedColor
	 *            given color
	 */
	public JColorArea(Color selectedColor) {
		super();
		listeners = new LinkedList<>();
		this.selectedColor = selectedColor;

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(null,
						"Pick new color", selectedColor);
				if (newColor != null && !newColor.equals(selectedColor)) {
					oldColor = JColorArea.this.selectedColor;
					JColorArea.this.selectedColor = newColor;
					notifyListeners();
					repaint();
				}
			}

		});
	}

	@Override
	public Dimension getPreferredSize() {
		int preffDim = 15;
		return new Dimension(preffDim, preffDim);
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public void paintComponent(Graphics g) {
		Insets ins = getInsets();

		g.setColor(selectedColor);
		g.fillRect(ins.left, ins.top, getWidth() - ins.left - ins.right,
				getHeight() - ins.top - ins.bottom);
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all listeners that change occured
	 */
	private void notifyListeners() {
		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, oldColor, selectedColor);
		}
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}
}
