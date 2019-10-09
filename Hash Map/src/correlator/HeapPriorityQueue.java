/*
 * Assignment 4, brendel_pr4
 */

package correlator;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/** Implements a priority queue of comparable objects using a max-heap represented as an array.
 * Line#51 was changed to use a greater-than operator to make this a max-heap. 
 * @author BJP, Monika, modified by Sam Brendel
 * @version 5.27C
 * @param <AnyKey> the key.
 * @param <AnyValue> the value.
 */
public class HeapPriorityQueue<AnyKey extends Comparable<? super AnyKey>, 
                             AnyValue extends Comparable<? super AnyValue>> {
    private HeapEntry[] elementData;
    private int size;
    private Comparator<HeapEntry> comparator;
    
    /**
     * Constructor.  Uses the stock comparator in the HeapEntry class which by value in ascending order.
     */
    public HeapPriorityQueue() {
        //elementData = (HeapEntry[]) new Object[10];
    	elementData = (HeapEntry[]) Array.newInstance(HeapEntry.class, 10);
        size = 0;
        comparator = new HeapEntry(); // uses the stock compare() method in the HeapEntry class.
    }
    
    /**
     * Constructor.
     * @param theComparator A custom comparator.
     */
    public HeapPriorityQueue(final Comparator<HeapEntry> theComparator) {
    	this();
    	this.comparator = theComparator; // custom comparator.
    }
    
    /** 
     * Adds the given element to this queue.  
     * Renamed to put().  Formerly was add().
     * @param entry the entry to put into the heap.
     */
    public void put(final HeapEntry entry) {
        // resize if necessary
        if (size + 1 >= elementData.length) {
            elementData = Arrays.copyOf(elementData, elementData.length * 2);
        }
        
        // insert as new rightmost leaf
        elementData[size + 1] = entry;
        
        // "bubble up" toward root as necessary to fix ordering
        int index = size + 1;
        boolean found = false;   // have we found the proper place yet?
        while (!found && hasParent(index)) {
            int parent = parent(index);
            //if (elementData[index].compareTo(elementData[parent]) < 0) {
            //if (compare(elementData[index].getValue(), elementData[parent].getValue()) > 0) { // Change to Max Heap by using a greater-than symbol
            if (comparator.compare(elementData[index], elementData[parent]) > 0) { // Change to Max Heap by using a greater-than symbol
                swap(elementData, index, parent(index));
                index = parent(index);
            } else {
                found = true;  // found proper location; stop the loop
            }
        }
        
        size++;
    }
    
    /**
     *  Returns true if there are no elements in this queue.
     * @return true if it is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /** 
     * Returns the minimum value in the queue without modifying the queue. 
     * If the queue is empty, throws a NoSuchElementException.
     * @return the maximum entry. 
     */
    public HeapEntry peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elementData[1];
    }
    
    /** 
     * Removes and returns the maximum value in the queue. 
     * If the queue is empty, throws a NoSuchElementException.
     * @return the maximum entry that has been removed.
     */
    public HeapEntry remove() {
    	HeapEntry result = peek();

        // move rightmost leaf to become new root
        elementData[1] = elementData[size];
        size--;
        
        // "bubble down" root as necessary to fix ordering
        int index = 1;
        boolean found = false;   // have we found the proper place yet?
        while (!found && hasLeftChild(index)) {
            int left = leftChild(index);
            int right = rightChild(index);
            int child = left;
            if (hasRightChild(index) &&
                //elementData[right].compareTo(elementData[left]) < 0) {
        		comparator.compare(elementData[right], elementData[left]) > 0) {
                child = right;
            }
            
            //if (elementData[index].compareTo(elementData[child]) > 0) {
            if (comparator.compare(elementData[index], elementData[child]) < 0) {
                swap(elementData, index, child);
                index = child;
            } else {
                found = true;  // found proper location; stop the loop
            }
        }
        
        return result;
    }
    
    /**
     * Returns the number of elements in the queue.
     * @return the size.
     */
    public int size() {
        return size;
    }
    
    /** 
     * Returns a string representation of this queue, such as "[10, 20, 30]";  
     * The elements are not guaranteed to be listed in sorted order.
     */
    public String toString() {
        String result = "[";
        if (!isEmpty()) {
            result += elementData[1];
            for (int i = 2; i <= size; i++) {
                result += ", " + elementData[i];
            }
        }
        return result + "]";
    }
    
    /**
     *  helpers for navigating indexes up/down the tree
     * @param index the index.
     * @return the index of its parent.
     */
    private int parent(int index) {
        return index / 2;
    }
    
    /**
     * returns index of left child of given index
     * @param index the index.
     * @return the index of its left child.
     */
    private int leftChild(int index) {
        return index * 2;
    }
    
    /**
     *  returns index of right child of given index
     * @param index the index.
     * @return the index of its right child.
     */
    private int rightChild(int index) {
        return index * 2 + 1;
    }
    
    /**
     *  returns true if the node at the given index has a parent (is not the root)
     * @param index the index.
     * @return true if it has a parent.
     */
    private boolean hasParent(int index) {
        return index > 1;
    }
    
    /**
     * returns true if the node at the given index has a non-empty left child
     * @param index the index.
     * @return true if it has a left child.
     */
    private boolean hasLeftChild(int index) {
        return leftChild(index) <= size;
    }
    
    /** 
     * returns true if the node at the given index has a non-empty right child
     * @param index the index.
     * @return true if it has a right child.
     */
    private boolean hasRightChild(int index) {
        return rightChild(index) <= size;
    }
    
    /**
     * switches the values at the two given indexes of the given array
     * @param a the heap entry.
     * @param index1 the first index.
     * @param index2 the second index.
     */
    private void swap(HeapEntry[] a, int index1, int index2) {
    	HeapEntry temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

    /**
     * Returns the top ten entries having the highest frequency.
     * @return the list of top ten.
     */
	public List<HeapEntry> getTopTen() {
		List<HeapEntry> list = new ArrayList<>(10);
		final int quantity = 10;
		
		for (int i = 0; i < quantity; i++) {
			list.add(remove());
		}
		
		// restore the entries back into the heap.
		for (HeapEntry heapEntry : list) {
			put(heapEntry);
		}
		
		return list;
	}
}
