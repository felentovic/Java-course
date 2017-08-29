package hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface used for strategy patter, getting field value.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public interface IFieldValueGetter {
	/**
	 * Returns requested field value from StudentRecord.
	 * 
	 * @param record
	 *            student record
	 * @return value of requested field
	 * @throws IllegalArgumentException
	 *             if null is given as record
	 */
	public String get(StudentRecord record);

}
