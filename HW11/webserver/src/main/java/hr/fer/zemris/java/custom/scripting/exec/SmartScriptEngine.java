package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.custom.scripting.tokens.functions.DecFmt;
import hr.fer.zemris.java.custom.scripting.tokens.functions.Duplicate;
import hr.fer.zemris.java.custom.scripting.tokens.functions.IFunction;
import hr.fer.zemris.java.custom.scripting.tokens.functions.PParamDel;
import hr.fer.zemris.java.custom.scripting.tokens.functions.PParamGet;
import hr.fer.zemris.java.custom.scripting.tokens.functions.PParamSet;
import hr.fer.zemris.java.custom.scripting.tokens.functions.ParamGet;
import hr.fer.zemris.java.custom.scripting.tokens.functions.SetMimeType;
import hr.fer.zemris.java.custom.scripting.tokens.functions.Sin;
import hr.fer.zemris.java.custom.scripting.tokens.functions.Swap;
import hr.fer.zemris.java.custom.scripting.tokens.functions.TParamDel;
import hr.fer.zemris.java.custom.scripting.tokens.functions.TParamGet;
import hr.fer.zemris.java.custom.scripting.tokens.functions.TParamSet;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Engine that executes smart scripts. Supports functions defined in package
 * hr.fer.zemris.java.custom.scripting.tokens.functions. Uses visitor pattern
 * 
 * @author Borna
 *
 */
public class SmartScriptEngine {
	
	/**
	 * Document node given through constructor
	 */
	private DocumentNode documentNode;
	/**
	 * Refernece to given {@link RequestContext} on which outputstream execution
	 * result will be written
	 */
	private RequestContext requestContext;
	/**
	 * Stack used for for loop variables
	 */
	private ObjectMultistack multistack;

	/**
	 * Map used fetching function by name
	 */
	private static Map<String, IFunction> functions;

	static {
		functions = new HashMap<>();
		functions.put("sin", new Sin());
		functions.put("decfmt", new DecFmt());
		functions.put("dup", new Duplicate());
		functions.put("swap", new Swap());
		functions.put("setMimeType", new SetMimeType());
		functions.put("paramGet", new ParamGet());
		functions.put("pparamGet", new PParamGet());
		functions.put("pparamSet", new PParamSet());
		functions.put("pparamDel", new PParamDel());
		functions.put("tparamDel", new TParamDel());
		functions.put("tparamSet", new TParamSet());
		functions.put("tparamGet", new TParamGet());

	}

	/**
	 * Visitor pattern for nodes
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.toString());
			} catch (IOException ignorable) {
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().toString();
			multistack.push(
					variable,
					new ValueWrapper(getValueFromToken(node
							.getStartExpression())));
			Object endExprValue = getValueFromToken(node.getEndExpression());

			while (multistack.peek(variable).numCompare(endExprValue) <= 0) {
				for (int i = 0; i < node.getnumberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}

				Object VWrapper2 = getValueFromToken(node.getStepExpression());
				ValueWrapper result = multistack.pop(variable).increment(
						VWrapper2);
				multistack.push(variable, result);
			}

			multistack.pop(variable);
		}

		/**
		 * Returns value from given token. Token can be
		 * {@link TokenConstantDouble} , {@link TokenConstantInteger},
		 * {@link TokenString}. If TokenString than variable with that name is
		 * fetched from multistack
		 * 
		 * @param token
		 *            token which value is retured
		 * @return value from given token. If TokenString than variable with
		 *         that name is fetched from multistack
		 */
		private Object getValueFromToken(Token token) {
			Object startExpr;
			if (token instanceof TokenVariable) {
				startExpr = multistack.peek(token.toString());
			} else {
				startExpr = token.getValue();
			}
			return startExpr;
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Deque<Object> stack = new ArrayDeque<Object>();
			for (int i = 0; i < node.getNumberOfTokens(); i++) {
				Token token = node.getToken(i);
				if (token instanceof TokenString
						|| token instanceof TokenConstantInteger
						|| token instanceof TokenConstantDouble) {
					stack.push(token.getValue());
					continue;
				}

				if (token instanceof TokenVariable) {
					findCurrentVariable(stack, token);
					continue;
				}

				if (token instanceof TokenOperator) {
					doOperation(stack, token);
					continue;
				}

				if (token instanceof TokenFunction) {
					doFunction(stack, token);
					continue;
				}
			}
			stack.descendingIterator().forEachRemaining((e) -> {
				try {
					requestContext.write(e.toString());
				} catch (Exception ignorable) {
				}
			});
		}

		/**
		 * Do function that represents given token on given stack
		 * 
		 * @param stack
		 *            given stack from which values will be taken
		 * @param token
		 *            {@link TokenFunction}
		 */
		private void doFunction(Deque<Object> stack, Token token) {
			String tokenName = token.toString();
			// substring because token name is i.e. @sin
			functions.get(tokenName.substring(1)).doFunction(stack,
					requestContext);
		}

		/**
		 * Do operation that represents given token on two last elements from
		 * stack
		 * 
		 * @param stack
		 *            two last elements are taken off from that stack
		 * @param token
		 *            {@link TokenOperator}
		 */
		private void doOperation(Deque<Object> stack, Token token) {
			TokenOperator operator = (TokenOperator) token;
			if (stack.size() < 2) {
				throw new EmptyStackException(
						"Not enough values on stack for operation "
								+ operator.toString());
			}

			ValueWrapper vWrapper1 = new ValueWrapper(stack.pop());
			ValueWrapper vWrapper2 = new ValueWrapper(stack.pop());
			if (operator.toString().equals("+")) {
				stack.push(vWrapper1.increment(vWrapper2).getValue());
			} else if (operator.toString().equals("-")) {
				stack.push(vWrapper1.decrement(vWrapper2).getValue());
			} else if (operator.toString().equals("*")) {
				stack.push(vWrapper1.multiply(vWrapper2).getValue());
			} else if (operator.toString().endsWith("/")) {
				stack.push(vWrapper1.divide(vWrapper2).getValue());
			} else {
				throw new IllegalArgumentException("Not supported operator"
						+ operator.toString());
			}
		}

		/**
		 * Returns first variable on given stack with given token name
		 * 
		 * @param stack
		 *            variables are stored on stack
		 * @param token
		 *            {@link TokenVariable}
		 */
		private void findCurrentVariable(Deque<Object> stack, Token token) {
			TokenVariable var = (TokenVariable) token;
			for (String varName : multistack.getKeySet()) {
				if (varName.equals(var.toString())) {
					stack.push(multistack.peek(varName).getValue());
					break;
				}
			}

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.getnumberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	};

	/**
	 * Constructs {@link SmartScriptEngine} with given {@link DocumentNode} and
	 * {@link RequestContext}
	 * 
	 * @param documentNode
	 *            given {@link DocumentNode}
	 * @param requestContext
	 *            given {@link RequestContext}
	 */
	public SmartScriptEngine(DocumentNode documentNode,
			RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		multistack = new ObjectMultistack();
	}

	/**
	 * Method executes parsed script using visitor pattern on given
	 * {@link DocumentNode}
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
