package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents negation function. Method apply return negative
 * value of given number
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class NegationFunction implements IFunction {

	@Override
	public double apply(double number) {
		return -number;
	}

	@Override
	public String getName() {
		return "+/-";
	}

}
