package hr.fer.zemris.java.tecaj.hw4.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Collection uses hash value of given key for mapping. Provides basic methods
 * like put, remove, contains, clear. For mapping is used TableEntry. LoadFactor
 * is default set to 0.75.
 * 
 * @author Borna Feldšar
 * @version 1.1
 *
 */
public class SimpleHashtable implements Iterable<SimpleHashtable.TableEntry> {
	private TableEntry[] table;
	private int capacity;
	private int numberOfPairs;
	private int modificationCount;

	private final static int defaultSize = 16;
	private final double loadFactor = 0.75;

	/**
	 * Data structure used for mapping key-value in SimpleHashtable.
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 *
	 */
	public static class TableEntry {
		private Object key;
		private Object value;
		private TableEntry next;

		/**
		 * Constructs table entry with key, value and pointer on next table
		 * entry.
		 * 
		 * @param key
		 *            key value
		 * @param value
		 *            value
		 * @param next
		 *            pointer on next table entry
		 */
		private TableEntry(Object key, Object value, TableEntry next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		// ------------------------getters-------------------------
		/**
		 * Returns TableEntry value.
		 * 
		 * @return value returned value
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Returns TableEntry key.
		 * 
		 * @return key returned key
		 */
		public Object getKey() {
			return key;
		}

		// --------------------------setter---------------------
		/**
		 * Setts TableEntry value
		 * 
		 * @param value
		 *            new TableEntry value
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * Returns TableEntry as String."Key=Value"
		 */
		public String toString() {
			return key + "=" + value;

		}
	}

	// --------------------constructors------------------------
	/**
	 * Default HashTable constructor. Capacity is set by defaultSize.
	 */
	public SimpleHashtable() {
		this(defaultSize);
	}

	/**
	 * HashTable constructor. Creates new TableEntry slots. Number of slots is
	 * first next potency number 2 bigger or equal capacity and can't be
	 * negative.
	 * 
	 * @param capacity
	 *            capacity number of TableEntry slots.
	 * @throws IllegalArgumentException
	 *             if capacity is smaller than one.
	 */
	public SimpleHashtable(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException(
					"Capacitiy must be positive number");
		}
		for (int i = 1;; i++) {
			int pow = (int) Math.pow(2, i);
			if (pow >= capacity) {
				capacity = pow;
				break;
			}
		}
		table = new TableEntry[capacity];
		this.capacity = capacity;
		numberOfPairs = 0;
	}

	// ---------------------------methods--------------------
	/**
	 * Associates the specified value with the specified key in this map. If the
	 * SimpleHashtable previously contained a mapping for the key, the old value
	 * is replaced by the specified value.
	 * 
	 * @param key
	 *            key with which the specified value is to be associated
	 * @param value
	 *            value to be associated with the specified key
	 */
	public void put(Object key, Object value) {

		if (key == null) {
			throw new IllegalArgumentException("Key can't be null");
		}

		boolean alreadyExists = false;
		int slot = Math.abs(key.hashCode()) % capacity;
		TableEntry previous = null;
		for (TableEntry current = table[slot]; current != null; current = current.next) {
			if (current.getKey().equals(key)) {
				current.setValue(value);
				alreadyExists = true;
				break;
			}
			previous = current;
		}

		if (!alreadyExists) {
			TableEntry newTable = new TableEntry(key, value, null);
			if (table[slot] == null) {
				table[slot] = newTable;
			} else {
				previous.next = newTable;
			}
			numberOfPairs++;
		}
		modificationCount++;

		if (numberOfPairs >= capacity * loadFactor) {
			resize();
		}
	}

	/**
	 * Method resizes table capacity twice and puts TableEntries in new table.
	 */
	private void resize() {
		capacity *= 2;
		SimpleHashtable tmpTable = new SimpleHashtable(capacity);
		for (SimpleHashtable.TableEntry pair : this) {
			tmpTable.put(pair.getKey(), pair.getValue());
		}
		table = tmpTable.table;
		modificationCount++;
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * SimpleHashtable contains no mapping for the key.
	 * 
	 * @param key
	 *            the key whose associated value is to be returned
	 * @return value the value to which the specified key is mapped, or null if
	 *         this SimpleHashtable contains no mapping for the key
	 */
	public Object get(Object key) {
		if (key == null) {
			return null;
		}

		int slot = Math.abs(key.hashCode()) % capacity;
		Object value = null;
		for (TableEntry current = table[slot]; current != null; current = current.next) {
			if (current.getKey().equals(key)) {
				value = current.value;
				break;
			}
		}
		return value;

	}

	/**
	 * Returns the number of key-value mappings.
	 * 
	 * @return Total number of key-value pairs.
	 */
	public int size() {
		return numberOfPairs;
	}

	/**
	 * Returns true if this SimpleHashtable contains a mapping for the specified
	 * key,false otherwise.
	 * 
	 * @param key
	 *            key whose presence is to be tested
	 * @return true if this SimpleHashtable contains a mapping for the specified
	 *         key, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		int slot = Math.abs(key.hashCode()) % capacity;
		boolean contains = false;
		for (TableEntry current = table[slot]; current != null; current = current.next) {
			if (current.getKey().equals(key)) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	/**
	 * Returns true if this SimpleHashtable maps one or more keys to the
	 * specified value.
	 * 
	 * @param value
	 *            value whose presence in this SimpleHashtable is to be tested.
	 * @return true if SimpleHashtable contains entered value,false otherwise.
	 */
	public boolean containsValue(Object value) {
		boolean contains = false;
		for (int currentSlot = 0; currentSlot < capacity; currentSlot++) {
			for (TableEntry currentTable = table[currentSlot]; currentTable != null; currentTable = currentTable.next) {
				if (currentTable.getValue() == value) {
					contains = true;
					break;
				}
			}
		}
		return contains;
	}

	/**
	 * Removes the mapping for a key from this SimpleHashtable if it is present.
	 * More formally, if this SimpleHashtable contains a mapping from key k to
	 * value v that mapping is removed.
	 * 
	 * @param key
	 *            key whose mapping is to be removed from the SimpleHashtable
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}
		int slot = Math.abs(key.hashCode()) % capacity;
		boolean removed = false;
		for (TableEntry current = table[slot], previous = null; current != null; current = current.next) {
			if (current.getKey().equals(key)) {
				if (previous == null) {
					table[slot] = current.next;
				} else {
					previous.next = current.next;
				}
				removed = true;
				break;
			}
			previous = current;
		}
		if (removed) {
			numberOfPairs--;
			modificationCount++;
		}
	}

	/**
	 * Removes all of the mappings from this SimpleHashtable. The table will be
	 * empty after this call returns.
	 */
	public void clear() {
		for (int i = 0; i < capacity; i++) {
			table[i] = null;
		}
		numberOfPairs = 0;
	}

	/**
	 * Returns true if number of stored pairs is zero, false otherwise.
	 * 
	 * @return true if table is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return numberOfPairs == 0;
	}

	/**
	 * Method returns SimpleHashTable as a string. "[Key=value]"
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[");
		for (SimpleHashtable.TableEntry pair : this) {
			s.append(pair).append(", ");
		}

		if (s.length() > 1) {
			int lastIndex = s.lastIndexOf(", ");
			s.replace(lastIndex, lastIndex + 2, ""); // remove ","
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * Factory method that creates SimpleHashtable iterator.
	 */
	@Override
	public Iterator<TableEntry> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Class that is used for iterator factory method.
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry> {
		private int slotCounter;
		private int pairCounter;
		private TableEntry currentPointer;
		private TableEntry nextPointer;
		private TableEntry previousPointer;
		private int modification;
		private int removeCounter;

		/**
		 * Default constructor for class IteratorImpl.
		 */
		private IteratorImpl() {
			nextPointer = table[0];
			modification = modificationCount;

		}

		/**
		 * Returns true if next mapping exist, false otherwise.
		 * 
		 * @throws ConcurrentModificationException
		 *             if table is modified while iterating
		 */
		@Override
		public boolean hasNext() {
			if (modification != modificationCount) {
				throw new ConcurrentModificationException(
						"Hash table can't be modified while iterating");
			}
			return pairCounter < numberOfPairs;

		}

		/**
		 * Return next mapping table which value and key is not null.
		 * 
		 * @throws ConcurrentModificationException
		 *             if table is modified while iterating
		 */
		@Override
		public TableEntry next() {
			if (modification != modificationCount) {
				throw new ConcurrentModificationException(
						"Hash table can't be modified while iterating.");
			}

			previousPointer = currentPointer;
			if (nextPointer == null) {
				while (table[++slotCounter] == null)
					;
				nextPointer = table[slotCounter];
			}

			currentPointer = nextPointer;
			nextPointer = currentPointer.next;
			pairCounter++;
			removeCounter = 0;
			return currentPointer;
		}

		/**
		 * Only safe way to remove mapping while iterating. Removes mapping
		 * which method next return.
		 * 
		 * @throws IllegalStateException
		 *             if method is called twice in a row.
		 * @throws ConcurrentModificationException
		 *             if table is modified while iterating
		 */
		@Override
		public void remove() {
			if (removeCounter != 0) {
				throw new IllegalStateException(
						"Remove method called twice in a row.");
			}
			if (modification != modificationCount) {
				throw new ConcurrentModificationException(
						"Hash table can't be modified while iterating.");
			}
			if (currentPointer == table[slotCounter]) {
				table[slotCounter] = nextPointer;
			} else {
				previousPointer.next = nextPointer;
			}
			currentPointer = null;
			modificationCount++;
			modification = modificationCount;
			removeCounter++;
		}
	}
}
