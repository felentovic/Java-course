package hr.fer.zemris.java.tomcat.glasanje;

import hr.fer.zemris.java.tomcat.dao.DAOProvider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet sluzi za glasanje po danom id-u benda. Povecava broj glasova za bend
 * s danim id-om
 * 
 * @author Borna
 *
 */
@WebServlet(urlPatterns = "/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String idS = req.getParameter("id");
		if (idS == null) {
			resp.sendError(400, "Odaberite za koga biste glasali!");
			return;
		}
		Long id;
		try {
			id = Long.parseLong(idS);
		} catch (NumberFormatException e) {
			req.setAttribute("error", new String[] { idS });
			req.getRequestDispatcher("/WEB-INF/pages/ErrorParam.jsp").forward(
					req, resp);
			return;
		}

		Long pollID = null;
		boolean exc = false;
		try {
			pollID = (Long) req.getSession().getAttribute("pollId");
		} catch (Exception e) {
			exc = true;
		}
		if (pollID == null || exc) {
			resp.sendError(400, "Odaberite anketu");
			return;
		}
		// provjera jel session namjesten za taj poll
		if (!DAOProvider.getDao().vazeciGlas(pollID, id)) {
			// reci nemas pristup
			resp.sendError(403, "Nemas pravo glasanja u anketi!");
			return;
		}
		DAOProvider.getDao().azurirajGlas(id);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

	}
}
