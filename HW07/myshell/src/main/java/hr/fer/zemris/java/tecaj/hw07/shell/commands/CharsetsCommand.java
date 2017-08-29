package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

/**
 * Command charsets takes no arguments and lists names of supported charsets for
 * your Java platform. A single charset name is written per line.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class CharsetsCommand extends AbstractCommand {
	
	/**
	 * Constructs charset command with command name "charset" and command description.
	 */
	public CharsetsCommand() {
		super(
				"charsets",
				Collections
				.unmodifiableList(Arrays.asList("Command  takes no arguments "
						+ "and lists names of supported charsets for your Java platform.")));

	}
	
	
	@Override
	public ShellStatus executeCommand(Environment e, String command) {

		if (!ArgumentsUtility.isEmpty(e, command)) {
			return ShellStatus.CONTINUE;

		}

		try {
			e.writeln("Available charsets for this Java platform are:");
		} catch (IOException ignorable) {
		}
		for (String str : Charset.availableCharsets().keySet()) {
			try {
				e.writeln(str);
			} catch (IOException ignorable) {
			}
		}

		return ShellStatus.CONTINUE;
	}

}
