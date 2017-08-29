package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents potention function, base is 10. Method apply return
 * value of function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Potention10Function implements IFunction {

	@Override
	public double apply(double number) {
		return Math.pow(10, number);
	}

	@Override
	public String getName() {
		return "10^x";
	}
}
