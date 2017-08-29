package hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators;


import java.text.Normalizer;

/**
 * Class implements IComparisonOperator and it is used for strategy pattern.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public  class SmallerThan implements IComparisonOperator {

	/**
	 * Returns true if value1 for each element is smaller than value2,false
	 * otherwise.
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		if (value1 == null || value2 == null) {
			throw new IllegalArgumentException(
					"Null given as value1 or value2.");
		}
		String[] splitted1 = value1.split(" ");
		String[] splitted2 = value2.split(" ");
		for (int i = 0; i < splitted1.length; i++) {
			for (int j = 0; j < splitted2.length; j++) {
				if (!((Normalizer.normalize(splitted1[i],
						Normalizer.Form.NFD).compareTo(Normalizer
						.normalize(splitted2[j], Normalizer.Form.NFD))) < 0)) {
					return false;
				}
			}
		}
		return true;
	}
}
