package hr.fer.zemris.java.tecaj.hw5.db.test.FieldNameGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters.LastNameGetter;

import org.junit.Assert;
import org.junit.Test;

public class LastNameGetterTest {
			
	@Test
	public void LastNameGetter_True_Value() {
		StudentRecord student= new StudentRecord("0036476676", "Živković", "Pejo", 4);
		IFieldValueGetter getter= new LastNameGetter();
		
		Assert.assertEquals(getter.get(student),"Živković");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void LastNameGetter_IllegalArgumentException_Null() {
		IFieldValueGetter getter= new LastNameGetter();
		
		Assert.assertEquals(getter.get(null),"");
	}
}
