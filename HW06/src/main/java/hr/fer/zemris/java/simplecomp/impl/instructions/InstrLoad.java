package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;

import java.util.List;

/**
 * Instruction load implements Instruction, and loads value from given memory
 * adress into given register.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class InstrLoad implements Instruction {

	private int indexRegistra;
	private int memoryAdress;

	/**
	 * Constructs instruction load with given list in which is on first place
	 * register and on second memory adress.
	 * 
	 * @param arguments
	 *            first register, second register
	 */
	public InstrLoad(List<InstructionArgument> arguments) {

		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		try {
			this.indexRegistra = ((Integer) arguments.get(0).getValue())
					.intValue();
			this.memoryAdress = ((Integer) arguments.get(1).getValue())
					.intValue();
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Expected integer in arguments.");
		}
	}

	/**
	 * Value from given memory adress is loaded into register.
	 */
	public boolean execute(Computer computer) {
		Memory memory = computer.getMemory();
		computer.getRegisters().setRegisterValue(indexRegistra,
				memory.getLocation(memoryAdress));
		return false;
	}

}
