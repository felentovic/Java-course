package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command expects single or two arguments; PROMPT, MORELINES, MULTILINE. One
 * arguments writes current symbol for i.e. PROMPT and second argument defines
 * new symbol for given function.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class SymbolCommand extends AbstractCommand {
	
	/**
	 * Constructs symbol command with command name "symbol" and command description.
	 */
	public SymbolCommand() {
		super(
				"symbol",
				Collections
						.unmodifiableList(Arrays
								.asList("Command expects single or two arguments; PROMPT,"
										+ "MORELINES, MULTILINE. One arguments writes current symbol for i.e. PROMPT"
										+ "and second argument defines new symbol for given function.")));
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
		Map<String, Character> symbols = new HashMap<>();
		symbols.put("PROMPT", e.getPromptSymbol());
		symbols.put("MORELINES", e.getMorelinesSymbol());
		symbols.put("MULTILINE", e.getMultilineSymbol());

		String symbolName = arguments.get(0).toUpperCase();
		Character symbol = symbols.get(symbolName);
		if (symbol == null) {
			try {
				e.writeln("Unknown symbol " + symbolName);
				return ShellStatus.CONTINUE;
			} catch (IOException ignorable) {
			}
		}
		if (arguments.size() == 1) {
			try {
				e.writeln("Symbol for " + symbolName + " is '" + symbol + "'");
			} catch (IOException ignorable) {
			}

		} else {
			String newCharacterT = arguments.get(1);
			if (newCharacterT.length() != 1) {
				try {
					e.writeln("Given new symbol :" + newCharacterT
							+ " can not be parsed into character.");
				} catch (IOException ignorable) {
				}
				return ShellStatus.CONTINUE;
			}

			Character oldSymbol = null;
			Character newSymbol = newCharacterT.charAt(0);
			if (symbolName.equals("PROMPT")) {
				oldSymbol = e.getPromptSymbol();
				e.setPromptSymbol(newSymbol);

			} else if (symbolName.equals("MORELINES")) {
				oldSymbol = e.getMorelinesSymbol();
				e.setMorelinesSymbol(newSymbol);

			} else {
				oldSymbol = e.getMultilineSymbol();
				e.setMultilineSymbol(newSymbol);

			}

			try {
				e.writeln("Symbol for " + symbolName + " changed from '"
						+ oldSymbol + "' to '" + newSymbol + "'");
			} catch (IOException ignorable) {
			}

		}

		return ShellStatus.CONTINUE;
	}

}
