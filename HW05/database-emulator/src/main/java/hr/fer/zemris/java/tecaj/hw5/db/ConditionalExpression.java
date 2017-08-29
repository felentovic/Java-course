package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters.IFieldValueGetter;

/**
 * Class uses for making an expression that consists with operator, fieldGetter
 * and string litteral.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class ConditionalExpression {
	IFieldValueGetter fieldGetter;
	String literal;
	IComparisonOperator comparisonOperator;

	/**
	 * Constructs new conditional expression.
	 * 
	 * @param fieldGetter
	 *            fieldGetter allows to get name from record
	 * @param literal
	 *            litteral which fieldGetter value will be compared
	 * @param comparisonOperator
	 *            operator for fieldGetter value and litteral will be compared
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String literal,
			IComparisonOperator comparisonOperator) {
		if (fieldGetter == null || literal == null
				|| comparisonOperator == null) {
			throw new IllegalArgumentException(
					"Null references given as conditionalExpression parameters.");
		}
		this.fieldGetter = fieldGetter;
		this.literal = literal;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter for fieldGetter.
	 * 
	 * @return fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for literal
	 * 
	 * @return literal
	 */
	public String getStringLiteral() {
		return literal;
	}

	/**
	 * Getter for comparisonOperator
	 * 
	 * @return comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
