package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Command exits my shell. Command takes no arguments.
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ExitCommand extends AbstractCommand {
	
	/**
	 * Constructs command with commadn name "exit" and takes no arguments.
	 */
	public ExitCommand() {
		super("exit", Collections
				.unmodifiableList(Arrays.asList("Exit shell.")));
	}

	@Override
	public ShellStatus executeCommand(Environment e, String command) {

		List<String> arguments = ArgumentsUtility.parseCommand(command, e);
		if (arguments == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (!ArgumentsUtility.checkArgumentsSize(e, arguments, 0)) {
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}
}
