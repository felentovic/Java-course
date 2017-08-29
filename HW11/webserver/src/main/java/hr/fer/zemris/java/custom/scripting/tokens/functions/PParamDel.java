package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Calles {@link RequestContext#removePersistentParameter(String)} method with
 * name value form stack
 * 
 * @author Borna
 *
 */
public class PParamDel implements IFunction {

	@Override
	public void doFunction(Deque<Object> stack, RequestContext requestContext) {
		if (!(stack.peek() instanceof String)) {
			throw new IllegalArgumentException(
					"Name for pparamDel function is not string");
		}
		String name = (String) stack.pop();
		requestContext.removePersistentParameter(name);

	}

}
