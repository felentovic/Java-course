package hr.fer.zemris.java.gui.layouts.calc.operators;


/**
 * Class implements {@link IOperator} and represents operator {@code \u221A}
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class RootOperator implements IOperator {

	/**
	 * Returns number1{@code\u221A}number2
	 */
	@Override
	public double applyOperator(double number1, double number2) {
		return Math.pow(number1, 1 / number2);
	}

	@Override
	public String getName() {
		return "n\u221Ax";
	}

}
