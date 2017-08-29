package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementation of instruction halt that stops computer. Implements Instruction.
 * @author Borna Feldšar
 * @version 1.0
 */
public class InstrHalt implements Instruction {
	
	/**
	 * Constructor expects no arguments in list.
	 * @param arguments zero arguments
	 */
	public InstrHalt(List<InstructionArgument> arguments) {

		if (!arguments.isEmpty()) {
			throw new IllegalArgumentException("Expected no arguments!");
		}
	}
	/**
	 * Executes instruction ,halts computer.
	 */
	public boolean execute(Computer computer) {
		return true;
	}

}
