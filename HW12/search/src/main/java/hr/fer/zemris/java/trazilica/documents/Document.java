package hr.fer.zemris.java.trazilica.documents;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Class extends {@link AbstractDocument}. Accepts source path through constructor and reades
 * all contest in UTF-8 Charset
 * @author Borna
 *
 */
public class Document extends AbstractDocument {
	/**
	 * Source file path
	 */
	private Path source;
	
	/**
	 * Constructs document with given path. Reades all file contest
	 * @param source file path
	 */
	public Document(Path source) {

		if (!Files.isReadable(source)) {
			throw new IllegalArgumentException("Path:" + source
					+ " is not readable.");
		}
		wordOccur = new HashMap<>();
		try {
			body = new String(Files.readAllBytes(source),
					StandardCharsets.UTF_8);
		} catch (IOException ignorable) {
		}
		this.source = source.toAbsolutePath().normalize();
		fillHashMap();
	}
	
	/**
	 * Returns source path
	 * @return source path
	 */
	public Path getSource() {
		return source;
	}

}
