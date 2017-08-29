package hr.fer.zemris.java.tecaj.hw5.observer1.Values;

import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorageObserver;

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
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: " + value + ", square is "
				+ value * value);

	}

}
