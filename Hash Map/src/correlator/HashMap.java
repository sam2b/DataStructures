/**
 * Assignment 4, brendel_pr4
 */

package correlator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * This class IS the hash table, which is in the form of a HashMap.
 * Implements a set of objects using a hash table. <BR>
 * The hash table uses separate chaining to resolve collisions.
 * @author BJP, Monika, modified by Sam Brendel
 * @version 5.27D
 * @param <E>
 */
public class HashMap<AnyKey extends Comparable<? super AnyKey>, AnyValue> 
                         implements MyTreeMap<AnyKey, AnyValue> {
	
    private static final double MAX_LOAD_FACTOR = 0.75;
    private HashEntry[] elementData;
    private HashEntry iteratorNode;
    private int iteratorIndex;
    private int iteratorSize;
    private int size;
    
    // Constructs an empty set.
    @SuppressWarnings("unchecked")
	public HashMap() {
    	elementData = (HashEntry[]) Array.newInstance(HashEntry.class, 10);
    	iteratorNode = null;
        size = 0;
        iteratorIndex = 0;
        iteratorSize = 0;
    }
    
    /** 
     * Puts the given element to this set. If it is already contained in the set, only the value is overwritten.
	 * @see correlator.MyTreeMap#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public void put(final AnyKey key, final AnyValue value) {
        if (!contains(key)) {
            if (loadFactor() >= MAX_LOAD_FACTOR) {
                rehash();
            }
            
            // insert new value at front of list
            int bucket = hashFunction(key);
            //elementData[bucket] = new HashEntry(key, elementData[bucket]);
            elementData[bucket] = new HashEntry(key, value, elementData[bucket]);
            size++;

            if (iteratorNode == null) iteratorNode = elementData[0];
            // if (size == 1) iteratorNode = elementData[0];
            
        } else {
        	// Since it exists, just update the value.
        	HashEntry existingEntry = getEntry(key);
        	existingEntry.value = value;
        }
    }
       
    // Removes all elements from the set.
    public void clear() {
        for (int i = 0; i < elementData.length; i++) {
            elementData[i] = null;
        }
        size = 0;
        iteratorNode = null;
        iteratorIndex = 0;
        iteratorSize = 0;
    }
    
    // Returns true if the given key is found in this set.
    public boolean contains(AnyKey key) {
        int bucket = hashFunction(key);
        HashEntry current = elementData[bucket];
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    // Returns true if there are no elements in this queue.
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Removes the given key if it is contained in the set.<BR>
     * If the set does not contain the key, has no effect.<BR>
     * Modified to return a node for use in the iterator's remove.
	 * @see correlator.MyTreeMap#remove(java.lang.Object)
     */
    public HashEntry remove(AnyKey key, final boolean returnNextNode) {
    	HashEntry nextNode = null;
        int bucket = hashFunction(key);
        
        if (elementData[bucket] != null) {
        	
            // check front of list
            if (elementData[bucket].key.equals(key)) {
                elementData[bucket] = elementData[bucket].next;
            	nextNode = elementData[bucket]; // Sam. 
                size--;
                
            } else {
                // check rest of list
                HashEntry current = elementData[bucket];
                while (current.next != null && !current.next.key.equals(key)) {
                    current = current.next;
                }
                
                // if the element is found, remove it
                if (current.next != null && current.next.key.equals(key)) {
                    current.next = current.next.next;
                    nextNode = current.next; // Sam.
                    size--;
				}
            }
        }
        
        if (size == 0) {
        	iteratorNode = null;
        	iteratorIndex = 0;
        }
        return nextNode;
    }

    // Returns the number of elements in the queue.
    public int size() {
        return size;
    }
    
    // Returns a string representation of this queue, such as "[10, 20, 30]";
    // The elements are not guaranteed to be listed in sorted order.
    public String toString() {
    	StringBuilder result = new StringBuilder();
        result.append("[");
        boolean first = true;
        if (!isEmpty()) {
            for (int i = 0; i < elementData.length; i++) {
                HashEntry current = elementData[i];
                while (current != null) {
                    if (!first) {
                    	result.append(", ");
                    }
                    result.append(current.key);
                    first = false;
                    current = current.next;
                }
            }
        }
        result.append("]");
        return result.toString();
    }
    
    /**
     * All of the keys in this HashMap.
     * @return a List of keys.
     */
    public List<AnyKey> keySet() {
    	List<AnyKey> list = new ArrayList<AnyKey>();
        if (!isEmpty()) {
            for (int i = 0; i < elementData.length; i++) {
                HashEntry current = elementData[i];
                while (current != null) {
                    list.add(current.key);
                    current = current.next;
                }
            }
        }
     	return list;
    }
    
    
    // Returns the preferred hash bucket index for the given key.
    private int hashFunction(AnyKey key) {
        return Math.abs(key.hashCode()) % elementData.length;
    }
    
    private double loadFactor() {
        return (double) size / elementData.length;
    }
    
    // Resizes the hash table to twice its former size.
    @SuppressWarnings("unchecked")
	private void rehash() {
        // replace element key array with a larger empty version
        HashEntry[] oldElementData = elementData;
        int newSize = getNextPrime(oldElementData.length);
        elementData = (HashEntry[]) Array.newInstance(HashEntry.class, newSize);  
        size = 0;

        //HashEntry currentEntry = null;
        HashEntry nextEntry = null;
        // re-add all of the old key into the new array
        for (int i = 0; i < oldElementData.length; i++) {  // BUCKET.
        	//currentEntry = oldElementData[i];
        	
            while (oldElementData[i] != null) {                 // NODES IN THE LIST FOR THE BUCKET.
            	nextEntry = (oldElementData[i] == null) 
          			  ? null 
  					  : oldElementData[i].next; // Get the next node outside of the while loop, else an infinite loop may occur.
            	//add(current.key); // original code.
            	reAdd(oldElementData[i]);
            	oldElementData[i] = nextEntry;
            }
        }
    }
    
    private int getNextPrime(final int oldSize) {
    	int newPrime = nextPrime(oldSize);
    	return (newPrime >= (2 * elementData.length)) ? newPrime : getNextPrime(newPrime+1); 
    }
    
    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     * @author Dr. Mark Allen Weiss
     * {@link} https://users.cs.fiu.edu/~weiss/dsj4/code/weiss/util/HashSet.java
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     * @author Dr. Mark Allen Weiss
     * {@link} https://users.cs.fiu.edu/~weiss/dsj4/code/weiss/util/HashSet.java
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
     
    /**
     * Helper for rehash(). Insert new value at front of linked list of HashEntry nodes.<BR>
     * Connects into the new bucket list, then disconnects from the old list.<BR>
     * This is a modified version of the put() method by re-linking the existing HashEntry nodes instead of creating
     * new nodes unnecessarily.  This increases efficiency of the data structure's rehash algorithm.<BR>
     * Caution when using this method: before running this routine, the elementData array should be increased, 
     * AND the size field to be set to zero. 
     * @param entry
     */
    private void reAdd(final HashEntry entry) {
        int bucket = hashFunction(entry.key); // Hash the key to determine what bucket it should go into.
        entry.next = elementData[bucket];     // The entry node now points to the front node (at bucket index).
        elementData[bucket] = entry;          // The bucket now points to entry, which is the new "front" node.
        size++;                               // Starting from zero, increment the size.
    }
    
    /** 
     * Represents a single key-value pair in a chain stored in one hash bucket.
     * @author BJP, Monika, modified by Sam Brendel
     * @version 5.21A
     */
    private class HashEntry {
        public AnyKey key;
        public HashEntry next;
        public AnyValue value;

        /**
         * Constructor.
         * @param theKey the key.
         * @param theValue the value.
         * @param theNext the entry's reference to the next entry.
         */
        public HashEntry(final AnyKey theKey, final AnyValue theValue, final HashEntry theNext) {
            this.key = theKey;
            this.value = theValue;
            this.next = theNext;
        }
        
        /**
         * Constructor.
         * @param theKey the key.
         * @param theNext the entry's reference to the next entry.
         */
        public HashEntry(final AnyKey theKey, final HashEntry theNext) {
            this(theKey, null, theNext);
        }
        
        /**
         * Debugging.
         * @return a string representation of the HashEntry and its next.
         */
        @Override
        public String toString() {
        	String s = "";
        	s = key.toString();
        	if(key != null) {
        		s += " --> ";
        		s += (next == null) ? "null" : next.toString();
        	}
        	return s;
        }
    }

	/** 
	 * Gets the value from the specified key.<BR>
	 * Borrowed code from the contains() method.
	 * @see correlator.MyTreeMap#get(java.lang.Object)
	 * @return the value for the specified  key.
	 */
	@Override
	public AnyValue get(AnyKey x) {
		HashEntry entry = getEntry(x);
		return (entry == null) ? null : entry.value;
	}
	
	/**
	 * Helper method.  Returns a reference to the node.  
	 * @param x the key.
	 * @return the actual object where this key lives.
	 */
	private HashEntry getEntry(final AnyKey x) {
        int bucket = hashFunction(x);
        HashEntry current = elementData[bucket];
        while (current != null) {
            if (current.key.equals(x)) {
                return current;
            }
            current = current.next;
        }
		return null;

	}

	/**
	 * An iterator, use in conjunction with next().
	 * @return true if there is still another entry in this table.
	 */
	public boolean hasNext() {
		return iteratorSize < size;
	}
	
	/**
	 * An iterator, use in conjunction with hasNext().
	 * @return the current key.
	 */
	public AnyKey next() {
		AnyKey result = null;

		// Search for a bucket with a node.
		while(iteratorNode == null
				&& iteratorSize < size) {
			iteratorNode = elementData[++iteratorIndex]; // The next bucket.
		}

		// Now we have a node that is not null.
		if (iteratorSize <= size) {
			result = iteratorNode.key;
			iteratorNode = iteratorNode.next; // The next node in the list.
			iteratorSize++;
		} // else result is null, which means we have exhausted the entire array and list in the last bucket.

		return result;
	}

	/**
	 * Resets the iterator to the beginning.
	 */
	public void iteratorReset() {
		iteratorNode = elementData[0];
		iteratorIndex = 0;
		iteratorSize = 0;
	}
	
	/**
	 * Removes the key from the table.
	 * @see correlator.MyTreeMap#remove(java.lang.Object)
	 */
	@Override
	public void remove(AnyKey x) {
		remove(x, false);
	}

	/**
	 * Returns the cardinality of the intersection between this table size and the other table size.<BR>
	 * Note: this is intended to be used with two HashMaps containing the normalized frequency.
	 * @param otherTable the other table for comparison.
	 * @return the cardinality of this set, which is quantity of intersecting elements. 
	 */
	public int intersection(final HashMap<AnyKey, AnyValue> otherTable) {
		HashMap<AnyKey, AnyValue> intersectMap = new HashMap<>();
		
		AnyKey thisTableKey;
		AnyKey otherTableKey;
		this.iteratorReset();
		otherTable.iteratorReset();
		
		while(this.hasNext() || otherTable.hasNext()) {
			
			if(this.hasNext()) {
				thisTableKey = this.next();
				if(otherTable.contains(thisTableKey)) {
					intersectMap.put(thisTableKey, null);
				}
			}
			
			if(otherTable.hasNext()) {
				otherTableKey = otherTable.next();
				if(this.contains(otherTableKey)) {
					intersectMap.put(otherTableKey, null);
				}
			}
		}
		
		return intersectMap.size;
	}
	
	/**
	 * Returns the cardinality of the union of this table and the other table.
	 * @param otherTable the other table for comparison.
	 * @return the the cardinality of this set, which is quantity of elements in the union. 
	 */
	public int union(final HashMap<AnyKey, AnyValue> otherTable) {
		return this.size + otherTable.size - this.intersection(otherTable);
	}

}

































