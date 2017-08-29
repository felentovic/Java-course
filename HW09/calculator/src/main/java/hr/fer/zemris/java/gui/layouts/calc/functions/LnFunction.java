package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents natural logarithm function. Method apply return
 * value of function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class LnFunction implements IFunction {

	@Override
	public double apply(double number) {
		return Math.log(number);
	}

	@Override
	public String getName() {
		return "ln";
	}
}
