package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction that sets program counter on given memory location.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class InstrJump implements Instruction {
	private int memoryLocation;

	/**
	 * Constructs jump instruction with given memory location on first argument.
	 * 
	 * @param arguments
	 *            memory location
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected one arguments!");
		}
		try {
			memoryLocation = (Integer) arguments.get(0).getValue();
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Expected integer in arguments.");
		}
	}

	/**
	 * Sets program counter in computer on given memory location.
	 */
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(memoryLocation);
		return false;
	}

}
