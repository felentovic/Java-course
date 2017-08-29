package hr.fer.zemris.java.tomcat.glasanje;

import hr.fer.zemris.java.tomcat.dao.DAOProvider;
import hr.fer.zemris.java.tomcat.model.Poll;
import hr.fer.zemris.java.tomcat.model.Stavka;

import java.io.IOException;
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
		String idS = req.getParameter("pollID");
		Long pollId;
		
		if (idS == null) {
			resp.sendError(400,"Odaberite anketu!");
			return;
		}
		try {
			pollId = Long.parseLong(idS);
		} catch (Exception e) {
			req.setAttribute("error", new String[] { "Nepostoji odabrana anketa" });
			req.getRequestDispatcher("/WEB-INF/pages/ErrorParam.jsp").forward(
					req, resp);
			return;
		}

		req.getSession().setAttribute("pollId", pollId);
		List<Stavka> stavke = DAOProvider.getDao().dohvatiPopisStavki(pollId);
		// ako ne postoji takav id
		if (stavke.isEmpty()) {
			req.getSession().removeAttribute("pollId");
			req.setAttribute("error", new String[] { idS });
			req.getRequestDispatcher("/WEB-INF/pages/ErrorParam.jsp").forward(
					req, resp);
			return;
		}
		
		//dohvatiti po id-u poll
		Poll zadaniPoll = DAOProvider.getDao().dohvatiPoll(pollId);
		if(zadaniPoll == null){
			req.setAttribute("error", new String[] { "Nevazeci poll" });
			req.getRequestDispatcher("/WEB-INF/pages/ErrorParam.jsp").forward(
					req, resp);
			return;
		}
		
		req.setAttribute("poll", zadaniPoll);
		req.setAttribute("stavke", stavke);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(
				req, resp);

	}

}
