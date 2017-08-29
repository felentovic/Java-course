package hr.fer.zemris.java.gui.layouts.calc.buttons;

import hr.fer.zemris.java.gui.layouts.calc.CalcMemory;

import java.util.EmptyStackException;

import javax.swing.JLabel;

/**
 * Class represents stack button and implements {@link IButton}
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class StackButton extends AbstractButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs button with given name
	 * 
	 * @param name
	 */
	public StackButton(String name) {
		super(name);
		initComponent();
	}

	/**
	 * If {@code push} button is pressed display value is pushed on stack. If
	 * {@code pop} button is pressed display value is seted to value from stack.
	 * If stack is empty message is displayed.
	 */
	@Override
	public void work(JLabel display, CalcMemory memory) {
		if (getText().equals("push")) {
			try {
				memory.stack.push(Double.parseDouble(display.getText()));
			} catch (NumberFormatException ignorable) {
			}
		} else {
			try {
				display.setText(memory.stack.pop().toString());
			} catch (EmptyStackException e) {
				display.setText("Stack is empty.");
			}
		}
	}

}
