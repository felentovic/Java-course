package hr.fer.zemris.java.tomcat;

import java.time.LocalDateTime;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener stores current system time as attribute {@code time} and 
 * default background color as attribute {@code defcolor}
 * @author Borna
 *
 */
@WebListener
public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		context.setAttribute("time", LocalDateTime.now());
		context.setAttribute("defcolor", "#FFFFFF");
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		context.removeAttribute("time");
		context.removeAttribute("defcolor");
	}

}
