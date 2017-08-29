package hr.fer.zemris.java.tomcat.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Generira izvještaj rezultata glasovanja. Postavlja attribute rezultati koji
 * su sortirani i pobjednici glasovanja.
 * 
 * @author Borna
 *
 */
@WebServlet(urlPatterns = "/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Pročitaj rezultate iz /WEB-INF/glasanje-rezultati.txt
		String imeDatoteke = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		Path stazaDat = Paths.get(imeDatoteke);

		if (!Files.exists(stazaDat)) {
			Files.createFile(stazaDat);
		}

		// Ucitaj rezultate
		List<String> linije = Files.readAllLines(stazaDat);
		Map<String, String> results = new HashMap<>();
		for (String linija : linije) {
			String[] params = linija.split("\t");
			results.put(params[0], params[1]);
		}

		// Ucitaj definiciju bendova
		Path defBendaStaza = Paths.get(req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt"));
		List<String> nameLines = Files.readAllLines(defBendaStaza);
		Map<String, String> bendoviDef = new HashMap<>();

		for (String line : nameLines) {
			String[] parameters = line.split("\t", 2);
			bendoviDef.put(parameters[0], parameters[1]);
		}

		// ucita glasove i imena benda te dodaje u rezultatiBend listu
		List<Result> rezultatiBend = new ArrayList<>();
		for (Map.Entry<String, String> bendDef : bendoviDef.entrySet()) {
			// ucitaj broj glasova
			String votes = results.get(bendDef.getKey());
			// ako je null postavi na vrijednost nula
			votes = votes == null ? "0" : votes;
			// definicija benda ime i link
			String[] defs = bendDef.getValue().split("\t");
			rezultatiBend.add(new Result(defs[0], defs[1], votes));
		}

		rezultatiBend.sort(Collections.reverseOrder());
		List<Result> pobjednici = getWinners(rezultatiBend);
		req.setAttribute("pobjednici", pobjednici);
		// ...
		// Pošalji ih JSP-u...
		req.setAttribute("rezultati", rezultatiBend);
		getServletContext().setAttribute("rezultati", rezultatiBend);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
				resp);

	}

	/**
	 * Vraca listu pobjednika napravljenu iz liste rezultatiBend
	 * 
	 * @param rezultatiBend
	 *            rezultati glasovanja
	 * @return listu pobjednika
	 */
	public List<Result> getWinners(List<Result> rezultatiBend) {
		List<Result> pobjednici = new ArrayList<>();

		int maxVote = -1;
		for (Result rezultat : rezultatiBend) {
			if (rezultat.getVotes() >= maxVote) {
				pobjednici.add(rezultat);
				maxVote = rezultat.getVotes();
			} else {
				break;
			}
		}
		return pobjednici;
	}

	/**
	 * Rezultat glasovanja sa imenom benda, linkom na pjesmu te brojem glasova
	 * 
	 * @author Borna
	 *
	 */
	public static class Result implements Comparable<Result> {
		/**
		 * Ime benda
		 */
		private String band;
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
		 * @param band
		 *            ime benda
		 * @param link
		 *            link na pjesmu
		 * @param votes
		 *            broj glasova
		 */
		public Result(String band, String link, String votes) {
			super();
			this.band = band;
			this.votes = Integer.parseInt(votes);
			this.link = link;

		}
		
		/**
		 * Getter za link
		 * @return link
		 */
		public String getLink() {
			return link;
		}
		
		/**
		 * Getter za bend
		 * @return bend
		 */
		public String getBand() {
			return band;
		}
		
		/**
		 * Vraca broj glasova
		 * @return broj glasova
		 */
		public Integer getVotes() {
			return votes;
		}

		@Override
		public int compareTo(Result o) {
			return votes.compareTo(o.votes);
		}

	}
}
