package hr.fer.zemris.java.tecaj.hw5.db.test;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

import org.junit.Test;
import org.junit.Assert;

public class StudentRecordTest {

			@SuppressWarnings("unused")
			@Test(expected= IllegalArgumentException.class)
			public void constructor_IllegalArgumentException_GradeGreatherThan5(){
				StudentRecord student= new StudentRecord("0000000001","Akšamović","Marin",7);
			
			}
			
			@Test
			public void setFinalGrade_True_SettingNewGrade(){
				StudentRecord student= new StudentRecord("0000000001","Akšamović","Marin",5);
				student.setFinalGrade(4);
				Assert.assertEquals(4, student.getFinalGrade());
			}
			
			@Test
			public void getJmbag_True_Getter(){
				StudentRecord student= new StudentRecord("0000000001","Akšamović","Marin",5);
				Assert.assertEquals("0000000001", student.getJmbag());
			}
			
			@Test
			public void getJFirstName_True_Getter(){
				StudentRecord student= new StudentRecord("0000000001","Akšamović","Marin",5);
				Assert.assertEquals("Marin", student.getFirstName());
			}
			
			@Test
			public void getJLastName_True_Getter(){
				StudentRecord student= new StudentRecord("0000000001","Akšamović","Marin",5);
				Assert.assertEquals("Akšamović", student.getLastName());
			}
}
