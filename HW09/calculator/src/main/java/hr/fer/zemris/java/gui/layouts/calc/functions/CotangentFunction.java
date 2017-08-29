package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents cotangent function. Method apply return value of
 * function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class CotangentFunction implements IFunction {

	@Override
	public double apply(double number) {
		return 1.0 / Math.tan(number);
	}

	@Override
	public String getName() {
		return "ctg";
	}
}
