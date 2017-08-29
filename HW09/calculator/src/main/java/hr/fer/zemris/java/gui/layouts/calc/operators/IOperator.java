package hr.fer.zemris.java.gui.layouts.calc.operators;

/**
 * Interface used for strategy patter.
 * 
 * @author Borna Feldšar
 * @version 1,0
 *
 */
public interface IOperator {
	/**
	 * Applies operator on given number1 and number2.
	 * 
	 * @param number1
	 *            first number
	 * @param number2
	 *            second number
	 * @return value of operation
	 */
	double applyOperator(double number1, double number2);

	String getName();
}
