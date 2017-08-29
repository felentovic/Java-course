package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Constructor must get a list of String objects and create an internal list of
 * student records.
 * 
 * @author Borna Feldšar
 * @version 1.1
 *
 */
public class StudentDatabase {
	private Set<StudentRecord> students = new LinkedHashSet<>();
	private String[] studentParts;
	private Map<String, StudentRecord> studentsMap = new HashMap<>();

	/**
	 * Constructor gets list of lines.Parses given list into students and
	 * creates internal list of student records.
	 * 
	 * @param lines
	 *            lines wich contains jmbag, lastName, firstName and finalGrade.
	 * @throws IllegalArgumentException
	 *             if grade is not from 1 to 5.
	 */
	public StudentDatabase(List<String> lines) {
		for (String c : lines) {
			studentParts = c.split("\t");
			if (studentParts.length < 4) {
				throw new ArithmeticException(
						"Invalid number of arguments in List students");
			}
			String jmbag = studentParts[0];
			StringBuilder lastName = new StringBuilder();
			for (int i = 1; i < studentParts.length - 2; i++) {
				lastName.append(studentParts[i]).append(" ");
			}
			String lastNameString = lastName
					.substring(0, lastName.length() - 1);
			String firstName = studentParts[studentParts.length - 2];

			int finalGrade = 0;
			try {
				finalGrade = Integer
						.parseInt(studentParts[studentParts.length - 1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Invalid number integer number: "
								+ studentParts[studentParts.length - 1]);
			}
			if (finalGrade < 1 || finalGrade > 5) {
				throw new IllegalArgumentException(
						"Valid grades are from 1 to 5");
			}

			StudentRecord student = new StudentRecord(jmbag, lastNameString,
					firstName, finalGrade);

			studentsMap.put(student.getJmbag(), student);
			this.students.add(student);
		}
	}

	/**
	 * Returns student for given jmbag. If record doesn't exist returns null.
	 * 
	 * @param jmbag
	 *            student jmbag
	 * @return student for given jmbag, or null if student with given jmbag doesnt exist
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentsMap.get(jmbag);
	}

	/**
	 * It calls accepts method on given filter-object with current record; each
	 * record for which accepts returns true is added to temporary list and this
	 * list is then returned by the filter method.
	 * 
	 * @param filter
	 *            interface with accept method
	 * @return list of records for which accepts method returns true
	 */
	public List<StudentRecord> filter(IFilter<StudentRecord> filter) {
		List<StudentRecord> filteredStudents = new LinkedList<>();
		for (StudentRecord student : students) {
			if (filter.accepts(student)) {
				filteredStudents.add(student);
			}
		}
		return filteredStudents;
	}

}
