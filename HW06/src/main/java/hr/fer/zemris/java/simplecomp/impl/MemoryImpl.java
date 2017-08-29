package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Class represents memory of computer with two methods, getLocation and set
 * value on given loaction.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class MemoryImpl implements Memory {
	private Object[] memory;

	/**
	 * Constructs memory with given size.
	 * 
	 * @param size
	 *            size of memory
	 */
	public MemoryImpl(int size) {
		memory = new Object[size];
	}
	
	public Object getLocation(int location) {
		return memory[location];
	}

	public void setLocation(int location, Object value) {
		memory[location] = value;

	}
}
