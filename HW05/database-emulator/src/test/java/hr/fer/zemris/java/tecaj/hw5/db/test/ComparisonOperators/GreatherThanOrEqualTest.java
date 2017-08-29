package hr.fer.zemris.java.tecaj.hw5.db.test.ComparisonOperators;

import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.GreatherThanOrEqual;
import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.IComparisonOperator;

import org.junit.Assert;
import org.junit.Test;

public class GreatherThanOrEqualTest {
			
	@Test
	public void GreatherThanOrEqual_True_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new GreatherThanOrEqual();
		Assert.assertTrue(operator.satisfied("Zec", "Zec"));
	}
	
	@Test
	public void GreatherThanOrEqual_False_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new GreatherThanOrEqual();
		Assert.assertFalse(operator.satisfied("Šimić","Zec" ));
	}
}
