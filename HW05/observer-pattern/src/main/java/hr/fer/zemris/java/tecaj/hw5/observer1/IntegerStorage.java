package hr.fer.zemris.java.tecaj.hw5.observer1;

import java.util.LinkedList;
import java.util.List;

/**
 * Storage that is Observable, and offers addObserver, removeObserver and
 * clearObservers method.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class IntegerStorage {
	private int value;
	private List<IntegerStorageObserver> observers;
	private List<IntegerStorageObserver> observersToRemove;

	/**
	 * Constructs new IntegerStorage with given value.
	 * 
	 * @param initialValue
	 *            given value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds observer to internal observers list, if it doesnit exist, creates
	 * it.
	 * 
	 * @param observer
	 *            observer that is added to observers list
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			observers = new LinkedList<>();
		}
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes the observer from internal observers list ,if present.
	 * 
	 * @param observer
	 *            observer that will be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observersToRemove == null) {
			observersToRemove = new LinkedList<>();
		}

		// Because of safe removing while iterating
		observersToRemove.add(observer);
	}

	/**
	 * Removes all observers from internal observers list.
	 */
	public void clearObservers() {
		if (observers != null) {
			observers.clear();
		}
	}

	/**
	 * Getter for value.
	 * 
	 * @return this value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Changes current value, if given value is different form current and
	 * notifies all observers in observer list.
	 * 
	 * @param value
	 *            given value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			//Delete observers before iterating
			if (observersToRemove != null) {
				observers.removeAll(observersToRemove);
				observersToRemove.clear();
			}
			// Notify all registered observers
			for (IntegerStorageObserver observer : observers) {
				observer.valueChanged(this);

			}

		}
	}

}
