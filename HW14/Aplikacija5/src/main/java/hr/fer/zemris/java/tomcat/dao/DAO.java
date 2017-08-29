package hr.fer.zemris.java.tomcat.dao;

import hr.fer.zemris.java.tomcat.model.Poll;
import hr.fer.zemris.java.tomcat.model.Stavka;

import java.util.List;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Borna
 *
 */
public interface DAO {

	/**
	 * Dohvaca popis pollova.
	 * 
	 * @return popis pollova.
	 * @throws DAOException
	 *             ako se dogodi greska prilikom dohvacanja podataka
	 */
	public List<Poll> dohvatiPopisPollova() throws DAOException;

	/**
	 * Dohvaca popis stavki za zadani pollId
	 * 
	 * @param pollId
	 *            dani pollId
	 * @return popis stavki za zadani pollID
	 * @throws DAOException
	 *             ako se dogodi greska prilikom dohvacanja podataka
	 */
	public List<Stavka> dohvatiPopisStavki(Long pollId) throws DAOException;

	/**
	 * Stvara tablicu PollOptions u bazi podataka
	 * 
	 * @throws DAOException
	 *             ako se dogodi greska prilikom stvaranja tablice
	 */
	public void napraviTablicuPollOptions() throws DAOException;

	/**
	 * Popunjava tablicu podataka sa definicijom iz dane staze datoteke
	 * 
	 * @param imeDatoteke
	 *            ime datoteke sa definicijom tablice za poll
	 * @throws DAOException
	 *             ako se dogodi greska prilikom popunjava tablice
	 */
	public void popuniPollove(String imeDatoteke) throws DAOException;

	/**
	 * Stvara tablicu Polls u bazi podataka
	 * 
	 * @throws DAOException
	 *             ako se dogodi greska prilikom stvaranja tablice
	 */
	public void napraviTablicuPolls() throws DAOException;

	/**
	 * Popunjava tablicu PollOptions s podacima iz dane datoteke za dani pollId
	 * 
	 * @param imeDatoteke
	 *            ime datoteke sa definicijom stavki
	 * @param idPolla
	 *            id polla za na kojeg se stavke odnose
	 * @throws DAOException
	 *             ako se dogodi greska prilikom popunjava tablice
	 */
	public void popuniStavke(String imeDatoteke, Long idPolla)
			throws DAOException;

	/**
	 * Vraca true ako je tablica prazna, inace vraca false.
	 * 
	 * @param imeTabl
	 *            ime tablice cije se praznoca ispituje
	 * @return true ako je tablica prazna, inace vraca false.
	 * 
	 * @throws DAOException
	 *             ako se dogodi greska prilikom ispitivanja je li tablica
	 *             prazna
	 */
	public boolean praznaTablica(String imeTabl) throws DAOException;

	/**
	 * Vraca true ako tablica postoji, inace vraca false.
	 * 
	 * @param imeTablice
	 *            ime tablice cije se postojanje ispituje
	 * @return true ako je tablica prazna, inace vraca false.
	 * 
	 * @throws DAOException
	 *             ako se dogodi greska prilikom ispitivanja je li tablica
	 *             prazna
	 */
	public boolean tablicaPostoji(String imeTablice) throws DAOException;

	/**
	 * Dohvaca id za naziv polla.
	 * 
	 * @param nazivPolla
	 *            predani naziv polla
	 * @return id za predani naziv polla
	 * @throws DAOException
	 *             ako se dogodi greska
	 */
	public Long dohvatiIdPolla(String nazivPolla) throws DAOException;

	/**
	 * Azurira glas za dani id stavke
	 * 
	 * @param idStavke
	 *            id stavke za koju se glas azurira
	 */
	public void azurirajGlas(Long idStavke);

	/**
	 * Provjerava je li glas vazeci. Odnosno vraca true ako je idPolla od
	 * predanog idStavke jednak prednom idPollu
	 * 
	 * @param pollId
	 *            predani idPolla
	 * @param idStavke
	 *            predani id Stavke
	 * @return true ako je idPolla od predanog idStavke jednak prednom idPollu
	 */
	public boolean vazeciGlas(Long pollId, Long idStavke);

	/**
	 * Vraca Poll za predani idPolla
	 * 
	 * @param idPolla
	 *            predani idPolla
	 * @return Poll za predani idPolla
	 */
	public Poll dohvatiPoll(Long idPolla);
}
