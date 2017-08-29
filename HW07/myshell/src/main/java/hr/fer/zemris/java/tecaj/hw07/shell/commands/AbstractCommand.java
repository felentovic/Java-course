package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class that implements interface ShellCommand and gives constructor. Method execute
 * is unimplemented.
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public abstract class AbstractCommand implements ShellCommand {
	private String commandName;
	/**
	 * Unmodifable list
	 */
	private List<String> commandDescription;
	
	/**
	 * Constructs abstract command with command name and command description.
	 * @param commandName command name
	 * @param commandDescription description as list separated by sentence
	 */
	public AbstractCommand(String commandName, List<String> commandDescription) {
		this.commandName = commandName;
		this.commandDescription = new LinkedList<String>();
		this.commandDescription.addAll(commandDescription);
	}
	
	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}
	
	
	@Override
	public abstract ShellStatus executeCommand(Environment e, String command);
	
}
