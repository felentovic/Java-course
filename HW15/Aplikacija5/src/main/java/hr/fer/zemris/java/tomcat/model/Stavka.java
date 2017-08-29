package hr.fer.zemris.java.tomcat.model;

/**
 * Rezultat glasovanja sa id-om stavke, imenom , linkom na pjesmu te brojem glasova
 * 
 * @author Borna
 *
 */
public class Stavka implements Comparable<Stavka> {
	/**
	 * id benda
	 */
	private Long id;
	/**
	 * Ime benda
	 */
	private String naziv;
	/**
	 * Broj glasova
	 */
	private Integer votes;
	/**
	 * Link na pjesmu
	 */
	private String link;

	/**
	 * Stvara rezultat iz predanih argumenata
	 * 
	 * @param id
	 *            id benda
	 * @param naziv
	 *            ime benda
	 * @param link
	 *            link na pjesmu
	 * @param votes
	 *            broj glasova
	 */
	public Stavka(Long id, String naziv, String link, Integer votes) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.votes = votes;
		this.link = link;

	}

	/**
	 * Getter za id
	 * 
	 * @return vrijednost id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Getter za link
	 * 
	 * @return link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Getter za bend
	 * 
	 * @return bend
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Vraca broj glasova
	 * 
	 * @return broj glasova
	 */
	public Integer getVotes() {
		return votes;
	}

	@Override
	public int compareTo(Stavka o) {
		return votes.compareTo(o.votes);
	}

}
