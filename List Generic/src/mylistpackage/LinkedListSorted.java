/*
 * TCSS342 Data Structures - Spring 2017 - Professor Monika Sobolewska
 * Author: Sam Brendel
 * Assignment: pr1
 */

package mylistpackage;

/**
 * Represents basic unsorted node-based list.
 * @author Sam Brendel, modified from Building Java Programs 3rd ed.
 * @version 4.15A
 * @param <E> is of any object type
 * @see java.lang.Comparable
 * @see mylistpackage.AbstractLinkedMyList
 */
public class LinkedListSorted<E extends Comparable<? super E>> extends AbstractLinkedMyList<E> {

	/**
	 * Constructor.  There is no default capacity since the only size restriction is 
	 * physical memory. 
	 */
    public LinkedListSorted() {
    	super();
    }

    /**
     * Replaces the value at the given index with the given value.
     * @param theIndex 0 {@code <=} index {@code <=} size
     * @param theValue the data to put in this element.
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size.
     * @throws IllegalArgumentException if theValue is not {@code <=} to the right-most node's data, 
     *         or if the value is not {@code >=} to the left-most node's data.
     * @see mylistpackage.AbstractLinkedMyList#set(int, Object)
     */
    @Override
	public void set(final int theIndex, final E theValue) {
		super.set(theIndex, theValue);
		
		/* Special case if the list only has a single element.  Comparison is
		 * irrelevant since set() replaces the data at the specified index. */
		if(getSize() == 1) {
			tail.data = theValue;
			
		} else {
			// The current node is the PRIOR node to where it should be inserted.
			ListNode<E> current =  (theIndex > 1) ? nodeAt(theIndex - 1) : nodeAt(theIndex);
			
			// Example list: [1, 8, 50, 99]
			//        Index:  0  1   2   3
			// the current node will be index 1, value of 8.
			// If I want to set index 2 to a value of 3, that would be illegal.
			// Must verify upper and lower.
	
			// Example list: [S, S, S, S]
			//        Index:  0  1  2  3
			// inserting "N" at index 0 is legal.
			
			int upper = theValue.compareTo(current.next.data);
	        int lower = theValue.compareTo(current.data);
			
	        if ((theIndex > 0 && (upper > 0 || lower < 0))
	          ||(theIndex == 0 && upper > 0)) {
				throw new IllegalArgumentException(
						"Wrong location to insert this element at index " + theIndex);
				
			} else if (theIndex == 0) {
				current.data = theValue; // Special case for index zero.
				
			} else {
				current.next.data = theValue; // This is the client's intended position.
			}
	        
		}
	}
    
    /**
     * Inserts the value at the correct location to preserve the ascending
     * order of the list.
     * @param value the value to insert.
     * @see mylistpackage.AbstractLinkedMyList#insert(Object)
     */
    public void insert(E value) {
    	ListNode<E> valueNode = new ListNode<E>(value);
    	ListNode<E> current = tail;
    	boolean insertAtEnd = false;

        if (isEmpty()) {
        	tail = valueNode;
        	valueNode.next = valueNode; // A list of a single element circles to itself.

        } else if (getSize() == 1) {
        	// The scenario where a single node list consists only of tail, which points to itself.
    		valueNode.next = current;
    		current.next = valueNode;
        	
        	// If the value is less than the only node in the list.
        	if (value.compareTo(current.data) < 0) {
        		tail = current;
        		
        	// If the value is greater than the only node in the list.
        	} else if (value.compareTo(current.data) > 0) {
        		tail = valueNode;
        	}
        	// If the two nodes' data is equal, it is ok for the tail to remain =current.
        	
        } else {
        	for(int i = 0; (i < getSize()) 
        			&& value.compareTo(current.next.data) > 0; i++) {
        		current = current.next;
        		
        		// This means the valueNode will be the last node in the list, aka: tail.
        		if(i == getSize() - 1) {
        			insertAtEnd = true;
        		}
        	}
        	
    		ListNode<E> temp = current.next;
    		current.next = valueNode;
    		valueNode.next = temp;
    		
    		// Special case when the data inserted is the last node in the list.
    		if(insertAtEnd) {
    			tail = valueNode;
    		}
        }

        myLastElement++;
    }

   // NOTE: Do no use binary search on any linked list.  There is no advantage since it is not an array.

   // IMPORTANT: Any method that uses insert() also needs to be overridden here, and calling this.insert().

}

