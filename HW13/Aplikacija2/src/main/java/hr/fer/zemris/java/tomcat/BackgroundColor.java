package hr.fer.zemris.java.tomcat;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet sets current background color that is stored into users session.
 * @author Borna
 *
 */
@WebServlet(name = "Background color chooser", urlPatterns = "/setcolor" )
public class BackgroundColor extends HttpServlet {
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String colorName = req.getParameter("color");
		Color color = getColor(colorName);
		color = color == null ? getColor("white") : color;
		String hex = String.format("#%02x%02x%02x", color.getRed(),
				color.getGreen(), color.getBlue());
		req.getSession().setAttribute("pickedBgCol", hex);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	
	/**
	 * Returns color by name.
	 * @param colorName given color name
	 * @return color by name
	 */
	private Color getColor(String colorName) {
		Color color;
		if (colorName == null) {
			return null;
		}
		try {
			Field field = Color.class.getField(colorName);
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}

}
