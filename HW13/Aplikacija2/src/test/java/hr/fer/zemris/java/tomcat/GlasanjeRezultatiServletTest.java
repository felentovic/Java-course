package hr.fer.zemris.java.tomcat;

import static org.junit.Assert.*;
import hr.fer.zemris.java.tomcat.glasanje.GlasanjeRezultatiServlet;
import hr.fer.zemris.java.tomcat.glasanje.GlasanjeRezultatiServlet.Result;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class GlasanjeRezultatiServletTest {

	@Test
	public void getWinners_True_WinnerListed() {
		GlasanjeRezultatiServlet servlet = new GlasanjeRezultatiServlet();
		List<Result> results = new LinkedList<>();
		Result result1 = new Result("Test1", "www.link.com", "100");
		Result result2 = new Result("Test2", "www.link2.com", "50");
		Result result3 = new Result("Test3", "www.link3.com", "70");
		results.add(result1);
		results.add(result2);
		results.add(result3);

		List<Result> winners = servlet.getWinners(results);
		assertEquals(1, winners.size());
		assertEquals(result1, winners.get(0));

	}

	@Test
	public void getWinners_True_WinnersListed() {
		GlasanjeRezultatiServlet servlet = new GlasanjeRezultatiServlet();
		List<Result> results = new LinkedList<>();
		Result result1 = new Result("Test1", "www.link.com", "100");
		Result result2 = new Result("Test2", "www.link2.com", "100");
		Result result3 = new Result("Test3", "www.link3.com", "70");
		results.add(result1);
		results.add(result2);
		results.add(result3);

		List<Result> winners = servlet.getWinners(results);
		assertEquals(2, winners.size());
		assertEquals(result1, winners.get(0));
		assertEquals(result2, winners.get(1));

	}

}
