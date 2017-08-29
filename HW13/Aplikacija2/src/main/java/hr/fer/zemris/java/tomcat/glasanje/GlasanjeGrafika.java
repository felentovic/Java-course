package hr.fer.zemris.java.tomcat.glasanje;

import hr.fer.zemris.java.tomcat.glasanje.GlasanjeRezultatiServlet.Result;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Servlet prikazuje grafikon temeljen na rezultatima glasovanja.
 * 
 * @author Borna
 *
 */
@WebServlet(urlPatterns = "/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Sirina slike
	 */
	private static int SIRINA = 600;
	/**
	 * Visina slike
	 */
	private static int VISINA = 300;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("image/png");

		@SuppressWarnings("unchecked")
		List<Result> rezultati = (List<Result>) getServletContext()
				.getAttribute("rezultati");

		PieDataset dataset = createDataset(rezultati);
		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, "");
		BufferedImage slika = chart.createBufferedImage(SIRINA, VISINA);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(slika, "png", bos);
			resp.getOutputStream().write(bos.toByteArray());
		} catch (IOException e) {
		}
	}

	/**
	 * Stvara {@link org.jfree.data.general.Dataset} temeljen na predanim
	 * rezultatima.
	 * 
	 * @param rezultati
	 *            predani rezultati glasovanja
	 * @return {@link PieDataset} stvoren na temelju predane liste razultata
	 */
	private PieDataset createDataset(List<Result> rezultati) {
		DefaultPieDataset result = new DefaultPieDataset();
		if (rezultati != null) {
			for (Result rez : rezultati) {
				result.setValue(rez.getBand(), rez.getVotes());
			}
		}
		return result;

	}

	/**
	 * Stvara {@link JFreeChart} temeljen na predanim podacima i naslovom.
	 * 
	 * @param dataset
	 *            predani podaci
	 * @param title
	 *            predani naslov
	 * @return {@link JFreeChart} stvoren od predanih argumenata
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}
