package hr.fer.zemris.java.tecaj.hw07.shell;
import java.io.IOException;

/**
 * Simulates environment with method that writes result on output stream.
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public interface Environment {
	/**
	 * Method reads entered text from keyboard.
	 * 
	 * @return entered string
	 * @throws IOException if error occures
	 */
	public String readLine() throws IOException;

	/**
	 * Method writes string on output stream.
	 * 
	 * @param s string that is writen on output stream
	 * @throws IOException if error occures
	 */
	public void write(String s) throws IOException;

	/**
	 * Method writes string on output stream that ends with '\n'
	 * @param s string that is writen on output strea
	 * @throws IOException if error occures
	 */
	public void writeln(String s) throws IOException;
	
	
	/**
	 * Iterable factory that creates new ShellCommand Iterator
	 * @return ShellCommand Iterator
	 */
	public Iterable<ShellCommand> commands();
	
	/**
	 * Getter for multiline symbol
	 * @return value of multiline symbol
	 */
	public Character getMultilineSymbol();
	
	/**
	 * Setter for multiline symbol
	 * @param symbol new value of multiline symbol
	 */
	public void setMultilineSymbol(Character symbol);
	
	/**
	 * Getter for prompt symbol
	 * @return value of prompt symbol
	 */
	public Character getPromptSymbol();
	
	/**
	 * Setter for prompt symbol
	 * @param symbol new value of prompt symbol
	 */
	public void setPromptSymbol(Character symbol);
	
	/**
	 * Getter for morelines symbol
	 * @return value of morelines symbol
	 */
	public Character getMorelinesSymbol();
	
	/**
	 * Setter for morelines symbol
	 * @param symbol new value of morelines symbol
	 */
	public void setMorelinesSymbol(Character symbol);
	
	/**
	 * Returns command for name.
	 * @param name command name
	 * @return shellcommand with given name
	 */
	public ShellCommand getCommand(String name);

}

