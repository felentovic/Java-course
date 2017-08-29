package hr.fer.zemris.java.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class represents stop words used to delete from vocabulary and query.
 * 
 * @author Borna
 *
 */
public class StopWords {
	/**
	 * instance used for singleton pattern
	 */
	private static StopWords instance;

	/**
	 * Set of stop words
	 */
	private Set<String> words;

	/**
	 * File body
	 */
	private String body;

	/**
	 * Constructs list of words
	 */
	private StopWords() {
		Path source = Paths.get("./dat/zaustavne_rijeci.txt");
		try {
			body = new String(Files.readAllBytes(source),
					StandardCharsets.UTF_8);
		} catch (IOException ignorable) {
		}
		words = new LinkedHashSet<>();
		fillSet();
	}

	/**
	 * Returns instance of StopWords
	 * 
	 * @return instance of StopWords
	 */
	public static StopWords getInstance() {
		if (instance == null) {
			instance = new StopWords();
		}

		return instance;
	}

	/**
	 * Filles set of stop words
	 */
	private void fillSet() {
		String regex = "\\p{L}+";
		Matcher matcher = Pattern.compile(regex).matcher(body);
		while (matcher.find()) {
			if (!matcher.group().isEmpty()) {
				words.add(matcher.group());
			}
		}
	}

	/**
	 * Return words set
	 * 
	 * @return words set
	 */
	public Set<String> getWords() {
		return words;
	}
}
