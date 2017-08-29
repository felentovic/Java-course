package hr.fer.zemris.java.tecaj.hw5.observer2.Values;

import hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageChange;
import hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageObserver;

/**
 * Class write a square of the integer stored in the IntegerStorage to the
 * standard output.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Writes a square of the integer stored in the IntegerStorage to the
	 * standard output.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		System.out.println("Provided new value: " + value + ", square is "
				+ value * value);

	}

}
