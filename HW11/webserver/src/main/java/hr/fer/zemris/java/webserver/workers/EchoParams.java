package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implement {@link IWebWorker}. Writes {@link RequestContext} parameters
 * as HTML table
 * 
 * @author Borna
 *
 */
public class EchoParams implements IWebWorker {
	/**
	 * Html table
	 */
	private String table;

	@Override
	public void processRequest(RequestContext context) {
		table = "<table>" + " " + " <thead align =   \"left\">" + "  </thead>"
				+ " <tbody align = \"right\">";
		context.getParameterNames().forEach(
				(name) -> {
					table += "<tr><td>" + name + ":" + "</td>" + "<td>"
							+ context.getParameter(name) + "</td></tr>";
				});
		table += "  </tbody>" + "</table>";
		try {
			context.write(table);
		} catch (IOException ignorable) {
		}
	}

}
