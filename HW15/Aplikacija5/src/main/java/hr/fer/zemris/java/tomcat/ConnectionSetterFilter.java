package hr.fer.zemris.java.tomcat;

import hr.fer.zemris.java.tomcat.dao.sql.SQLConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

/**
 * Filter sluzi za uspostavljanje sjednice s bazom podataka. Mapiran je na
 * {@code /glasanje} , {@code /glasanje-glasaj} i {@code /glasanje-rezultati}.
 * Veza je spremljena u LocalThread mapu.
 * 
 * @author Borna
 *
 */
@WebFilter(filterName = "filterBazaPod", urlPatterns = { "/glasanje",
		"/glasanje-glasaj", "/glasanje-rezultati"})
public class ConnectionSetterFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		DataSource ds = (DataSource) request.getServletContext().getAttribute(
				"hr.fer.zemris.dbpool");
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}
		SQLConnectionProvider.setConnection(con);
		try {
			chain.doFilter(request, response);
		} finally {
			SQLConnectionProvider.setConnection(null);
			try {
				con.close();
			} catch (SQLException ignorable) {
			}
		}
	}
}
