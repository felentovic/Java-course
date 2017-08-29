package hr.fer.zemris.java.tecaj.hw5.db.test.ComparisonOperators;

import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.GreatherThan;
import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.IComparisonOperator;

import org.junit.Assert;
import org.junit.Test;

public class GreatherThanTest {
			
	@Test
	public void GreatherThan_False_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new GreatherThan();
		Assert.assertFalse(operator.satisfied("Šimić","Zec" ));
	}
	
	@Test
	public void GreatherThan_True_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new GreatherThan();
	
		Assert.assertTrue(operator.satisfied("Zec", "Feldšar"));
	}
}
