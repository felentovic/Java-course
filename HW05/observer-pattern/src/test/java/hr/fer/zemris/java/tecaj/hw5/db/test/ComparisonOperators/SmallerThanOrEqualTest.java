package hr.fer.zemris.java.tecaj.hw5.db.test.ComparisonOperators;

import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.SmallerThanOrEqual;

import org.junit.Assert;
import org.junit.Test;

public class SmallerThanOrEqualTest {
		
	@Test
	public void SmallerThanOrEqual_True_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new SmallerThanOrEqual();
		Assert.assertTrue(operator.satisfied("Šimić","Zec" ));
	}
	
	@Test
	public void SmallerThanOrEqual_False_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new SmallerThanOrEqual();
		Assert.assertFalse(operator.satisfied("Zec", "Akšamović"));
	}
}
