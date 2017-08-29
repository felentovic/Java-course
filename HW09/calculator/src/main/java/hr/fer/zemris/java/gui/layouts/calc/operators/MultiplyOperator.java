package hr.fer.zemris.java.gui.layouts.calc.operators;


/**
 * Class represents multiply operator.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class MultiplyOperator implements IOperator {

	@Override
	public String getName() {
		return "*";
	}

	/**
	 * Returns number1 {@code*}number2.
	 */
	@Override
	public double applyOperator(double number1, double number2) {
		return number1 * number2;
	}
}
