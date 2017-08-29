package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Command expects a single argument: name and prints a tree.
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class TreeCommand extends AbstractCommand{
	
	/**
	 * Constructs tree command with command name "tree" and command description
	 */
	public TreeCommand() {
		super("tree", Collections
				.unmodifiableList(Arrays.asList("Command expects a single argument: "
				+ "directory name and prints a tree.")));
	}

	@Override
	public ShellStatus executeCommand(Environment e, String command) {

		List<String> arguments = ArgumentsUtility.parseCommand(command,e);
		
		if(arguments==null){
			return ShellStatus.CONTINUE;
		}
		
		if (!ArgumentsUtility.checkArgumentsSize(e, arguments, 1)) {
			return ShellStatus.CONTINUE;
		}
		
		Path root = Paths.get(arguments.get(0)).toAbsolutePath().normalize();
		if (!Files.isDirectory(root)) {
			try {
				e.writeln("Given path " + root
						+ " is not directory or does not exist.");
			} catch (IOException ignorable) {}
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(root, new IndentiraniIspis());

		} catch (IOException ignorable) {}

		return ShellStatus.CONTINUE;
	}
	
	private static class IndentiraniIspis implements FileVisitor<Path> {
		private int indentLevel;

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			indentLevel -= 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			System.out.printf("%" + indentLevel + "s%s%n", "",
					file.getFileName());

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			if (indentLevel == 0) {
				System.out.println(dir);
			} else {
				System.out.printf("%" + indentLevel + "s%s%n", "",
						dir.getFileName());
			}
			indentLevel += 2;
			return FileVisitResult.CONTINUE;
		}

	}
}
