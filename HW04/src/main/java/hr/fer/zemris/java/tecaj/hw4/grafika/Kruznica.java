package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Poseban slucaj elipse gdje su mala i velika poluos jednake duljine. Sadrzi
 * metode za crtanje kruznice na sliku.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Kruznica extends Elipsa {

	public static final StvarateljLika STVARATELJ = new KruznicaStvaratelj();

	/**
	 * Konstruira kruznicu zadanu sa koordinatama sredista i radijusom.
	 * 
	 * @param x
	 *            x koordinata sredista
	 * @param y
	 *            y koordinata sredista
	 * @param radijus
	 *            radijus kruznice
	 * @throws IllegalArgumentException
	 *             ako je barem jedan broj manji od nule
	 */
	public Kruznica(int x, int y, int radijus) {
		super(x, y, radijus, radijus);
	}

	/**
	 * Klasa sluzi za stvaranje novog lika kruznice i implementira sucelje
	 * stvaratelj lika.
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 *
	 */
	private static class KruznicaStvaratelj implements StvarateljLika {

		/**
		 * Vraca naziv lika "KRUG".
		 */
		@Override
		public String nazivLika() {
			return "KRUG";
		}

		/**
		 * Vraca geometrijski lik stvoren s karakterisikama primljenim kroz
		 * parametri.
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
			return new Kruznica(parametriBrojevi[0], parametriBrojevi[1],
					parametriBrojevi[2]);
		}
	}
}
