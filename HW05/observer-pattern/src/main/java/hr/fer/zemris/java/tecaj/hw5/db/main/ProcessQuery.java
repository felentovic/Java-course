package hr.fer.zemris.java.tecaj.hw5.db.main;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.QueryFilter;
import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class process given query on database. And prints table on system out.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class ProcessQuery {
	private static int jmbagLength = 10;
	private String line;
	private StudentDatabase database;

	/**
	 * Constructor saves given database and query in String.
	 * 
	 * @param line
	 *            given query
	 * @param database
	 *            student database
	 * @throws IllegalArgumentException
	 *             if query is in invalid form
	 */
	public ProcessQuery(String line, StudentDatabase database) {
		this.line = line;
		this.database = database;

	}
	
	/**
	 * Process given query on student database.
	 */
	public void process() {
		QueryFilter queryFilter = new QueryFilter(line);

		if (queryFilter.getJMBAG().isPresent()) {
			List<StudentRecord> jmbag = new LinkedList<StudentRecord>();
			StudentRecord student = database.forJMBAG(queryFilter.getJMBAG()
					.get());
			boolean accepts = false;
			try {
				accepts = queryFilter.accepts(student);
			} catch (IllegalArgumentException e) {

			}
			System.out.println("Using index for record retrieval.");
			if (accepts) {
				jmbag.add(student);
				PrintTable table = new PrintTable(jmbag, jmbagLength, student
						.getLastName().length(), student.getFirstName()
						.length());
				System.out.println(table.getTable());
				System.out.printf("Records selected:%d\n", jmbag.size());
			} else {
				System.out.printf("Records selected:%d\n", 0);
			}
		} else {

			List<StudentRecord> students = database.filter(queryFilter);

			int maxLastNameLength = 0;
			int maxFirstNameLength = 0;
			for (StudentRecord student : students) {
				int firstNameLength = student.getFirstName().length();
				int lastNameLength = student.getLastName().length();
				if (firstNameLength > maxFirstNameLength) {
					maxFirstNameLength = firstNameLength;
				}

				if (lastNameLength > maxLastNameLength) {
					maxLastNameLength = lastNameLength;
				}
			}
			if (students.size() > 0) {
				PrintTable table = new PrintTable(students, jmbagLength,
						maxLastNameLength, maxFirstNameLength);
				System.out.println(table.getTable());
			}
			System.out.printf("Records selected:%d\n", students.size());
		}
	}
}
