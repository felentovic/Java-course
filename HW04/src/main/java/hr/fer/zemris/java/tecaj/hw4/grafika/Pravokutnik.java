package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa predstavlja geometrijski lik pravokutnik. Sadrzi metode za crtanje
 * pravokutnika na sliku.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class Pravokutnik extends GeometrijskiLik {

	private int visina, sirina, x1, y1;

	public static final StvarateljLika STVARATELJ = new PravokuntikStvaratelj();

	/**
	 * Konstruira pravokuntik zadan s koordinatama gornjeg lijevog vrha, visinom
	 * i sirinom.
	 * 
	 * @param x
	 *            x koordinata gornjeg lijevog vrha
	 * @param y
	 *            y koordinata gornjeg lijevog vrha
	 * @param visina
	 *            visina pravokutnika
	 * @param sirina
	 *            sirina pravokutnika
	 * @throws IllegalArgumentException
	 *             ako su visina ili sirina negativne
	 */
	public Pravokutnik(int x, int y, int visina, int sirina) {

		if (visina <= 0 || sirina <= 0) {
			throw new IllegalArgumentException(
					"Negativna sirina ili visina u parametrima pravokutnika.");
		}

		x1 = x;
		y1 = y;
		this.visina = visina;
		this.sirina = sirina;

	}

	/**
	 * Vraca true ako pravokuntik sadrzi tocku, inace false.
	 */
	@Override
	public boolean sadrziTocku(int x, int y) {
		if (x < x1 || x >= x1 + sirina) {
			return false;
		}
		if (y < y1 || y >= y1 + visina) {
			return false;
		}
		return true;
	}

	/**
	 * Pali piksele na slici i tako graficki prikazuje pravokutnik.
	 */
	@Override
	public void popuniLik(Slika slika) {
		int sirinaSlike = slika.getSirina();
		int visinaSlike = slika.getVisina();

		if (x1 >= sirinaSlike || y1 >= sirinaSlike || x1 + sirina < 0
				|| y1 + visina < 0) {
			return;
		}

		for (int i = x1; i < x1 + sirina; i++) {
			for (int j = y1; j < y1 + visina; j++) {
				if (i >= 0 && i < sirinaSlike && j >= 0 && j < visinaSlike) {
					slika.upaliTocku(i, j);
				}
			}
		}
	}

	/**
	 * Klasa sluzi za stvaranje novog lika pravokuntik i implementira sucelje
	 * stvaratelj lika.
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 */
	private static class PravokuntikStvaratelj implements StvarateljLika {

		/**
		 * Vraca naziv lika "PRAVOKUTNIK"
		 */
		@Override
		public String nazivLika() {
			return "PRAVOKUTNIK";
		}

		/**
		 * Vraca pravokuntik s karakteristikama primljenih kroz paramatri.
		 * 
		 * @throws IllegalArgumentException
		 *             ako nisu dana 4 argumenta unutar parametri
		 * @throws IllegalArgumentException
		 *             ako parametri nisu integer brojevi
		 * @throws IllegalArgumentException
		 *             ako su duljina i sirina pravokutnika negativni
		 */
		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {

			String[] parametriNiz = parametri.split("\\s+");
			if (parametriNiz.length != 4) {
				throw new IllegalArgumentException("Netocan broj argumenata.");
			}
			int[] parametriBrojevi = new int[4];
			for (int i = 0; i < parametriNiz.length; i++) {
				try {
					parametriBrojevi[i] = Integer.parseInt(parametriNiz[i]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"Nisu dani integer brojevi.");
				}
			}
			return new Pravokutnik(parametriBrojevi[0], parametriBrojevi[1],
					parametriBrojevi[2], parametriBrojevi[3]);

		}
	}
}
