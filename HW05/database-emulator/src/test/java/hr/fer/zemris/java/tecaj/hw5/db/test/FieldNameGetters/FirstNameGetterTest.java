package hr.fer.zemris.java.tecaj.hw5.db.test.FieldNameGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters.FirstNameGetter;
import hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters.IFieldValueGetter;

import org.junit.Assert;
import org.junit.Test;

public class FirstNameGetterTest {
			
	@Test
	public void FirstNameGetter_True_Value() {
		StudentRecord student= new StudentRecord("0036476676", "Živković", "Pejo", 4);
		IFieldValueGetter getter= new FirstNameGetter();
		
		Assert.assertEquals(getter.get(student),"Pejo");
	}
	
	//exceptions
		@Test(expected=IllegalArgumentException.class)
		public void FirstNameGetter_IllegalArgumentException_Null() {
			IFieldValueGetter getter= new FirstNameGetter();
			
			Assert.assertEquals(getter.get(null),"");
		}
		
}
