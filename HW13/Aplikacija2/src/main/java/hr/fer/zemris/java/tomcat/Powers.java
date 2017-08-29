package hr.fer.zemris.java.tomcat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet accepts a three parameters a (integer from {@code[-100,100]}) b
 * (integer from {@code [-100,100]}) and n (where {@code n>=1} and {@code n<=5}
 * ). If any parameter is invalid, invalid parameters message is displayed.
 * Action creates a Microsoft Excel document with n pages. On page i there is a
 * table with two columns. The first column contains integer numbers from
 * {@code a} to {@code b}. The second column should contain {@code i-th} powers
 * of these numbers.
 * 
 * @author Borna
 *
 */
@WebServlet(urlPatterns = "/powers")
public class Powers extends HttpServlet {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String arg1 = req.getParameter("a");
		String arg2 = req.getParameter("b");
		String arg3 = req.getParameter("n");

		Integer a;
		Integer b;
		Integer n;
		try {
			a = Integer.parseInt(arg1);
			b = Integer.parseInt(arg2);
			n = Integer.parseInt(arg3);
		} catch (NumberFormatException e) {
			req.setAttribute("error", new String[] { arg1, arg2, arg3 });
			req.getRequestDispatcher("/WEB-INF/pages/ErrorParam.jsp").forward(
					req, resp);
			return;
		}
		if (a < -100 || a > 100) {
			req.setAttribute("message", "Invalid parameter range: " + a
					+ ". Range must be from -100 to 100");
			req.getRequestDispatcher("/WEB-INF/pages/InvalidParam.jsp")
					.forward(req, resp);
			return;
		}

		if (b < -100 || b > 100) {
			req.setAttribute("message", "Invalid parameter range: " + b
					+ ". Range must be from -100 to 100");
			req.getRequestDispatcher("/WEB-INF/pages/InvalidParam.jsp")
					.forward(req, resp);
			return;
		}

		if (n < 1 || n > 5) {
			req.setAttribute("message", "Invalid parameter range: " + n
					+ ". Range must be from 1 to 5");
			req.getRequestDispatcher("/WEB-INF/pages/InvalidParam.jsp")
					.forward(req, resp);
			return;
		}
		resp.setContentType("application/vnd.ms-excel");
		HSSFWorkbook hwb = generateExcelFile(a, b, n);
		hwb.write(resp.getOutputStream());

	}

	/**
	 * Generates {@link HSSFWorkbook} from given arguments. Columns are form a
	 * to b on n pages. I-th page represents it-th power of number
	 * 
	 * @param a
	 *            given start number
	 * @param b
	 *            given end number
	 * @param n
	 *            number of pages
	 * @return Generates {@link HSSFWorkbook} from given arguments
	 * @throws IOException
	 *             if error occures
	 */
	private HSSFWorkbook generateExcelFile(int a, int b, int n)
			throws IOException {
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int page = 0; page < n; page++) {
			HSSFSheet sheet = hwb.createSheet("Page" + (page + 1));
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Number");
			rowhead.createCell(1).setCellValue((page + 1) + "-th power");
			for (int i = 1, num = a; i < b - a + 1; i++, num++) {
				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(num);
				row.createCell(1).setCellValue(Math.pow(num, page + 1));
			}
		}
		return hwb;
	}
}
