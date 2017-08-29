package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents arc tangens function. Method apply return value of
 * function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ArcTangensFunction implements IFunction {

	@Override
	public double apply(double number) {
		return Math.atan(number);
	}

	@Override
	public String getName() {
		return "atan";
	}
}
