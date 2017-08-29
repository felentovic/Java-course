package hr.fer.zemris.java.tecaj.hw5.db.test;

import hr.fer.zemris.java.tecaj.hw5.db.main.StudentDB;

import java.io.ByteArrayInputStream;
import org.junit.Test;

public class StudentDBTest {

	@Test
	public void main() {
		String str = "query lastName=\"*\"\n" 
					+ "firstName=\"Marko\"\n"
					+ "quit\n";
		ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
		System.setIn(in);
		StudentDB.main(null);
	}
}
