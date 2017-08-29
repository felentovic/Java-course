package hr.fer.zemris.java.tecaj.hw5.db.main;

import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class used for demonstration for searching database.First word in input must
 * be query and rest is statement. It allows searching with and operator.
 * You can search by last name and first name only using letters, and by jmbag using numbers. 
 * Letters and numbers must be inside qoutes.
 * Database searching is ended entering word: quit.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class StudentDB {

	/**
	 * Main method that runs at the beggining of program.
	 * 
	 * @param args
	 *            arguments of method main
	 */
	public static void main(String[] args) {

		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./dat/database.txt"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Unknown directory " + e.getMessage());
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		StudentDatabase database = new StudentDatabase(lines);

		while (true) {
			System.out.print("> ");

			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				System.out.println("Unknown I/O error.");
				System.exit(1);
			}

			if (line == null) {
				System.out.println("Unknown I/O error.");
				System.exit(1);
			}
			
			if (line.matches("(\\s+)?(?i)quit(\\s+)?")) {
				System.out.print("End database searching.");
				break;
			}

			if (!line.matches("(\\s+)?(?i)query\\s+.*")) {
				System.out
						.println("Invalid entry. Query must start with word: query.");
				continue;
			}

			line = line.trim().replace("query", "");
			ProcessQuery processQuery=new ProcessQuery(line, database);
			try {
				processQuery.process();
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}

}
