package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Calles {@link RequestContext#setTemporaryParameter(String, String)} method
 * with values poped from stack
 * 
 * @author Borna
 *
 */
public class TParamSet implements IFunction {

	@Override
	public void doFunction(Deque<Object> stack, RequestContext requestContext) {

		if (!(stack.peek() instanceof String)) {
			throw new IllegalArgumentException(
					"Name for tparamSet function is not string");
		}
		String name = (String) stack.pop();

		Object value = stack.pop();
		requestContext.setTemporaryParameter(name, value.toString());

	}

}
