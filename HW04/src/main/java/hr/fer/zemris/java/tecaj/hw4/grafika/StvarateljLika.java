package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Sucelje definira dvije osnovne metode za stvaranje lika s pripadajucim
 * karakteristikama
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public interface StvarateljLika {
	
	/**
	 * Vraca naziv lika.
	 * @return naziv lika
	 */
	public String nazivLika();
	
	/**
	 * Vraca geometrijski lik stvoren s karakterisikama primljenim kroz parametri.
	 * @param parametri string koji sadrzi karakteristike geometrijskog lika
	 * @return geometrijski lik s zadanim karakteristikama
	 *  @throws IllegalArgumentException
		 *             ako nije dan valjan broj argumenata unutar parametri
		 * @throws IllegalArgumentException
		 *             ako parametri nisu integer brojevi
		 * @throws IllegalArgumentException
		 *             ako su brojevi negativni
	 */
	public GeometrijskiLik stvoriIzStringa(String parametri);

}
