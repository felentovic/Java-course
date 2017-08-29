package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkDirCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeCommand;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class with main method that simulates shell with commands: help, exit, cat,
 * copy, charsets, tree, mkdir,ls, hex, symbol. First arguments must be command
 * and than follows command arguments.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class MyShell {

	private static Map<String, ShellCommand> commands;

	static {
		commands = new HashMap<String, ShellCommand>();
		ShellCommand[] cc = { new HelpCommand(), new ExitCommand(),
				new CatCommand(), new CopyCommand(), new CharsetsCommand(),
				new HexdumpCommand(), new LsCommand(), new MkDirCommand(),
				new TreeCommand(), new SymbolCommand() };
		for (ShellCommand c : cc) {
			commands.put(c.getCommandName(), c);
		}
	}

	/**
	 * Class that implements interface environment. Methods write and writeln
	 * writes on System.out Default multilineSymbol is {@code|}, morelinesSymbol
	 * {@code\} and prompt symbol {@code>}
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 *
	 */
	public static class EnvironmentImpl implements Environment {
		Character multilineSymbol;
		Character promptSymbol;
		Character moreLinesSymbol;

		public EnvironmentImpl() {
			multilineSymbol = '|';
			moreLinesSymbol = '\\';
			promptSymbol = '>';
		}

		private BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));
		private BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(System.out));

		@Override
		public String readLine() throws IOException {
			return reader.readLine();
		}

		/**
		 * Method writes string on System.out.
		 */
		@Override
		public void write(String s) throws IOException {
			writer.write(s);
			writer.flush();
		}

		/**
		 * Method writes string on System.out with \n on the end.
		 */
		@Override
		public void writeln(String s) throws IOException {
			writer.write(s);
			writer.newLine();
			writer.flush();
		}

		@Override
		public Iterable<ShellCommand> commands() {

			return new Iterable<ShellCommand>() {

				@Override
				public Iterator<ShellCommand> iterator() {
					return new Iterator<ShellCommand>() {
						Iterator<ShellCommand> it = commands.values()
								.iterator();

						@Override
						public boolean hasNext() {
							return it.hasNext();
						}

						@Override
						public ShellCommand next() {
							if (it.hasNext()) {
								return it.next();
							}else{
								throw new NoSuchElementException("No more elements");
							}
						}
					};
				}
			};
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character multilineSybol) {
			this.multilineSymbol = multilineSybol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character promptSymbol) {
			this.promptSymbol = promptSymbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return moreLinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character moreLinesSymbol) {
			this.moreLinesSymbol = moreLinesSymbol;
		}

		@Override
		public ShellCommand getCommand(String name) {
			return commands.get(name.toLowerCase());
		}
	}

	/**
	 * Main method of my shell class.
	 * 
	 * @param args
	 *            main methods arguments
	 */
	public static void main(String[] args) {
		Environment environment = new EnvironmentImpl();

		try {
			environment.writeln("Welcome to MyShell v 1.0");
			String realLine = "";
			boolean moreLines = false;
			while (true) {
				if (moreLines) {
					environment.write(environment.getMultilineSymbol()
							.toString());
				} else {
					environment.write(environment.getPromptSymbol().toString());
				}
				environment.write(" ");
				String line = environment.readLine().trim();
				if (line.endsWith(environment.getMorelinesSymbol().toString())) {
					realLine += line.substring(0, line.length() - 1);
					moreLines = true;
					continue;
				} else {
					if (moreLines) {
						realLine += line;
					} else {
						realLine = line;
					}
					moreLines = false;
				}
				String lineArray[] = realLine.split("\\s+", 2);
				String cmd = lineArray[0];
				String arg = "";
				if (lineArray.length > 1) {
					arg = lineArray[1];
				}

				ShellCommand shellCommand = commands.get(cmd.toLowerCase());
				if (shellCommand == null) {
					environment.writeln("Unknown command!");
					continue;
				}

				if (shellCommand.executeCommand(environment, arg).equals(
						ShellStatus.TERMINATE)) {
					break;
				}

				if (!moreLines) {
					realLine = "";
				}
			}

		} catch (IOException ignorable) {
		}
	}

}
