package hr.fer.zemris.java.tecaj.hw5.observer2;

import hr.fer.zemris.java.tecaj.hw5.observer2.Values.*;

/**
 * Class with main method that simulates IntegerStorage.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class ObserverExample {

	/**
	 * Main method used for testing observers.
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.addObserver(new DoubleValue());
		istorage.addObserver(new ChangeCounter());
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.removeObserver(observer);
		istorage.setValue(15);
	}
}
