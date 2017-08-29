package hr.fer.zemris.java.gui.layouts.calc.operators;


/**
 * Class represents divide operator.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class DivideOperator implements IOperator {

	@Override
	public String getName() {
		return "/";
	}
	
	/**
	 * Returns number1 {@code/}number2.
	 */
	@Override
	public double applyOperator(double number1, double number2) {
		return number1 / number2;
	}

}
