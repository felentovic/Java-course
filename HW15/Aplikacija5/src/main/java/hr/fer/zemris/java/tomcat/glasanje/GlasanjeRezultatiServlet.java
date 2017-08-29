package hr.fer.zemris.java.tomcat.glasanje;

import hr.fer.zemris.java.tomcat.dao.DAOProvider;
import hr.fer.zemris.java.tomcat.model.Stavka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		Long pollId = null;
		try {
			pollId = (Long) req.getSession().getAttribute("pollId");
		} catch (Exception e) {
			pollId = null;
		}

		if (pollId == null) {
			resp.sendError(400,"Odaberite anketu!");
			return;
		}
		
		List<Stavka> rezultati = DAOProvider.getDao()
				.dohvatiPopisStavki(pollId);
		rezultati.sort(Collections.reverseOrder());
		List<Stavka> pobjednici = getWinners(rezultati);
		req.setAttribute("pobjednici", pobjednici);
		// ...
		// Pošalji ih JSP-u...
		req.setAttribute("rezultati", rezultati);
		getServletContext().setAttribute("rezultati", rezultati);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
				resp);

	}

	/**
	 * Vraca listu pobjednika napravljenu iz liste rezultatiBend
	 * 
	 * @param rezultati
	 *            rezultati glasovanja
	 * @return listu pobjednika
	 */
	public List<Stavka> getWinners(List<Stavka> rezultati) {
		List<Stavka> pobjednici = new ArrayList<>();

		int maxVote = -1;
		for (Stavka rezultat : rezultati) {
			if (rezultat.getVotes() >= maxVote) {
				pobjednici.add(rezultat);
				maxVote = rezultat.getVotes();
			} else {
				break;
			}
		}
		return pobjednici;
	}
}
