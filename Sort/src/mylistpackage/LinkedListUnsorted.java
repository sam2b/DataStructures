package mylistpackage;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents basic unsorted node-based list.
 * 
 * @author modified from Building Java Programs 3rd ed.
 * @version Sep 2016, 4.28A
 * @param <E>
 *            is of any object type
 */
public class LinkedListUnsorted<E extends Comparable<? super E>> implements MyList<E> {

	/**
	 * Reference to the first node in the list.
	 */
	private ListNode<E> front;

	/**
	 * Reference to the last node in the list.
	 */
	private ListNode<E> back;

	/**
	 * index of the last list element
	 */
	private int size;

	/**
	 * Constructs an empty list.
	 */
	public LinkedListUnsorted() {
		front = null;
		back = null;
		size = -1;
	}

	/**
	 * @see mylistpackage.MyList#getSize()
	 */
	@Override
	public int getSize() {
		return size + 1;
	}

	/**
	 * Sam modified 4.22
	 */
	public void myinsertion() {
		insertionSort();
	}
	
	/**
	 * +5 +n(+1 +2 +1 +3 +1 +3 +10m +2)
	 * A Simple insertion sort. Must start from the beginning and compare each element.
	 */
	private void insertionSort() {
		ListNode<E> current = front;
		ListNode<E> aux = front;
		ListNode<E> tempA = null;
		ListNode<E> tempCurrent = null;
		ListNode<E> auxPrevious = null;
		
		//System.out.println("\n\nBefore: " + this); // debugging
		
		// n(+1 +2 +1 +3 +1 +3 +10m +2 )
		// Look through all n elements, from front to back.
		while (current != null) {
			
            // Special case current is the last element of the list.
			tempCurrent = (current.next == null) ? current : current.next;
			
			aux = front;
            // Determine if the node needs to be moved.
			if (tempCurrent.data.compareTo(current.data) < 0) {
				
				// STEP 1: REMOVE THE NODE.
				tempA = current.next;
                // Special case current is the last element of the list.
				current.next = (current.next == null) ? null : current.next.next;
				
				/* STOP WHEN AUX REACHES CURRENT, OR WHEN AT THE BACK OF THE LIST.
                   AFTER THAT, CURRENT WILL ADVANCE, AND THIS PROCESS REPEATS. */
				do {
					if (tempA.data.compareTo(aux.data) <= 0) { // Determine where the node needs to be inserted.

						// STEP 2: PUT THE NODE IN THE CORRECT SPOT.
						if (aux == front) {
							tempA.next = front;
							front = tempA;
							
						} else {
							tempA.next = auxPrevious.next;
							auxPrevious.next = tempA;
						}
						
						if (tempA == back) {
							back = current;
							current = null; // Sort done. Null is the sentinel.
						}
						
						break; // stop the inner while loop here.
					}
					
					// Advance aux.
					auxPrevious = aux;
					aux = aux.next;
				} while(auxPrevious != current);
				
			} else {
				// Advance only if the condition is false;
				current = current.next;
			}
			//System.out.println(this); // debugging
		}
		
		//System.out.println("After:  " + this); // debugging
	}

	/**
	 * Sam modified 4.22
	 */
	public void myselection() {
		selectionSort();
	}

	/**
	 * An object that stores the desired node's references to its 
	 * previous node, current node, and next node node, like a snapshot in time.
     * It basically emulates having a doubley-linked list, which makes managing
     * node swapping easier, thus making it easier to shuffling nodes in a 
     * singly-linked list.  It also makes the code easier to write and read.
     * There is not much overhead with this object.
	 * @author Sam Brendel
	 */
	private class AuxWrapper {
		private ListNode<E> preNode;
		private ListNode<E> curNode;
		private ListNode<E> nxtNode; // optional.
		ListNode<E> last = null;

		public AuxWrapper() {
			preNode = null;
			curNode = null;
			nxtNode = null;
		}
		
		/**
		 * +3
		 * Constructor.
		 * @param thePrevious
		 * @param theCurrent
		 */
		public AuxWrapper(final ListNode<E> thePrevious, final ListNode<E> theCurrent) {
			this(thePrevious, theCurrent, null);
		}

		/**
		 * +3
		 * Constructor.
		 * @param thePrevious
		 * @param theCurrent
		 * @param theNext optional.
		 */
		public AuxWrapper(final ListNode<E> thePrevious, final ListNode<E> theCurrent, final ListNode<E> theNext) {
			this.preNode = thePrevious;
			this.curNode = theCurrent;
			this.nxtNode = theNext;
		}
		
		/**
		 * +3
		 * Copy.
		 * @param other
		 */
		public void copyFrom(final AuxWrapper other) {
			this.preNode = other.preNode;
			this.curNode = other.curNode;
			this.nxtNode = other.nxtNode;
		}
		
        /**
		 * +5
		 * Update the NodeAux node relative to its previous ListNode node.
		 * @param theNodeAux
		 * @param theNode
		 */
		public void updateRelativeTo(final ListNode<E> theNode) {
			if (theNode == front) {
				this.preNode = front;
				
			} else {
				this.preNode = theNode;
			}
			
			this.curNode = theNode.next;
			
			if (theNode.next != null) {
				this.nxtNode = theNode.next.next;
			} else {
				this.nxtNode = null;
			}
		}

		/**
		 * +6
		 * @param other
		 */
		public void replaceWith(final AuxWrapper other) {

			// Only use this method once in the swap() method.
			if (this.curNode == front) {
				front = other.curNode;
				
			} else {
				this.preNode.next = other.curNode;  // Link it in.
			}
			
			other.curNode.next = this.curNode.next; // Link it in.

			this.nxtNode = other.nxtNode;
			
			// mind the back.
			if (this.curNode == back) {
				back = other.curNode;
			} 
		}
		
		/**
		 * +5
		 */
		public void advance() {
			this.preNode = this.curNode;
			
			if (this.curNode != null) {
				this.curNode = this.curNode.next;
			}
			
			if (this.nxtNode != null) {
				this.nxtNode = this.nxtNode.next;
			}
		}

		/**
		 * +3
		 */
		@SuppressWarnings("unused")
		public void clean() {
			this.preNode = null;
			this.curNode = null;
			this.nxtNode = null;
		}
		
		/**
		 * +17 +3 +3 +6 = +29
		 * @param other
		 * @param tempA pass in an existing temporary variable to speed up the algorithm by not instantiating anew each time.
		 * @param tempB pass in an existing temporary variable to speed up the algorithm by not instantiating anew each time.
		 */
		public void swapWith(final AuxWrapper other, AuxWrapper tempA, AuxWrapper tempB) {
			//String block = ""; // debugging TODO remove me
			
			//try { // debugging TODO remove me
				
				// If swapping side by side. 100% with opposite order data.
				if(this.curNode == other.preNode) {
					//block = "First block."; // debugging TODO remove me
					last = other.curNode.next;
					
					if (this.curNode == front) {
						front = other.curNode;
					} else {
						this.preNode.next = other.curNode;    // Step 1.
					}
					
					other.curNode.next = this.curNode;        // Step 2.
					this.curNode.next = last;                 // Step 3.
					
				// If swapping general.
				} else {
					//block = "Second block."; // debugging TODO remove me
					//final AuxWrapper tempA =  new AuxWrapper(this);   // front-most node.
					//final AuxWrapper tempB =  new AuxWrapper(other);  // back-most node.
					tempA.copyFrom(this);
					tempB.copyFrom(other);
					last = tempB.curNode.next;

					// Step 1.
					this.replaceWith(other);

					// Step 2.
					// tempB.replace(tempA); DO NOT DO THIS.  manually do it below.
					tempB.preNode.next = tempA.curNode;
					tempA.curNode.next = last; 
					
					if (tempA.curNode == front) {
						front = tempB.curNode;
					}
					
					if (tempB.curNode == back) {
						back = tempA.curNode;
					}
					
					tempA = null; // clean up.
					tempB = null; // clean up.
				}
				
				// Cleanup.
				last = null;
				tempA = null;
				tempB = null; 
				
			/*} catch (Exception e) { // debugging TODO remove me
				System.err.println("Exception at " + block);
				System.err.println(e.getMessage());
				throw e;
				
			}*/
			
			//System.out.println(block); // debugging TODO remove me
		}
		
		/**
		 * Only used for debugging.  This allows you to clearly see what data
		 * is being held and pointed to.  Otherwise, what a mess.
		 */
		@Override
		public String toString() {  // debugging, otherwise keep commented out.
			StringBuilder s = new StringBuilder();

			// The AuxWrapper's pointers.
			s.append(this.preNode);
			s.append("---");
			s.append(this.curNode);
			s.append("---");
			s.append(this.nxtNode); // Not the actual node's next.
			
			// The actual list's pointer.
			s.append("   actuall ");
			s.append(this.curNode);
			s.append("->");
			s.append(this.curNode.next); // The actual node's pointer.
			return s.toString();
		}
	}

	// +8 n((n-m)(+2 +3 +3 +3 +12 +3(n-(n-m)) +3(n-(n-m)) +3)) 
	// =26n^2 or O(n^2)
	private void selectionSort() {
		// +17 +n(+1 + n(+2 +4 +5 +5) +4 +29 +5 +m +5 +m +5)
		int index = 0;
		int auxIndex = 0;
		final AuxWrapper current  = new AuxWrapper(front, front);
		final AuxWrapper aux      = new AuxWrapper(front, front);
		final AuxWrapper smallest = new AuxWrapper(front, front);
		AuxWrapper tempA = new AuxWrapper();
		AuxWrapper tempB = new AuxWrapper();
		//ListNode<E> nextNode = null; 
		//boolean isSwapped = false;
		
		// 1) Find the smallest from index while index < size.
		// 2) compare smallest with nodeAt(index)
		// 3)     swap if smaller.
		// 4) repeat while i < size
		
		//System.out.println("\n--------------------------------------\nBefore: " + this); // debugging TODO remove me.
		
		// n
		while(index < size) {
			
			// +1 + n(+2 +3 +5 +5) +3 +29 +5 +m +5 +m +5
			// Find smallest
			for (int i = auxIndex; i <= size; i++) {
				if (aux.curNode.data.compareTo(smallest.curNode.data) < 0) { // +4 compareTo()
					smallest.updateRelativeTo(aux.preNode);
				}
				aux.advance();
			} // end find smallest.
			
			// Swap the nodes if true.
			if (current.curNode.data.compareTo(smallest.curNode.data) > 0) { // +4 compareTo()
				current.swapWith(smallest, tempA, tempB);
			}

			current.updateRelativeTo(nodeAt(index++)); // iteration unavoidable. +3(nodeAt() n-(n-m))
			
			aux.updateRelativeTo(nodeAt(auxIndex++)); // +3(nodeAt() n-(n-m))
			smallest.updateRelativeTo(current.preNode); // The new smallest is the same node as current.
		}
		//System.out.println("After:  " + this); // debugging TODO remove me.
	}

	/**
	 * @see mylistpackage.MyList#isEmpty()
	 */
	public boolean isEmpty() {
		return size == -1;
	}

	/**
	 * @see mylistpackage.MyList#contains(java.lang.Object)
	 */
	public boolean contains(final E value) {
		return getIndex(value) >= 0;
	}

	/**
	 * @see mylistpackage.MyList#insert(java.lang.Object)
	 */
	@Override
	public void insert(final E value) {
		final ListNode<E> valueNode = new ListNode<E>(value);
		if (size == -1) {
			front = valueNode;
			back = valueNode;
		} else {
			back.next = valueNode;
			back = valueNode;
		}
		size++;
	}

	/**
	 * @see mylistpackage.MyList#clear()
	 */
	public void clear() {
		front = null;
		back = null;
		size = -1;
	}

	/**
	 * Creates a comma-separated, bracketed version of the list.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (size == -1) {
			return "[]";
		} else {
			String result = "[" + front.data;
			ListNode<E> current = front.next;
			while (current != null) {
				result += ", " + current.data;
				current = current.next;
			}
			result += "]";
			return result;
		}
	}

	/**
	 * @see mylistpackage.MyList#remove(java.lang.Object)
	 */
	@Override
	public void remove(E value) {
		final int location = getIndex(value);
		if (location > -1)
			removeAtIndex(location);
	}

	/*********************************************
	 * Index list methods follow
	 *********************************************/

	/**
	 * Returns the index of value.
	 * 
	 * @param value
	 *            assigned.
	 * @return index of value if in the list, -1 otherwise.
	 */
	public int getIndex(E value) {
		int index = 0;
		ListNode<E> current = front;
		while (current != null) {
			if (current.data.equals(value)) {
				return index;
			}
			index++;
			current = current.next;
		}
		return -1;
	}

	/**
	 * Removes value at the given index, shifting subsequent values up.
	 * 
	 * @param index
	 *            <= size and index >= 0
	 * @throws IndexOutOfBoundsException
	 *             if index < 0 or index > size
	 */
	public void removeAtIndex(final int index) {
		checkIndex(index);
		if (index == 0) {
			front = front.next;
			if (size == 0)
				back = null;
		} else {
			final ListNode<E> current = nodeAt(index - 1);
			current.next = current.next.next;
			if (current.next == null)
				back = current;
		}

		size--;
	}

	/**
	 * Replaces the value at the given index with the given value.
	 * 
	 * @param 0
	 *            <= index <=size
	 * @param value
	 *            is assigned
	 * @throws IndexOutOfBoundsException
	 *             if index < 0 or index > size
	 */
	public void set(final int index, final E value) {
		checkIndex(index);
		ListNode<E> current = nodeAt(index);
		current.data = value;
	}

	/**
	 * Returns the value at the given index in the list.
	 * 
	 * @param 0
	 *            <= index <=size
	 * @throws IndexOutOfBoundsException
	 *             if index < 0 or index > size
	 * @return the value at the given index in the list.
	 */
	public E get(final int index) {
		checkIndex(index);
		final ListNode<E> current = nodeAt(index);
		return current.data;
	}

	/**
	 * Returns the node at a specific index.
	 * 
	 * @param index
	 *            where 0 <= index <= size
	 * @return reference to the node at a specific index
	 */
	private ListNode<E> nodeAt(final int index) {
		ListNode<E> current = front;
		for (int i = 1; i <= index; i++) {
			current = current.next;
		}
		return current;
	}

	/**
	 * Checks if the index is a legal index of the curNode list.
	 * 
	 * @param index
	 * @throws IndexOutOfBoundsException
	 *             if the given index is not a legal index of the curNode list
	 */
	private void checkIndex(final int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("index: " + index);
		}
	}

	/*********************************************
	 * Index list methods end
	 *********************************************/

	/*********************************************
	 * Iterator list class / methods follow
	 *********************************************/

	/**
	 * Returns an iterator for this list.
	 * 
	 * @return an iterator for the list.
	 */
	public Iterator<E> iterator() {
		return new LinkedIterator();
	}

	/**
	 * Represents a list node.
	 * 
	 * @author Building Java Programs 3rd ed.
	 *
	 * @param <E>
	 *            is of any object type
	 */
	private static class ListNode<E extends Comparable<? super E>> {

		/**
		 * Data stored in this node.
		 */
		public E data;

		/**
		 * Link to nxtNode node in the list.
		 */
		public ListNode<E> next;

		/**
		 * Constructs a node with given data and a null link.
		 * 
		 * @param data
		 *            assigned
		 */
		public ListNode(final E data) {
			this(data, null);
		}

		/**
		 * Constructs a node with given data and given link.
		 * 
		 * @param data
		 *            assigned
		 * @param nxtNode
		 *            assigned
		 */
		public ListNode(final E data, final ListNode<E> next) {
			this.data = data;
			this.next = next;
		}

		@Override
		public String toString() {
			return "[" + this.data.toString() + "]";
		}
	}

	/**
	 * Represents an iterator for the list.
	 * 
	 * @author modified from BuildingJavaPrograms 3rd Edition
	 */
	private class LinkedIterator implements Iterator<E> {

		/**
		 * Location of curNode value to return.
		 */
		private ListNode<E> current;

		/**
		 * flag that indicates whether list element can be removed.
		 */
		private boolean removeOK;

		/**
		 * Location of the prior value in case of removal.
		 */
		private ListNode<E> prior;

		/**
		 * Constructs an iterator for the given list.
		 */
		public LinkedIterator() {
			current = front;
			removeOK = false;
			prior = null;
		}

		/**
		 * Returns whether there are more list elements.
		 * 
		 * @return true if there are more elements left, false otherwise
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return current != null;
		}

		/**
		 * Returns the nxtNode element in the iteration.
		 * 
		 * @throws NoSuchElementException
		 *             if no more elements.
		 * @return the nxtNode element in the iteration.
		 * @see java.util.Iterator#next()
		 */
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			prior = current;
			final E result = current.data;
			current = current.next;
			removeOK = true;
			return result;
		}

		/**
		 * Removes the last element returned by the iterator.
		 * 
		 * @throws IllegalStateException
		 *             if a call to nxtNode has not been made before call to
		 *             remove.
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			if (!removeOK) {
				throw new IllegalStateException();
			}
			LinkedListUnsorted.this.remove(prior.data);
			removeOK = false;
		}
	}

	/**
	 * Helper method to swaps a[i] with a[j].
	 * 
	 * @param a
	 * @param i
	 * @param j
	 */
	public static void swap(final int[] a, final int i, final int j) {
		if (i != j) {
			final int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}

}
