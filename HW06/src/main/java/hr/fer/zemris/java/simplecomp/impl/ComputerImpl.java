package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Class simulates microcomputer with memory and registers.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class ComputerImpl implements Computer {
	private MemoryImpl memory;
	private RegistersImpl registers;

	/**
	 * Constructs computer with given memory size and number of registers.
	 * 
	 * @param memorySize
	 *            size of memory
	 * @param registerLength
	 *            number of registers
	 */
	public ComputerImpl(int memorySize, int registerLength) {
		memory = new MemoryImpl(memorySize);
		registers = new RegistersImpl(registerLength);
	}

	public Memory getMemory() {
		return memory;
	}

	public Registers getRegisters() {
		return registers;
	}

}
