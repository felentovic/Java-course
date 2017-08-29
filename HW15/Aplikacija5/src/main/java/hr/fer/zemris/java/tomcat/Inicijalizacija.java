package hr.fer.zemris.java.tomcat;

import hr.fer.zemris.java.tomcat.dao.DAOProvider;
import hr.fer.zemris.java.tomcat.dao.sql.SQLConnectionProvider;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Listener koji sluzi za inicijalizaciju podataka web aplikacije. Stvara popis
 * pollova i listu stavki za odredeni poll.
 * 
 * @author Borna
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String configFileName = sce.getServletContext().getRealPath(
				"/WEB-INF/dbsettings.properties");
		String bendoviDef = sce.getServletContext().getRealPath(
				"/WEB-INF/bendovi-definicija.txt");
		String izboriDef = sce.getServletContext().getRealPath(
				"/WEB-INF/izbori-definicija.txt");
		String polloviDef = sce.getServletContext().getRealPath(
				"/WEB-INF/polls-definicija.txt");
		String connectionURL = ucitajSvojstvaBaze(configFileName);
		if (connectionURL == null) {
			throw new RuntimeException("Inicijalizacija baze nije uspjela!");

		}
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException(
					"Pogreška prilikom inicijalizacije polla.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		// spoji se s bazom
		try {
			otvoriVezu(cpds);
		} catch (IOException ignorable) {
			throw new RuntimeException("Ne mogu uspostavit vezu s bazom");
		}

		Connection con = SQLConnectionProvider.getConnection();

		if (!DAOProvider.getDao().tablicaPostoji("Polls")) {
			DAOProvider.getDao().napraviTablicuPolls();
		}

		if (DAOProvider.getDao().praznaTablica("Polls")) {
			DAOProvider.getDao().popuniPollove(polloviDef);
		}

		if (!DAOProvider.getDao().tablicaPostoji("PollOptions")) {
			DAOProvider.getDao().napraviTablicuPollOptions();

		}

		Long idBendova = DAOProvider.getDao().dohvatiIdPolla(
				"Glasanje za omiljeni bend");
		Long idIzbora = DAOProvider.getDao().dohvatiIdPolla(
				"Glasanje za izbore");

		if (DAOProvider.getDao().praznaTablica("PollOptions")) {
			DAOProvider.getDao().popuniStavke(bendoviDef, idBendova);
			DAOProvider.getDao().popuniStavke(izboriDef, idIzbora);
		}

		sce.getServletContext().setAttribute("pollovi",
				DAOProvider.getDao().dohvatiPopisPollova());
		zatvoriVezu(con);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce
				.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Vraca connectionURL za uspostavljanje veze s bazom. Ako je jedan od
	 * svojstava null, odnosno nepostojeci vraca null.
	 * 
	 * @param staza
	 *            staza do datoteke s svojstvima baze podataka
	 * @return connectionURL za uspostavljanje veze s bazom.Ako je jedan od
	 *         svojstava null, odnosno nepostojeci vraca null.
	 */
	private String ucitajSvojstvaBaze(String staza) {
		Properties prop = new Properties();

		String connectionURL = null;
		try (InputStream input = Files.newInputStream(Paths.get(staza));) {

			prop.load(input);
			String dbName = prop.getProperty("name");
			String host = prop.getProperty("host");
			String port = prop.getProperty("port");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName
					+ ";user=" + user + ";password=" + password;
			if (dbName == null || host == null || port == null || user == null
					|| password == null) {
				connectionURL = null;
			}
		} catch (IOException e) {
			connectionURL = null;
		}

		return connectionURL;

	}

	/**
	 * Zatvara predanu sjednicu s bazom podataka u mapi LocalThread
	 * 
	 * @param con
	 *            predana veza
	 */
	private void zatvoriVezu(Connection con) {
		SQLConnectionProvider.setConnection(null);
		try {
			con.close();
		} catch (SQLException ignorable) {

		}
	}

	/**
	 * Stvara vezu s bazom podataka kroz predani {@link DataSource}. Veza je
	 * pohranjena u LocalThread mapi.
	 * 
	 * @param ds
	 *            predani {@link DataSource}
	 * @throws IOException
	 *             ako se dogodi greska
	 */
	private void otvoriVezu(DataSource ds) throws IOException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}
		SQLConnectionProvider.setConnection(con);

	}
}
