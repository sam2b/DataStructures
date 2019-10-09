/*
 * TCSS342 Data Structures - Spring 2017 - Professor Monika Sobolewska
 * Author: Sam Brendel
 * Assignment: pr1
 */

package mylistpackage;

/**
 * The linked array list data structure containing a sorted list of elements in 
 * ascending order.  Each elemental data must be a Comparable type.  The underlying 
 * data structure is an array, but the entity can be operated on same as a linked 
 * list.  This implies the array is re-sizable.
 * @author Sam Brendel
 * @param <E> the Comparable data type.
 * @see mylistpackage.AbstractLinkedArrayMyList
 * @version 4.16C
 */
public class LinkedArrayListSorted<E extends Comparable<? super E>> extends AbstractLinkedArrayMyList<E> {

	/**
	 * Constructor parameterless, which has a default capacity. 
	 */
	public LinkedArrayListSorted() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor that sets the specified size of the list.
	 * @param theSize the initial size of the list.
	 */
	@SuppressWarnings("unchecked")
	public LinkedArrayListSorted(final int theSize) {
		super(theSize);
		myElementData = (E[]) new Comparable[theSize];
	}

	/**
     * Rids the list of all nodes, resulting in a size of zero elements.
	 * @see mylistpackage.AbstractLinkedArrayMyList#clear()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		super.clear();
		myElementData = (E[]) new Comparable[DEFAULT_CAPACITY];
	}

	/**
    * Determines if the list contains at least one element of the value.
    * @return true if the value exists at least once in the list.
    * @see mylistpackage.AbstractLinkedArrayMyList#contains(Object)
	*/
	public boolean contains(final E theValue) {
		if (!isEmpty()) { // We cannot search an empty list.
			//final int index = Arrays.binarySearch(myElementData, 0, back + 1, theValue);
			final int index = binarySearch(theValue);
			return isFound(index);
		}
		
		return false;
	}

    /**
     * Returns the index of value.
     * @param theValue the data in this element.
     * @return the index of value if in the list, -1 otherwise.
     * @see mylistpackage.AbstractLinkedArrayMyList#getIndex(Object)
     */
	public int getIndex(final E theValue) {
		int index = -1;
		if (!isEmpty()) {
			//index = Arrays.binarySearch(myElementData, 0, back + 1, theValue);
			index = binarySearch(theValue);
			index = (index < 0) ? -1 : index; // I explicitly want -1 and no other.
		}

		return index;
	}

	/**
     * Inserts the a new element with the value into the list.
     * @param theValue the value the value to insert into the list.
     * @see mylistpackage.AbstractLinkedArrayMyList#insert(Object)
	 */
	@Override
	public void insert(final E theValue) {
		super.insert(theValue);
		int index;
		
		if (isEmpty()) {
			myElementData[0] = theValue;
			front = 0;
			back++;
			
		} else {
			index = binarySearch(theValue);
			back++; // Increment here to anticipate the incoming
			
			/* Restore to the positive index value for preparation to insert at this
			 * particular index.
			 */
			if (index < 0) {
				index *= -1;
			} 
			
			/* Determines if theValue should be inserted either before the value
			 * of an element already present at index.
			 */
			if(index > 0 && theValue.compareTo(myElementData[index-1]) < 0) {
				index--;
			}

			shiftIt(myElementData, index, theValue);
		}
	}

	/**
	 * Determines if the index is found in this list, if {@code >=} 0.
	 * @param index the index to inspect.
	 * @return true if found.
	 */
	private boolean isFound(final int index) {
		return index >= 0;
		// return index != -(index) - 1;
	}

	/**
     * Removes an element from the list.  If there is more than one element that contains the
     * value, there is no guarantee which particular one will be removed.
     * @param value the value to remove.
     * @see mylistpackage.AbstractLinkedArrayMyList#remove(Object)
	 */
	public void remove(final E value) {
		if (!isEmpty()) {
			final int index = this.getIndex(value);
			
			if (back >= 0 && index >= 0) {
				this.removeAtIndex(index);
			}
		}
	}

    /**
     * Removes value at the given index, shifting subsequent values up.
     * @param theIndex {@code <=} size and index {@code >=} 0
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size
     * @see mylistpackage.AbstractLinkedArrayMyList#removeAtIndex(int)
     */
	@Override
	public void removeAtIndex(final int theIndex) {
		super.removeAtIndex(theIndex);

		// Shift elements.
		for (int i = theIndex; i < back; i++) {
			myElementData[i] = myElementData[i + 1];
		}

		myElementData[back--] = null;
		
		if (getSize() < 1) {
			front = -1;
		}
	}

	/**
	 * Replaces the value at the given index with the given value.
	 * @param theIndex the index where the element lives, and must be {@code <=} size and index {@code >=} 0.
	 * @param theValue the value the value to replace within the element at the specified index.
	 * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size
	 * @see mylistpackage.AbstractLinkedArrayMyList#set(int, Object)
	 */
	@Override
	public void set(final int theIndex, final E theValue) {
		super.set(theIndex, theValue);
		final int lower = 1;
		int upper = 1;

		if (theIndex == 0) {
			// It is legal to set(0, "bbb") in a list that only has a single
			// element of "aaa".
			/*
			 * if(getSize() == 1) { myElementData[theIndex] = theValue;
			 * 
			 * } else
			 */ 
			if (getSize() > 1 && myElementData[theIndex + 1].compareTo(theValue) < 0) {
				throw new IllegalArgumentException("Wrong location to insert this element at index " + theIndex);

			} else {
				myElementData[theIndex] = theValue;
			}
			
		} else { // getSize() > 1 && theIndex > 0
			// Make sure a null element is not compared. Example: if the list
			// only has a single element.
			if (myElementData[theIndex + 1] == null) {
				upper = 0;
			}

			// theValue must be in between elements to keep natural order.
			// Example is [1, 99, 2] where theValue = 99.
			if (myElementData[theIndex - lower].compareTo(theValue) > 0
					|| myElementData[theIndex + upper].compareTo(theValue) < 0) {
				throw new IllegalArgumentException("Wrong location to insert this element at index " + theIndex);

			} else {
				myElementData[theIndex] = theValue;
				// else theValue must either be in between a lower and higher
				// value, or next to an equal value.
			}
		}
	}

	/**
	 * Inserts the data into the list and shifts all subsequent elements forward to retain 
	 * in ascending sortation.
	 * @param theList the list to insert into and shift.
	 * @param theIndex the index where to insert the data.
	 * @param theData the data to insert.
	 */
	private void shiftIt(final E[] theList, final int theIndex, final E theData) {
		E current = theData;
		E temp;
		
		for (int i = theIndex; i <= back; i++) {
			temp = myElementData[i];
			myElementData[i] = current;
			current = temp;
		}
	}

	/**
	 * Binary search algorithm that is O(log(n)), and only relevant on a sorted
	 * list in ascending order, and assumes the list is not empty.
	 * If the data does not exist, a negative index value shall be returned. The
	 * negative of this value is where the data would have been found if it existed. 
	 * @param theValue the value to search for.
	 * @return the index of the data found, else if not found then the same 
	 *         index number is returned, but negated.
	 */
	private int binarySearch(final E theValue) {
		int back = 0, front = getSize() - 1, mid = front / 2;
		int comparison = -99; // arbitrary initial value.
		//E debug = null;
		
		//When back > front, the search is finished.
		while(back <= front) { // loops up to 7 times when cutting in half.
			mid = (front + back) / 2; // Floor of value intentional for odd number. 
			comparison = theValue.compareTo(this.get(mid)); 
			
			if (comparison < 0) {
				front = mid - 1;
				
			} else if (comparison > 0) {
				back = mid + 1;
				
			} else {
				//debug = get(mid);
				return mid;
				// Stops the loop because it found the data. So now return mid.
			}
			
			//debug = get(mid);
		}
		
		return -(mid + 1);
		// Not found.  The index (positive value) is where the value should have been.
	}
	
}




