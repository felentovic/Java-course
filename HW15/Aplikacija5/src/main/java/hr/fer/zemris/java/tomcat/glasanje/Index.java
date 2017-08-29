package hr.fer.zemris.java.tomcat.glasanje;

import hr.fer.zemris.java.tomcat.model.Poll;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet mapiran na {@code /index.html} i postavlja kao atribute zahtjeva
 * listu pollova. 
 * 
 * @author Borna
 *
 */
@WebServlet("/index.html")
public class Index extends HttpServlet {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		@SuppressWarnings("unchecked")
		List<Poll> pollovi = (List<Poll>) getServletContext().getAttribute(
				"pollovi");
		req.setAttribute("pollovi", pollovi);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);

	}
}
