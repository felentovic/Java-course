package hr.fer.zemris.java.gui.layouts.calc.functions;

/**
 * Interface used for strategy pattern.
 * 
 * @author Borna
 *
 */
public interface IFunction {

	/**
	 * Applies function on given number and returns result
	 * 
	 * @param number
	 *            given number
	 * @return result of function at given number
	 */
	public double apply(double number);

	/**
	 * Returns function name.
	 * 
	 * @return function name
	 */
	public String getName();
}
