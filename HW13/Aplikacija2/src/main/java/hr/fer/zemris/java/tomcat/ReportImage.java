package hr.fer.zemris.java.tomcat;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
 * Generates report image using jfreechart library. Data for char is fixed.
 * 
 * @author Borna
 *
 */
@WebServlet(urlPatterns = "/reportImage")
public class ReportImage extends HttpServlet {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Image width
	 */
	private static int WIDTH = 600;
	/**
	 * Image height
	 */
	private static int HEIGHT = 300;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("image/png");

		PieDataset dataset = createDataset();
		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, "");
		BufferedImage image = chart.createBufferedImage(WIDTH, HEIGHT);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", bos);
			resp.getOutputStream().write(bos.toByteArray());
		} catch (IOException e) {
		}
	}

	/**
	 * Crates fixes dataset for jfreechart.
	 * 
	 * @return dataset for jfreechart.
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;

	}

	/**
	 * Creates {@link JFreeChart} based on {@link PieDataset} and title
	 * 
	 * @param dataset
	 *            given {@link org.jfree.data.general.Dataset}
	 * @param title
	 *            given JFreeChart title
	 * @return {@link JFreeChart} created from given parameters
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
