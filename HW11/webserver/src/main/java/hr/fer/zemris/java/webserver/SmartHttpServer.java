package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class with main method that represents http server based on TCP protocol
 * 
 * @author Borna
 *
 */
public class SmartHttpServer {
	/**
	 * Adress on which server is run
	 */
	private String address;
	/**
	 * Port on which server is run
	 */
	private int port;
	/**
	 * Number of worker threads for this server
	 */
	private int workerThreads;
	/**
	 * Session timeout
	 */
	private int sessionTimeout;
	/**
	 * Map with mime types. Key is file extension
	 */
	private final Map<String, String> mimeTypes;
	/**
	 * Server thread
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool for worker threads
	 */
	private ExecutorService threadPool;
	/**
	 * Document root path
	 */
	private Path documentRoot;
	/**
	 * Workers map. Key is workers path
	 */
	private Map<String, IWebWorker> workersMap;
	/**
	 * Sessions map
	 */
	private Map<String, SessionMapEntry> sessions;
	/**
	 * Used for generating radnom session id
	 */
	private Random sessionRandom;
	/**
	 * Used for garbage session collector thread
	 */
	private ScheduledExecutorService sessionCollector;

	/**
	 * Constructs {@link SmartHttpServer} with parameters parsed form given file
	 * name
	 * 
	 * @param configFileName
	 *            given file name
	 */
	public SmartHttpServer(String configFileName) {
		mimeTypes = new HashMap<String, String>();
		sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
		sessionRandom = new Random();

		loadServerProperties(configFileName);
	}

	/**
	 * Loades server properties and sets intern variables
	 * 
	 * @param configFileName
	 *            given config file name
	 */
	private void loadServerProperties(String configFileName) {
		Properties prop = new Properties();

		try (InputStream input = Files
				.newInputStream(Paths.get(configFileName));) {

			prop.load(input);
			address = prop.getProperty("server.address");
			port = Integer.parseInt(prop.getProperty("server.port"));
			workerThreads = Integer.parseInt(prop
					.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(prop
					.getProperty("session.timeout"));
			documentRoot = Paths.get(prop.getProperty("server.documentRoot"))
					.toAbsolutePath().normalize();

			Path mimeTypePath = Paths
					.get(prop.getProperty("server.mimeConfig"))
					.toAbsolutePath().normalize();
			loadMimeProperties(mimeTypePath);

			Path workersPath = Paths.get(prop.getProperty("server.workers"))
					.toAbsolutePath().normalize();
			loadWorkesrProperties(workersPath);
		} catch (IOException ignorable) {
		}
	}

	/**
	 * Loades workers paths and full qualified class name and puts in workers
	 * map. Not used
	 * 
	 * @param workersPath
	 *            given path to file
	 */
	private void loadWorkesrProperties(Path workersPath) {
		Properties prop = new Properties();
		workersMap = new HashMap<>();
		try (InputStream input = Files.newInputStream(workersPath);) {
			prop.load(input);
			prop.forEach((path, fqcn) -> {
				if (workersMap.get(path) != null) {
					throw new IllegalArgumentException(
							"Worker with given path " + path + "already exist");
				}
				try {
					Class<?> referenceToClass = this.getClass()
							.getClassLoader().loadClass((String) fqcn);
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					workersMap.put((String) path, iww);
				} catch (Exception e) {
					throw new IllegalArgumentException(
							"No class with given fully qualified class name");
				}

			});
		} catch (IOException ignorable) {
		}
	}

	/**
	 * Loades mime properties from given path and puts it in inter map
	 * 
	 * @param mimeTypepath
	 *            given path to file
	 */
	private void loadMimeProperties(Path mimeTypepath) {
		Properties prop = new Properties();

		try (InputStream input = Files.newInputStream(mimeTypepath);) {
			prop.load(input);
			mimeTypes.put("html", prop.getProperty("html"));
			mimeTypes.put("htm", prop.getProperty("htm"));
			mimeTypes.put("txt", prop.getProperty("txt"));
			mimeTypes.put("gif", prop.getProperty("gif"));
			mimeTypes.put("png", prop.getProperty("png"));
			mimeTypes.put("jpg", prop.getProperty("jpg"));

		} catch (IOException ignorable) {
		}

	}

	/**
	 * Returns {@link IWebWorker} for given IWebWorker name
	 * 
	 * @param name
	 *            IWebWorker name
	 * @return IWebWorker for given name
	 */
	private IWebWorker getIWebWorker(String name) {
		if (name.isEmpty() || !Character.isUpperCase(name.charAt(0))) {
			return null;
		}

		IWebWorker iww;
		String fqcn = "hr.fer.zemris.java.webserver.workers." + name;
		try {
			Class<?> referenceToClass = this.getClass().getClassLoader()
					.loadClass((String) fqcn);
			Object newObject = referenceToClass.newInstance();
			iww = (IWebWorker) newObject;
		} catch (Exception e) {
			iww = null;
		}
		return iww;
	}

	/**
	 * Creates server thread and starts it. Creates and garbage session
	 * collector
	 */
	protected synchronized void start() {
		if (serverThread != null) {
			return;
		}
		sessionCollector = Executors.newSingleThreadScheduledExecutor((r) -> {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		});
		sessionCollector
				.scheduleAtFixedRate(
						() -> {
							for (Iterator<SessionMapEntry> it = sessions
									.values().iterator(); it.hasNext();) {
								if (it.next().validUntil < System
										.currentTimeMillis() / 1000) {
									it.remove();
								}
							}
						}, 0, 5, TimeUnit.MINUTES);
		serverThread = new ServerThread();
		serverThread.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	/**
	 * Stopes server execution
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();

	}

	/**
	 * Class extends {@link Thread} and represents server thread. Accepts TCP
	 * sockets and processes them
	 * 
	 * @author Borna
	 *
	 */
	protected class ServerThread extends Thread {
		/**
		 * Server socket
		 */
		private ServerSocket serverSocket;

		/**
		 * Closes server socket
		 */
		@Override
		public void interrupt() {
			super.interrupt();
			try {
				serverSocket.close();
			} catch (IOException ignorable) {
			}
		}

		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress
						.getByName(address), port));

				while (true) {
					Socket client;
					try {
						client = serverSocket.accept();
					} catch (SocketException e) {
						break;
					}
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}

			} catch (IOException ignorable) {
				return;
			}
		}
	}

	/**
	 * Class implements {@link Runnable} and repreesnts job for worker thread.
	 * 
	 * @author Borna
	 *
	 */
	private class ClientWorker implements Runnable {
		/**
		 * Client socket
		 */
		private final Socket csocket;
		/**
		 * Input stream
		 */
		private PushbackInputStream istream;
		/**
		 * Socket output stream
		 */
		private OutputStream ostream;
		/**
		 * HTTP version
		 */
		private String version;
		/**
		 * Header method
		 */
		private String method;
		/**
		 * Params map
		 */
		private final Map<String, String> params;
		/**
		 * Persistent parameters
		 */
		private Map<String, String> permPrams;
		/**
		 * Output cookies list
		 */
		private final List<RCCookie> outputCookies;
		/**
		 * Session id for this worker
		 */
		private String SID;

		/**
		 * Constructs {@link ClientWorker} with given client sokcet
		 * 
		 * @param csocket
		 *            given client socket
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
			params = new HashMap<String, String>();
			permPrams = null;
			outputCookies = new ArrayList<>();
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				List<String> headers = readRequest();
				String[] firstLine = headers.isEmpty() ? null : headers.get(0)
						.split(" ");
				ostream = new BufferedOutputStream(csocket.getOutputStream());
				if (firstLine == null || firstLine.length != 3) {
					sendError(400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(405, "Method Not Allowed");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1")) {
					sendError(505, "HTTP Version Not Supported");
					return;
				}

				int quest = firstLine[1].indexOf('?');

				String path;
				if (quest < 0 || quest + 1 > firstLine[1].length()) {
					path = firstLine[1];
				} else {
					path = firstLine[1].substring(0, quest);
				}

				boolean newCookie = checkSession(headers);

				RequestContext reqCont = new RequestContext(ostream, params,
						permPrams, outputCookies);
				if (newCookie) {
					RCCookie cookie = new RCCookie("sid", SID, null, address,
							"/", true);
					reqCont.addRCCookie(cookie);
				}
				Path requestedPath = documentRoot.resolve("." + path)
						.toAbsolutePath().normalize();
				String paramString;
				if (quest < 0) {
					paramString = "";
				} else {
					paramString = firstLine[1].substring(quest + 1);

				}

				if (!requestedPath.startsWith(documentRoot)) {
					sendError(403, "Forbidden");
					return;
				}

				if (!parseParameters(paramString)) {
					sendError(400, "Bad request");

					return;
				}

				if (path.startsWith("/ext/")) {
					IWebWorker worker = getIWebWorker(path.replace("/ext/", ""));
					if (worker != null) {
						worker.processRequest(reqCont);
						ostream.flush();
					} else {
						sendError(400, "Bad request");

					}
					return;
				}
				if (!Files.exists(requestedPath)
						|| !Files.isReadable(requestedPath)
						|| !Files.isRegularFile(requestedPath)) {
					sendError(404, "Page not found");
					return;
				}

				int dot = requestedPath.getFileName().toString()
						.lastIndexOf('.');
				if (requestedPath.getFileName().toString().length() < dot + 1) {
					sendError(404, "Bad request");

				}
				String fileExtension = requestedPath.getFileName().toString()
						.substring(dot + 1);

				if (!fileExtension.equals("smscr")) {
					String mimeTy = mimeTypes.get(fileExtension);
					if (mimeTy == null) {
						mimeTy = "application/octet-stream";
					}
					reqCont.setMimeType(mimeTy);
				}

				reqCont.setStatusCode(200);
				if (fileExtension.equals("smscr")) {
					final byte[] bytes = Files.readAllBytes(requestedPath);
					String docBody = new String(bytes, StandardCharsets.UTF_8);
					SmartScriptParser parser = new SmartScriptParser(docBody);
					SmartScriptEngine sse = new SmartScriptEngine(
							parser.getDocumentNode(), reqCont);
					sse.execute();
				} else {
					reqCont.write(Files.readAllBytes(requestedPath));
				}
				ostream.flush();

			} catch (IOException ignorable) {
			} finally {
				try {
					csocket.close();
				} catch (IOException ignorable) {
				}
			}
		}

		/**
		 * Return session id for generated map
		 * 
		 * @return session id for generated map
		 */
		private String generateNewSessionMap() {
			SessionMapEntry sessionMapEntry = new SessionMapEntry();
			initSessionMapEntry(sessionMapEntry);
			sessions.put(sessionMapEntry.sid, sessionMapEntry);
			return sessionMapEntry.sid;
		}

		/**
		 * Initialize given {@link SessionMapEntry}
		 * 
		 * @param sessionMapEntry
		 *            given {@link SessionMapEntry}
		 */
		private void initSessionMapEntry(SessionMapEntry sessionMapEntry) {
			int length = 20;
			sessionMapEntry.sid = getRandomUpperCaseString(length);
			sessionMapEntry.validUntil = System.currentTimeMillis() / 1000
					+ sessionTimeout;
			sessionMapEntry.map = new ConcurrentHashMap<>();
		}

		/**
		 * Parses given parameters and stores values for parameters name
		 * 
		 * @param paramString
		 *            given parameters
		 * @return false if parameters are entered invalid, otherwise true
		 */
		private boolean parseParameters(String paramString) {
			if (paramString.isEmpty()) {
				return true;
			}
			String[] params = paramString.split("&");
			for (String param : params) {
				String[] nameVal = param.split("=");
				if (nameVal.length != 2) {
					return false;
				}
				this.params.put(nameVal[0], nameVal[1]);
			}

			return true;
		}

		/**
		 * Reades request header and returns it as list of strings. Every line
		 * is new value in list
		 * 
		 * @return list of lines in header
		 */
		private List<String> readRequest() {
			List<String> header = new LinkedList<>();
			StringBuilder sb = new StringBuilder();
			byte[] array = new byte[2];
			int counter = 0;
			try {
				int length = istream.available();
				if (length == 0) {
					// empty header. finally block will close that socket
					return null;
				}
				while (counter < length) {
					istream.read(array);

					if ((array[0] == 13 && array[1] == 10)) {
						header.add(sb.toString());
						// reset string builder for new line
						sb.setLength(0);

					} else if (array[1] == 13) {
						istream.unread(array, 1, 1);
						// exampe 1\r we need to store 1
						sb.append(new String(new byte[] { array[0] },
								StandardCharsets.US_ASCII));
						counter--;
					} else {
						sb.append(new String(array, StandardCharsets.US_ASCII));
					}
					counter += 2;
				}
			} catch (IOException e) {
				return null;
			}
			return header;
		}

		/**
		 * Sends error as response with given status text and status code
		 * 
		 * @param statusCode
		 *            given status code
		 * @param statusText
		 *            given status text
		 * @throws IOException
		 *             if error while writing occures
		 */
		private void sendError(int statusCode, String statusText)
				throws IOException {
			String response = "<html>" + "<head><title>" + statusText
					+ "</title></head>" + "<body><h1>Error " + statusCode
					+ "</h1>" + "<p>" + statusText + "</p></body></html>";
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n"
					+ "Server: Smart http server\r\n"
					+ "Content-Type: text/html;charset=UTF-8\r\n"
					+ "Content-Length: " + response.length()
					+ "Connection: close\r\n" + "\r\n")
					.getBytes(StandardCharsets.ISO_8859_1));
			ostream.write(response.getBytes(StandardCharsets.UTF_8));
			ostream.flush();
		}

		/**
		 * Provjerava jel postoji cookie sa danim sid, ako ne postoji stvara
		 * mapu za dani sid i stavlja referencu permParams na mapu od danog sida
		 * 
		 * @param header
		 * @param reqCont
		 * @return
		 */
		/**
		 * Checks are in session map cookie with given session id from header.
		 * If there is no such cookie new cookie is created and his session id
		 * is generated. Reference of permParams is set to referenc on cookie
		 * inter map
		 * 
		 * @param header
		 *            header from cookie is parsed
		 * @return true if new cookie must be generated, otherwise false
		 */
		private synchronized boolean checkSession(List<String> header) {
			String sidCandidate = null;
			for (String line : header) {
				if (line.startsWith("Cookie")) {
					String tmp = line.replace("Cookie:", "");
					String[] params = tmp.split(";");
					sidCandidate = parseCookieParams(params);
					break;
				}
			}

			boolean newCookie = false;
			if (sidCandidate == null) {
				sidCandidate = generateNewSessionMap();
				newCookie = true;
			}
			SID = sidCandidate;
			permPrams = sessions.get(SID).map;
			return newCookie;

		}

		/**
		 * Parses cookie parameters and if cookie is not valid anymore he is
		 * deleted from session map, otherwise his valid time is increased for
		 * {@code now + session.timeout}
		 * 
		 * @param params
		 *            given parameters
		 * @return sessiond id from cookie
		 */
		private String parseCookieParams(String[] params) {
			String sidCandidate = null;
			for (String param : params) {
				String[] paramVal = param.split("=");
				if (paramVal.length != 2) {
					return null;
				}
				if (paramVal[0].trim().equals("sid")) {
					sidCandidate = paramVal[1].replace("\"", "");
					SessionMapEntry entry = sessions.get(sidCandidate);
					if (entry == null) {
						return null;
					}
					if (entry.validUntil < System.currentTimeMillis() / 1000) {
						sidCandidate = null;
						sessions.remove(sidCandidate);
					} else {
						entry.validUntil = System.currentTimeMillis() / 1000
								+ sessionTimeout;
						sessions.put(sidCandidate, entry);
					}
					break;
				}
			}

			return sidCandidate;
		}

		/**
		 * Returns random generated string of uppercase characters.
		 * 
		 * @param length
		 *            length of generated string
		 * @return random generated string of uppercase characters
		 */
		private String getRandomUpperCaseString(int length) {
			byte[] array = new byte[length];
			for (int i = 0; i < length; i++) {
				array[i] = (byte) (sessionRandom.nextInt('Z' - 'A' + 1) + 'A');
			}
			return new String(array, StandardCharsets.US_ASCII);
		}
	}

	/**
	 * Class used for session cookies and his properties
	 * 
	 * @author Borna
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session id
		 */
		String sid;
		/**
		 * Until cookie with this session id is valid
		 */
		long validUntil;
		/**
		 * Cookies parameters map
		 */
		Map<String, String> map;
	}

	/**
	 * Main method that starts program
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		final SmartHttpServer httpServer = new SmartHttpServer(
				"./config/server.properties");
		httpServer.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		while (true) {
			System.out
					.println("Please enter \"stop\" to stop SmartHttpServer.");
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException ignorable) {
			}
			if (line != null && line.equalsIgnoreCase("stop")) {
				break;
			}
		}
		try {
			reader.close();
		} catch (IOException ignorable) {
		}
		httpServer.stop();
	}

}
