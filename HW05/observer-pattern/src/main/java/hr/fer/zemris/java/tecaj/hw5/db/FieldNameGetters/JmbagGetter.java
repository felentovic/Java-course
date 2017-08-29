package hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class implements IFieldValueGetter and it is used for strategy pattern.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public  class JmbagGetter implements IFieldValueGetter {

	/**
	 * Returns jmbag field form studentRecord.
	 */
	@Override
	public String get(StudentRecord record) {
		if (record == null) {
			throw new IllegalArgumentException("Null given as record.");
		}
		return record.getJmbag();
	}

}
