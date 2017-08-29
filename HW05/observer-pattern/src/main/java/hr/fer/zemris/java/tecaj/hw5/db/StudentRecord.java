package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Class represents student record with jmbag, first name, last name and final grade.
 * @author Borna Feldšar
 * @version 1.1
 */
public class StudentRecord {
	private final String jmbag, firstName, lastName;
	private int finalGrade;

	/**
	 * Constructs new StudentRecord with given parameters.
	 * 
	 * @param jmbag
	 *            student jmbag
	 * @param lastName
	 *            stundet last name
	 * @param firstName
	 *            student first name
	 * @param grade
	 *            students grade
	 * @throws IllegalArgumentException
	 *             if grade is not from 1 to 5.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			Integer grade) {
		if (jmbag == null || lastName == null || firstName == null) {
			throw new IllegalArgumentException("Arguments are null reference.");
		}
		if (grade < 1 || grade > 5) {
			throw new IllegalArgumentException("Valid grades are from 1 to 5");
		}
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = grade;
	}
	
	/**
	 * Returns hash code value from StudentRecord. For hash code is used jmbag.
	 */
	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}
	
	/**
	 * Returns true if records are equals. Compares by jmbag.
	 */
	@Override
	public boolean equals(Object student) {
		if (student == null || !(student instanceof StudentRecord)) {
			return false;

		}
		return jmbag.equals(((StudentRecord) student).jmbag);
	}
	
	/**
	 * Getter for jmbag.
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Getter for first name.
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Getter for last name.
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Getter for final grade.
	 * @return final grade.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	/**
	 * Setter for final grade.
	 * @param finalGrade new value of final grade
	 */
	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}

}
