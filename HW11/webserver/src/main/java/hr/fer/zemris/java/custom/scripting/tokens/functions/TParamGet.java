package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Retreives value from {@link RequestContext}, if value for given name is null,
 * defaulet value is pushed on stack
 * 
 * @author Borna
 *
 */
public class TParamGet implements IFunction {

	@Override
	public void doFunction(Deque<Object> stack, RequestContext requestContext) {

		Object defValue = stack.pop();

		if (!(stack.peek() instanceof String)) {
			throw new IllegalArgumentException(
					"Name for tparamGet function is not string");
		}
		String name = (String) stack.pop();

		String value = requestContext.getTemporaryParameter(name);
		stack.push(value == null ? defValue : value);
	}

}
