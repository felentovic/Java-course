package hr.fer.zemris.java.tomcat;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.java.tomcat.glasanje.GlasanjeRezultatiServlet;
import hr.fer.zemris.java.tomcat.model.Stavka;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class GlasanjeRezultatiServletTest {

	@Test
	public void getWinners_True_WinnerListed() {
		GlasanjeRezultatiServlet servlet = new GlasanjeRezultatiServlet();
		List<Stavka> results = new LinkedList<>();
		Stavka result1 = new Stavka(Long.valueOf(1), "Test1", "www.link.com",
				100);
		Stavka result2 = new Stavka(Long.valueOf(2), "Test2", "www.link2.com",
				50);
		Stavka result3 = new Stavka(Long.valueOf(3), "Test3", "www.link3.com",
				70);
		results.add(result1);
		results.add(result2);
		results.add(result3);

		List<Stavka> winners = servlet.getWinners(results);
		assertEquals(1, winners.size());
		assertEquals(result1, winners.get(0));

	}

	@Test
	public void getWinners_True_WinnersListed() {
		GlasanjeRezultatiServlet servlet = new GlasanjeRezultatiServlet();
		List<Stavka> results = new LinkedList<>();
		Stavka result1 = new Stavka(Long.valueOf(1), "Test1", "www.link.com",
				100);
		Stavka result2 = new Stavka(Long.valueOf(2), "Test2", "www.link2.com",
				100);
		Stavka result3 = new Stavka(Long.valueOf(3), "Test3", "www.link3.com",
				70);
		results.add(result1);
		results.add(result2);
		results.add(result3);

		List<Stavka> winners = servlet.getWinners(results);
		assertEquals(2, winners.size());
		assertEquals(result1, winners.get(0));
		assertEquals(result2, winners.get(1));

	}

	@Test
	public void testGetters_True_StavkaGetters() {
		Long id = Long.valueOf(2);
		String naziv = "Testna stavka";
		String link = "Testni link";
		Integer votes = 100;
		Stavka stavka = new Stavka(id, naziv, link, votes);
		assertEquals(id, stavka.getId());
		assertEquals(naziv, stavka.getNaziv());
		assertEquals(link, stavka.getLink());
		assertEquals(votes, stavka.getVotes());

	}
}
