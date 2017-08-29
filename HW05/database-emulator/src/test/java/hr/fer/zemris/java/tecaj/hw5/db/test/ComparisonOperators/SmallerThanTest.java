package hr.fer.zemris.java.tecaj.hw5.db.test.ComparisonOperators;

import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.SmallerThan;

import org.junit.Assert;
import org.junit.Test;

public class SmallerThanTest {
			
	@Test
	public void SmallerThan_True_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new SmallerThan();
		Assert.assertTrue(operator.satisfied("Šimić","Zec" ));
	}
	
	@Test
	public void SmallerThan_False_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new SmallerThan();
		Assert.assertFalse(operator.satisfied("Zec","Zec" ));
	}
}
