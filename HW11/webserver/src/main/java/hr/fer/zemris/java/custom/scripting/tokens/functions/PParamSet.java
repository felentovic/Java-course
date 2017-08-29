package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Calles {@link RequestContext#setPersistentParameter(String, String)} method
 * with values poped from stack
 * 
 * @author Borna
 *
 */
public class PParamSet implements IFunction {

	@Override
	public void doFunction(Deque<Object> stack, RequestContext requestContext) {
		if (!(stack.peek() instanceof String)) {
			throw new IllegalArgumentException(
					"Name for pparamSet function is not string");
		}
		String name = (String) stack.pop();

		Object value = stack.pop();
		requestContext.setPersistentParameter(name, value.toString());

	}

}
