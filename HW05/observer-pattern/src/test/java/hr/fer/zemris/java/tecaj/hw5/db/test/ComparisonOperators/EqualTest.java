package hr.fer.zemris.java.tecaj.hw5.db.test.ComparisonOperators;

import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.Equal;
import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.IComparisonOperator;

import org.junit.Assert;
import org.junit.Test;

public class EqualTest {
			
	@Test
	public void Equal_True_ComparingTwoStringsUTF8(){
		IComparisonOperator operator= new Equal();
		Assert.assertTrue(operator.satisfied("Šimić","Ši*" ));
		Assert.assertTrue(operator.satisfied("Zec", "*ec"));
		Assert.assertTrue(operator.satisfied("Zec", "*"));
		Assert.assertTrue(operator.satisfied("Zec", "Zec"));
	}
}
