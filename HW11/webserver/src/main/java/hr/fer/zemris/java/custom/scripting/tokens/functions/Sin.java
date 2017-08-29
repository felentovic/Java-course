package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Calculates sin function on value poped from stack
 * 
 * @author Borna
 *
 */
public class Sin implements IFunction {

	@Override
	public void doFunction(Deque<Object> stack, RequestContext requestContext) {
		ValueWrapper wrapper = new ValueWrapper(stack.pop());
		wrapper.increment(0);
		Double degree = Double.parseDouble(wrapper.getValue().toString());
		Double radians = degree * Math.PI / 180.0;
		Double result = Math.sin(radians);
		stack.push(result);
	}

}
