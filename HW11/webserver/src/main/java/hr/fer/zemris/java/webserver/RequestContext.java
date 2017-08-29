package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class generates http header and offers output stream where data will be
 * writen. Offeres methods for setting parameters and adding {@link RCCookie}
 * 
 * @author Borna
 *
 */
public class RequestContext {

	/**
	 * stream where data and header are writen
	 */
	private OutputStream outputStream;
	/**
	 * charset that is used for text documents
	 */
	private Charset charset;
	/**
	 * encoding used for creating charset, default UTF-8
	 */
	private String encoding;
	/**
	 * status code for context, default 200
	 */
	private int statusCode;
	/**
	 * status text, default OK
	 */
	private String statusText;
	/**
	 * mime type for header
	 */
	private String mimeType;
	/**
	 * map for parameters
	 */
	private Map<String, String> parameters;
	/**
	 * map fot temporary parameters
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * map for persistant parameters
	 */
	private Map<String, String> persistentParameters;
	/**
	 * list of output cookies
	 */
	private List<RCCookie> outputCookies;
	/**
	 * flag. true ig header is generated
	 */
	private boolean headerGenerated;

	/**
	 * Class represents cookie with name, domain, value, path, max age and flaf
	 * if cookie is http cookie.
	 * 
	 * @author Borna
	 *
	 */
	public static class RCCookie {
		/**
		 * cookie name
		 */
		private String name;
		/**
		 * cookie value
		 */
		private String value;
		/**
		 * cookie domain
		 */
		private String domain;
		/**
		 * cookie path
		 */
		private String path;
		/**
		 * cookie max age
		 */
		private Integer maxAge;
		/**
		 * flag if cookie is http cookie
		 */
		private boolean isHttp;

		/**
		 * Constructs new {@link RCCookie} wtih given parameters
		 * 
		 * @param name
		 *            cookie name
		 * @param value
		 *            cookie value
		 * @param maxAge
		 *            cookie max age
		 * @param domain
		 *            cookie domain
		 * @param path
		 *            cookie path
		 * @param isHttp
		 *            true if cookie is http only cookie, otherwise false
		 */
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path, boolean isHttp) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.isHttp = isHttp;
		}

		/**
		 * Getter for name
		 * 
		 * @return value of name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for value
		 * 
		 * @return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter for domain
		 * 
		 * @return domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Getter for path
		 * 
		 * @return value of path
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * Getter for max age
		 * @return value of max age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Getter for isHttp flag.
		 * 
		 * @return value of isHttp flag
		 */
		public boolean isHttp() {
			return isHttp;
		}

	}

	/**
	 * Constructs {@link RequestContext} with given parameters. Parameters,
	 * temporaryParameters and outputCookies can be null, if null they are
	 * treated as empty.
	 * 
	 * @param outputStream
	 *            output stream
	 * @param parameters
	 *            given parameters
	 * @param persistentParameters
	 *            given persistant parameters
	 * @param outputCookies
	 *            given output cookies
	 */
	public RequestContext(OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new IllegalArgumentException("OutputStream cant be null.");
		}
		this.outputStream = outputStream;

		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.parameters = Collections.unmodifiableMap(this.parameters);

		this.persistentParameters = persistentParameters == null ? new HashMap<>()
				: persistentParameters;

		this.outputCookies = outputCookies == null ? new LinkedList<>()
				: outputCookies;
		temporaryParameters = new HashMap<>();
		encoding = "UTF-8";
		statusCode = 200;
		statusText = "OK";
		mimeType = "text/html";
	}

	/**
	 * Setter for encoding
	 * 
	 * @param encoding 
	 *            new value for encoding
	 * @throws RuntimeException
	 *             if method is called after header have been generated
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header is already generated and you cant change this property.");
		}
		this.encoding = encoding;
	}

	/**
	 * Setter for statusCode
	 * 
	 * @param statusCode
	 *            new value for statusCode
	 * @throws RuntimeException
	 *             if method is called after header have been generated
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header is already generated and you cant change this property.");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Setter for statusText
	 * 
	 * @param statusText
	 *            new value for statusText
	 * @throws RuntimeException
	 *             if method is called after header have been generated
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header is already generated and you cant change this property.");
		}
		this.statusText = statusText;
	}

	/**
	 * Setter for mimeType
	 * 
	 * @param mimeType
	 *            new value for mimeType
	 * @throws RuntimeException
	 *             if method is called after header have been generated
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header is already generated and you cant change this property.");
		}
		this.mimeType = mimeType;
	}

	/**
	 * Returns parameter for intern map or null if parameter for given name does
	 * not exist.
	 * 
	 * @param name
	 *            given key name
	 * @return parameter for intern map or null if parameter for given name does
	 *         not exist
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Retrieves names of all parameters in parameters map. Set is read-only
	 * 
	 * @return read- only set with all names of parameters
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Returns persistent parameter for intern map or null if parameter for
	 * given name does not exist.
	 * 
	 * @param name
	 *            given key name
	 * @return persistent parameter for intern map or null if parameter for
	 *         given name does not exist
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Retrieves names of all persistent parameters in parameters map. Set is
	 * read-only
	 * 
	 * @return read- only set with all names of persistent parameters
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Stores a value to persistentParameters map
	 * 
	 * @param name
	 *            given name
	 * @param value
	 *            given value for key name
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes a value from persistentParameters map
	 * 
	 * @param name
	 *            given key name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Returns temporary parameter for intern map or null if parameter for given
	 * name does not exist.
	 * 
	 * @param name
	 *            given key name
	 * @return temporary parameter for intern map or null if parameter for given
	 *         name does not exist
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Retrieves names of all temporary parameters in parameters map. Set is
	 * read-only
	 * 
	 * @return read- only set with all names of temporary parameters
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Stores a value to temporaryParameters map
	 * 
	 * @param name
	 *            given name
	 * @param value
	 *            given value for key name
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes a value from temporaryParameters map
	 * 
	 * @param name
	 *            given key name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Addes given cookie to intern cookies list
	 * 
	 * @param rcCookie
	 *            given cookie that is added in intern list
	 * @throws RuntimeException
	 *             if method is called after header have been generated
	 * 
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header is already generated and you cant add new cookies.");
		}
		outputCookies.add(rcCookie);
	}

	/**
	 * Writes given byte array on output stream. First time this method is
	 * called header is generated.
	 * 
	 * @param data
	 *            data that is written on output stream
	 * @return this {@link RequestContext}
	 * @throws IOException
	 *             if error during writing occurs
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data);
		return this;
	}

	/**
	 * Writes given string in charset that is stored in encoding variable on
	 * output stream. First time this method is called header is generated.
	 * 
	 * @param text
	 *            text that is written on output stream
	 * @return this {@link RequestContext}
	 * @throws IOException
	 *             if error during writing occurs
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Generates header and writes it on output stream. Header is generated only
	 * once
	 * 
	 * @throws IOException
	 *             if error during writing occurs
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);
		StringBuilder builder = new StringBuilder();
		builder.append("HTTP/1.1").append(" ").append(statusCode).append(" ")
				.append(statusText).append("\r\n");
		builder.append("Content-Type: ").append(mimeType);
		String ext = mimeType.startsWith("text/") ? "; charset="
				+ charset.toString() : "";
		builder.append(ext).append("\r\n");

		if (!outputCookies.isEmpty()) {
			builder.append("Set-Cookie: ");
			for (RCCookie cookie : outputCookies) {
				builder.append(cookie.getName()).append("=").append("\"")
						.append(cookie.getValue()).append("\"; ");

				if (cookie.getDomain() != null) {
					builder.append("Domain=").append(cookie.getDomain())
							.append("; ");
				}

				if (cookie.getName() != null) {
					builder.append("Path=").append(cookie.getPath())
							.append("; ");
				}

				if (cookie.getMaxAge() != null) {
					builder.append("MaxAge=").append(cookie.getMaxAge())
							.append(";");
				}
				if (cookie.isHttp) {
					builder.append("HttpOnly");
				}
				builder.append("\r\n");
			}
		}

		builder.append("\r\n");
		headerGenerated = true;
		String header = builder.toString();
		outputStream.write(header.getBytes(StandardCharsets.ISO_8859_1));
	}

}
