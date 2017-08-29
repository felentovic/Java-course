package hr.fer.zemris.java.tomcat;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Generates a table of values of trigonometric functions sin(x) and cos(x) for
 * all integer angles (in degrees) in a range determined by URL parameters a and
 * b (if a is missing, it is assumed {@code a=0}; if b is missing, it is assumed
 * {@code b=360}; if {@code a > b}, parameters are swaped; if {@code b > a+720},
 * b is seted to {@code a+720})
 * 
 * @author Borna
 *
 */
@WebServlet(name = "trigonometric", urlPatterns = "/trigonometric")
public class TrigonometricFunctions extends HttpServlet {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String arg1 = req.getParameter("a");
		String arg2 = req.getParameter("b");

		Double a = getDoubleFormParm(arg1, 0);
		Double b = getDoubleFormParm(arg2, 360);
		if (a == null || b == null) {
			req.setAttribute("error", new String[] { arg1, arg2 });
			req.getRequestDispatcher("/WEB-INF/pages/ErrorParam.jsp").forward(
					req, resp);
			return;
		}

		if (a > b) {
			double tmp = a;
			a = b;
			b = tmp;
		}

		if (b > a + 720) {
			b = a + 720;
		}

		List<Result> results = new LinkedList<>();
		results.add(new Result(a));
		results.add(new Result(b));
		req.setAttribute("results", results);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometricRes.jsp")
				.forward(req, resp);

	}

	/**
	 * Return double value form parameters. If given param is null than default
	 * value is returned .
	 * 
	 * @param param
	 *            given parameter
	 * @param defVal
	 *            default value of parameter
	 * @return double value form parameters. If given param is null than default
	 *         value is returned.
	 */
	private Double getDoubleFormParm(String param, double defVal) {
		Double num;
		if (param == null) {
			num = defVal;
		} else {
			try {
				num = Double.parseDouble(param);
			} catch (NumberFormatException e) {
				num = null;
			}
		}
		return num;
	}

	/**
	 * Decimal format for formmating values in result class
	 */
	private static DecimalFormat format = new DecimalFormat("#.#####");

	/**
	 * Class represents trigonometric result with degree, calculated sinus and
	 * cosinu
	 * 
	 * @author Borna
	 *
	 */
	public static class Result {
		/**
		 * degree for math functions
		 */
		private double degree;

		/**
		 * degree in radians
		 */
		private double degreeRad;

		/**
		 * sinus value form degree
		 */
		private String sin;

		/**
		 * cosinus value from degree
		 */
		private String cos;

		/**
		 * Constructs result from given results
		 * 
		 * @param degree
		 *            given degree
		 */
		public Result(double degree) {
			super();
			this.degree = degree;
			degreeRad = degree * Math.PI / 180;
			sin = format.format(Math.sin(degreeRad));
			cos = format.format(Math.cos(degreeRad));
		}

		/**
		 * Getter for degree
		 * 
		 * @return degree
		 */
		public String getDegree() {
			return format.format(degree);
		}

		/**
		 * Getter for sinus value form degree
		 * 
		 * @return sinus value from degree
		 */
		public String getSin() {
			return sin;
		}

		/**
		 * Getter for cosinus value form degree
		 * 
		 * @return cosinus value from degree
		 */
		public String getCos() {
			return cos;
		}
	}
}
