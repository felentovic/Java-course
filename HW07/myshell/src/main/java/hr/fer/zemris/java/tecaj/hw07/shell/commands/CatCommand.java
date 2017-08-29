package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

/**
 * Command cat takes one or two arguments. The first argument is path to some
 * file and is mandatory. The second argument is charset name that should be
 * used to interpret chars from bytes. If not provided, a default platform
 * charset should be used. This command opens given file and writes its content
 * to console
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class CatCommand extends AbstractCommand {
	
	/**
	 * Constructs cat command with command name "cat" and description.
	 */
	public CatCommand() {
		super(
				"cat",
				Collections
				.unmodifiableList(Arrays.asList(
						"Command takes one or two arguments.",
						" The first argument is path to some file and is mandatory.",
						" The second argument is charset name that should be used to interpret chars from bytes.",
						" If not provided, a default platform charset should be used.",
						" This command opens given file and writes its content to console.")));

	}

	@Override
	public ShellStatus executeCommand(Environment e, String command) {

		List<String> arguments = ArgumentsUtility.parseCommand(command, e);
		if (arguments == null) {
			return ShellStatus.CONTINUE;
		}

		if (!ArgumentsUtility.checkArgumentsSize(e, arguments, 1, 2)) {
			return ShellStatus.CONTINUE;
		}

		Charset charset = null;
		if (arguments.size() == 1) {
			charset = Charset.defaultCharset();
		} else {
			String givenCharset = arguments.get(1);
			try {
				charset = Charset.forName(givenCharset);
			} catch (IllegalCharsetNameException | UnsupportedCharsetException e1) {
				try {
					e.writeln("Unsupported charset: " + givenCharset);
				} catch (IOException ignorable) {
				}
				return ShellStatus.CONTINUE;

			}
		}

		Path source = Paths.get(arguments.get(0)).toAbsolutePath().normalize();

		if (!ArgumentsUtility.canOpenFile(e, source)) {
			return ShellStatus.CONTINUE;
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(Files.newInputStream(source)), charset))) {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				e.writeln(line);
			}

		} catch (IOException ignorable) {
		}

		return ShellStatus.CONTINUE;
	}

}
