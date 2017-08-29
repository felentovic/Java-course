package hr.fer.zemris.java.tecaj.hw5.db.main;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

import java.util.List;

/**
 * Class makes table string from given list of students.
 * @author Borna Felšar
 * @version 1.0
 */
public class PrintTable {
	private StringBuilder table= new StringBuilder();
	
	/**
	 * Constructs table as a string from given list of students.
	 * @param students list of students 
	 * @param jmbagLength jmbag length
	 * @param maxLastNameLength maximum last name length
	 * @param maxFirstNameLength maximum first name length
	 */
	public PrintTable(List<StudentRecord> students, int jmbagLength,
			int maxLastNameLength, int maxFirstNameLength) {
		plus();
		for (int i = 0; i < jmbagLength + 2; i++) {
			equal();
		}
		plus();
		for (int i = 0; i < maxLastNameLength + 2; i++) {
			equal();
		}
		plus();
		for (int i = 0; i < maxFirstNameLength + 2; i++) {
			equal();
		}
		plus();
		for (int i = 0; i < 1 + 2; i++) {
			equal();
		}
		plus();
		table.append("\n");
		for (StudentRecord student : students) {
			line();
			table.append(" "+student.getJmbag()+" ");
			line();
			table.append(" "+student.getLastName()+" ");
			for (int i = 0; i < maxLastNameLength
					- student.getLastName().length(); i++) {
				table.append(" ");
			}
			line();
			table.append(" "+student.getFirstName()+" ");
			for (int i = 0; i < maxFirstNameLength
					- student.getFirstName().length(); i++) {
				table.append(" ");
			}
			line();
			table.append(" "+student.getFinalGrade()+" ");
			line();
			table.append("\n");
		}
		plus();
		for (int i = 0; i < jmbagLength + 2; i++) {
			equal();
		}
		plus();
		for (int i = 0; i < maxLastNameLength + 2; i++) {
			equal();
		}
		plus();
		for (int i = 0; i < maxFirstNameLength + 2; i++) {
			equal();
		}
		plus();
		for (int i = 0; i < 1 + 2; i++) {
			equal();
		}
		plus();
		table.append("\n");
	}
	
	/**
	 * Returns table as string.
	 * @return table as string.
	 */
	public String getTable() {
		return table.toString();
	}

	/**
	 * Prints '+' on system out.
	 */
	private  void plus() {
		table.append("+");
	}

	/**
	 * Prints '=' on system out.
	 */
	private  void equal() {
		table.append("=");
	}

	/**
	 * Prints '' on system out.
	 */
	private  void line() {
		table.append("|");
	}

}
