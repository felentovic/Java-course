package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Program simulates Visitor pattern for writing file parsed into tokens on
 * system out
 *
 * @author Borna
 *
 */
public class TreeWriter {

	/**
	 * Main method
	 *
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("File path expected.");
			return;
		}

		final Path path = Paths.get(args[0]).toAbsolutePath().normalize();

		if (!Files.isReadable(path)) {
			System.out.println("Not readable file.");
			return;
		}

		String docBody = null;
		try {
			final byte[] bytes = Files.readAllBytes(path);
			docBody = new String(bytes, StandardCharsets.UTF_8);

		} catch (final IOException e) {
			System.out.println("Error while reading file");
			return;
		}

		final SmartScriptParser p = new SmartScriptParser(docBody);
		final WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}

	/**
	 * Class implements {@link INodeVisitor} and writes text from each node on system output.
	 * @author Borna
	 *
	 */
	static class WriterVisitor implements INodeVisitor {
		/**
		 * Contains all text from nodes after visit
		 */
		private final StringBuilder text;

		/**
		 * Constructor initialize variables
		 */
		public WriterVisitor() {
			text = new StringBuilder();
		}

		/**
		 * Appends given node as text to intern string builder
		 */
		@Override
		public void visitTextNode(TextNode node) {
			text.append(node.asText());
		}

		/**
		 * Appends given node as text to intern string builder
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			text.append(node.asText());

		}

		/**
		 * Appends given node as text to intern string builder
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			text.append(node.asText());

		}

		/**
		 * Appends given node as text to intern string builder
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.getnumberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.println(text);
		}

	}
}
