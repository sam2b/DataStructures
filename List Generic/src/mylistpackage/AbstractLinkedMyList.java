/*
 * TCSS342 Data Structures - Spring 2017 - Professor Monika Sobolewska
 * Author: Sam Brendel
 * Assignment: pr1
 */

package mylistpackage;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Abstract class for all LinkedList data structures.
 * @author Sam Brendel
 * @param <E> the data type.
 * @see mylistpackage.MyList
 * @version 4.15A
 */
public abstract class AbstractLinkedMyList<E> implements MyList<E> {

    /**
     * Reference to the last node in the list.
     */
    protected ListNode<E> tail; 

    /**
     * index of the last list element.
     */
    protected int myLastElement;  

    /**
     * Constructor for an empty list.
     */
    public AbstractLinkedMyList() {
        tail = null;
        myLastElement = -1;
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
     * Determines if the list is empty.  
     * @see mylistpackage.MyList#isEmpty()
     * @return true if the list contains zero elements.
     */
    public boolean isEmpty() {
        return myLastElement == -1;
    }

    /**
     * Determines if the list contains at least one element of the value.
     * @see mylistpackage.MyList#contains(java.lang.Object)
     * @return true if the value exists at least once in the list.
     */
    public boolean contains(E value) {
        return getIndex(value) >= 0;
    }

    /**
     * Rids the list of all nodes, resulting in a size of zero elements.
     * @see mylistpackage.MyList#clear()
     */
    public void clear() {
        //front = null;
        tail = null;
        myLastElement = -1;
    }

    /**
     * Creates a comma-separated, bracketed version of the list.
     * @see java.lang.Object#toString()
     * @return the string representation of the list of elements starting from the first 
     * element.
     */
    public String toString() {
        if (isEmpty()) {
            return "[]";
            
        } else {
        	/*ListNode<E> startNode = tail.next;
            String result = "[" + startNode.data;

            // The first node has to be processed outside of the loop.
            ListNode<E> current = startNode.next;
            result += ", " + current.data;
            current = current.next;
            
            while (current != startNode) {
                result += ", " + current.data;
                current = current.next;
            }*/

        	Iterator<E> itr = iterator();
            String result = "[" + itr.next();
            
            while(itr.hasNext()) {
	            result += ", " + itr.next();
            }
        	
        	result += "]";
            return result;
        }
    }
    
    /**
     * Removes a node containing the value from the list.  If there are more than one node 
     * that contains the value, there is no guarantee which particular one will be removed.
     * @see mylistpackage.MyList#remove(java.lang.Object)
     */
    @Override
    public void remove(E value) {
    	// I do not like this old code because it is 2n runtime.
    	/*int location = getIndex(value);
        if (location > -1) 
           removeAtIndex(location);*/

    	ListNode<E> previous = tail;
    	
    	// This is 1n run time.
    	for (int i = 0; i < getSize(); i++) {
    		
    		// If I found the element, delete the node.
			if (value.equals(previous.next.data)) {
				previous.next = previous.next.next;
				myLastElement--;
				break; // stop the loop.
			}
			
			previous = previous.next;
		}
    }

    /*********************************************
     * Index list methods follow
     *********************************************/

    /**
     * Returns the index of value.
     * @param value the data in this element..
     * @return index of value if in the list, -1 otherwise.
     * @see mylistpackage.MyList#getIndex(Object)
     */
    public int getIndex(E value) {
        int index = 0;
        ListNode<E> current, startNode;
        
        if(isEmpty()) {
/*        	current = tail;
            if (current.data.equals(value)) {
                return index;
            }
*/
        	return -1; //Guaranteed the value is not in an empty list.
        	
    	} else {
        	startNode = tail.next;
        	current = startNode;

	        do {
		        if (current.data.equals(value)) {
	                return index; // found it!
	            } else {
		            current = current.next;
			        index++;
	            }
	        } while (current != startNode);
        }
        
        return -1; // not found.
    }
    
    /**
     * Removes value at the given index, shifting subsequent values up.
     * @param index {@code <=} size and index {@code >=} 0
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size
     * @see mylistpackage.MyList#removeAtIndex(int)
     */
    public void removeAtIndex(int index) {
        checkIndex(index);

        // If there is only one node in the list, remove it.
        if (getSize() == 1) {
        	tail = null;
        	
        //} else if(!isEmpty()) {
        	// The first index is treated uniquely.
        } else if (index == 0) {
	            tail.next = tail.next.next;

	        } else { 
	        	// Index 1 and higher.
                ListNode<E> previous = nodeAt(index - 1); // previous node.
                previous.next = previous.next.next;
	        }
        //}
        
        // else if list is empty, do absolutely nothing.
        myLastElement--;
    }
    
    /**
     * Replaces the value at the given index with the given value.
     * @param index 0 {@code <=} index {@code <=}size
     * @param value the data to put in this element..
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size
     * @see mylistpackage.MyList#set(int, Object)
     */
    public void set(int index, E value) {
		verifyBounds(index);
		// Then override more code below.
    }
    
    /**
     * Returns the value at the given index in the list.
     * @param index 0 {@code <=} index {@code <=}size
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >}size
     * @return the value at the given index in the list.
     * @see mylistpackage.MyList#get(int)
     */
    public E get(int index) {
        checkIndex(index);
        return nodeAt(index).data;
    }

    /**
     * Returns the node at a specific index.
     * @param index where 0 {@code <=} index {@code <=} size
     * @return reference to the node at a specific index
     */
    protected ListNode<E> nodeAt(int index) {
    	ListNode<E> current = tail.next;

    	// Note: This private method assumes the index is already a valid value.
        
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        
        return current;
    }
    
    /**
     * Checks if the index is a legal index of the current list.
     * @param index the index to inspect.
     * @throws IndexOutOfBoundsException if the given index is not a legal index of the current list
     */
    protected void checkIndex(int index) {
        if (index < 0 || index >= getSize()) {
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
     * @return an iterator for the list.
     */
    public MyIterator<E> iterator() {
        return new LinkedIterator();
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
   
    /**
     * Represents a list node.
     * @author Building Java Programs 3rd ed.
     * @param <E> is of any object type
     * @version 4.8E
     */
    protected static class ListNode<E> {

        /**
         * Data stored in this node.
         */
        public E data; 

        /**
         * Link to next node in the list.
         */
        public ListNode<E> next;  

       
        /**
         * Constructs a node with given data and a null link.
         * @param data the data in this element.
         */
        public ListNode(E data) {
            this(data, null);
        }

        /**
         * Constructs a node with given data and given link.
         * @param data the data this element contains.
         * @param next a pointer to the next element.
         */
        public ListNode(E data, ListNode<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * Represents an iterator for the list.
     * @author modified from BuildingJavaPrograms 3rd Edition
     * @see mylistpackage.MyIterator
     * @version 4.8E
     */
    private class LinkedIterator implements MyIterator<E> {
        
        /**
         * Location of current value to return.
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
         * Keeps track of what index the current node is at.  Since this is
         * a circular list, there is no other way to internally keep track
         * of where the start and end is.  The alternative is infinite
         * loops for the client.
         */
        private long index;

        /**
         * Constructor for an iterator for the given list.
         */
        public LinkedIterator() {
        	index = 0;
            current = tail; // do not set to tail.next, because an empty list starts with tail = null.
            removeOK = false;
            prior = null;
        }

        /**
         * Returns whether there are more list elements.
         * @return true if there are more elements left, false otherwise
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            //return current != null;
        	return index <= myLastElement;
        	
            /* If I do not use an index:
             * Since I cannot keep track of the startNode, this is implicitly 
               infinite since this is a circular linked list.
               So, then, it is up to the user to keep track of first node 
               returned by this iterator if and "end" is of any concern. */ 
        }

        /**
         * Returns the next element in the iteration.
         * @throws NoSuchElementException if no more elements.
         * @return the current element's data in the iteration.  Returns only the data, not the node.
         * @see java.util.Iterator#next()
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            // Initially.
            if (index == 0) {
            	current = tail.next;
            }
            
        	index++;
            prior = current;
            E result = current.data;
            current = current.next;
            removeOK = true;
            return result;
        }

        /**
         * Removes the last element returned by the iterator.
         * @throws IllegalStateException if a call to next has not been made
         *             before call to remove.
         * @see java.util.Iterator#remove()
         */
        public void remove() {
            if (!removeOK) {
                throw new IllegalStateException();
            }
            AbstractLinkedMyList.this.remove(prior.data);
            removeOK = false;
            index--;
        }

        /**
         * Resets the iterator to the first element, same as the constructor does.
         * @see mylistpackage.MyIterator#reset()
         */
		public void reset() {
        	index = 0;
            current = tail;
            removeOK = false;
            prior = null;
		}
    }

}
