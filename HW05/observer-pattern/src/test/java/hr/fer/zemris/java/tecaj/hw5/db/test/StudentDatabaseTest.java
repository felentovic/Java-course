package hr.fer.zemris.java.tecaj.hw5.db.test;


import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StudentDatabaseTest {
	
	@Test
	public void constructor_True_TestArguments() {
		List<String> lines = new LinkedList<>();
		lines.add("0000000001	Akšamović	Marin	2");
		lines.add("0000000002	Bakamović	Petra	3");
		StudentDatabase database = new StudentDatabase(lines);
		Assert.assertEquals( new StudentRecord(
				"0000000001", "Akšamović", "Marin", 2),database.forJMBAG("0000000001"));

	}
	
	@SuppressWarnings("unused")
	@Test(expected= IllegalArgumentException.class)
	public void constructor_IllegalArgumentException_GradeBiggerThan5() {
		List<String> lines = new LinkedList<>();
		lines.add("0000000001	Akšamović	Marin	7");
		lines.add("0000000002	Bakamović	Petra	3");
		StudentDatabase database = new StudentDatabase(lines);

	}
	
	@SuppressWarnings("unused")
	@Test(expected= IllegalArgumentException.class)
	public void constructor_NumberFormatException_NotInteger() {
		List<String> lines = new LinkedList<>();
		lines.add("0000000001	Akšamović	Marin	7.5");
		lines.add("0000000002	Bakamović	Petra	3");
		StudentDatabase database = new StudentDatabase(lines);

	}
}
