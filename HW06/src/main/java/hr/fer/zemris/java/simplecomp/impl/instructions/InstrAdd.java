package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction thath sets value of first register in sum of second two.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class InstrAdd implements Instruction {
	private int indexRegistra1;
	private int indexRegistra2;
	private int indexRegistra3;

	/**
	 * Constructs instruction add with given list in which is on first place
	 * register in which is added value of register2 and register3.
	 * 
	 * @param arguments
	 *            first register, second register, third register
	 */
	public InstrAdd(List<InstructionArgument> arguments) {

		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		if (!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}

		this.indexRegistra1 = ((Integer) arguments.get(0).getValue())
				.intValue();
		this.indexRegistra2 = ((Integer) arguments.get(1).getValue())
				.intValue();
		this.indexRegistra3 = ((Integer) arguments.get(2).getValue())
				.intValue();

	}

	/**
	 * Adds value of register2 + register3 in register1.
	 */
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegistra3);
		computer.getRegisters().setRegisterValue(
				indexRegistra1,
				Integer.valueOf(((Integer) value1).intValue()
						+ ((Integer) value2).intValue()));
		return false;
	}

}
