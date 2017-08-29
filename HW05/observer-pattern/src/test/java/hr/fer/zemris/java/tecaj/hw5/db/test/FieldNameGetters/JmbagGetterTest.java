package hr.fer.zemris.java.tecaj.hw5.db.test.FieldNameGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters.JmbagGetter;

import org.junit.Assert;
import org.junit.Test;

public class JmbagGetterTest {
				
	@Test
	public void Jmbagetter_True_Value() {
		StudentRecord student= new StudentRecord("0036476676", "Živković", "Pejo", 4);
		IFieldValueGetter getter= new JmbagGetter();
		
		Assert.assertEquals(getter.get(student),"0036476676");
	}
	

	
	@Test(expected=IllegalArgumentException.class)
	public void JmbagGetter_IllegalArgumentException_Null() {
		IFieldValueGetter getter= new JmbagGetter();
		
		Assert.assertEquals(getter.get(null),"");
	}
}
