package hr.fer.zemris.java.tomcat.dao.sql;

import hr.fer.zemris.java.tomcat.dao.DAO;
import hr.fer.zemris.java.tomcat.dao.DAOException;
import hr.fer.zemris.java.tomcat.model.Poll;
import hr.fer.zemris.java.tomcat.model.Stavka;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti.
 * 
 * @author Borna
 */
public class SQLDAO implements DAO {

	@Override
	public void napraviTablicuPollOptions() throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement pst = con
				.prepareStatement("CREATE TABLE PollOptions"
						+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
						+ " optionTitle VARCHAR(100) NOT NULL,"
						+ " optionLink VARCHAR(150) NOT NULL,"
						+ " pollID BIGINT,"
						+ " votesCount BIGINT,"
						+ " FOREIGN KEY (pollID) REFERENCES Polls(id))");) {
			pst.executeUpdate();
		} catch (Exception e) {
			throw new DAOException(
					"Pogreška prilikom kreiranja tablice PollOptions.", e);
		}
	}

	@Override
	public void popuniPollove(String imeDatoteke) {
		Connection con = SQLConnectionProvider.getConnection();
		Path stazaDat = Paths.get(imeDatoteke);
		List<String> linije;
		try {
			linije = Files.readAllLines(stazaDat);
		} catch (IOException e) {
			throw new DAOException(
					"Greška prilikom citanja pollova iz datoteke", e);
		}
		// parsira linije i dodaje u bazu podataka
		for (String linija : linije) {
			if (linija.startsWith("#")) {
				continue;
			}
			String[] parameters = linija.split("\t");
			umetniPoll(con, parameters[0], parameters[1]);
		}

	}

	/**
	 * Ubacuje poll u tablicu Polls.
	 * 
	 * @param con
	 *            sjednica s bazom
	 * @param naslov
	 *            naslov polla
	 * @param poruka
	 *            poruka polla
	 */
	private void umetniPoll(Connection con, String naslov, String poruka) {

		try (PreparedStatement pst = con
				.prepareStatement("INSERT INTO Polls (title, message)"
						+ " values (?,?)");) {

			pst.setString(1, naslov);
			pst.setString(2, poruka);
			pst.executeUpdate();
		} catch (Exception e) {
			throw new DAOException(
					"Pogreška prilikom umetanja u tablicu PollOptions.", e);
		}
	}

	@Override
	public void napraviTablicuPolls() {
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement("CREATE TABLE Polls"
				+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
				+ " title VARCHAR(150) NOT NULL,"
				+ " message CLOB(2048) NOT NULL" + ")");) {

			pst.executeUpdate();
		} catch (Exception e) {
			throw new DAOException(
					"Pogreška prilikom kreiranja tablice Polls.EXC:"
							+ e.getMessage(), e);
		}
	}

	@Override
	public void popuniStavke(String imeDatoteke, Long id) {
		Connection con = SQLConnectionProvider.getConnection();
		Path stazaDat = Paths.get(imeDatoteke);
		List<String> linije;
		try {
			linije = Files.readAllLines(stazaDat);
		} catch (IOException e) {
			throw new DAOException(
					"Greška prilikom čitanja bendova iz datoteke", e);
		}
		// parsira linije i dodaje u bazu podataka
		for (String linija : linije) {
			if (linija.startsWith("#")) {
				continue;
			}
			String[] parameters = linija.split("\t");
			umetniStavku(con, parameters[0], parameters[1], id);
		}

	}

	@Override
	public boolean praznaTablica(String imeTabl) {
		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement pst = con.prepareStatement("Select id from "
				+ imeTabl)) {
			try (ResultSet rs = pst.executeQuery()) {
				return rs == null || !rs.next();
			}

		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom citanja iz tablice "
					+ imeTabl + ". EXC:" + e.getMessage(), e);
		}
	}

	@Override
	public Long dohvatiIdPolla(String nazivPolla) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con
				.prepareStatement("SELECT id FROM Polls WHERE TITLE LIKE ?");) {

			pst.setString(1, nazivPolla);
			long id = 10;
			try (ResultSet rs = pst.executeQuery();) {
				if (rs != null && rs.next()) {
					id = rs.getLong(1);
				}
			}
			return id;
		} catch (Exception e) {
			throw new DAOException(
					"Pogreška prilikom dohvacanja iz tablice Polls. Naziv polla: "
							+ nazivPolla, e);
		}
	}

	/**
	 * Ubacuje stavku u tablicu PollOptions.
	 * 
	 * @param con
	 *            sjednica
	 * @param naziv
	 *            naziv stavke
	 * @param link
	 *            link stavke
	 * @param id
	 *            id stavke
	 */
	private void umetniStavku(Connection con, String naziv, String link, Long id) {

		try (PreparedStatement pst = con
				.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollId,"
						+ " votesCount)" + " values (?,?,?,?)");) {

			pst.setString(1, naziv);
			pst.setString(2, link);
			pst.setLong(3, id);
			pst.setInt(4, 0);

			pst.executeUpdate();
		} catch (Exception e) {
			throw new DAOException(
					"Pogreška prilikom umetanja u tablicu PollOptions.", e);
		}
	}

	@Override
	public boolean tablicaPostoji(String imeTablice) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		try {
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, imeTablice.toUpperCase(),
					null);
			return rs.next();
		} catch (SQLException e) {
			throw new DAOException("Greška prilikom citaranj tablice"
					+ imeTablice, e);
		}
	}

	@Override
	public List<Poll> dohvatiPopisPollova() throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		List<Poll> pollovi = new ArrayList<>();
		try (PreparedStatement pst = con
				.prepareStatement("Select id, title, message from Polls");) {

			try (ResultSet rs = pst.executeQuery();) {
				while (rs != null && rs.next()) {
					long id = rs.getLong(1);
					String title = rs.getString(2);
					String message = rs.getString(3);
					pollovi.add(new Poll(id, title, message));
				}
				return pollovi;
			}
		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom dohvacanja pollova");
		}
	}

	@Override
	public List<Stavka> dohvatiPopisStavki(Long pollId) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		List<Stavka> stavke = new ArrayList<>();
		try (PreparedStatement pst = con
				.prepareStatement("Select id, optionTitle, optionLink, votesCount"
						+ " from PollOptions where pollId=?");) {

			pst.setLong(1, pollId);
			try (ResultSet rs = pst.executeQuery();) {
				while (rs != null && rs.next()) {
					long id = rs.getLong(1);
					String title = rs.getString(2);
					String link = rs.getString(3);
					int votes = rs.getInt(4);
					stavke.add(new Stavka(id, title, link, votes));
				}
				return stavke;
			}
		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom dohvacanja stavki.");
		}
	}

	@Override
	public void azurirajGlas(Long idStavke) {
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con
				.prepareStatement("Update PollOptions Set votesCount=votesCount+1 where id=?");) {

			pst.setLong(1, idStavke);
			pst.executeUpdate();

		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom dohvacanja bendova.");
		}
	}

	@Override
	public boolean vazeciGlas(Long pollId, Long idStavke) {
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement("Select Count(*)"
				+ " from PollOptions where pollId=? and id=?");) {

			pst.setLong(1, pollId);
			pst.setLong(2, idStavke);
			try (ResultSet rs = pst.executeQuery();) {
				int ukupno = 0;
				while (rs != null && rs.next()) {
					ukupno = rs.getInt(1);
				}
				// ako nema nijednog rezultata znaci nevazeci glas
				return ukupno == 1;
			}
		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom dohvacanja bendova.");
		}
	}

	@Override
	public Poll dohvatiPoll(Long pollId) {
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con
				.prepareStatement("Select id, title, message"
						+ " from Polls where id=?");) {

			pst.setLong(1, pollId);
			try (ResultSet rs = pst.executeQuery();) {
				if (rs != null && rs.next()) {
					long id = rs.getLong(1);
					String title = rs.getString(2);
					String message = rs.getString(3);
					return new Poll(id, title, message);
				}
				return null;
			}
		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom dohvacanja stavki.");
		}
	}
}
