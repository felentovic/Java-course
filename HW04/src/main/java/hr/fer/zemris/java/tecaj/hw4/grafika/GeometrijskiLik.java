package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa reprezentira geometrijski lik s metodoma crtanja na system out.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public abstract class GeometrijskiLik {

	/**
	 * Vraca true ako geometrijski lik sadrzi tocku, inace false.
	 * 
	 * @param X
	 *            x koordinata
	 * @param Y
	 *            y koordinata
	 * @return true ako geometrijski lik sadrzi tocku, inace false.
	 */
	public abstract boolean sadrziTocku(int X, int Y);

	/**
	 * Pali piksele na slici i tako graficki prikazuje geometrijski lik.
	 * 
	 * @param slika
	 *            slika na kojoj se pale pikseli
	 */
	public void popuniLik(Slika slika) {
		for (int y = 0, sirinaSlike = slika.getSirina(), visinaSlike = slika
				.getVisina(); y < sirinaSlike; y++) {

			for (int x = 0; x < visinaSlike; x++) {
				if (sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}
	
}
