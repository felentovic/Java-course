package hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators;


/**
 * Class implements IComparisonOperator and it is used for strategy pattern.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public  class Equal implements IComparisonOperator {

	/**
	 * Returns true if value1 for each element is equal to value2,false
	 * otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             if value2 containst more than one special character *
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		if (value1 == null || value2 == null) {
			throw new IllegalArgumentException(
					"Null given as value1 or value2.");
		}

		int counter = 0;
		for (int i = 0; i < value2.length(); i++) {
			if (value2.charAt(i) == '*') {
				counter++;
			}
		}
		if (counter > 1) {
			throw new IllegalArgumentException(
					"Invalid number of special character *.");
		}

		int index = value2.indexOf('*');

		String beforeWildCart;
		if (index > 0) {
			beforeWildCart = value2.substring(0, index);
		} else {
			beforeWildCart = "";
		}

		String afterWildCart;
		if (index >= 0 && index != value2.length() - 1) {
			afterWildCart = value2.substring(index + 1);
		} else {
			afterWildCart = "";
		}

		boolean accepts = false;
		if (beforeWildCart.isEmpty() && afterWildCart.isEmpty()
				&& !value2.equals("*")) {
			String[] splitted1 = value1.split(" ");
			String[] splitted2 = value2.split(" ");
			// test for every part of entered query if it is equal to one of
			// the
			// value1, example: value2=Brlić; value1= Brlić Mažuranić
			for (int i = 0; i < splitted2.length; i++) {
				for (int j = 0; j < splitted1.length; j++) {
					if (splitted1[j].equals(splitted2[i])) {
						accepts = true;
						break;
					} else {
						accepts = false;
					}
				}
			}
		} else if (value2.equals("*")) {
			accepts = true;

		} else {
			// test if one of the value1 matches value2.
			// example: value2=*anić, value1=Brlić Mažuranić
			String[] splitted1 = value1.split(" ");
			for (int i = 0; i < splitted1.length; i++) {
				if (splitted1[i].matches(beforeWildCart + ".*"
						+ afterWildCart)) {
					accepts = true;
					break;
				}
			}

		}
		return accepts;
	}

}
