package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Command takes a single argument: directory name, and creates the appropriate
 * directorystructure.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class MkDirCommand extends AbstractCommand {
	
	/**
	 * Constructs mkdir command with command name "mkdir" and command description.
	 */
	public MkDirCommand() {
		super(
				"mkdir",
				Collections
						.unmodifiableList(Arrays
								.asList("Command takes a single argument: "
										+ "directory name, and creates the appropriate directorystructure.")));
	}

	@Override
	public ShellStatus executeCommand(Environment e, String command) {
		List<String> arguments = ArgumentsUtility.parseCommand(command, e);
		if (arguments == null) {
			return ShellStatus.CONTINUE;
		}

		if (!ArgumentsUtility.checkArgumentsSize(e, arguments, 1)) {
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(arguments.get(0)).toAbsolutePath();

		if (Files.exists(path)) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(System.in)));
			try {
				String decision;
				do {
					e.writeln("Directory already exists. Should i overwrite it? y/n");
					decision = reader.readLine();
					if (decision.toLowerCase().equals("n")) {
						return ShellStatus.CONTINUE;
					}
				} while (!decision.toLowerCase().equals("y"));

			} catch (IOException ignorable) {
			}
		}

		try {
			Files.deleteIfExists(path);
			Files.createDirectories(path);
		} catch (IOException ignoralbe) {
		}

		return ShellStatus.CONTINUE;
	}

}
