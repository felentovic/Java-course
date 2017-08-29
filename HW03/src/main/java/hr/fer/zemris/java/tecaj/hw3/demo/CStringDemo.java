package hr.fer.zemris.java.tecaj.hw3.demo;

import hr.fer.zemris.java.tecaj.hw3.CString;

/**
 * Class uses for CString main method.
 * @author Borna Feldšar.
 * @version 1.0
 *
 */
public class CStringDemo {
	public static void main(String[] args) {
		char[] array= new char[]{'T','a','t','a'};
		CString string= new CString(array);
		array[0]='F';
		System.out.println(string);
		System.out.println(array);
		
		System.out.println(new CString("ababab").replaceAll(new CString("ab"),
				new CString("abab")));
	}
}
