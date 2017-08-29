package hr.fer.zemris.java.trazilica;

import hr.fer.zemris.java.trazilica.documents.AbstractDocument;
import hr.fer.zemris.java.trazilica.documents.Document;
import hr.fer.zemris.java.trazilica.documents.QueryDocument;
import hr.fer.zemris.java.trazilica.vectors.Vector;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class represents search engine with his own vocabulary and documents based on
 * TF-IDF component.
 * 
 * @author Borna
 *
 */
public class SearchEngine {

	/**
	 * Set of documents in root folder
	 */
	private Set<Document> documents = new LinkedHashSet<>();
	/**
	 * Vocabulary
	 */
	private Set<String> vocabularySet = new LinkedHashSet<String>();

	/**
	 * Vocabulary map. Used for vocabulary indexs in vectors
	 */
	private Map<String, Integer> vocabularyMap = new HashMap<>();

	/**
	 * Drf components for words
	 */
	private Map<String, Double> dtfComp = new HashMap<>();

	/**
	 * Document vectors base on TF-IDF components
	 */
	private Map<Document, Vector> docVectors = new HashMap<>();

	/**
	 * Visitor for adding documents in intern list.
	 * 
	 * @author Borna
	 *
	 */
	private class FileVisit implements FileVisitor<Path> {

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			if (Files.isReadable(file)) {
				documents.add(new Document(file));
			}

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * Constructs engine with root path for documents
	 * 
	 * @param start
	 *            root path
	 */
	public SearchEngine(Path start) {
		fillVocabulary(start);
		for (Document doc : documents) {
			docVectors.put(doc, calculateITIDFvekt(doc));
		}
	}

	/**
	 * Returns vocabulary set
	 * 
	 * @return vocabulary set
	 */
	public int getVocabularSize() {
		return vocabularySet.size();
	}

	/**
	 * Return query results
	 * 
	 * @param query
	 *            given query
	 * @return results for given query
	 */
	public List<Result> getResults(String query) {
		QueryDocument queryDoc = new QueryDocument(query, vocabularySet);
		Set<String> querySet = queryDoc.getOccurenceMap().keySet();
		System.out.println("Query is: " + querySet);
		Vector qVector = calculateITIDFvekt(queryDoc);
		List<Result> resultsList = new LinkedList<>();
		if (querySet.isEmpty()) {
			return resultsList;
		}

		// calculate cosine with every doc
		for (Document doc : documents) {
			double cos = docVectors.get(doc).cosine(qVector);
			if (cos > 1e-6) {
				resultsList.add(new Result(doc.getSource(), cos));
			}

		}
		resultsList.sort(Collections.reverseOrder());

		return resultsList;

	}

	/**
	 * Filles vocabulary with given root path.
	 * 
	 * @param root
	 *            root path
	 */
	private void fillVocabulary(Path root) {
		try {
			Files.walkFileTree(root, new FileVisit());
		} catch (IOException e) {
		}
		int index = 0;
		for (Document doc : documents) {
			vocabularySet.addAll(doc.getOccurenceMap().keySet());
		}

		for (String word : vocabularySet) {
			vocabularyMap.put(word, index);
			index++;
		}
		fillDTFComp();
	}

	/**
	 * Calculates ITDTF vector for given document.
	 * 
	 * @param doc
	 *            given document
	 * @return ITDTF vector for given document
	 */
	private Vector calculateITIDFvekt(AbstractDocument doc) {
		double[] components = new double[vocabularySet.size()];
		HashMap<String, Integer> occurMap = doc.getOccurenceMap();
		for (Entry<String, Integer> word : occurMap.entrySet()) {
			Integer index = vocabularyMap.get(word.getKey());
			components[index] = word.getValue() * dtfComp.get(word.getKey());
		}

		return new Vector(components);
	}

	/**
	 * Filles DTF component for vocabulary words.
	 */
	private void fillDTFComp() {
		for (String word : vocabularySet) {
			int counter = 0;
			for (Document doc : documents) {
				if (doc.getOccurenceMap().keySet().contains(word)) {
					counter++;
				}
			}
			dtfComp.put(word, Math.log(documents.size() / (double) counter));
		}
	}

	/**
	 * Class represents search engine result
	 * 
	 * @author Borna
	 *
	 */
	public static class Result implements Comparable<Result> {
		/**
		 * Source document path
		 */
		private Path source;
		/**
		 * Cosine between vectors
		 */
		private Double cosine;

		/**
		 * Constructs result with given parameters
		 * 
		 * @param source
		 *            given source
		 * @param cosine
		 *            given cosine
		 */
		public Result(Path source, Double cosine) {
			super();
			this.source = source;
			this.cosine = cosine;
		}

		/**
		 * Getter for source
		 * 
		 * @return source value
		 */
		public Path getSource() {
			return source;
		}

		/**
		 * Getter for cosine
		 * 
		 * @return cosine value
		 */
		public Double getCosine() {
			return cosine;
		}

		@Override
		public String toString() {
			return source + " " + cosine;
		}

		@Override
		public int compareTo(Result o) {
			return cosine.compareTo(o.cosine);
		}

	}
}
