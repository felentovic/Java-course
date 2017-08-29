package hr.fer.zemris.java.trazilica.documents;

import hr.fer.zemris.java.trazilica.StopWords;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class represents document with his own vocabulary and inter map of words and
 * words occurence.
 * 
 * @author Borna
 *
 */
public class AbstractDocument {
	/**
	 * Map for words and occurence
	 */
	protected HashMap<String, Integer> wordOccur;
	/**
	 * Body for words 
	 */
	protected String body;
	
	/**
	 * Fills hash map with words occurence 
	 */
	protected void fillHashMap() {
		String regex = "\\p{L}+";
		Matcher matcher = Pattern.compile(regex).matcher(body);
		while (matcher.find()) {
			if (!matcher.group().isEmpty()) {
				increaseOccurence(matcher.group());
			}
		}

		for (String word : StopWords.getInstance().getWords()) {
			wordOccur.remove(word.toLowerCase());
		}

	}
	
	/**
	 * Returns occurence map
	 * @return occurence map
	 */
	public HashMap<String, Integer> getOccurenceMap() {
		return wordOccur;
	}
	
	/**
	 * Increase given word occurence in occurence map
	 * @param word2 given word
	 */
	private void increaseOccurence(String word2) {
		String word = word2.toLowerCase();
		Integer occur = wordOccur.get(word);
		if (occur == null) {
			occur = Integer.valueOf(0);
		}
		occur++;
		wordOccur.put(word, occur);
	}

}
