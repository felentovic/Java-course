package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Stack that has map of stacks inside. Every stack has his key string.
 * @author Borna
 *
 */
public class ObjectMultistack {
	/**
	 * Intern map of stacks
	 */
	private Map<String, MultistackEntry> stack = new HashMap<String, MultistackEntry>();

	/**
	 * Pushes valueWrapper for given string in entry, if entry doesn't exits
	 * creates new one.
	 * 
	 * @param name
	 *            key for valueWrapper
	 * @param valueWrapper
	 *            value that is pushed in MultistackEntry
	 * @throws IllegalArgumentException
	 *             if valueWrapper is null
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry entry = stack.get(name);

		if (entry == null) {
			entry = new MultistackEntry();
			stack.put(name, entry);
		}
		entry.push(valueWrapper);

	}

	/**
	 * Returns last element from MultistackEntry for given key name and removes
	 * element from stack
	 * 
	 * @param name
	 *            key name
	 * @return last valueWrapper from MultiStackEntry
	 * @throws EmptyStackException
	 *             if stack is empty
	 * @throws MapContainstNoSuchKey
	 *             if map doesnt contain name key
	 */
	public ValueWrapper pop(String name) {
		return peekOrPop(name, true);
	}

	/**
	 * Returns last element from MultistackEntry for given key name, it doesn't
	 * remove the element.
	 * 
	 * @param name
	 *            key name
	 * @return last valueWrapper from MultiStackEntry
	 * @throws EmptyStackException
	 *             if stack is empty
	 * @throws MapContainstNoSuchKey
	 *             if map doesnt contain name key
	 */
	public ValueWrapper peek(String name) {
		return peekOrPop(name, false);
	}

	/**
	 * Returns true if MultistackEntry for given key name is empty or doesn't
	 * exist, false otherwise.
	 * 
	 * @param name
	 *            key name
	 * @return true if MultistackEntry for given key name is empty or doesn't
	 *         exist, false otherwise.
	 * @throws MapContainstNoSuchKey
	 *             if map doesnt contain given key name
	 */
	public boolean isEmpty(String name) {
		MultistackEntry entry = stack.get(name);
		boolean empty;
		if (entry == null) {
			throw new MapContainstNoSuchKey("Map doesnt contain key: " + name);
		} else {
			empty = entry.isEmpty();
		}
		return empty;
	}
	

	/**
	 * Returns last element from MultistackEntry for given key name, it removes
	 * element if flag pop is true.
	 * 
	 * @param name
	 *            key name
	 * @param pop
	 *            if true element is removed form stack
	 * @return last element from MultistackEntry for given key name.
	 * @throws EmptyStackException
	 *             if MultiStack is empty
	 * @throws MapContainstNoSuchKey
	 *             if map doesnt contain name key
	 */
	private ValueWrapper peekOrPop(String name, boolean pop) {
		MultistackEntry entry = stack.get(name);
		ValueWrapper value;
		if (entry == null) {
			throw new MapContainstNoSuchKey("Map doesnt contain key: " + name);
		} else {
			value = entry.peek();
			if (pop) {
				entry.pop();
			}
		}
		return value;
	}
	
	/**
	 * Returns key set of intern map(name, stack) 
	 * @return key set of intern map(name, stack)
	 */
	public Set<String> getKeySet(){
		return stack.keySet();
	}
	
	/**
	 * MultiStack entry that is added in map
	 * @author Borna
	 *
	 */
	static class MultistackEntry {
		/**
		 * Collection of value wrappers
		 */
		private List<ValueWrapper> collection = new LinkedList<ValueWrapper>();

		/**
		 * Returns true if stack is empty, false otherwise.
		 * 
		 * @return true for empty stack, false otherwise
		 */
		public boolean isEmpty() {
			return collection.isEmpty();
		}

		/**
		 * Pushes given value on the stack. Value can't be null.
		 * 
		 * @param value given {@link ValueWrapper}
		 * @throws IllegalArgumentException
		 *             if value is null
		 */
		public void push(ValueWrapper value) {
			if (value == null) {
				throw new IllegalArgumentException(
						"Value on stack can't be null.");
			}
			collection.add(value);
		}

		/**
		 * Removes last value pushed on stack from stack and returns it.
		 * 
		 * @return last ValueWrapper placed on stack.
		 * @throws EmptyStackException
		 *             if stack is empty
		 */
		public ValueWrapper pop() {
			return peekOrPop(true);
		}

		/**
		 * Returns last element placed on stack but does not delete it from
		 * stack.
		 * 
		 * @return last ValueWrapper placed on stack.
		 */
		public ValueWrapper peek() {
			return peekOrPop(false);
		}

		/**
		 * Returns last element placed on stack and if flag pop is true removes
		 * the element, otherwise doesn't.
		 * 
		 * @param pop
		 *            if true the element will be removed form stack.
		 * @return last element placed on stack.
		 */
		private ValueWrapper peekOrPop(boolean pop) {
			if (collection.isEmpty()) {
				throw new EmptyStackException();
			}

			int size = collection.size();
			ValueWrapper value = collection.get(size - 1);
			if (pop) {
				collection.remove(size - 1);
			}
			return value;
		}
	}
}
