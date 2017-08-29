package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Instruction decrement implements insruction and decrements register value.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class InstrDecrement implements Instruction {

	private int indexRegistra;

	/**
	 * Constructs instruction decrement with given list in which is on first
	 * place register which value is decremented for 1.
	 * 
	 * @param arguments
	 *            first register, second register, third register
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {

		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		this.indexRegistra = ((Integer) arguments.get(0).getValue()).intValue();
	}

	/**
	 * Decrements value in given register.
	 */
	public boolean execute(Computer computer) {
		int value = ((Integer) computer.getRegisters().getRegisterValue(
				indexRegistra));	
			computer.getRegisters().setRegisterValue(indexRegistra, value - 1);
		
		return false;
	}

}
