package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * Class simulates enetred program on computer.
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	/**
	 * @throws IllegalArgumentException
	 *             if invalid arguments are given in asemler.
	 */
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		while (true) {
			int pc = computer.getRegisters().getProgramCounter();
			Instruction instr = (Instruction) computer.getMemory().getLocation(
					pc);
			computer.getRegisters().incrementProgramCounter();

			if (instr.execute(computer)) {
				break;
			}

		}
		return true;
	}

}
