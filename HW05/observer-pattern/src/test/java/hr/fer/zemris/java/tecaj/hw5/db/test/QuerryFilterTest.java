package hr.fer.zemris.java.tecaj.hw5.db.test;

import hr.fer.zemris.java.tecaj.hw5.db.QueryFilter;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

import org.junit.Assert;
import org.junit.Test;

public class QuerryFilterTest {
	
	@Test
	public void accepts_True_TestingParsing(){
		QueryFilter filter = new QueryFilter(" lastName=\"Živ*\" and firstName<\"Z\"");
		StudentRecord student= new StudentRecord("0036476676", "Živković", "Pejo", 4);
		
		Assert.assertTrue(filter.accepts(student));
	}
	
	@Test
	public void consturctor_IllegalArgumentException_ParsingForJMBAG(){
		QueryFilter filter = new QueryFilter(" lastName=\"Živ*\" and jmbag>\"0036476676\"");
		StudentRecord student= new StudentRecord("0036476676", "Živković", "Pejo", 4);
		Assert.assertFalse(filter.accepts(student));
	}
	
	@Test
	public void getJMBAG_IllegalArgumentException_ParsingForJMBAG(){
		QueryFilter filter = new QueryFilter(" lastName=\"Živ*\" and jmbag=\"0036476676\"");
		Assert.assertEquals("0036476676", filter.getJMBAG().get());
	}
	
	//exceptions
	@SuppressWarnings("unused")
	@Test(expected= IllegalArgumentException.class)
	public void constructor_IllegalArgumentException_InvalidQuery(){
		QueryFilter filter = new QueryFilter(" lastName=\"Živ*\" and grade=5");
		StudentRecord student= new StudentRecord("0036476676", "Živković", "Pejo", 4);
	}
	
	@SuppressWarnings("unused")
	@Test(expected= IllegalArgumentException.class)
	public void constructor_IllegalArgumentException_NotEnoughAnd(){
		QueryFilter filter = new QueryFilter(" lastName=\"Živ*\" firstName<\"P\"");
		StudentRecord student= new StudentRecord("0036476676", "Živković", "Pejo", 4);
	}
	
	@Test(expected= IllegalArgumentException.class)
	public void getJMBAG_IllegalArgumentException_ParsingForJMBAGTwice(){
		QueryFilter filter = new QueryFilter(" jmbag=\"000000003\" and jmbag=\"0036476676\"");
		Assert.assertEquals("0036476676", filter.getJMBAG().get());
	}
	
	
	
}
