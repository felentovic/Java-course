package hr.fer.zemris.java.tecaj.hw5.observer1.Values;

import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorageObserver;

/**
 * Class write to the standard output double value of the current value which is
 * stored in subject, but only first two times since its registation with
 * subject; after writing the double value for the second time, the observer
 * automatically de-registers itself from the subject.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {
	int counter = 0;

	/**
	 * Write to the standard output double value of the current value which is
	 * stored in subject, but only first two times since its registation with
	 * subject; after writing the double value for the second time, the observer
	 * DoubleValue automatically de-registers itself from the subject.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Double value: " + istorage.getValue() * 2);
		if (counter >= 2) {
			istorage.removeObserver(this);
		}

	}

}
