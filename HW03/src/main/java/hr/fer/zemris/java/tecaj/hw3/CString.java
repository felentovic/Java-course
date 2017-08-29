package hr.fer.zemris.java.tecaj.hw3;

public class CString {

	/**
	 * Char array where data is stored.
	 */
	final private char[] data;
	/**
	 * Offset from begging off char array.
	 */
	final private int offset;
	/**
	 * Length of used char array.
	 */
	final private int count;

	// ----------------------constructors--------------------------
	/**
	 * Creates new char array with given offset and given count.
	 * 
	 * @param data
	 *            char array
	 * @param offset
	 *            index of first showen char in char array.
	 * @param count
	 *            length of used char array.
	 * @throws IllegalArgumentException
	 *             if offset is smaller then zero or bigger then array length
	 *             count
	 * @throws IllegalArgumentException
	 *             if count is bigger then array length or smaller then zero
	 */
	public CString(char[] data, int offset, int count) {
		this(data, offset, count, false);
	}

	/**
	 * Creates new char array. Length is set to array length and offset is set
	 * to zero.
	 * 
	 * @param data
	 *            char array
	 */
	public CString(char[] data) {
		this(data, 0, data.length, false);
	}

	/**
	 * Constructs new char array if given Cstring char array is not full used,
	 * otherwise it reuse original's character array.
	 * 
	 * @param original
	 *            original CString
	 */
	public CString(CString original) {
		if (original.count < original.data.length) {
			data = new char[original.count];
			System.arraycopy(original.data, original.offset, data, 0,
					original.count);
		} else {
			data = original.data;
		}

		offset = 0;
		count = original.count;

	}

	/**
	 * Creates new Cstring creating new char array.
	 * 
	 * @param s
	 *            String which contest will be stored in char array.
	 */
	public CString(String s) {
		this(s.toCharArray());
	}

	/**
	 * Private constructor that creates new char array or uses same reference.
	 * Depending on boolean sameReference
	 * 
	 * @param data
	 *            char array
	 * @param offset
	 *            index of first showen char in char array.
	 * @param count
	 *            length of used char array.
	 * @param sameReference
	 *            true if char arrays share same reference, false otherwise
	 * @throws IllegalArgumentException
	 *             if offset is smaller then zero or bigger then array length
	 *             count
	 * @throws IllegalArgumentException
	 *             if count is bigger then array length or smaller then zero
	 */
	private CString(char[] data, int offset, int count, boolean sameReference) {

		if (offset < 0 || offset > data.length - count) {
			throw new IllegalArgumentException(
					"Offset can't be smaller then 0 or bigger then lengt");
		}

		if (count > data.length || count < 0) {
			throw new IllegalArgumentException(
					"Count can't be bigger then data count");
		}
		this.offset = offset;
		this.count = count;

		if (!sameReference) {
			this.data = new char[count];
			System.arraycopy(data, offset, this.data, 0, count);
		} else {
			this.data = data;
		}
	}

	// --------------------------methods-----------------------------------
	/**
	 * Returns Cstring length.
	 * 
	 * @return Cstring length
	 */
	public int length() {
		return count;
	}

	/**
	 * Returns char at given index
	 * 
	 * @param index
	 *            index of char
	 * @return char
	 * @throws IllegalArgumentException
	 *             if index is smaller then zero or bigger then count
	 */
	public char charAt(int index) {
		if (index < 0 || index >= count) {
			throw new IllegalArgumentException("Invalid index");
		}
		return data[offset + index];
	}

	/**
	 * Allocates a new array, copies string content into it and returns it
	 * 
	 * @return new char array
	 */
	public char[] toCharArray() {
		char[] newArray = new char[count];
		System.arraycopy(data, offset, newArray, 0, count);
		return newArray;
	}

	/**
	 * Returns index of first occurrence of char or -1.
	 * 
	 * @param c
	 *            given char
	 * @return index of char
	 */
	public int indexOfChar(char c) {
		int index = -1;
		for (int i = offset; i < count; i++) {
			if (data[i] == c) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * Returns true if this string begins with given string, false otherwise.
	 * 
	 * @param s
	 *            given string to compare
	 * @return true if this string begins with given string, false otherwise
	 */
	public boolean startsWith(CString s) {
		return s.equals(this.left(s.count));
	}

	/**
	 * Returns true if this Cstring ends with given CString, false otherwise.
	 * Returns false if given CString length is bigger than this Cstring length.
	 * 
	 * @param s
	 *            given string
	 * @return true if this Cstring ends with given Cstring,
	 */
	public boolean endsWith(CString s) {
		return s.equals(this.right(s.count));
	}

	/**
	 * Returns true if this CString contains given CString at any position,
	 * false otherwise
	 * 
	 * @param s
	 *            given CSstring
	 * @return true if this CString contains given CString at any position,
	 *         false otherwise
	 */
	public boolean contains(CString s) {
		if (s.count > count) {
			return false;
		}
		boolean contains = false;
		for (int i = 0, j = 0; i < count && j < s.count; i++) {
			if (charAt(i) == s.charAt(j)) {
				j++;
				contains = true;
			} else {
				j = 0;
				contains = false;
			}
		}
		return contains;
	}

	/**
	 * Returns new CString which represents a part of original string; position
	 * endIndex does not belong to the substring;
	 * 
	 * @param startIndex
	 *            the beginning index
	 * @param endIndex
	 *            the ending index
	 * @return New CString if startIndex and endIndex are different from zero,
	 *         count
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0) {
			throw new IllegalArgumentException(
					"Start index can't be smaller then zero");
		}

		if (endIndex < startIndex || endIndex > count) {
			throw new IllegalArgumentException(
					"End index can't be smaller then start index or biger then count");
		}

		return new CString(data, offset + startIndex, endIndex - startIndex,
				true);

	}

	/**
	 * Returns new CString which represents starting part of original string and
	 * is of length n
	 * 
	 * @param n
	 *            length of new CString
	 * @return reference of this CString if length is equal this length,
	 *         otherwise new reference
	 * @throws IllegalArgumentException
	 *             if n is bigger then this count.
	 */
	public CString left(int n) {
		if (n > count || n < 0) {
			throw new IllegalArgumentException(
					"Length of new CString is bigger then count");
		}
		return substring(0, n);
	}

	/**
	 * Returns new CString which represents ending part of original string and
	 * is of length n
	 * 
	 * @param n
	 *            length of new CString
	 * @return reference of this CString if length is equal this length,
	 *         otherwise new reference
	 * @throws IllegalArgumentException
	 *             if n is bigger then this count.
	 */
	public CString right(int n) {
		if (n > count || n < 0) {
			throw new IllegalArgumentException(
					"Length of new CString is bigger then count");
		}
		return substring(offset + count - n, offset + count);

	}

	/**
	 * Creates a new CString which is concatenation of current and given CString
	 * 
	 * @param s
	 *            given CString
	 * @return new Cstring which is concatenation of current and given CString
	 */
	public CString add(CString s) {
		if (s.count == 0) {
			return this;
		}
		char[] newData = new char[count + s.count];
		System.arraycopy(this.data, offset, newData, 0, count);
		System.arraycopy(s.data, s.offset, newData, count, s.count);
		return new CString(newData);
	}

	/**
	 * Creates a new CString in which each occurrence of old character is
	 * replaces with new character
	 * 
	 * @param oldChar
	 *            char that will be replaced
	 * @param newChar
	 *            new char that replaces old one
	 * @return new CString in which each occurrence of old character is replaces
	 *         with new character
	 */
	public CString replaceAll(char oldChar, char newChar) {
		char[] newData = new char[count];
		for (int i = offset, j = 0; i < count; i++, j++) {
			if (data[i] == oldChar) {
				newData[j] = newChar;
			} else {
				newData[j] = data[i];
			}
		}
		return new CString(newData);
	}

	/**
	 * Creates a new CString in which each occurrence of old substring is
	 * replaces with the new substring.
	 * 
	 * @param oldStr
	 *            part of this CString that will be replaced
	 * @param newStr
	 *            new value of CString
	 * @return new CString in which each occurrence of old substring is replaces
	 *         with the new substring
	 */
	public CString replaceAll(CString oldStr, CString newStr) {

		ArrayBackedIndexedCollection indexes = countImpressions(oldStr);
		if (indexes.size() == 0) {
			return this;
		}

		// creates char array with new size, old str replaced with new str
		int size = this.count - indexes.size() * (oldStr.count - newStr.count);
		char[] newData = new char[size];

		// i uses for iterating over this data array
		// j use for getting indexes from collection
		// z uses for iterating over newData array
		// k uses for iterating over newStr
		for (int i = 0, j = 0, z = 0, index; i < count; i++) {

			try {
				index = (int) indexes.get(j);
			} catch (IndexOutOfBoundsException e) {
				index = -1;
			}
			if (i == index) {
				for (int k = 0; k < newStr.count; z++, k++) {
					newData[z] = newStr.charAt(k);
				}
				// -1 because we will skip one letter in original data
				i += oldStr.count - 1;
				j++;

			} else {
				newData[z] = data[i];
				z++;
			}
		}
		return new CString(newData);

	}

	/**
	 * Count number of impressions of oldStr in this CString
	 * 
	 * @param oldStr
	 *            CString which number of impressions method counts
	 * @return indexes impressions stored in collection
	 */
	private ArrayBackedIndexedCollection countImpressions(CString str) {
		ArrayBackedIndexedCollection indexes = new ArrayBackedIndexedCollection();

		for (int i = 0, j = 0; i < count; i++) {
			if (charAt(i) == str.charAt(j)) {
				j++;
				if (j == str.count) {// we reached end of str
					indexes.add(i - j + 1);
					j = 0;
				}
			} else {
				j = 0;
			}
		}
		return indexes;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CString)) {
			return false;
		}
		CString other = (CString) obj;
		if (other.count != count) {
			return false;
		}
		for (int i = 0; i < count; i++) {
			if (charAt(i) != other.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns new string with copied data that starts from offset with length
	 * count.
	 */
	@Override
	public String toString() {
		return new String(data, offset, count);
	}

}
