/*
 * TCSS342 Data Structures - Spring 2017 - Professor Monika Sobolewska
 * Author: Sam Brendel
 * Assignment: pr1
 */

package mylistpackage;
import java.util.Iterator;

/**
 * My custom iterator which adds a reset() method.
 * {@inheritDoc}
 * @author Sam Brendel
 * @param <E> the data type.
 * @see java.util.Iterator
 * @version 4.13A
 */
public interface MyIterator<E> extends Iterator<E> {

	/*
	 * Gets the index number of the current node.
	 */
	//int getIndex();
	
	/**
	 * Resets the iterator to the first element.
	 */
	void reset();
}
