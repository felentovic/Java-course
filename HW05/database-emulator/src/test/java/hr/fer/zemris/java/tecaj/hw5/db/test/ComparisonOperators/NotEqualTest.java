package hr.fer.zemris.java.tecaj.hw5.db.test.ComparisonOperators;

import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.NotEqual;

import org.junit.Assert;
import org.junit.Test;

public class NotEqualTest {
			

	@Test
	public void NotEqual_True_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new NotEqual();
		Assert.assertTrue(operator.satisfied("Šimić","Zec" ));
		
	}
	
	@Test
	public void NotEqual_False_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new NotEqual();
		Assert.assertFalse(operator.satisfied("Zec","Zec" ));
		
	}
}
