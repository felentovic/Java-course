package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Wrapper which value can be Integer, Double, String or null. Via constructor
 * accepts and string representation of number. Provides simple arithmetic
 * methods.
 *
 * @author Borna Feldšar
 * @version 1.0
 */
public class ValueWrapper {

	/**
	 * value that ValueWrapper contains
	 */
	private Object value;
	/**
	 * Used to set separator symbol to {@code .}
	 */
	private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	/**
	 * Formatter used to parse given double value from string
	 */
	private static DecimalFormat formatter;

	/**
	 * Constructs new ValueWrapper form given value. Value can be Integer,
	 * Double or String representing number.
	 *
	 * @param value
	 *            value
	 * @throws RuntimeException
	 *             if value is not Integer, Double or String representing
	 *             number.
	 */
	public ValueWrapper(Object value) {
		symbols.setDecimalSeparator('.');
		formatter = new DecimalFormat("0.##", symbols);

		this.value = value;
	}

	/**
	 * Getter for value.
	 *
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for value.
	 *
	 * @param value
	 *            new value in ValueWrapper
	 * @throws RuntimeException
	 *             if given value is not Integer, Double or String representing
	 *             number
	 */
	public void setValue(Object value) {
		this.value = value;

	}

	/**
	 * Increments this value with given value and stores result in this value.
	 *
	 * @param incValue
	 *            value with which this value is incremented. Can be Integer,
	 *            Dobule, String representing number or ValueWrapper instance
	 * @return return this {@link ValueWrapper}
	 * @throws RuntimeException
	 *             if given value is not Integer,Double or String
	 */
	public ValueWrapper increment(Object incValue) {
		value = operation(incValue, (o1, o2) -> o1 + o2);
		return this;
	}

	/**
	 * Decremetns this value with given value and stores result in this value.
	 *
	 * @param decValue
	 *            value with which this value is decremented. Can be Integer,
	 *            Dobule, String representing number or ValueWrapper instance
	 * @return return this {@link ValueWrapper}
	 *
	 * @throws RuntimeException
	 *             if given value is not Integer,Double or String
	 */
	public ValueWrapper decrement(Object decValue) {
		value = operation(decValue, (o1, o2) -> o1 - o2);
		return this;

	}

	/**
	 * Multiplies this value with given value and stores result in this value.
	 *
	 * @param mulValue
	 *            value with which this value is multiplied. Can be Integer,
	 *            Dobule, String representing number or ValueWrapper instance
	 * @return return this {@link ValueWrapper}
	 *
	 * @throws RuntimeException
	 *             if given value is not Integer,Double or String
	 */
	public ValueWrapper multiply(Object mulValue) {
		value = operation(mulValue, (o1, o2) -> o1 * o2);
		return this;

	}

	/**
	 * Divides this value with given value and stores result in this value.
	 *
	 * @param divValue
	 *            value with which this value is divided. Can be Integer,
	 *            Dobule, String representing number or ValueWrapper instance
	 * @return return this {@link ValueWrapper}
	 *
	 * @throws RuntimeException
	 *             if given value is not Integer,Double or String
	 * @throws IllegalArgumentException
	 *             if second argument is zero
	 */
	public ValueWrapper divide(Object divValue) {
		value = operation(divValue,
				(o1, o2) -> {
					if (o2 == 0) {
						throw new IllegalArgumentException(
								"You can't divide by zero.");
					}
					return o1 / o2;
				});
		return this;
	}

	/**
	 * The method returns an integer less than zero if currently stored value is
	 * smaller than argument, an integer greater than zero if currently stored
	 * value is larger than argument or an integer 0 if they are equal.
	 *
	 * @param withValue
	 *            value that is compared with this value. It can be Double,
	 *            Integer, String representation of number or ValueWrapper
	 * @return return an integer less than zero if currently stored value is
	 *         smaller than argument, an integer greater than zero if currently
	 *         stored value is larger than argument or an integer 0 if they are
	 *         equal.
	 * @throws RuntimeException
	 *             if given value is not ValueWrapper,
	 */
	public int numCompare(Object withValue) {
		Object value1 = value;
		Object value2 = null;

		if (withValue instanceof ValueWrapper) {
			value2 = ((ValueWrapper) withValue).getValue();
		} else {
			value2 = new ValueWrapper(withValue).getValue();
		}

		if (value1 == null) {
			value1 = Integer.valueOf(0);
		}
		if (value2 == null) {
			value2 = Integer.valueOf(0);
		}

		if (value1 instanceof String) {
			value1 = parseValue(value1);
		}

		if (value2 instanceof String) {
			value2 = parseValue(value2);
		}

		if (value1 instanceof Integer) {
			value1 = ((Integer) value1).doubleValue();
		}

		if (value2 instanceof Integer) {
			value2 = ((Integer) value2).doubleValue();
		}

		return ((Double) value1).compareTo((Double) value2);
	}

	/**
	 * Return true if object value is integer number, otherwise false.
	 *
	 * @param value
	 *            check type of value
	 * @return true if object value is integer number, false if it is double.
	 * @throws RuntimeException
	 *             if value is not Integer or Double
	 */
	private boolean checkNumber(Object value) {
		boolean isValueInteger = false;

		if (value == null) {
			isValueInteger = true;

		} else if (value instanceof Integer) {
			isValueInteger = true;

		} else if (value instanceof Double) {
			isValueInteger = false;

		} else {
			throw new RuntimeException(
					"Unsuported value type in ValueWrapper argument.");
		}

		return isValueInteger;

	}

	/**
	 * Parses given string if it represents double or integer number.
	 *
	 * @param value
	 *            string that is parsed
	 * @return object from given string
	 * @throws RuntimeException
	 *             if string can not be parsed in double or integer value
	 */
	private Object parseValue(Object value) {
		final String str = (String) value;
		Object obj = null;
		boolean thrw = false;

		if (str.contains(".") || str.contains("E")) {
			try {
				obj = formatter.parse(str).doubleValue();
			} catch (final ParseException e) {
				thrw = true;
			}
		} else {
			try {
				obj = Integer.parseInt(str);
			} catch (final NumberFormatException e) {
				thrw = true;
			}
		}

		if (thrw) {
			throw new RuntimeException(
					"Unsuported value type in ValueWrapper argument for parsing String: "
							+ value);
		}
		return obj;
	}

	/**
	 * Interface used for strategy patter. Provides one method operation.
	 *
	 * @author Borna Feldšar
	 * @version 1.0
	 */
	private interface IOperation {

		/**
		 * Method returns result of operation of first argument on second one.
		 *
		 * @param arg1
		 *            first argument
		 * @param arg2
		 *            second argument
		 * @return result of operation
		 */
		Double operation(double arg1, double arg2);
	}

	/**
	 * Returns value of operation of this value on given value. Operation is
	 * provided via IOperation interface.
	 *
	 * @param givenValue
	 *            value over which operation with this value is done
	 * @param oper
	 *            instance of interface IOperation
	 * @return result of operation
	 * @throws RuntimeException
	 *             if givenValue is not Integer, Double, String representing
	 *             number or ValueWrapper
	 */
	private Object operation(Object givenValue, IOperation oper) {
		Object operationValue;
		if (givenValue instanceof ValueWrapper) {
			operationValue = ((ValueWrapper) givenValue).getValue();
		} else {
			operationValue = new ValueWrapper(givenValue).getValue();
		}

		if (operationValue instanceof String) {
			operationValue = parseValue(operationValue);
		}

		final boolean isOperValInteger = checkNumber(operationValue);
		if (isOperValInteger) {
			operationValue = ((Integer) operationValue).doubleValue();
		}

		Object storedValue = value;
		if (value instanceof String) {
			storedValue = parseValue(value);
		}

		boolean isValueInteger = checkNumber(storedValue);
		if (isValueInteger) {
			if (value != null) {
				storedValue = ((Integer) storedValue).doubleValue();

			} else {
				storedValue = Integer.valueOf(0).doubleValue();

			}
		}

		Object newValue = oper.operation((double) storedValue,
				(double) operationValue);

		if (isOperValInteger && isValueInteger) {
			newValue = ((Double) newValue).intValue();

		}
		isValueInteger = checkNumber(newValue);
		return newValue;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
