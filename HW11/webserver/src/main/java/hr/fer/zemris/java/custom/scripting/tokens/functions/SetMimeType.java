package hr.fer.zemris.java.custom.scripting.tokens.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Calles {@link RequestContext#setMimeType(String)} wtih value poped from stack
 * @author Borna
 *
 */
public class SetMimeType implements IFunction{

	@Override
	public void doFunction(Deque<Object> stack, RequestContext requestContext) {
		if (!(stack.peek() instanceof String)) {
			throw new IllegalArgumentException(
					"String for mime type expected");
		}
		String mimeType = (String) stack.pop();
		requestContext.setMimeType(mimeType);
		
	}

}
