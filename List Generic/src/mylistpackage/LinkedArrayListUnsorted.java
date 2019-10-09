/*
 * TCSS342 Data Structures - Spring 2017 - Professor Monika Sobolewska
 * Author: Sam Brendel
 * Assignment: pr1
 */

package mylistpackage;

/**
 * The linked array list data structure containing a unsorted elements, and may
 * change order upon deleting an element.  The underlying data structure is an 
 * array, but the entity can be operated on same as a linked list.  This implies 
 * the array is re-sizable.
 * @author Sam Brendel, modified from Building Java Programs 3rd ed.
 * @version 4.16C
 * @param <E> is of any object type
 * @see mylistpackage.AbstractLinkedArrayMyList
 */
public class LinkedArrayListUnsorted<E> extends AbstractLinkedArrayMyList<E> {

	/**
	 * Constructor with default initial capacity.
	 */
	public LinkedArrayListUnsorted() {
		this(DEFAULT_CAPACITY);
	}

	/*
	 * Constructor with specified initial capacity.
	 * @param theSize the initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public LinkedArrayListUnsorted(final int theSize) {
		super(theSize);
		myElementData = (E[]) new Object[theSize];
	}

	/**
     * Rids the list of all nodes, resulting in a size of zero elements.
	 * @see mylistpackage.AbstractLinkedArrayMyList#clear()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		super.clear();
		myElementData = (E[]) new Object[DEFAULT_CAPACITY];
	}

	/**
     * Determines if the list contains at least one element of the value.
     * @see mylistpackage.MyList#contains(java.lang.Object)
     * @return true if the value exists at least once in the list.
	 */
	public boolean contains(final E value) {
		if (!isEmpty()) { // We cannot search an empty list.
			for (int i = 0; i <= back; i++) {
				if (myElementData[i].equals(value)) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Returns the index of value.
	 * @param value the data in this element.
	 * @return index of value if in the list, -1 otherwise.
	 * @see mylistpackage.MyList#getIndex(Object)
	 */
	public int getIndex(final E value) {
		for (int i = 0; i <= back; i++) {
			if (myElementData[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}


	/**
	 * Replaces the value at the given index with the given value.
	 * @param theIndex the index where the element lives, and must be {@code <=} size and index {@code >=} 0.
	 * @param value the value to replace within the element at the specified index.
	 * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size
	 * @see mylistpackage.MyList#set(int, Object)
	 */
	@Override
	public void set(final int theIndex, final E value) {
		super.set(theIndex, value);
		myElementData[theIndex] = value;
	}

	/**
     * Removes an element from the list.  If there is more than one element that contains the
     * value, there is no guarantee which particular one will be removed.
     * @param value the value to remove.
     * @see mylistpackage.MyList#remove(java.lang.Object)
	 */
	public void remove(final E value) {
		final int index = getIndex(value);
		if (back >= 0 && index >= 0) {
			myElementData[index] = myElementData[back];
			myElementData[back] = null;
			back--;
		}
	}

    /**
     * Removes value at the given index, but does not shift any elements since sorting
     * is not required, except the last element shall replace this deleted element.
     * @param theIndex {@code <=} size and index {@code >=} 0
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >} size
     * @see mylistpackage.AbstractLinkedArrayMyList#removeAtIndex(int)
     */
	@Override
	public void removeAtIndex(final int theIndex) {
		super.removeAtIndex(theIndex);

		if (back >= 0 && theIndex >= 0) {
			myElementData[theIndex] = myElementData[back];
			myElementData[back] = null;
			back--;
		}
	}
	
	/**
     * Inserts the a new element with the value into the list.
     * @param value the value to insert into the list. 
	 * @see mylistpackage.MyList#insert(java.lang.Object)
	 */
	@Override
	public void insert(final E value) {
		super.insert(value);
		
		myElementData[++back] = value;
	}

}
