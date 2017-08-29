package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents sinus function. Method apply returns value of
 * function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class SinusFunction implements IFunction {

	@Override
	public double apply(double number) {
		return Math.sin(number);
	}

	@Override
	public String getName() {
		return "sin";
	}

}
