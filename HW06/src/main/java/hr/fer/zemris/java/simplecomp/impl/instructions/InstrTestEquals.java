package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction sets flag on 1 if value from register1 is equals to value from
 * register2, otherwise on 0.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class InstrTestEquals implements Instruction {
	private int indexRegistra1;
	private int indexRegistra2;

	/**
	 * Constructs instruction TestEquals, on first argument is register1 and on
	 * second register2.
	 * 
	 * @param arguments
	 *            first register, second register
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
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
	 * Sets computer flag on 1 if value from given registers are equal,
	 * otherwise 0.
	 */
	public boolean execute(Computer computer) {
		int value1 = (Integer) computer.getRegisters().getRegisterValue(
				indexRegistra1);
		int value2 = (Integer) computer.getRegisters().getRegisterValue(
				indexRegistra2);
		computer.getRegisters().setFlag(value1 == value2);
		return false;
	}

}
