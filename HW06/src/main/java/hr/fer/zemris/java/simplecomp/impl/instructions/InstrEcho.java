package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction prints register value on System.out
 * @author Borna Feldšar
 * @version 1.0
 */
public class InstrEcho implements Instruction {
	private int indexRegistra;

	/**
	 * Constructs echo instruction with one arguments in list, register
	 * 
	 * @param arguments
	 *            one register
	 */
	public InstrEcho(List<InstructionArgument> arguments) {

		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected one argument!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		this.indexRegistra = ((Integer) arguments.get(0).getValue()).intValue();

	}

	/**
	 * Prints value of given register on System.out
	 */
	public boolean execute(Computer computer) {
		System.out.print(computer.getRegisters().getRegisterValue(
				indexRegistra));
		return false;
	}

}
