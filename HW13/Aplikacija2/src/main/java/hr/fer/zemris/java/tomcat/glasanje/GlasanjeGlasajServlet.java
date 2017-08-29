package hr.fer.zemris.java.tomcat.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

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

		String imeDatoteke = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		String id = req.getParameter("id");

		azurirajGlas(id, imeDatoteke);

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

	}

	/**
	 * Azurira dani glas u tekstualnoj datoteci. Pristup datoteci je
	 * višedretveno siguran.
	 * 
	 * @param id
	 *            id benda za kojem se glas ažurira
	 * @param imeDatoteke
	 *            ime gdje se nalazi datoteka
	 * @throws IOException
	 *             ako se dogodi greska pri pisanju
	 */
	private synchronized void azurirajGlas(String id, String imeDatoteke)
			throws IOException {
		if (id == null) {
			return;
		}

		Path stazaDat = Paths.get(imeDatoteke);
		List<String> linije = Files.readAllLines(stazaDat);
		String novaLinija = null;
		boolean azuriran = false;
		// nadji liniju s dobiveim id-om i uvecaj vrijednost
		// obrise staru liniju
		for (Iterator<String> it = linije.iterator(); it.hasNext();) {
			String line = it.next();
			if (line.startsWith(id + "\t")) {
				String[] params = line.split("\t");
				int num = Integer.parseInt(params[1]);
				num++;
				novaLinija = id + "\t" + num;
				it.remove();
				azuriran = true;
				break;
			}
		}

		// dodaj novu ako je stara obrisana
		if (azuriran) {
			linije.add(novaLinija);
		}
		Files.write(stazaDat, linije);
	}
}
