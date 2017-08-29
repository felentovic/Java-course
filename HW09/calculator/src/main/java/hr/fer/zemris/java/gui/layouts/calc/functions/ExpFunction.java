package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents exponential function, base is Eulers number. Method
 * apply return value of function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ExpFunction implements IFunction {

	@Override
	public double apply(double number) {
		return Math.exp(number);
	}

	@Override
	public String getName() {
		return "e^x";
	}
}
