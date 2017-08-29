package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Thrown by methods in the ObjectMultiStack if key doesnt exist in stack.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class MapContainstNoSuchKey extends RuntimeException {
	/**
	 * Defauld serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new MapContainsNoSuchKey with null as its error message
	 * string.
	 */
	public MapContainstNoSuchKey() {
		super();
	}

	/**
	 * Constructs a new MapContainsNoSuchKey with error message.
	 * 
	 * @param message
	 *            error message.
	 */
	public MapContainstNoSuchKey(String message) {
		super(message);
	}

	/**
	 * Constructs a new MapContainsNoSuchKey with cause of exception.
	 * 
	 * @param cause
	 *            cause of exception
	 */
	public MapContainstNoSuchKey(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new MapContainsNoSuchKey with error message and cause of
	 * exception.
	 * 
	 * @param message
	 *            error message
	 * @param cause
	 *            cause of exception
	 */
	public MapContainstNoSuchKey(String message, Throwable cause) {
		super(message, cause);
	}
}
