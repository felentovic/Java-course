package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Poseban slucaj pravokutnika kojem su sirina i visina jednake. Sadrzi metode
 * za crtanje kvadrata na sliku.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Kvadrat extends Pravokutnik {

	public static final StvarateljLika STVARATELJ = new KvadratStvaratelj();

	/**
	 * Konstruira kvadrat zadan s koordinata gornjeg lijevog vrha i duljinom
	 * stranice.
	 * 
	 * @param x
	 *            x koordinata gornjeg lijevog vrha
	 * @param y
	 *            y koordinata gornjeg lijevog vrha
	 * @param side
	 *            duljina stranice kvadrata
	 * @throws IllegalArgumentException
	 *             ako je barem jedan broj manji od 0
	 */
	public Kvadrat(int x, int y, int side) {
		super(x, y, side, side);
	}

	private static class KvadratStvaratelj implements StvarateljLika {

		/**
		 * Vraca naziv lika "KVADRAT".
		 */
		@Override
		public String nazivLika() {
			return "KVADRAT";
		}

		/**
		 * Vraca kvadrat stvoren s karakterisikama primljenim kroz parametri.
		 * 
		 * @throws IllegalArgumentException
		 *             ako nisu dana 3 argumenta unutar parametri
		 * @throws IllegalArgumentException
		 *             ako parametri nisu integer brojevi
		 * @throws IllegalArgumentException
		 *             ako su brojevi negativni
		 */
		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {

			String[] parametriNiz = parametri.split("\\s+");
			if (parametriNiz.length != 3) {
				throw new IllegalArgumentException("Netocan broj argumenata.");
			}
			int[] parametriBrojevi = new int[3];
			for (int i = 0; i < parametriNiz.length; i++) {
				try {
					parametriBrojevi[i] = Integer.parseInt(parametriNiz[i]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"Nisu dani integer brojevi.");
				}
			}
			return new Kvadrat(parametriBrojevi[0], parametriBrojevi[1],
					parametriBrojevi[2]);
		}
	}
}
