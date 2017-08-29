package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * IntegerStorage interface that actions who subscribe on IntegerStorage have to
 * implement.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Notifies actions who had subscribed on IntegerStorage about changing
	 * value to preform their actions.
	 * 
	 * @param istorage
	 *            IntegerStorage with new value
	 */
	public void valueChanged(IntegerStorage istorage);

}
