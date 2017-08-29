package hr.fer.zemris.java.gui.layouts.calc.buttons;

import java.awt.Dimension;

import javax.swing.JButton;

/**
 * Abstract class that extendes {@link JButton} and implements {@link IButton}
 * has one method for initializing component size.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public abstract class AbstractButton extends JButton implements IButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs {@link AbstractButton} with given name
	 * 
	 * @param name
	 *            given name
	 */
	public AbstractButton(String name) {
		super(name);
	}

	/**
	 * Initialize components size to squared.
	 */
	public void initComponent() {
		Dimension buttonDim = this.getMinimumSize();
		int square = Math.max(buttonDim.height, buttonDim.width);
		buttonDim.height = square;
		// because of integer division
		buttonDim.width = (int) (1.1*square);
		this.setMinimumSize(buttonDim);
	}

}
