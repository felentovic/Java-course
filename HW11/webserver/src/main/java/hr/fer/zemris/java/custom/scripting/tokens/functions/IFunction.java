package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Interface for strategy pattern
 * 
 * @author Borna
 *
 */
public interface IFunction {

	/**
	 * Do function on values from stack or from {@link RequestContext}
	 * parameters and stores result on stack
	 * 
	 * @param stack
	 *            contains used values
	 * @param requestContext
	 *            contains used parameters
	 */
	public void doFunction(Deque<Object> stack, RequestContext requestContext);
}
