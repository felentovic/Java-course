package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Duplicates last element from stack, and stores result on stack
 * @author Borna
 *
 */
public class Duplicate implements IFunction{

	@Override
	public void doFunction(Deque<Object> stack, RequestContext requestContext) {
		Object value = stack.peek();
		stack.push(value);		
	}

}
