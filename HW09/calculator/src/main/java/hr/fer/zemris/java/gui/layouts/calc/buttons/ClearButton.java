package hr.fer.zemris.java.gui.layouts.calc.buttons;

import hr.fer.zemris.java.gui.layouts.calc.CalcMemory;

import javax.swing.JLabel;

public class ClearButton extends AbstractButton {

	private static final long serialVersionUID = 1L;

	public ClearButton(String name) {
		super(name);
		initComponent();
	}

	/**
	 * If {@code res } button is pressed than memory is reseted. If {@code clr}
	 * button is pressed than user can enter this number again.
	 */
	@Override
	public void work(JLabel display, CalcMemory memory) {

		if (getText().equals("res")) {
			memory.clear();
			display.setText("0.0");
		} else {
			memory.number1 = 0.0;
			display.setText("0.0");
		}

	}

}
