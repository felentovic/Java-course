package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Swapes two last elements on stack
 * @author Borna
 *
 */
public class Swap implements IFunction{

	@Override
	public void doFunction(Deque<Object> stack, RequestContext requestContext) {
		Object object1 = stack.pop();
		Object object2 = stack.pop();
		stack.push(object1);
		stack.push(object2);
		
	}

}
