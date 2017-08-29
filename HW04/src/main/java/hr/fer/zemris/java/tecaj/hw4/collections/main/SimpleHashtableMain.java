package hr.fer.zemris.java.tecaj.hw4.collections.main;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;
/**
 * Class uses for SimpleHashtable main method.
 * @author Borna Feldšar
 * @version	1.0
 */
public class SimpleHashtableMain {
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable examMarks = new SimpleHashtable(2);
		// fill data:
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5)); // overwrites old grade for
													// Ivana
		// query collection:
		Integer kristinaGrade = (Integer) examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes:
																			// 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes:
																			// 4
		System.out.println(examMarks);


	}
}
