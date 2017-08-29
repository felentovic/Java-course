package hr.fer.zemris.java.tomcat.dao;

/**
 * Exception extends {@link RuntimeException}. It is used by {@link DAOProvider}
 * 
 * @author Borna
 *
 */
public class DAOException extends RuntimeException {
	
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs empty {@link DAOException}
	 */
	public DAOException() {
	}

	/**
	 * Constructs a new Dao exception with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or
	 * disabled.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 * @param enableSuppression
	 *            whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            whether or not the stack trace should be writable
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructs a new Dao exception with the specified detail message and
	 * cause.
	 * 
	 * @param message
	 *            specified exception message
	 * @param cause
	 *            exception cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new Dao exception with the specified detail message.
	 * 
	 * @param message
	 *            specified message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructs a new Dao exception with the specified cause and a detail
	 * message of (cause==null ? null : cause.toString()) (which typically
	 * contains the class and detail message of cause). This constructor is
	 * useful for runtime exceptions that are little more than wrappers for
	 * other throwables.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}
