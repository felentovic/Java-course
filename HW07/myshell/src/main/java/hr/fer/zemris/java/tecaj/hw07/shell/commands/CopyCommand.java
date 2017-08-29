package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The copy command expects two arguments: source file name and destination file
 * name (i.e. paths and names). If destination file exists, command ask user is
 * it allowed to overwrite it. Command works only with files (no directories).
 * If the second argument is directory, it is assumed that user wants to copy
 * the original file in that directory using the original file name.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class CopyCommand extends AbstractCommand {

	/**
	 * Constructs copy command wtih command name "copy" and command description.
	 */
	public CopyCommand() {
		super(
				"copy",
				Collections
						.unmodifiableList(Arrays
								.asList("The copy command expects two arguments: source file name and destination file"
										+ " name (i.e. paths and names).",
										" If destination file exists, command ask user is it allowed to overwrite it.",
										" Command works only with files (no directories).",
										"If the second argument is directory, it is assumed that user wants to copythe original file in that directory using the original file name.")));

	}

	@Override
	public ShellStatus executeCommand(Environment e, String command) {

		List<String> paths = ArgumentsUtility.parseCommand(command, e);
		if (paths == null) {
			return ShellStatus.CONTINUE;
		}

		if (!ArgumentsUtility.checkArgumentsSize(e, paths, 2)) {
			return ShellStatus.CONTINUE;
		}

		Path source = Paths.get(paths.get(0)).toAbsolutePath().normalize();
		Path destination = Paths.get(paths.get(1)).toAbsolutePath().normalize();

		if (!ArgumentsUtility.canOpenFile(e, source)) {
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(destination)) {
			destination = destination.resolve(source.getFileName());
		}
		try {
			if(Files.isSameFile(source, destination)){
				e.writeln("Source and destination file are the same.");
				return ShellStatus.CONTINUE;
			}
		} catch (IOException ignorable) {}
		if (Files.exists(destination)) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(System.in)));
			try {
				String decision;
				do {
					e.writeln("File already exists. Should i overwrite it? y/n");
					decision = reader.readLine();
					if (decision.toLowerCase().equals("n")) {
						return ShellStatus.CONTINUE;
					}
				} while (!decision.toLowerCase().equals("y"));

			} catch (IOException ignorable) {
			}
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(
				source, StandardOpenOption.READ));

				OutputStream os = new BufferedOutputStream(
						Files.newOutputStream(destination,
								StandardOpenOption.CREATE))) {
			byte[] buff = new byte[4096];
			while (true) {
				int r = is.read(buff);
				if (r < 1) {
					break;
				}
				os.write(buff, 0, r);
			}
		} catch (IOException ignorable) {
		}

		return ShellStatus.CONTINUE;
	}

}
