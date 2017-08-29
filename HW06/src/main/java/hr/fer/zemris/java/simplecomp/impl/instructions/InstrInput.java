package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Instruction inputs given value on given memory location. If values are
 * integer flag is set on 1, otherwise on 0.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class InstrInput implements Instruction {

	private int memoryLocation;

	/**
	 * Constructs instruction add with given list in which is on first place
	 * register in whom value form register2 is moved.
	 * 
	 * @param arguments
	 *            memory location
	 * 
	 */
	public InstrInput(List<InstructionArgument> arguments) {

		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		memoryLocation = (Integer) arguments.get(0).getValue();

	}

	/**
	 * Inputs given value on memory location. If given memory and location are
	 * integer flag is set on 1 otherwise on 0.
	 */
	public boolean execute(Computer computer) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(System.in)));

		String entry = null;
		try {
			entry = reader.readLine();
		} catch (IOException e1) {
			return false;
		}
		int inputedValue=0;
		boolean flag=true;
		try {
			inputedValue = Integer.parseInt(entry);

		} catch (NumberFormatException e) {
			flag = false;
		}
		
		if (flag) {
			computer.getMemory().setLocation(memoryLocation, inputedValue);
		}
		computer.getRegisters().setFlag(flag);
		return false;
	}

}
