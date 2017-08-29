package hr.fer.zemris.java.tomcat.glasanje;

import hr.fer.zemris.java.tomcat.glasanje.GlasanjeRezultatiServlet.Result;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet stvara Microsoft Excel dokument sa rezultatima glasovanja
 * 
 * @author Borna
 *
 */
@WebServlet(urlPatterns = "/glasanje-xls")
public class GlasanjeXls extends HttpServlet {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/vnd.ms-excel");
		@SuppressWarnings("unchecked")
		List<Result> rezultati = (List<Result>) getServletContext()
				.getAttribute("rezultati");
		HSSFWorkbook hwb = generateExcelFile(rezultati);
		hwb.write(resp.getOutputStream());

	}

	/**
	 * Generira {@link HSSFWorkbook} sa danom listom rezultata glasovanja
	 * 
	 * @param rezultati
	 *            rezultati glasovanja
	 * @return generiran {@link HSSFWorkbook}
	 * @throws IOException
	 *             ako se dogodi greska za vijeme pisanja
	 */
	private HSSFWorkbook generateExcelFile(List<Result> rezultati)
			throws IOException {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Rezultati glasanja");

		HSSFRow vrsniRed = sheet.createRow(0);
		vrsniRed.createCell(0).setCellValue("Bend");
		vrsniRed.createCell(1).setCellValue("Broj glasova");
		int brojacRed = 1;
		if (rezultati == null) {
			return hwb;
		}

		for (Result rez : rezultati) {
			HSSFRow row = sheet.createRow(brojacRed);
			row.createCell(0).setCellValue(rez.getBand());
			row.createCell(1).setCellValue(rez.getVotes());
			brojacRed++;
		}

		return hwb;
	}
}
