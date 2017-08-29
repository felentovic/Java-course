package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Class used for parsing given query, and filtering student if they accepts 
 * given statemnts in query.
 */
import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.*;
import hr.fer.zemris.java.tecaj.hw5.db.FieldNameGetters.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * QueryFilter implements IFilter and it is used to filter students if they
 * accepts given conditions.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class QueryFilter implements IFilter<StudentRecord> {
	private String query;
	private Optional<String> jmbag = Optional.empty();

	private LinkedList<ConditionalExpression> expressions = new LinkedList<>();

	private static final Map<String, IComparisonOperator> OPERATORS;
	static {
		OPERATORS = new LinkedHashMap<>();
		OPERATORS.put("<=", new SmallerThanOrEqual());
		OPERATORS.put("!=", new NotEqual());
		OPERATORS.put(">=", new GreatherThanOrEqual());
		OPERATORS.put("=", new Equal());
		OPERATORS.put(">", new GreatherThan());
		OPERATORS.put("<", new SmallerThan());

	}

	/**
	 * Construct receive one argument, query and parses given query.
	 * 
	 * @param query
	 *            given query
	 */
	public QueryFilter(String query) {
		this.query = query;
		parseQuery();

	}

	/**
	 * Returns true if StudentRecord accepts all conditions form query.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		boolean recordSatisfies = true;
		for (ConditionalExpression expr : expressions) {
			recordSatisfies = expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), expr.getStringLiteral());
			if (!recordSatisfies) {
				break;
			}
		}
		return recordSatisfies;
	}

	/**
	 * Method that returns record jmbag. Used for O(1) searching.
	 * 
	 * @return optional string jmbag.
	 */
	public Optional<String> getJMBAG() {
		return jmbag;
	}

	/**
	 * Parses this query into instructions and stores them into expressions
	 * list.
	 * 
	 * @throws IllegalArgumentException
	 *             if query doesn't contain known fields
	 * @throws IllegalArgumentException
	 *             if and operator is on invalid place
	 */
	private void parseQuery() {
		final String operatorRegex = "(>=|<=|>|<|=|!=)";
		final String regex = "(?<lastName>\\s+(?i)lastName(\\s+)?" + operatorRegex	+ "(\\s+)?\"(\\p{L}+|-|\\*| )*\")" 
				+ "|(?<firstName>\\s+(?i)firstName(\\s+)?"	+ operatorRegex + "(\\s+)?\"(\\p{L}+|-|\\*| )*\")"
				+ "|(?<jmbag>\\s+(?i)jmbag(\\s+)?" + operatorRegex + "(\\s+)?\"\\d+\")"
				+ "|(?<and>)\\s+(?i)and"
				+ "|(?<other>.*) ";

		
		Matcher match = Pattern.compile(regex).matcher(query);		
		boolean nextAnd = false;
		while (match.find()) {
			if (!match.group().isEmpty()) {
				String matchedGroup;
				if ((matchedGroup = match.group("lastName")) != null) {
					addToList(matchedGroup.trim(), nextAnd, new LastNameGetter());
					nextAnd = true;

				} else if ((matchedGroup = match.group("firstName")) != null) {
					addToList(matchedGroup.trim(), nextAnd, new FirstNameGetter());
					nextAnd = true;

				} else if ((matchedGroup = match.group("jmbag")) != null) {
					addToList(matchedGroup.trim(), nextAnd, new JmbagGetter());
					nextAnd = true;

				} else if (match.group("and") != null) {
					if (nextAnd) {
						nextAnd = false;
					} else {
						throw new IllegalArgumentException(
								"And opertor is on invalid place."
										+ " He must be between two queries.");
					}
				} else {
					throw new IllegalArgumentException("Unknown query. Query must contain form"
							+ "field(operator)\"argument\"");
				}
			}
		}
		
		if(expressions.isEmpty()){
			throw new IllegalArgumentException("Unknown query. Query must be in form"
					+ " field(operator)\"argument\"");
		}

	}

	/**
	 * Adds given instruction to expressions list.
	 * 
	 * @param matchedGroup
	 *            part of query. example lastName="B*"
	 * @param nextAnd
	 *            if and statement is next
	 * @param field
	 *            field getter
	 */
	private void addToList(String matchedGroup, boolean nextAnd,
			IFieldValueGetter field) {
		if (nextAnd) {
			throw new IllegalArgumentException(
					"And opertor is on invalid place."
							+ "He must be between two queries.");
		}
		final String jmbagRegex = "(?i)jmbag(\\s+)?=(\\s+)?\"\\d+\"";
		if(matchedGroup.matches(jmbagRegex)){
			try{
				if(!jmbag.get().
						equals(getLitteral(matchedGroup))){
					throw new IllegalArgumentException("You can't search by two different JMBAGs.");
				}
			}catch(NoSuchElementException e){						
			}
		
		
			jmbag = Optional.of(getLitteral(matchedGroup));
		}
		expressions.add(new ConditionalExpression(field,
				getLitteral(matchedGroup), getOperator(matchedGroup)));

	}

	/**
	 * Returns operator form given query.
	 * 
	 * @param query
	 *            query from which operator is returned
	 * @return IComparisonOperator form query
	 */
	private IComparisonOperator getOperator(String query) {
		IComparisonOperator operator=null;
		for (String oper : OPERATORS.keySet()) {
			if (query.contains(oper)
					&& query.indexOf(oper) < query.indexOf('\"')) {
				operator= OPERATORS.get(oper);
				break;
			}
		}
		return operator;
	}
	
	/**
	 * Returns litteral form given query. Only value inside qoutes.
	 * @param query given query from which litteral is returned
	 * @return litteral, value inside qoutes
	 */
	private String getLitteral(String query) {
		return query
				.substring(query.indexOf('\"') + 1, query.lastIndexOf('\"'));
	}
}
