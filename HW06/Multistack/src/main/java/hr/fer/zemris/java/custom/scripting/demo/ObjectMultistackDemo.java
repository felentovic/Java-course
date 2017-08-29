package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Class that simulates use of valueWrapper.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class ObjectMultistackDemo {

	/**
	 * Main method used for run program.
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		ObjectMultistack multistack = new ObjectMultistack();
		
		try {
			ValueWrapper year = new ValueWrapper("9");
			multistack.push("year", year);
			System.out.println("Current value for year: "
					+ multistack.peek("year").getValue());

			year.divide(2);
			System.out.println("Current value for year: "+year.getValue());
			

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
