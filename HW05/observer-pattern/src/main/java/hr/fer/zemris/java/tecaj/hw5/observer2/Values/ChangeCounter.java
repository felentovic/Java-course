package hr.fer.zemris.java.tecaj.hw5.observer2.Values;

import hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageChange;
import hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageObserver;

/**
 * Action that implements IntegerStorageObserver and counts (and writes to the
 * standard output) the number of times value stored integer has been changed
 * since the registration.
 * 
 * @author Borna Feldšar
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	int changeCounter = 0;

	/**
	 * Counts number of times value stored integer has been changed since the
	 * registration.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		changeCounter++;
		System.out.println("Number of value changes since tracking: "
				+ changeCounter);
	}

}
