package hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators;

/**
 * Interface used for strategy patter, operation on two strign values.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public interface IComparisonOperator {

	/**
	 * Returns if value1 satisfies operation on value2.
	 * 
	 * @param value1
	 *            first argument
	 * @param value2
	 *            second argument, can contain special character *
	 * @return true if value1 satisfies operation on value2, false
	 *         otherwise.
	 * @throws IllegalArgumentException
	 *             if null is given as value1 or value2
	 */
	public boolean satisfied(String value1, String value2);

}
