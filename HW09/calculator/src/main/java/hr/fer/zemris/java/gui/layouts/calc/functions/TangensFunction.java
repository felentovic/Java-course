package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents tangens function. Method apply return value of
 * function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class TangensFunction implements IFunction {

	@Override
	public double apply(double number) {
		return Math.tan(number);
	}

	@Override
	public String getName() {
		return "tan";
	}
}
