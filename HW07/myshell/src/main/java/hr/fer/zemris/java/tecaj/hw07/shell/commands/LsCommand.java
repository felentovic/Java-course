package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Takes a single argument – directory – and writes a directory listing. The
 * output consists of 4 columns. First column indicates if current object is
 * directory (d), readable (r) writable (w) and executable (x). Second column
 * contains object size in bytes that is right aligned and occupies 10
 * characters. Follows file creation date/time and finally file name.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class LsCommand extends AbstractCommand {
	
	/**
	 * Constructs ls command with command name "ls" and command description.
	 */
	public LsCommand() {
		super(
				"ls",
				Collections
						.unmodifiableList(Arrays
								.asList("Takes a single argument – directory – and writes a directory listing.",
										"The output consists of 4 columns.",
										"First column indicates if current object is directory (d), readable (r), "
												+ "writable (w) and executable (x).",
										"Second column contains object size in bytes that is right aligned and "
												+ "occupies 10 characters. Follows file creation date/time and finally file name.")));

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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = Paths.get(arguments.get(0)).toAbsolutePath().normalize();

		if (!Files.isDirectory(path)) {
			try {
				e.writeln("Entered path " + path + " is not directory.");
			} catch (IOException ignorable) {
			}
			return ShellStatus.CONTINUE;
		}
		
		
		try {
			Files.list(path).forEach((o1) -> {
				String output;
				output = addAttribute(Files.isDirectory(o1), "d");
				output += addAttribute(Files.isReadable(o1), "r");
				output += addAttribute(Files.isWritable(o1), "w");
				output += addAttribute(Files.isExecutable(o1), "x");
				try {
					output += " " + String.format("%10d", Files.size(o1));
				} catch ( IOException e1 ) {
					output+=" %10d";
				}

				// -----------setting for time and date-------------
					
				BasicFileAttributeView faView = Files.getFileAttributeView(
						o1, BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = null;
				try {
					attributes = faView.readAttributes();
				} catch (IOException e1) {
					try {
						e.writeln(e1.getMessage());
					} catch (IOException ignorable) {
					}
				}

				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime
						.toMillis()));

					output += " " + formattedDateTime;
					output += " " + o1.getFileName();
					try {
						e.writeln(output);
					} catch (Exception e1) {
					}
				});
		} catch (IOException ignorable) {
		}

		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Returns given character or - .
	 * @param add flaf if character or - is returnred
	 * @param character given character
	 * @return given character or - 
	 */
	private String addAttribute(boolean add, String character) {
		String str;
		if (add) {
			str = character;
		} else {
			str = "-";
		}
		return str;
	}
}
