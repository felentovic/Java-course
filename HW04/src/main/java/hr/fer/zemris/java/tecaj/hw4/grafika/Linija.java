package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa predstavlja geometrijski lik liniju. Sadrzi metode za crtanje linije na
 * sliku.
 * 
 * @author Borna Feldšar
 * @version 1.0
 * 
 */
public class Linija extends GeometrijskiLik {

	private int x1, x2, y1, y2;

	public static final StvarateljLika STVARATELJ = new LinijaStvaratelj();

	/**
	 * Konstruira liniju zadanu s koordinatama dvije tocke.
	 * 
	 * @param x1
	 *            x koordinata prve tocke
	 * @param y1
	 *            y koordinata prve tocke
	 * @param x2
	 *            x koordinata druge tocke
	 * @param y2
	 *            y koordinata druge tocke
	 */
	public Linija(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * Vraca true ako linija sadrzi danu tocku, inace false.
	 */
	@Override
	public boolean sadrziTocku(int X, int Y) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		double error = 0;
		boolean sadrzi = false;
		if (dx == 0) {
			int max = Math.max(y1, y2);
			for (int y = Math.min(y1, y2); y < max; y++)
				if (x1 == X && y == Y) {
					sadrzi = true;
					break;
				}

		} else {
			double k = dy / dx;
			int y = y1;
			double absDx = Math.abs(dx);
			for (int x = x1; x != x2; x = (int) (x + dx / absDx)) {
				if (x == X && y == Y) {
					sadrzi = true;
					break;
				}
				error += Math.abs(k);
				if (error >= 0.5) {
					y = (int) (y + dy / Math.abs(dy));
					error -= 1;
				}
			}
		}
		return sadrzi;
	}

	/**
	 * Pali piksele na slici i tako graficki prikazuje liniju.
	 */
	@Override
	public void popuniLik(Slika slika) {
		int sirinaSlike = slika.getSirina();
		int visinaSlike = slika.getVisina();

		double dx = x2 - x1;
		double dy = y2 - y1;
		double error = 0;
		if (dx == 0) {
			int max = Math.max(y1, y2);
			for (int y = Math.min(y1, y2); y < max; y++) {
				if (y >= 0 && y < visinaSlike && x1 >= 0 && x1 < sirinaSlike) {
					slika.upaliTocku(x1, y);
				}

			}
		} else {
			double k = dy / dx;
			int y = y1;
			double absDx = Math.abs(dx);
			for (int x = x1; x != x2; x = (int) (x + dx / absDx)) {
				if (x >= 0 && x < sirinaSlike && y >= 0 && y < visinaSlike) {
					slika.upaliTocku(x, y);
				}
				error += Math.abs(k);
				if (error >= 0.5) {
					y = (int) (y + dy / Math.abs(dy));
					error -= 1;
				}
			}
		}
		slika.nacrtajSliku(System.out);
	}

	/**
	 * Klasa sluzi za stvaranje novog lika linije i implementira sucelje
	 * stvaratelj lika.
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 */
	private static class LinijaStvaratelj implements StvarateljLika {

		/**
		 * Vraca string naziv lika "LINIJA".
		 */
		@Override
		public String nazivLika() {
			return "LINIJA";
		}

		/**
		 * Vraca liniju s karakteristikama primljenih kroz parametri.
		 * 
		 * @throws IllegalArgumentException
		 *             ako nisu dana 4 argumenta unutar parametri
		 * @throws IllegalArgumentException
		 *             ako parametri nisu integer brojevi
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
			return new Linija(parametriBrojevi[0], parametriBrojevi[1],
					parametriBrojevi[2], parametriBrojevi[3]);
		}
	}
}
