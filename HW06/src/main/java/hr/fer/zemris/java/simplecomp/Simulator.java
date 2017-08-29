package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class creates computer with memory and registers, and user gives path to file via program 
 * arguments or enters via keyboard, where instructions for computer are writen.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Simulator {
	/**
	 * Main metoda koja sluzi za pokretanje programa.
	 * @param args argumenti main metode.
	 */
	public static void main(String[] args){
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions");
		
		String path = null;
		if (args.length == 1) {
			path = args[0];
		} else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Enter path: ");
			try {
				path = reader.readLine();
			} catch (IOException e) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				System.err.println("Error in reader.");
				System.exit(-1);
			}
			
		}
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		try {
			ProgramParser.parse(path, comp, creator);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		try {
			exec.go(comp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		
	
	}
}
