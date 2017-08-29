package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Class provides newValue, oldValue, and reference to IntegerStorage. All
 * parameters are read-only.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class IntegerStorageChange {
	private final IntegerStorage istorage;
	private final int oldValue;
	private final int newValue;
	
	/**
	 * Constructs IntegerStorageChange with given paramteres.
	 * @param istorage reference to IntegerStorage
	 * @param value old value in IntegerStorage
	 * @param newValue new value in IntegerStorage
	 */
	public IntegerStorageChange(IntegerStorage istorage, int oldValue, int newValue) {
		super();
		this.istorage = istorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public IntegerStorage getIstorage() {
		return istorage;
	}

	public int getOldValue() {
		return oldValue;
	}

	public int getNewValue() {
		return newValue;
	}

}
