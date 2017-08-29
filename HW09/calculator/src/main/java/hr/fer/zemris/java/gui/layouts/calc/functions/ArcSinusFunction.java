package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents arc sinus function. Method apply return value of
 * function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ArcSinusFunction implements IFunction {

	@Override
	public double apply(double number) {
		return Math.asin(number);
	}

	@Override
	public String getName() {
		return "asin";
	}

}
