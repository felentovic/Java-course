package hr.fer.zemris.java.gui.layouts.calc.buttons;

import hr.fer.zemris.java.gui.layouts.calc.CalcMemory;
import hr.fer.zemris.java.gui.layouts.calc.Calculator;

import javax.swing.JLabel;

/**
 * Interface used for creating {@link Calculator} buttons. Has one method work.
 * 
 * @author Borna
 *
 */
public interface IButton {

	/**
	 * Button operation is stored in memory and displayed on display.
	 * 
	 * @param display
	 *            display on whom result is showen
	 * @param memory
	 *            memory in which result of operation is stored
	 */
	public void work(JLabel display, CalcMemory memory);

}
