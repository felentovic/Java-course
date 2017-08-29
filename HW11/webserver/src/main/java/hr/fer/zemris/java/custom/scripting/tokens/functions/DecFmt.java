package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.text.DecimalFormat;
import java.util.Deque;

/**
 * Function formattes value from stack and stores result. Format is poped from stack.
 * @author Borna
 *
 */
public class DecFmt implements IFunction{

	@Override
	public void doFunction(Deque<Object> stack, RequestContext requestContext) {
		if (!(stack.peek() instanceof String)) {
			throw new IllegalArgumentException(
					"Not decimal format given.");
		}
		String format = (String) stack.pop();
		DecimalFormat formatter = new DecimalFormat(format);
		ValueWrapper wrapper = new ValueWrapper(stack.pop());
		wrapper.increment(0);
		String result = formatter.format((Double) wrapper.getValue());
		stack.push(result);
		
	}

}
