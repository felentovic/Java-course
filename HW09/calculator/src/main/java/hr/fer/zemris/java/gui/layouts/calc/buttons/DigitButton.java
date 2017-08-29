package hr.fer.zemris.java.gui.layouts.calc.buttons;

import hr.fer.zemris.java.gui.layouts.calc.CalcMemory;

import javax.swing.JLabel;

/**
 * Class represents digits button. Changes display value.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class DigitButton extends AbstractButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs digit button with given string name.
	 * 
	 * @param name
	 */
	public DigitButton(String name) {
		super(name);
		initComponent();
	}

	/**
	 * Changes display value. Adds given button value to display.
	 */
	@Override
	public void work(JLabel display, CalcMemory memory) {

		if (memory.newNumber) {
			display.setText("");
			memory.newNumber = false;
		}

		if (!(display.getText().contains(".") && getText().equals("."))) {
			display.setText(display.getText() + getText());
		}
	}

}
