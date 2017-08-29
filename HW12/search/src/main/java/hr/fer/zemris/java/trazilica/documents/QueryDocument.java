package hr.fer.zemris.java.trazilica.documents;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Class extends {@link AbstractDocument} and represents query document. Removes
 * words that are not in vocabular
 * 
 * @author Borna
 *
 */
public class QueryDocument extends AbstractDocument {
	/**
	 * Vocabular
	 */
	private Set<String> vocabularySet;

	/**
	 * Constructs QueryDocument with given parameters
	 * 
	 * @param body
	 *            given query body
	 * @param vocabularySet
	 *            vocabular
	 */
	public QueryDocument(String body, Set<String> vocabularySet) {
		wordOccur = new HashMap<>();
		this.body = body;
		this.vocabularySet = vocabularySet;
		fillHashMap();
		removeUnknownWords();
	}

	/**
	 * Removes words that are not in vocabulary
	 */
	private void removeUnknownWords() {
		for (Iterator<String> it = wordOccur.keySet().iterator(); it.hasNext();) {
			if (!vocabularySet.contains(it.next())) {
				it.remove();
				;
			}
		}
	}
}
