package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing plain text in the document.
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class TextNode extends Node {
	/**
	 * Value of this text node
	 */
	private String text;
	
	/**
	 * Descaped value of this text node. With {@code " } in string
	 */
	private String descapeText;

	/**
	 * Constructor with text as parameter
	 * 
	 * @param text
	 *            text which node represents
	 */
	public TextNode(String text) {
		this.text = text;
		descapeText = text.replace("\\\\", "\\");
		descapeText = descapeText.replace("\\{", "{");
	}
	
	/**
	 * Returns string representation of text node for parsing.
	 */
	@Override
	public String asText() {
		return text;
	}
	
	/**
	 * Returns string representation of text node.
	 */
	@Override
	public String toString() {
		return descapeText;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
