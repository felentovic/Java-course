package hr.fer.zemris.java.trazilica;

import hr.fer.zemris.java.trazilica.SearchEngine.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class represents console with search engine. Supported commands are
 * {@code query, type, results, exit}. Root path is entered through command line
 * 
 * @author Borna
 *
 */
public class Console {
	/**
	 * List of results
	 */
	static List<Result> results = null;
	/**
	 * Counter for results
	 */
	static int counter;

	/**
	 * Main method
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected root document");
			return;
		}

		long time1 = System.currentTimeMillis();
		SearchEngine search = new SearchEngine(Paths.get(args[0]));
		long time2 = System.currentTimeMillis();
		System.out
				.println("Time for initialization: " + (time2 - time1) + "ms");
		System.out.println("Vocabulary size is " + search.getVocabularSize());
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String query = "";
		while (true) {
			System.out.print("Enter command >");
			try {
				query = reader.readLine();
			} catch (IOException ignorable) {
			}

			if (query == null || query.equalsIgnoreCase("exit")) {
				break;
			} else if (query.matches("query\\s+.*")) {
				String quer = query.replaceFirst("query", "");
				results = search.getResults(quer);
				printResults(results);

			} else if (query.matches("type\\s+.*")) {
				if (results == null) {
					System.out.println("Not allowed. First enter query.");
				} else {
					typeCommand(query);
				}

			} else if (query.matches("results\\s*")) {
				if (results == null) {
					System.out.println("Not allowed. First enter query.");
				} else {
					printResults(results);
				}

			} else {
				System.out.println("Unkonwn command.");
			}
		}
	}

	/**
	 * Method represents type command with given query. Prints document with
	 * given index in query.
	 * 
	 * @param query
	 *            given query
	 * @throws IllegalArgumentException
	 *             if index is invalid
	 */
	private static void typeCommand(String query) {
		String num = query.replaceFirst("type", "");
		int number;
		try {
			number = Integer.parseInt(num.trim());
		} catch (NumberFormatException e) {
			System.out.println("Invalid query: " + query);
			return;
		}
		if (number >= counter || number < 0) {
			System.out.println("Invalid index: " + number);
			return;
		}
		byte[] array = null;
		try {
			array = Files.readAllBytes(results.get(number).getSource());
		} catch (IOException ignorable) {
			return;
		}
		String body = new String(array, StandardCharsets.UTF_8);
		System.out.println(body);

	}

	/**
	 * Prints given list results on {@link System#out}
	 * 
	 * @param results
	 *            given results
	 */
	private static void printResults(List<Result> results) {
		StringBuilder sb = new StringBuilder();
		counter = 0;
		for (Result res : results) {
			sb.append("[ ").append(counter).append("] ").append("(")
					.append(String.format("%.4f", res.getCosine()))
					.append(") ").append(res.getSource()).append("\n");
			counter++;
			if (counter >= 10) {
				break;
			}
		}

		if (sb.length() == 0) {
			sb.append("Empty results.");
		} else {
			sb.insert(0, "Top " + counter + " results:\n");
		}
		System.out.println(sb.toString());
	}
}
