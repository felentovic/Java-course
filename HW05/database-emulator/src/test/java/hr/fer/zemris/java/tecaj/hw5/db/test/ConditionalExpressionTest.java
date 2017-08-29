package hr.fer.zemris.java.tecaj.hw5.db.test;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.ConditionalExpression;
import hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters.*;
import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.*;

public class ConditionalExpressionTest {

	@Test
	public void getFieldGetter_True_Value() {
		ConditionalExpression expression = new ConditionalExpression(
				new LastNameGetter(), "Bos*", new Equal());
		Assert.assertTrue(expression.getFieldGetter() instanceof IFieldValueGetter);
	}

	@Test
	public void getStringLitteral_True_Value() {
		ConditionalExpression expression = new ConditionalExpression(
				new LastNameGetter(), "Bos*", new Equal());
		Assert.assertEquals(expression.getStringLiteral(), "Bos*");
	}
	
	@Test
	public void getComparisonOperator_True_Value() {
		ConditionalExpression expression = new ConditionalExpression(
				new LastNameGetter(), "Bos*", new Equal());
		Assert.assertTrue(expression.getComparisonOperator() instanceof IComparisonOperator);
	}
	
}
