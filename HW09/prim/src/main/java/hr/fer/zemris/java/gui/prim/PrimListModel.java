package hr.fer.zemris.java.gui.prim;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class implements {@link ListModel} and represents view on stored prime
 * integers.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class PrimListModel implements ListModel<Integer> {

	private int prim;
	private List<ListDataListener> listeners;
	private List<Integer> primNumbers;

	/**
	 * Constructs model and adds number one as first prim number.
	 */
	public PrimListModel() {
		prim = 1;
		listeners = new LinkedList<ListDataListener>();
		primNumbers = new LinkedList<Integer>();
		primNumbers.add(prim);
	}

	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	public Integer getElementAt(int index) {
		if (index < 0 || index > getSize() - 1) {
			throw new IllegalArgumentException("Index : " + index
					+ " smaller than zero or" + " bigger than list size -1 ");
		}
		return primNumbers.get(index);
	}

	public int getSize() {
		return primNumbers.size();
	}

	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * Stores next prime number in prime numbers list and notify all listeners.
	 */
	public void next() {
		boolean primeFound = false;

		while (!primeFound) {
			prim++;
			primeFound = true;

			for (int divisor = 2; divisor <= Math.sqrt(prim); ++divisor) {
				if (prim % divisor == 0) {
					primeFound = false;
					break;
				}
			}

		}
		primNumbers.add(prim);
		intervalAdded();

	}
	
	/**
	 * Notify all listeners that new number is added.
	 */
	private void intervalAdded() {
		ListDataEvent nextEvent = new ListDataEvent(this,
				ListDataEvent.INTERVAL_ADDED, getSize() - 1, getSize() - 1);
		for (ListDataListener listener : listeners) {
			listener.intervalAdded(nextEvent);
		}

	}
}
