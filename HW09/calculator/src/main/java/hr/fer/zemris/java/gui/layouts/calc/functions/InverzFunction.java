package hr.fer.zemris.java.gui.layouts.calc.functions;


/**
 * Class that represents inverz function. Method apply return value of
 * function at given point.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class InverzFunction implements IFunction {

	@Override
	public double apply(double number) {
		return 1 / number;
	}

	@Override
	public String getName() {
		return "1/x";
	}
}
