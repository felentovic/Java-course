package hr.fer.zemris.java.tecaj.hw3.demo;

import hr.fer.zemris.java.tecaj.hw3.IntegerSequence;

/**
 * Class uses for IntegerSequence main method.
 * @author Borna Feldšar
 * @version 1.0
 */
public class IntegerSequenceDemo {
	public static void main(String[] args) {
		IntegerSequence range = new IntegerSequence(1, 11, 2);
		for (int i : range) {
			for (int j : range) {
				System.out.println("i=" + i + ", j=" + j);
			}
		}

	}
}
