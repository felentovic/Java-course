package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa predstavlja geometrijski lik elipsu. Sadrzi metode za crtanje elipse na
 * sliku.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Elipsa extends GeometrijskiLik {
	private int x, y, horizontalniRadijus, vertikalniRadijus;

	public static final StvarateljLika STVARATELJ = new ElipsaStvaratelj();

	/**
	 * Konstruira elipsu zadanu s koordinatama sredista i duljinama male i
	 * velike poluosi.
	 * 
	 * @param x
	 *            x kooridnata sredista
	 * @param y
	 *            y kooridnata sredista
	 * @param horizontalniRadijus
	 *            duljina horizontalne poluosi
	 * @param vertikalniRadijus
	 *            duljina vertikalne poluosi
	 * @throws IllegalArgumentException
	 *             ako su horizontalni ili vertikalni radijus negativni
	 */
	public Elipsa(int x, int y, int horizontalniRadijus, int vertikalniRadijus) {
		if (horizontalniRadijus <= 0 || vertikalniRadijus <= 0) {
			throw new IllegalArgumentException(
					"Negativni radijusi u konstrukciji elipse.");
		}
		this.x = x;
		this.y = y;
		this.horizontalniRadijus = horizontalniRadijus;
		this.vertikalniRadijus = vertikalniRadijus;
	}

	/**
	 * Vraca true ako elipsa sadrzi tocku, inace false.
	 */
	@Override
	public boolean sadrziTocku(int X, int Y) {

		return (Math.pow(X - x, 2) / Math.pow(horizontalniRadijus, 2)
				+ Math.pow(Y - y, 2) / Math.pow(vertikalniRadijus, 2) <= 1.0);

	}

	@Override
	public void popuniLik(Slika slika) {
		int sirinaSlike = slika.getSirina();
		int visinaSlike = slika.getVisina();

		if (x + horizontalniRadijus < 0
				|| x - horizontalniRadijus >= sirinaSlike
				|| y + vertikalniRadijus < 0
				|| y - vertikalniRadijus >= visinaSlike) {
			return;
		}
		for (int j = y - vertikalniRadijus; j <= y + vertikalniRadijus; j++) {
			for (int i = x - horizontalniRadijus; i <= x + horizontalniRadijus; i++) {
				if (Math.pow(i - x, 2) / Math.pow(horizontalniRadijus, 2)
						+ Math.pow(j - y, 2) / Math.pow(vertikalniRadijus, 2) <= 1.0) {
					if (i >= 0 && i < sirinaSlike && j >= 0 && j < visinaSlike) {
						slika.upaliTocku(i, j);
					}
				}
			}
		}
	}

	/**
	 * Klasa sluzi za stvaranje novog lika elipse i implementira sucelje
	 * stvaratelj lika.
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 */
	private static class ElipsaStvaratelj implements StvarateljLika {

		/**
		 * Vraca naziv lika "ELIPSA"
		 */
		@Override
		public String nazivLika() {
			return "ELIPSA";
		}

		/**
		 * Vraca elipsu s karakteristikama primljenih kroz parametri.
		 * 
		 * @throws IllegalArgumentException
		 *             ako nisu dana 4 argumenta unutar parametri
		 * @throws IllegalArgumentException
		 *             ako parametri nisu integer brojevi
		 * @throws IllegalArgumentException
		 *             ako su horizontalni ili negativni radijus negativni
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
			return new Elipsa(parametriBrojevi[0], parametriBrojevi[1],
					parametriBrojevi[2], parametriBrojevi[3]);
		}
	}

}
