package hr.fer.zemris.java.tecaj.hw5.db.test;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.main.PrintTable;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PrintTableTest {

	@Test
	public void getTable_True_Value() {
		List<StudentRecord> students = new LinkedList<>();
		students.add(new StudentRecord("0000000060", "Vignjevanović", "Irena",
				5));
		students.add(new StudentRecord("0000000061", "Vukojević", "Renato", 3));
		PrintTable table = new PrintTable(students, 10, 13, 6);
		Assert.assertEquals(
				  "+============+===============+========+===+\n"
				+ "| 0000000060 | Vignjevanović | Irena  | 5 |\n"
				+ "| 0000000061 | Vukojević     | Renato | 3 |\n"
				+ "+============+===============+========+===+\n",table.getTable());
	}
}
