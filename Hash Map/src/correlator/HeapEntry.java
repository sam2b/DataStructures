/*
 * Assignment 4, brendel_pr4
 */

package correlator;

import java.util.Comparator;

/**
 * An element in the HeapPriorityQueue array field named "elementData".  
 * This element contains a key-value pair.
 * @author Sam Brendel
 * @version 5.27C
 */
public class HeapEntry implements Comparator<HeapEntry> {

	// This class must to be parameterized so I can have a generic key field and generic value field.
	private String key;
	private Integer value;

	/**
	 * Gets the key for this entry.
	 * @return the key.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gets the value for this entry.
	 * @return the value.
	 */
	public Integer getValue() {
		return value;
	}
	
	/**
	 * Constructor
	 */
	public HeapEntry() {
		this.key = null;
		this.value = null;
	}

	/**
	 * Constructor
	 */
	public HeapEntry(final String theKey, final Integer theValue) {
		this.key = theKey;
		this.value = theValue;
	}
	
	/**
	 * Constructor
	 */
	public HeapEntry(final String theKey) {
		this(theKey, null);
	}

	/**
	 * Compares the value field of the HeapEntry by value in descending order.<BR>
	 * If the values are the same, the keys are compared in ascending order. 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @param entry1 the first entry.
     * @param entry2 the second entry.
	 */
	@Override
	public int compare(HeapEntry entry1, HeapEntry entry2) {
		return (entry1.value.equals(entry2.value)) ? entry2.key.compareTo(entry1.key) // ascending order.
				                                   : entry1.value.compareTo(entry2.value);
	}
	
	/**
	 * For debugging.
	 */
	@Override
	public String toString() {
		return "[" + key + ", " + value + "]";
	}

}
