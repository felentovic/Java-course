package hr.fer.zemris.java.tomcat.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet ucitava listu bendova i postavlja ih kao attribut zahtjeva
 * 
 * @author Borna
 *
 */
@WebServlet(urlPatterns = "/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String imeDatoteke = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		Path stazaDat = Paths.get(imeDatoteke);
		List<String> linije = Files.readAllLines(stazaDat);
		if (!Files.exists(stazaDat)) {
			Files.createFile(stazaDat);
		}
		// parsira linije i dodaje u listu bendova id i ime
		List<Bend> bendovi = new ArrayList<>();
		for (String linija : linije) {
			String[] parameters = linija.split("\t");
			bendovi.add(new Bend(parameters[0], parameters[1]));
		}

		req.setAttribute("bendovi", bendovi);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(
				req, resp);

	}

	/**
	 * Klasa predstvalja bend sa imenom i id-om.
	 * 
	 * @author Borna
	 *
	 */
	public static class Bend {
		/**
		 * ime benda
		 */
		private String ime;
		/**
		 * id benda
		 */
		private String id;

		/**
		 * Konstruira bend sa danim argumentima
		 * 
		 * @param id
		 *            id benda
		 * @param ime
		 *            ime benda
		 */
		public Bend(String id, String ime) {
			super();
			this.ime = ime;
			this.id = id;
		}

		/**
		 * Getter za ime
		 * 
		 * @return vrijedost imena
		 */
		public String getIme() {
			return ime;
		}

		/**
		 * Getter za id
		 * 
		 * @return vrijednost id-a
		 */
		public String getId() {
			return id;
		}

	}
}
