package hr.fer.zemris.java.tecaj.hw5.db.test;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.main.ProcessQuery;

public class ProcessQueryTest {
		
	@Test
	public void process_ValidQuery(){
		List<String> lines = new LinkedList<>();
		lines.add("0000000001	Akšamović	Marin	5");
		lines.add("0000000002	Bakamović	Petra	3");
		
		StudentDatabase database = new StudentDatabase(lines);
		String line=" lastName=\"*\"";
		ProcessQuery processQuery= new ProcessQuery(line, database);
		processQuery.process();
	}
	
	@Test
	public void process_UsingIndexForRetrieval(){
		List<String> lines = new LinkedList<>();
		lines.add("0000000001	Akšamović	Marin	5");
		lines.add("0000000002	Bakamović	Petra	3");
		
		StudentDatabase database = new StudentDatabase(lines);
		String line=" jmbag=\"0000000001\"";
		ProcessQuery processQuery= new ProcessQuery(line, database);
		processQuery.process();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void process_IllegalArgumentExcetpion_AndMisses(){
		List<String> lines = new LinkedList<>();
		lines.add("0000000001	Akšamović	Marin	3");
		lines.add("0000000002	Bakamović	Petra	3");
		
		StudentDatabase database = new StudentDatabase(lines);
		String line="lastName=\"*\" firstName=\"Marin\"";
		ProcessQuery processQuery= new ProcessQuery(line, database);
		processQuery.process();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void process_IllegalArgumentExcetpion_DifferentJmbags(){
		List<String> lines = new LinkedList<>();
		lines.add("0000000001	Akšamović	Marin	3");
		lines.add("0000000002	Bakamović	Petra	3");
		
		StudentDatabase database = new StudentDatabase(lines);
		String line="jmbag=\"000000001\" and jmbag=\"0000000001\"";
		ProcessQuery processQuery= new ProcessQuery(line, database);
		processQuery.process();
	}
	
}
