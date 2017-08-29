package hr.fer.zemris.java.tomcat.model;

/**
 * Klasa definira Poll sa id-om, naslovom i porukom. Koristi se kao ispis
 * trenutno postojecih anketa
 * 
 * @author Borna
 *
 */
public class Poll {
	/**
	 * id polla
	 */
	private long id;
	/**
	 * naslov polla
	 */
	private String title;
	/**
	 * poruka koju poll sadrzi
	 */
	private String message;

	/**
	 * Stvara poll sa predanim parametrima
	 * 
	 * @param id2
	 *            id polla
	 * @param title
	 *            naslov polla
	 * @param message
	 *            poruka polla
	 */
	public Poll(long id2, String title, String message) {
		this.id = id2;
		this.title = title;
		this.message = message;
	}

	/**
	 * Getter za id polla
	 * 
	 * @return id polla
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Getter za naslov polla
	 * 
	 * @return naslov polla
	 */

	public String getTitle() {
		return title;
	}

	/**
	 * Getter za poruku polla
	 * 
	 * @return poruku polla
	 */
	public String getMessage() {
		return message;
	}

}
