package hr.fer.zemris.java.tecaj.hw4.grafika.demo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw4.grafika.*;
import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa crta likove s karakteristikama zadanim u datoteci cija se putanja
 * predaje kao argument. Drugi i treci argument su sirina, odnosno duljina
 * slike.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Crtalo {

	/**
	 * Main methoda demonstrira crtanje likova.
	 * 
	 * @param args
	 *            argumenti metode main
	 */
	public static void main(String[] args) {
		SimpleHashtable stvaratelji = null;
		try {
			stvaratelji = podesi(Linija.class, Pravokutnik.class,
					Kvadrat.class, Elipsa.class, Kruznica.class);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

		String[] definicije = null;
		if (args.length != 3) {
			System.out.println("Broj argumenata mora biti 3."
					+ " Ime datoteke, širina slike i visina slike. ");
			System.exit(1);
		}

		try {
			definicije = Files.readAllLines(Paths.get(args[0]),
					StandardCharsets.UTF_8).toArray(new String[0]);
		} catch (IOException e1) {
			System.out.println("Nepostojeca datoteka");
			System.exit(1);
		}

		GeometrijskiLik[] likovi = new GeometrijskiLik[definicije.length];
		int index = 0;
		for (String d : definicije) {
			d = d.replaceAll("\\s+", " ");
			int indexRazmaka = d.indexOf(" ");
			if (indexRazmaka < 0) {
				System.out.println("Neispravan unos u datoteci");
				System.exit(1);
			}

			String lik = d.substring(0, indexRazmaka);
			String parametri = d.substring(indexRazmaka + 1);
			StvarateljLika stvaratelj = (StvarateljLika) stvaratelji.get(lik);
			if (stvaratelj == null) {
				System.out.println("Nepostojeci lik: " + lik);
				System.exit(1);
			}

			try {
				likovi[index++] = stvaratelj.stvoriIzStringa(parametri);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage()
						+ " Slika nece biti nacrtana!");
				System.out.println("Problem u retku:" + d);
				System.exit(1);
			}
		}

		int duljinaSlike = 0;
		int sirinaSlike = 0;
		try {
			duljinaSlike = Integer.parseInt(args[1]);
			sirinaSlike = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			System.out.println("Argumenti moraju biti integer brojevi");
			System.exit(1);
		}

		if (duljinaSlike <= 0 || sirinaSlike <= 0) {
			System.out.println("Duljina i sirina slike moraju biti veci od 0");
			System.exit(1);
		}

		Slika slika = new Slika(duljinaSlike, sirinaSlike);
		for (GeometrijskiLik lik : likovi) {
			lik.popuniLik(slika);
		}

		slika.nacrtajSliku(System.out);
		PrikaznikSlike.prikaziSliku(slika);
	}

	/**
	 * Metoda zaduzena za popunavanje kolekcije, koja prima varijabilan broj
	 * argumenata: popis razreda iz kojih treba dohvatiti stvaratelje i upisati
	 * ih u kolekciju.
	 * 
	 * @param razredi
	 *            razredi iz kojih treba dohvatiti stvaratelje i upisati ih u
	 *            kolekciju.
	 * @return kolekciju s razredima i stvarateljima
	 */
	private static SimpleHashtable podesi(Class<?>... razredi) {
		SimpleHashtable stvaratelji = new SimpleHashtable();
		for (Class<?> razred : razredi) {
			try {
				Field field = razred.getDeclaredField("STVARATELJ");
				StvarateljLika stvaratelj = (StvarateljLika) field.get(null);
				stvaratelji.put(stvaratelj.nazivLika(), stvaratelj);
			} catch (Exception ex) {
				throw new RuntimeException(
						"Nije moguće doći do stvaratelja za razred "
								+ razred.getName() + ".", ex);
			}
		}
		return stvaratelji;
	}

}
