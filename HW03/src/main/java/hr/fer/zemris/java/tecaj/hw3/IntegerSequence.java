package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;

/**
 * This class uses for iterating over integer numbers, with start, end and step
 * number. Implements iterable.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class IntegerSequence implements Iterable<Integer> {

	private final int startExpresion;
	private final int endExpression;
	private final int step;

	/**
	 * Sets variables for iterating and throws exception if argumetns are
	 * invalid
	 * 
	 * @param startExpresion
	 *            number from iterating starts
	 * @param endExpression
	 *            number with iterating ends
	 * @param step
	 *            number with previous iterating number will be increased
	 * @throws IllegalArgumentException
	 *             if start expression is bigger then end and step biger then 0
	 *             if start expression is smaller then end and step smaller then
	 *             0
	 */
	public IntegerSequence(int startExpresion, int endExpression, int step) {
		super();
		if (startExpresion >= endExpression && step >= 0
				|| startExpresion <= endExpression && step <= 0) {
			throw new IllegalArgumentException(
					"Invalid start expresion, end expression and step value");
		}
		this.startExpresion = startExpresion;
		this.endExpression = endExpression;
		this.step = step;
	}

	/**
	 * Factory method that creates new iterator.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new MyIterator();
	}

	/**
	 * Private class which implements iterator interface. Uses variables from
	 * IntegerSequence
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 *
	 */
	private class MyIterator implements Iterator<Integer> {
		private int i = startExpresion;
		private int tmp;

		/**
		 * Return true if next value exists, false otherwise.
		 */
		@Override
		public boolean hasNext() {
			return i < endExpression;

		}

		/**
		 * Returns next value for iterating.
		 */
		@Override
		public Integer next() {
			tmp = i;
			i += step;
			return tmp;
		}

		/**
		 * Removes current value from iterating in safe way.
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException(
					"Remove metode is unsupported!");
		}
	}
}
