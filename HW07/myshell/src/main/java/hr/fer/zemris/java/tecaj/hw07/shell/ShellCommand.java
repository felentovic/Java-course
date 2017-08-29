package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.List;

/**
 * Myshell command that executes with given arguments on given environment.
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public interface ShellCommand {
	
	/**
	 * Method that executes command with given command arguments on given environment
	 * @param env environment
	 * @param arguments command arguments
	 * @return if CONTINUE shell continues with execution, if TERMINATE shell stops with execution.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Getter for command name.
	 * @return value of command name
	 */
	String getCommandName();
	
	/**
	 * Getter for command description.
	 * @return list of command description.
	 */
	List<String> getCommandDescription();
}
