package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface used for visitor pattern. Visit {@link TextNode},
 * {@link ForLoopNode}, {@link EchoNode}, {@link DocumentNode}
 *
 * @author Borna
 *
 */
public interface INodeVisitor {

	/**
	 * Job that is done on given {@link TextNode}
	 * @param node given TextNode
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Job that is done on given {@link ForLoopNode}
	 * @param node given ForLoopNode
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Job that is done on given {@link EchoNode}
	 * @param node given EchoNode
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Job that is done on given {@link DocumentNode}
	 * @param node given {@link DocumentNode}
	 */
	public void visitDocumentNode(DocumentNode node);

}
