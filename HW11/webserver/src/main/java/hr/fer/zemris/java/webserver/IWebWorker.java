package hr.fer.zemris.java.webserver;

/**
 * Interface that implements workers and result writes on {@link RequestContext}
 * {@link java.io.OutputStream}
 * 
 * @author Borna
 *
 */
public interface IWebWorker {

	/**
	 * Worker processes his function and writes result on {@link RequestContext}
	 * {@link java.io.OutputStream}
	 * 
	 * @param context
	 *            result is written on this {@link RequestContext} output stream
	 */
	public void processRequest(RequestContext context);

}
