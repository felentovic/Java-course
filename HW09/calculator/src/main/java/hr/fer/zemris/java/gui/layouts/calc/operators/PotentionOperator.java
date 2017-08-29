package hr.fer.zemris.java.gui.layouts.calc.operators;


/**
 * Class implements {@link IOperator} and represents operator {@code ^}
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class PotentionOperator implements IOperator {

	/**
	 * Returns number1^number2
	 */
	@Override
	public double applyOperator(double number1, double number2) {
		return Math.pow(number1, number2);
	}

	@Override
	public String getName() {
		return "x^n";
	}

}
