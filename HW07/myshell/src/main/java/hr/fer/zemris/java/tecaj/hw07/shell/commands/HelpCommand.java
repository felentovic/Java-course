package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Lists all commands and their descriptions if no arguments are given, otherwise
 * writes command description for given command.
 * 
 * @author Borna
 *
 */
public class HelpCommand extends AbstractCommand {
	
	/**
	 * Creates help command with command name "help" and command description.
	 */
	public HelpCommand() {
		super(
				"help",
				Collections
						.unmodifiableList(Arrays
								.asList("Lists all commands and their descriptions if no arguments are given,"
										+ "otherwise writes command description for given command.")));
	}

	@Override
	public ShellStatus executeCommand(Environment e, String command) {

		List<String> arguments = ArgumentsUtility.parseCommand(command, e);

		if (arguments == null) {
			return ShellStatus.CONTINUE;
		}

		if (!ArgumentsUtility.checkArgumentsSize(e, arguments, 0, 1)) {
			return ShellStatus.CONTINUE;
		}

		if (arguments.size() == 0) {
			for (ShellCommand c : e.commands()) {
				try {
					e.write(c.getCommandName() + "- ");
					for (String str : c.getCommandDescription()) {
						e.write(str);
					}
					e.writeln("");
				} catch (IOException ignorable) {
				}
			}
		} else {
			String givenCommand = arguments.get(0);
			ShellCommand comm = e.getCommand(givenCommand);

			if (comm == null) {
				try {
					e.writeln("Given command " + givenCommand
							+ " does not exist in MyShell.");
				} catch (IOException ignorable) {
				}
			} else {
				try {
					e.write(comm.getCommandName() + "- ");

					for (String str : comm.getCommandDescription()) {
						e.writeln(str);
					}

				} catch (IOException ignorable) {
				}
			}
		}
		return ShellStatus.CONTINUE;
	}
}
