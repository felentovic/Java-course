package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Used for filtering records, if they accepts conditions.
 * @author Borna Feldšar
 * @version 1.0
 *
 * @param <T> parameterized interface
 */
public interface IFilter<T> {
	
	/**
	 * Returns true if record accepts conditions.
	 * @param record given parameterized object
	 * @return true if record accepts conditions, false otherwise.
	 */
	public boolean accepts(T record);
}
