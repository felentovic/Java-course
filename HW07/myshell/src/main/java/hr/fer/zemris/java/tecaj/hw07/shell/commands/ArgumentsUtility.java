package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that provides static methods for work with command arguments.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ArgumentsUtility {

	/**
	 * Returns true if given command is empty and writes appropriate message if
	 * not.
	 * 
	 * @param e
	 *            Environment on which appropriate message will be writen
	 * @param command
	 *            string command
	 * @return true if given command is empty, false otherwise.
	 */
	public static boolean isEmpty(Environment e, String command) {
		if (command == null) {
			try {
				e.writeln("Command is null.");
			} catch (IOException e1) {
			}
			return false;
		}
		if (command.trim().isEmpty()) {
			return true;
		} else {
			try {
				e.writeln("Method expects no arguments.");
			} catch (IOException ignorable) {
			}
		}
		return false;
	}

	/**
	 * Returns true if arguments size is equal one of allowed size, otherwise
	 * false and writes appropriate message.
	 * 
	 * @param e
	 *            Environment on which appropriate message will be writen
	 * @param arguments
	 *            arguments size is checked
	 * @param allowedSize
	 *            allowed sizes of arguments
	 * @return true if arguments size is equal one of allowed size, otherwise
	 *         false
	 */
	public static boolean checkArgumentsSize(Environment e,
			List<String> arguments, int... allowedSize) {
		String allowedSizes = "";
		for (int size : allowedSize) {
			if (arguments.size() == size) {
				return true;
			}
			allowedSizes += size + ", ";
		}
		allowedSizes = allowedSizes.substring(0, allowedSizes.length() - 2);
		try {
			e.write("Invalid number of arguments for command. Allowed sizes are: ");
			e.writeln(allowedSizes);
		} catch (IOException ignorable) {
		}
		return false;
	}

	/**
	 * Parses given command and writes message on environment if error occures.
	 * Inside qoutes everything is considered like one string. Inside qoutes
	 * escaping is allowed
	 * 
	 * @param command
	 *            command name
	 * @param e
	 *            environment
	 * @return list of parsed arguments
	 */
	public static List<String> parseCommand(String command, Environment e) {
		if (command == null) {
			try {
				e.writeln("Command is null.");
			} catch (IOException e1) {
			}
			return null;
		}
		LinkedList<String> list = new LinkedList<String>();
		list.add("");
		command = command.trim();
		boolean insideQoute = false;
		for (int i = 0; i < command.length(); i++) {
			char currentChar = command.charAt(i);

			if (currentChar == '\\') {
				if (i != command.length() - 1) {
					switch (command.charAt(i + 1)) {
					case '\\':
						addCharacter(list, '\\');
						i++;
						break;
					case '\"':
						addCharacter(list, '\"');
						i++;
						break;
					default:
						addCharacter(list, '\\');
					}
				} else {
					addCharacter(list, currentChar);
				}
			} else if (currentChar == ' ') {
				if (!insideQoute) {
					list.add("");
				} else {
					addCharacter(list, ' ');
				}

			} else if (currentChar == '\"') {
				insideQoute = !insideQoute;
				list.add("");

			} else {
				addCharacter(list, currentChar);
			}

		}

		if (insideQoute) {
			try {
				e.writeln("Unclosed qoutes.");
			} catch (IOException e1) {
			}
			return null;
		}
		// removes empty strings
		list.removeAll(Arrays.asList(""));
		return list;
	}

	/**
	 * Adds character in last stored string.
	 * 
	 * @param list
	 *            list of string
	 * @param character
	 *            character that is added
	 */
	private static void addCharacter(LinkedList<String> list,
			Character character) {
		String str = list.getLast();
		list.removeLast();
		str += character;
		list.add(str);
	}

	/**
	 * If file is directory or can't be opened false is returned, otherwise
	 * true.
	 * 
	 * @param e
	 *            envinment on which message is written
	 * @param source
	 *            file path
	 * @return true if file is directory or can't be opened, false otherwise.
	 */
	public static boolean canOpenFile(Environment e, Path source) {
		boolean status = true;
		if (!Files.isReadable(source) || Files.isDirectory(source)) {
			try {
				e.writeln("Entered source path: " + source
						+ " is directory or does not exist.");
			} catch (IOException ignorable) {
			}
			status = false;
		}
		return status;
	}
}
