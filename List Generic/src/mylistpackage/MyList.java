/*
 * TCSS342 Data Structures - Spring 2017 - Professor Monika Sobolewska
 * Author: Sam Brendel
 * Assignment: pr1
 * Version: 4.8D
 */
package mylistpackage;

/**
 * Represents MyList interface.
 * @author Monika
 * @version Sep 26, 2016
 * @param <Type> is of any object type.
 */
public interface MyList<Type> {

    /**
     * Returns the current number of elements in the list.
     * @return the current number of elements in the list {@code >=} 0
     */
    public int getSize();

    /**
     * Returns whether the list is empty.
     * @return true if list is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns whether value is in the list.
     * @param value the data in this element.
     * @return true if value in the list, false otherwise.
     */
    public boolean contains(Type value);

    /**
     * Inserts an element into the list.
     * @param value the data put in the list.
     */
    public void insert(Type value);
    
    /**
     * Clears the list.
     */
    public void clear();
    
    /**
     * Returns a string representation of list contents.
     * @return a string representation of list contents.
     * @see Object#toString()
     */
    @Override
    public String toString();
    
    /**
     * Removes first element occurrence from the list.
     * @param value the data to remove from this list.
     */
    public void remove(Type value);
    
    /*********************************************
     * Index list methods follow
     *********************************************/

    /**
     * Returns the index of value.
     * 
     * @param value the data in this element.
     * @return index of value if in the list, -1 otherwise.
     */
    public int getIndex(Type value);
    
    /**
     * Removes value at the given index.
     * @param index 0 {@code <=} index {@code <=} size
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >=} size
     */
    public void removeAtIndex (int index);
    
    /**
     * Replaces the value at the given index with the given value.
     * @param index 0 {@code <=} index {@code <=} size
     * @param value the data to put in this element.
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >} size
     */
    public void set(int index, Type value);
    
    /**
     * Returns the value at the given index in the list.
     * @param index 0 {@code <=} index {@code <=} size
     * @throws IndexOutOfBoundsException if index {@code <} 0 or index {@code >} size
     * @return the value at the given index in the list.
     */
    public Type get(int index);
    
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
    public MyIterator<Type> iterator();
    
    /*********************************************
     * Iterator list class / methods end
     *********************************************/

}
