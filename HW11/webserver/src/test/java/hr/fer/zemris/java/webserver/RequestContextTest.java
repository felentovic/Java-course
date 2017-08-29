package hr.fer.zemris.java.webserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class RequestContextTest {

	// --------------------RCCookie test -----------------------------

	@Test
	public void testGetters() {
		RCCookie cookie = new RCCookie("testCookie", "5", 20, "127.0.0.1", "/",
				true);
		assertEquals("getName", "testCookie", cookie.getName());
		assertEquals("getValue", "5", cookie.getValue());
		assertEquals("getMaxAge", 20.0, cookie.getMaxAge().doubleValue(), 1e-15);
		assertEquals("getDomain", "127.0.0.1", cookie.getDomain());
		assertEquals("getPath", "/", cookie.getPath());
		assertEquals("isHttp", true, cookie.isHttp());

	}

	// ------------------ ReqestContext test ------------------------------
	@Test
	public void contructorTest_True_NotNullMaps() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		assertTrue(reqCont.getParameterNames().isEmpty());
		assertTrue(reqCont.getPersistentParameterNames().isEmpty());
		assertTrue(reqCont.getTemporaryParameterNames().isEmpty());
		assertEquals(null, reqCont.getParameter("test"));
	}

	@Test
	public void settersTest_True_NewValues() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		reqCont.setEncoding("ASCII");
		reqCont.setMimeType("image/jpg");
		reqCont.setStatusCode(200);
		reqCont.setStatusText("OK");
	}

	@Test
	public void setPersistentParameter_True_NewValue() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		reqCont.setPersistentParameter("test", "2");
		assertTrue(reqCont.getPersistentParameterNames().size() == 1);
		assertTrue(reqCont.getPersistentParameterNames().contains("test"));
		assertEquals("2", reqCont.getPersistentParameter("test"));

	}

	@Test
	public void setTemporaryParameter_True_NewValue() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		reqCont.setTemporaryParameter("test", "2");
		assertTrue(reqCont.getTemporaryParameterNames().size() == 1);
		assertTrue(reqCont.getTemporaryParameterNames().contains("test"));
		assertEquals("2", reqCont.getTemporaryParameter("test"));

	}

	@Test
	public void removePersistentParameter_True_DeletedValue() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		reqCont.setPersistentParameter("test", "2");
		assertTrue(reqCont.getPersistentParameterNames().size() == 1);
		assertTrue(reqCont.getPersistentParameterNames().contains("test"));
		assertEquals("2", reqCont.getPersistentParameter("test"));
		reqCont.removePersistentParameter("test");
		assertTrue(reqCont.getPersistentParameterNames().isEmpty());

	}

	@Test
	public void removeTemporaryParameter_True_DeletedValue() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		reqCont.setTemporaryParameter("test", "2");
		assertTrue(reqCont.getTemporaryParameterNames().size() == 1);
		assertTrue(reqCont.getTemporaryParameterNames().contains("test"));
		assertEquals("2", reqCont.getTemporaryParameter("test"));
		reqCont.removeTemporaryParameter("test");
		assertTrue(reqCont.getTemporaryParameterNames().isEmpty());

	}

	@Test
	public void addRCCookie_True_ValueAdded() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		reqCont.addRCCookie(new RCCookie("testCookie", "5", 20, "127.0.0.1",
				"/", true));
		try {
			reqCont.write("TEST".getBytes());
		} catch (IOException ignorable) {
		}

	}

	@Test
	public void writeMethodTest_True_ExpectedValue() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		RequestContext reqCont = new RequestContext(os, null, null, null);
		reqCont.write("Test");
		String header = os.toString("ISO_8859_1");
		String exHeader = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n"
				+ "\r\n"
				+ "Test";
		assertEquals(exHeader, header );
	}

	// -------------------------exceptions------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void constructor_IllegalArgumentException_NullStream() {
		@SuppressWarnings("unused")
		RequestContext reqCont = new RequestContext(null, null, null, null);

	}

	@Test(expected = RuntimeException.class)
	public void setMimeType_RuntimeException_ValueSetedAfterGeneratedHeader() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		try {
			reqCont.write("TEST");
		} catch (IOException ignorable) {
		}
		reqCont.setMimeType("image/jpg");
	}

	@Test(expected = RuntimeException.class)
	public void setEncoding_RuntimeException_ValueSetedAfterGeneratedHeader() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		try {
			reqCont.write("TEST");
		} catch (IOException ignorable) {
		}
		reqCont.setEncoding("ANSII");
	}

	@Test(expected = RuntimeException.class)
	public void setStatusCode_RuntimeException_ValueSetedAfterGeneratedHeader() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		try {
			reqCont.write("TEST");
		} catch (IOException ignorable) {
		}
		reqCont.setStatusCode(200);
	}

	@Test(expected = RuntimeException.class)
	public void setStatusText_RuntimeException_ValueSetedAfterGeneratedHeader() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		try {
			reqCont.write("TEST");
		} catch (IOException ignorable) {
		}
		reqCont.setStatusText("ERROR");
	}

	@Test(expected = RuntimeException.class)
	public void addRCCookie_RuntimeException_ValueSetedAfterGeneratedHeader() {
		RequestContext reqCont = new RequestContext(System.out, null, null,
				null);
		try {
			reqCont.write("TEST");
		} catch (IOException ignorable) {
		}
		reqCont.addRCCookie(new RCCookie("testCookie", "5", 20, "127.0.0.1",
				"/", true));
	}
}
