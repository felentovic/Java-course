package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

public class RegistersImpl implements Registers {
	private boolean flag;;
	private int programCounter;
	private Object[] registers;

	public RegistersImpl(int regsLen) {
		registers = new Object[regsLen];
		flag = false;
		programCounter = 0;
	}

	public boolean getFlag() {
		return flag;
	}

	public int getProgramCounter() {
		return programCounter;
	}

	public Object getRegisterValue(int index) {
		return registers[index];
	}

	public void incrementProgramCounter() {
		programCounter++;

	}

	public void setFlag(boolean value) {
		flag = value;

	}

	public void setProgramCounter(int value) {
		programCounter = value;

	}

	public void setRegisterValue(int index, Object value) {
		registers[index] = value;

	}
}
