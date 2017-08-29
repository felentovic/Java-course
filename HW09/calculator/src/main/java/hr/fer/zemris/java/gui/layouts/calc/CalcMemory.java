package hr.fer.zemris.java.gui.layouts.calc;

import hr.fer.zemris.java.gui.layouts.calc.operators.IOperator;

import java.util.Stack;

/**
 * Class used for {@link Calculator} memory. Memory have stack, flag for reset,
 * number, flag for new number and operator.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class CalcMemory {
	public Double number1;
	public IOperator operator;
	public boolean resetNedded;
	public Stack<Double> stack;
	public boolean newNumber;

	/**
	 * Constructor initialize variables.
	 */
	public CalcMemory() {
		stack = new Stack<Double>();
		clear();
	}

	/**
	 * Reset {@link CalcMemory}
	 */
	public void clear() {
		newNumber = true;
		number1 = 0.0;
		operator = null;
		resetNedded = false;
		stack.clear();
	}
}
