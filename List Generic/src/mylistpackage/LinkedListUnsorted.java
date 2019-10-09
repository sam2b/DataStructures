/*
 * TCSS342 Data Structures - Spring 2017 - Professor Monika Sobolewska
 * Author: Sam Brendel
 * Assignment: pr1
 */

package mylistpackage;

/**
 * Represents basic unsorted node-based list.
 * @author Sam Brendel
 * @version 4.13A
 * @param <E> is of any object type
 * @see mylistpackage.AbstractLinkedMyList
 */
public class LinkedListUnsorted<E> extends AbstractLinkedMyList<E> {

	/**
	 * Constructor.  There is no default capacity since the only size restriction is 
	 * physical memory. 
	 */
    public LinkedListUnsorted() {
    	super();
    }
    
    /**
     * Inserts the a new node with the value into the list. 
     * @see mylistpackage.MyList#insert(java.lang.Object)
     */
    public void insert(E value) {
    	ListNode<E> valueNode = new ListNode<E>(value);
    	
        if (isEmpty()) {
        	tail = valueNode;
        	valueNode.next = valueNode; // A list of a single element circles to itself.
        	
        } else {
        	ListNode<E> startNode = tail.next; // keep track to preserve the circular link.
        	tail.next = valueNode;  // The current node's next points to the new node.
        	tail = valueNode;       // This new node now becomes the tail node.
        	valueNode.next = startNode; // Now the circle is preserved.
        }
        
        myLastElement++;
    }

    /**
     * Replaces the value at the given index with the given value.
     * @param index 0 {@code <=} index {@code <=}size
     * @param value the data to put in this element..
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size
     * @see mylistpackage.MyList#set(int, Object)
     */
    @Override
    public void set(int index, E value) {
		super.set(index, value);
        checkIndex(index);
        ListNode<E> current = nodeAt(index);
        current.data = value;
    }
    

    
}

