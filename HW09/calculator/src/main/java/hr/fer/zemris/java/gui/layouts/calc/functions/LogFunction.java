package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents logarithm with base ten function. Method apply
 * return value of function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class LogFunction implements IFunction {

	@Override
	public double apply(double number) {
		return Math.log10(number);
	}

	@Override
	public String getName() {
		return "log";
	}
}
