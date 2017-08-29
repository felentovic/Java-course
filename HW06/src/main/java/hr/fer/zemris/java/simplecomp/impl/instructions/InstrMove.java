package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Instruction moves value form register 2 in register1.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class InstrMove implements Instruction {
	private int indexRegistra1;
	private int indexRegistra2;

	/**
	 * Constructs instruction add with given list in which is on first place
	 * register in whom value form register2 is moved.
	 * 
	 * @param arguments
	 *            first register, second register
	 */
	public InstrMove(List<InstructionArgument> arguments) {

		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.indexRegistra1 = ((Integer) arguments.get(0).getValue())
				.intValue();
		this.indexRegistra2 = ((Integer) arguments.get(1).getValue())
				.intValue();

	}

	/**
	 * Moves value form register2 to register1.
	 */
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(indexRegistra2);
		computer.getRegisters().setRegisterValue(indexRegistra1, value);
		return false;
	}

}
