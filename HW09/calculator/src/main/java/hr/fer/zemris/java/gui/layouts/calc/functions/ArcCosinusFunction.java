package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents arc cosinus function. Method apply return value of
 * function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ArcCosinusFunction implements IFunction {

	@Override
	public double apply(double number) {
		return Math.acos(number);
	}

	@Override
	public String getName() {
		return "acos";
	}

}
