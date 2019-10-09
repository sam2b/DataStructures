package mylistpackage;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents basic unsorted array-based list.
 * 
 * @author modified from Building Java Programs 3rd ed.
 * @version Sep 26, 2016
 * @param <E> is of any object type
 */
public class ArrayListUnsorted<E extends Comparable<? super E>> implements MyList<E> {

    /**
     * default list capacity.
     */
    private static final int DEFAULT_CAPACITY = 100;

    /**
     * list of values
     */
    private E[] elementData;

    /**
     * index of the last element in the list
     */
    private int size;

    /**
     * Constructs an empty list of default capacity.
     */
    public ArrayListUnsorted() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty list of the given capacity.
     * 
     * @param capacity > 0
     * @throws IllegalArgumentException if capacity <= 0
     */
    @SuppressWarnings("unchecked")
    public ArrayListUnsorted(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }
        elementData = (E[])(new Comparable[capacity]);
        size = -1;
    }

    /**
     * @see mylistpackage.MyList#getSize()
     */
    public int getSize() {
        return size + 1;
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
    public boolean contains(E value) {
        for (int i = 0; i <= size; i++) {
            if (elementData[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see mylistpackage.MyList#insert(java.lang.Object)
     */
    public void insert(E value) {
        ensureCapacity(size + 2);
        size++;
        elementData[size] = value;       
    }

    /**
     * @see mylistpackage.MyList#clear()
     */
    @SuppressWarnings("unchecked")
	public void clear() {
    	elementData = (E[]) new Object[DEFAULT_CAPACITY];
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
            String result = "[" + elementData[0];
            for (int i = 1; i <= size; i++) {
                result += ", " + elementData[i];
            }
            result += "]";
            return result;
        }
    }

    /**
     * @see mylistpackage.MyList#remove(java.lang.Object)
     */
    public void remove(E value) {
        int index = getIndex(value);
        if (size >= 0 && index >= 0) {
            elementData[index] = elementData[size];
            elementData[size] = null;
            size--;
        }
    }

    /**
     * Ensures that the underlying array has the given capacity; if not,
     * increases the size by 100.
     * 
     * @param capacity > elementData.length.
     */
    private void ensureCapacity(int capacity) {
        if (capacity > elementData.length) {
            int newCapacity = elementData.length + 100;
            if (capacity > newCapacity) {
                newCapacity = capacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    /*********************************************
     * Index list methods follow
     *********************************************/

    /**
     * Returns the index of value.
     * 
     * @param value assigned.
     * @return index of value if in the list, -1 otherwise.
     */
    public int getIndex(E value) {
        for (int i = 0; i <= size; i++) {
            if (elementData[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Removes value at the given index, shifting subsequent values up.
     * 
     * @param index <= size and index >= 0
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     */
    public void removeAtIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = index; i < size; i++) {
            elementData[i] = elementData[i + 1];
        }
        elementData[size] = null;
        size--;
    }

    /**
     * Replaces the value at the given index with the given value.
     * 
     * @param index <= size and index >= 0
     * @value is assigned
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     */
    public void set(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        elementData[index] = value;
    }

    /**
     * Returns the value at the given index in the list.
     * 
     * @param index <= size and index >= 0
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     * @return the value at the given index in the list.
     */
    public E get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        return elementData[index];
    }
    
    // Sam modified 4.22
    public void myinsertion() {
    	//Arrays.sort(elementData, 0, size + 1);
    	insertionSort(elementData);
    }
    
    /**
     * +1 +n +n(+2 +7m + 1)
     * Simple insertion sort.
     * @param a an array of Comparable items.
     */
    private void insertionSort( E [ ] a ) {
        for( int p = 1; p < size; p++ )
        {
            E tmp = a[p]; // O(n) to create an array.
            int j = p;

            for( ; j > 0 && tmp.compareTo( a[ j - 1 ] ) < 0; j-- ) {
            //while(j > 0 && tmp.compareTo(a[ j - 1 ] ) < 0) {
            	a[j] = a[j-1];
            }
            
            a[j] = tmp;
        }
    }

    /**
     * +1 +m(+m +1 +m(+4)
     * Internal insertion sort routine for subarrays
     * that is used by quicksort.
     * @param a an array of Comparable items.
     * @param low the left-most index of the subarray.
     * @param n the number of items to sort.
     */
    private void insertionSort( E [ ] a, int low, int high )
    {
        for( int p = low + 1; p <= high; p++ )
        {
            E tmp = a[ p ]; // O(n) to create an array.
            int j;

            for( j = p; j > low && tmp.compareTo( a[ j - 1 ] ) < 0; j-- )
                a[ j ] = a[ j - 1 ];
            a[ j ] = tmp;
        }
    }
    
    /**
     * +1 +n(+3 n(5) +1 +n +2) 
     * Sam modified 4.22
     * Places the elements of the given array into sorted order
     * using the selection sort algorithm.
     * post: array is in sorted (nondecreasing) order
     */
    public void myselection() {
    	//Arrays.sort(elementData, 0, size + 1);
    	
        for (int i = 0; i < size - 1; i++) {
        	
            // find index of smallest element
            int smallest = i;
            for (int j = i + 1; j < size; j++) {
            	
                if (elementData[j].compareTo(elementData[smallest]) < 0) {
                    smallest = j;
                }
            }
            
            swap(elementData, i, smallest);  // swap smallest to front
        }
    }
    
    /**
     * +1 +n +2
     * Swaps a[i] with a[j].
     * @param a
     * @param i
     * @param j
     */
    private void swap(E[] a, int i, int j) {
        if (i != j) {
            E temp = a[i]; // O(m) to create an array
            a[i] = a[j];
            a[j] = temp;
        }
    }
    

    /**
     * Sam Modified 4.22
     */
    public void myquick() {
    	//Arrays.sort(elementData, 0, size + 1);
    	quicksort(elementData, 0, size - 1);
    }
    

    /**
     * +2 +(1 +m(+m +1 +m(+4)) or +3(+4 +n +2) +n+2 +1 +2m + 2 +n
     * Internal quicksort method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * @param a an array of Comparable items.
     * @param low the left-most index of the subarray.
     * @param high the right-most index of the subarray.
     */
    private void quicksort( E [ ] a, int low, int high )
    {
    	final int CUTOFF = 10;
    	
        if( low + CUTOFF > high )
            insertionSort( a, low, high ); // SHOULD I REALLY DO THIS INSTEAD?
        
        else {
	        // Sort low, middle, high
	
	    	int middle = ( low + high ) / 2;
	        if( elementData[ middle ].compareTo( elementData[ low ] ) < 0 )
	            swapReferences( elementData, low, middle );
	        if( elementData[ high ].compareTo( elementData[ low ] ) < 0 )
	            swapReferences( elementData, low, high );
	        if( elementData[ high ].compareTo( elementData[ middle ] ) < 0 )
	            swapReferences( elementData, middle, high );
	
	            // Place pivot at position high - 1
	        swapReferences( elementData, middle, high - 1 );
	        E pivot = elementData[ high - 1 ];
	
	        // Begin partitioning
	        int i, j;
	        for( i = low, j = high - 1; ; )
	        {
	            while( elementData[ ++i ].compareTo( pivot ) < 0 )
	                ;
	            while( pivot.compareTo( elementData[ --j ] ) < 0 )
	                ;
	            if( i >= j )
	                break;
	            swapReferences( elementData, i, j );
	        }
	
            // Restore pivot
	        swapReferences( elementData, i, high - 1 );
	
	        quicksort( elementData, low, i - 1 );    // Sort small elements
	        quicksort( elementData, i + 1, high );   // Sort large elements
	
        }
    }


    /**
     * n + 2
     * Helper method to swap to elements in an array.
     * @param a an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    private void swapReferences( E [ ] a, int index1, int index2 )
    {
        E tmp = a[ index1 ]; // O(n) for creating an array.
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = tmp;
    }


    

    /**
     * Sam Modified 4.22
     */
    public void mymerge() {
    	//Arrays.sort(elementData, 0, size + 1);
    	mergeSort2(elementData);
    }

    @SuppressWarnings("unchecked")
	private void mergeSort2(E[] a) {
        E[] temp = (E[])(new Comparable[a.length]);
        mergeSort2(a, temp, 0, a.length - 1);
    }
    
    private void mergeSort2(E[] a, E[] temp, int left, int right) {
        if (left >= right) {  // base case
            return;
        }
        
        // sort the two halves
        int mid = (left + right) / 2;
        mergeSort2(a, temp, left, mid);
        mergeSort2(a, temp, mid + 1, right);
        
        // merge the sorted halves into a sorted whole
        merge2(a, temp, left, right);
    }
    
    private void merge2(E[] a, E[] temp, int left, int right) {
        int mid = (left + right) / 2;
        int count = right - left + 1;
        
        int l = left;                  // counter indexes for L, R
        int r = mid + 1;
        
        // main loop to copy the halves into the temp array
        for (int i = 0; i < count; i++)
            if (r > right) {           // finished right; use left
            temp[i] = a[l++];
        } else if (l > mid) {      // finished left; use right
            temp[i] = a[r++];
        } else if (a[l].compareTo(a[r]) < 0) {  // left is smaller
            temp[i] = a[l++];
        } else {                   // right is smaller (better)
            temp[i] = a[r++];
        }
        
        // copy sorted temp array back into main array
        for (int i = 0; i < count; i++) {
            a[left + i] = temp[i];
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
        return new ArrayListIterator();
    }

    /**
     * Represents an iterator for the list.
     * 
     * @author BuildingJavaPrograms 3rd Edition
     */
    private class ArrayListIterator implements Iterator<E> {

        /**
         * current position within the list.
         */
        private int position;

        /**
         * flag that indicates whether list element can be removed.
         */
        private boolean removeOK;

        /**
         * Constructs an iterator for the given list
         */
        public ArrayListIterator() {
            position = 0;
            removeOK = false;
        }

        /**
         * Returns whether there are more list elements.
         * 
         * @return true if there are more elements left, false otherwise
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            return position <= size;
        }

        /**
         * Returns the next element in the iteration.
         * 
         * @throws NoSuchElementException if no more elements.
         * @return the next element in the iteration.
         * @see java.util.Iterator#next()
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = elementData[position];
            position++;
            removeOK = true;
            return result;
        }

        /**
         * Removes the last element returned by the iterator.
         * 
         * @throws IllegalStateException if a call to next has not been made
         *             before call to remove.
         * @see java.util.Iterator#remove()
         */
        public void remove() {
            if (!removeOK) {
                throw new IllegalStateException();
            }
            ArrayListUnsorted.this.removeAtIndex(position - 1);
            position--;
            removeOK = false;
        }
    }

    /*********************************************
     * Iterator list class / methods end
     *********************************************/
    
}
