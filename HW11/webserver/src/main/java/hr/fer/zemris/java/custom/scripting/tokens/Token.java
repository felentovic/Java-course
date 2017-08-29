package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * Token representing attributes inside tags.
 * @author Borna Feldšar
 * @version 1.0
 */
public abstract class Token {
		/**
		 * Returns empty string.
		 * @return empty string.
		 */
		public abstract String asText();
		
		/**
		 * Retur value of token
		 * @return value of token
		 */
		public abstract Object getValue();
}
