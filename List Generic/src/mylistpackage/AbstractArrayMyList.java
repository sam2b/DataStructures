/*
 * TCSS342 Data Structures - Spring 2017 - Professor Monika Sobolewska
 * Author: Sam Brendel
 * Assignment: pr1
 */

package mylistpackage;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Abstract class for all ArrayList data structures.
 * @author Sam Brendel
 * @param <E> the data type.
 * @version 4.8E
 */
public abstract class AbstractArrayMyList<E> implements MyList<E> {

	/**
	 * Default list capacity.
	 */
	protected static final int DEFAULT_CAPACITY = 100;

	/**
	 * The underlying array which contains the data.
	 */
	protected E[] myElementData;

	/**
	 * The index of the last element in the list.  This is always 1 less than the list size.
	 */
	protected int myLastElement;

	/**
	 * Constructor for an empty list of the given capacity. myLastElement will be
	 * initially -1 because the list does not yet have elemental data.
	 * @param capacity the specified quantity of elements, but must be {@code >}0.
	 * @throws IllegalArgumentException if capacity {@code <=} 0
	 */
	public AbstractArrayMyList(final int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity: " + capacity);
		}

		myLastElement = -1; // Initial value since there is no data yet.
		// myElementData = (E[]) new Object[capacity]; // The child classes will
		// have their own unique lines like this.
	}

	/**
	 * Ensures that the underlying array has the given capacity; if not,
	 * increases the size by 100.
	 * @param capacity The quantity of elements to increase the capacity to if the
	 *        it must be increased if the specified capacity {@code >}myElementData.length.
	 */
	protected void ensureCapacity(final int capacity) {
		if (capacity > myElementData.length) {
			int newCapacity = myElementData.length + 100;
			
			// This is dead code, and will never be true because of the line above.
			/*if (capacity > newCapacity) {
				newCapacity = capacity;
			}*/
			myElementData = Arrays.copyOf(myElementData, newCapacity);
		}
	}

	/**
	 * Gets the actual size of the list, which is the quantity of elemental data available
	 * within the list.
	 * @see mylistpackage.MyList#getSize()
	 */
	@Override
	public int getSize() {
		return myLastElement + 1;
	}

	/**
     * Inserts the a new element with the value into the list.
     * @param value the value to insert into the list. 
	 * @see mylistpackage.MyList#insert(java.lang.Object)
	 */
	@Override
	public void insert(final E value) {
		ensureCapacity(myLastElement + 2);
		// Then override with more code below.
	}
	
	/*********************************************
	 * Index list methods follow
	 *********************************************/

    /**
     * Removes value at the given index, but does not shift any elements since sorting
     * is not required, except the last element shall replace this deleted element.
     * @param theIndex {@code <=} size and index {@code >=} 0
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >} size
     * @see mylistpackage.AbstractLinkedMyList#removeAtIndex(int)
     */
	@Override
	public void removeAtIndex(final int theIndex) {
		verifyBounds(theIndex);
		// Then override with more code below.
	}
	
	/**
	 * Returns the value at the given index in the list.
	 * @param theIndex {@code <=} size and index {@code >=} 0
	 * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size
	 * @return the value at the given index in the list.
	 * @see mylistpackage.MyList#get(int)
	 */
	@Override
	public E get(final int theIndex) {
		verifyBounds(theIndex);
		return myElementData[theIndex];
	}

	/**
	 * Replaces the value at the given index with the given value.
	 * @param theIndex the index where the element lives, and must be {@code <=} size and index {@code >=} 0.
	 * @param theValue the value the value to replace within the element at the specified index.
	 * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size
	 * @see mylistpackage.AbstractLinkedMyList#set(int, Object)
	 */
	@Override
	public void set(final int theIndex, final E theValue) {
		verifyBounds(theIndex);
	}

	
	/*********************************************
	 * Index list methods end
	 *********************************************/
	
	/**
     * Determines if the list is empty.  
     * @see mylistpackage.MyList#isEmpty()
     * @return true if the list contains zero elements.
     * @see mylistpackage.MyList#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return myLastElement == -1;
	}

	/**
	 * Returns an iterator for this list.
	 * @return an iterator for the list.
	 * @see mylistpackage.MyList#iterator()
	 */
	@Override
	public MyIterator<E> iterator() {
		return new ArrayListIterator();
	}

	/**
     * Creates a comma-separated, bracketed version of the list.
     * @see java.lang.Object#toString()
     * @return the string representation of the list of elements starting from the first 
     * element.
	 */
	@Override
	public String toString() {
		if (myLastElement == -1) {
			return "[]";
		} else {
			String result = "[" + myElementData[0];
			for (int i = 1; i <= myLastElement; i++) {
				result += ", " + myElementData[i];
			}
			result += "]";
			return result;
		}
	}

    /**
     * Verifies theIndex is valid, which must be {@code >=} 0 or {@code <=} myLastElement.
     * @param theIndex to inspect.
     */
	protected void verifyBounds(final int theIndex) {
		// Negative index or and index larger than the last element's index.
		if (theIndex < 0 || theIndex > myLastElement) {
			throw new IndexOutOfBoundsException();
		}
	}

	/*********************************************
	 * Iterator list class / methods follow
	 *********************************************/

	/**
	 * Represents an iterator for the list.
	 * @author BuildingJavaPrograms 3rd Edition
	 * @see mylistpackage.MyIterator
	 * @version 4.8E
	 */
	private class ArrayListIterator implements MyIterator<E> {

		/**
		 * current position within the list.
		 */
		private int position;

		/**
		 * flag that indicates whether list element can be removed.
		 */
		private boolean removeOK;

		/**
		 * Constructor for an iterator for the given list
		 */
		public ArrayListIterator() {
			position = 0;
			removeOK = false;
		}

		/**
		 * Returns whether there are more list elements.
		 * @return true if there are more elements left, false otherwise
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return position <= myLastElement;
		}

		/**
		 * Returns the next element in the iteration.
		 * @throws NoSuchElementException if no more elements.
		 * @return the current element data in the iteration.
		 * @see java.util.Iterator#next()
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			final E result = myElementData[position];
			position++;
			removeOK = true;
			return result;
		}

		/**
		 * Removes the last element returned by the iterator.
		 * @throws IllegalStateException if a call to next has not been made before call to 
		 *                               remove.
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			if (!removeOK) {
				throw new IllegalStateException();
			}
			AbstractArrayMyList.this.removeAtIndex(position - 1);
			position--;
			removeOK = false;
		}

		/**
		 * Resets the iterator to the first element, same as the constructor.
		 * @see mylistpackage.MyIterator#reset()
		 */
		public void reset() {
			position = 0;
			removeOK = false;
		}

	}

	/*********************************************
	 * Iterator list class / methods end
	 *********************************************/


}
