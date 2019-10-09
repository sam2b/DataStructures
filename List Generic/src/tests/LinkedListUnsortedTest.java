/*
 * TCSS342 Data Structures - Spring 2017 - Professor Monika Sobolewska
 * Author: Sam Brendel
 * Assignment: pr1
 */

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import mylistpackage.LinkedListUnsorted;
import mylistpackage.MyIterator;
import mylistpackage.MyList;

/**
 * JUnit test of LinkedListUnsorted
 * @author Sam Brendel, Kaiyaun Shi, Monika
 * @version 4.16B
 */
public class LinkedListUnsortedTest {

	private MyList<String> list;// array list

	/**
	 * test contains with 205 elements list and element does NOT exist
	 */
	@Test
	public final void arrayContain205F() {
		fillList(list, 205);
		assertFalse("contain 205 false fail", list.contains("str"));
	}

	/**
	 * test contains with 205 elements list and element exist at the head of the
	 * list
	 */
	@Test
	public final void arrayContain205HT() {
		fillList(list, 205);
		assertTrue("contain 205 head true fail", list.contains("str0"));
	}

	/**
	 * test contains with 2 elements list and element exist at the tail of the
	 * list
	 */
	@Test
	public final void arrayContain205TT() {
		fillList(list, 205);
		assertTrue("contain 205 tail true fail", list.contains("str204"));
	}

	/**
	 * test contains with 2 elements list and element does NOT exist
	 */
	@Test
	public final void arrayContain2F() {
		list.insert("aaa");
		list.insert("bbb");
		assertFalse("contain 2 false fail", list.contains("ccc"));
	}

	/**
	 * test contains with 2 elements list and element exist at the head of the
	 * list
	 */
	@Test
	public final void arrayContain2HT() {
		list.insert("aaa");
		list.insert("bbb");
		assertTrue("contain 2 head true fail", list.contains("aaa"));
	}

	/**
	 * test contains with 2 elements list and element exist at the tail of the
	 * list
	 */
	@Test
	public final void arrayContain2TT() {
		list.insert("aaa");
		list.insert("bbb");
		assertTrue("contain 2 tail true fail", list.contains("bbb"));
	}

	/**
	 * test clear by clear an empty list
	 */
	@Test
	public final void clear0() {
		list.clear();
		assertEquals("clear 0 fail", "[]", list.toString());
	}

	/**
	 * test clear by clear a 1 element list
	 */
	@Test
	public final void clear1() {
		list.insert("aaa");
		list.clear();
		assertEquals("clear 1 fail", "[]", list.toString());
	}

	/**
	 * test clear by clear a 205 elements list
	 */
	@Test
	public final void clear205() {
		fillList(list, 205);
		list.clear();
		assertEquals("clear 205 fail", "[]", list.toString());
	}

	/**
	 * test clear by clear a 3 elements list
	 */
	@Test
	public final void clear3N() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.clear();
		assertEquals("clear 3 fail", "[]", list.toString());
	}

	/**
	 * test contains with 1 element and it does NOT exist in the list
	 */
	@Test
	public final void contain1FN() {
		list.insert("aaa");
		assertFalse("contain 1 false fail", list.contains("bbb"));
	}

	/**
	 * test contains with 1 element and it exists in the list
	 */
	@Test
	public final void contain1TN() {
		list.insert("aaa");
		assertTrue("contain 1 true fail", list.contains("aaa"));
	}

	/**
	 * test contains with empty list
	 */
	@Test
	public final void containEmpty() {
		assertFalse("contain empty fail", list.contains("aaa"));
	}

	/**
	 * test get by get 0 index from an empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void get0False() {
		list.get(0);
	}

	/**
	 * test get by get out of bound index from a 1 element list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void get1False() {
		list.insert("aaa");
		list.get(1);
	}

	/**
	 * test get by get 0 index from a 1 element list
	 */
	@Test
	public final void get1N() {
		list.insert("aaa");
		assertEquals("get 1 fail", "aaa", list.get(0));
	}

	/**
	 * test get by get head index from a 205 elements list
	 */
	@Test
	public final void get205Head() {
		fillList(list, 205);
		assertEquals("get 205 head fail", "str0", list.get(0));
	}

	/**
	 * test get by get middle index from a 205 elements list
	 */
	@Test
	public final void get205Middle() {
		fillList(list, 205);
		assertEquals("get 205 middle fail", "str101", list.get(101));
	}

	/**
	 * test get by get tail index from a 205 elements list
	 */
	@Test
	public final void get205Tail() {
		fillList(list, 205);
		assertEquals("get 205 tail fail", "str204", list.get(204));
	}

	/**
	 * test get by get head index from a 2 elements list
	 */
	@Test
	public final void get2Head() {
		list.insert("aaa");
		list.insert("bbb");
		assertEquals("get 2 head fail", "aaa", list.get(0));
	}

	/**
	 * test get by get tail index from a 2 elements list
	 */
	@Test
	public final void get2Tail() {
		list.insert("aaa");
		list.insert("bbb");
		assertEquals("get 2 tail fail", "bbb", list.get(1));
	}

	/**
	 * test get by get one next to head index from a 5 elements list
	 */
	@Test
	public final void get5HeadNextN() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		assertEquals("get 5 head next fail", "bbb", list.get(1));
	}

	/**
	 * test get by get one next to tail index from a 5 elements list
	 */
	@Test
	public final void get5TailNextN() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		assertEquals("get 5 tail next fail", "ddd", list.get(3));
	}

	/**
	 * test getIndex by get NOT exist element
	 */
	@Test
	public final void getIndex0() {
		assertEquals("get 0 index fail", -1, list.getIndex("aaa"));
	}

	/**
	 * test getIndex by get NOT exist element from 1 element list
	 */
	@Test
	public final void getIndex1FalseN() {
		list.insert("aaa");
		assertEquals("get 1 index False fail", -1, list.getIndex("bbb"));
	}

	/**
	 * test getIndex by get exist element from 1 element list
	 */
	@Test
	public final void getIndex1TrueN() {
		list.insert("aaa");
		assertEquals("get 1 index True fail", 0, list.getIndex("aaa"));
	}

	/**
	 * test getIndex by get NOT exist element from 205 elements list
	 */
	@Test
	public final void getIndex205False() {
		fillList(list, 205);
		assertEquals("get 1 index False fail", -1, list.getIndex("str-1"));
	}

	/**
	 * test getIndex by get head element from 205 elements list
	 */
	@Test
	public final void getIndex205Head() {
		fillList(list, 205);
		assertEquals("get 205 index head fail", 0, list.getIndex("str0"));
	}

	/**
	 * test getIndex by get tail element from 205 elements list
	 */
	@Test
	public final void getIndex205Tail() {
		fillList(list, 205);
		assertEquals("get 1 index tail fail", 204, list.getIndex("str204"));
	}

	/**
	 * test get by get negative index from an empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void getNegative() {
		list.get(-1);
	}

	/**
	 * test getSize with 0 element
	 */
	@Test
	public final void getSize0() {
		assertEquals("empty size fail", 0, list.getSize());
	}

	/**
	 * test getSize with 205 elements
	 */
	@Test
	public final void getSize205() {
		fillList(list, 205);
		assertEquals("size 205 fail", 205, list.getSize());
	}

	/**
	 * test getSize with 3 elements
	 */
	@Test
	public final void getSize3N() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		assertEquals("size 3 fail", 3, list.getSize());
	}

	/**
	 * test insert by insert 1 element
	 */
	@Test
	public final void insert1N() {
		list.insert("aaa");
		assertEquals("insert 1 fail", "[aaa]", list.toString());
	}

	/**
	 * test insert by insert 205 elements
	 */
	@Test
	public final void insert205() {
		fillList(list, 205);

		String str = "[str0";
		for (int i = 1; i < 205; i++)
			str += ", str" + i;
		str += "]";
		assertEquals("insert 205 fail", str, list.toString());
	}

	/**
	 * test insert by insert 3 elements
	 */
	@Test
	public final void insert3N() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		assertEquals("insert 3 fail", "[aaa, bbb, ccc]", list.toString());
	}

	/**
	 * test isEmpty with empty list
	 */
	@Test
	public final void isEmpty0() {
		assertTrue("0 empty fail", list.isEmpty());
	}

	/**
	 * test isEmpty with 205 elements
	 */
	@Test
	public final void isEmpty205() {
		fillList(list, 205);
		assertFalse("205 empty fail", list.isEmpty());
	}

	/**
	 * test isEmpty with 3 elements
	 */
	@Test
	public final void isEmpty3N() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		assertFalse("3 empty fail", list.isEmpty());
	}

	/**
	 * Tests the iterator calling next() on an empty list.
	 */
	@Test(expected = NoSuchElementException.class)
	public final void iteratorEmptyListHasNext() {
		list.clear();
		MyIterator<String> itr = list.iterator();
		itr.next(); // hasNext() is false;
	}
	
	/**
	 * Tests the iterator calling next() when hasNext() is false.
	 */
	@Test(expected = NoSuchElementException.class)
	public final void iteratorEndOfListHasNext() {
		fillList(list, 5);
		MyIterator<String> itr = list.iterator();

		for(int i = 0; i < 5; i++) {
			itr.next();
		}
		
		itr.next(); // hasNext() is false;
	}
	
	/**
	 * test iterator by iterate empty list
	 */
	@Test
	public final void iterator0() {
		list = new LinkedListUnsorted<>(); // an empty list.
		final Iterator<String> itr = list.iterator(); // get the iterator after the list is created.

		// This string should only be "[]" since the list is empty.
		String str = "[";
		while (itr.hasNext()) {
			str += ", " + itr.next();
		}
		str += "]";

		assertEquals("iterator 0 fail", str, list.toString());
	}

	/**
	 * test iterator by iterate 1 element list
	 */
	@Test
	public final void iterator1N() {
		list.insert("aaa");
		final Iterator<String> itr = list.iterator();

		String str = "[" + itr.next();
		while (itr.hasNext()) {
			str += ", " + itr.next();
		}
		str += "]";

		assertEquals("iterator 1 fail", str, list.toString());
	}

	/**
	 * test iterator by iterate 205 elements list
	 */
	@Test
	public final void iterator205() {
		fillList(list, 205);
		final Iterator<String> itr = list.iterator();

		String str = "[" + itr.next();
		while (itr.hasNext()) {
			str += ", " + itr.next();
		}
		str += "]";

		assertEquals("iterator 205 fail", str, list.toString());
	}

	/**
	 * test remove method of iterator
	 */
	@Test
	public final void iteratorRemove() {
		final int max = 205;
		final int indexDelete = 101;
		fillList(list, max);
		//final String last = al.get(max - 1); 
		
		MyIterator<String> itr = list.iterator();

		while (itr.hasNext()) {
			final String temp = itr.next();
			if (temp.equals("str" + indexDelete)) // str2
				itr.remove();
		}

		//itr = al.iterator();
		itr.reset();

		String str = "[" + itr.next();
		while (itr.hasNext()) {
			str += ", " + itr.next();
		}
		str += "]";

		/* Why do this at all?  Just compare with the actual array.
		String testStr = "[str0";
		for (int i = 1; i < max; i++) {
			if (i != indexDelete) {
				testStr += ", str" + i;
			} else {
				testStr += last;
			}
		}
		testStr += "]";
		*/

		assertEquals("iterator remove fail", str, list.toString());
	}

	/**
	 * Test invalid remove method of iterator by not calling next() before hand.
	 */
	@Test(expected = IllegalStateException.class)
	public final void iteratorRemoveWithoutNext() {
		final int max = 205;
		fillList(list, max);
		Iterator<String> itr = list.iterator();

		itr.remove(); // must call next() first.
	}
	
	/**
	 * Test reset method of the iterator.
	 */
	@Test
	public final void iteratorReset() {
		fillList(list, 205);
		MyIterator<String> itr = list.iterator();
		String firstElement = list.get(0);
		
		for(int i = 0; i < 10; i++) {
			itr.next();
		}
		
		itr.reset();
		assertEquals("The iterator is not reset to the first element", 
				     firstElement, itr.next());
	}
	
	/**
	 * Tests the iterator when getting the last element's data.
	 */
	@Test
	public final void verifyIteratorStopsAtTheEnd() {
		fillList(list, 205);

		int counter = 0;
		Iterator<String> itr = list.iterator();
		String lastDataFromIterator = "";
		String lastDataFromIndex = list.get(list.getSize() - 1);
		
		while(itr.hasNext()) {
			lastDataFromIterator = itr.next();
			counter++;
		}

		assertTrue("Last element does not contain the same data.", lastDataFromIndex.equals(lastDataFromIterator));
		
		assertTrue("Quantity of iterators differs from list size.", counter == list.getSize());
	}

	/**
	 * Tests the iterator when getting the first element's data.
	 */
	@Test
	public final void verifyIteratorAtFirstElement() {
		fillList(list, 205);
		String firstDataFromIndex = list.get(0);
		String firstDataFromIterator = "";

		Iterator<String> itr = list.iterator();
		firstDataFromIterator = itr.next();
		
		assertTrue("The elemental data is different.", firstDataFromIndex.equals(firstDataFromIterator));
	}

	/**
	 * Tests the iterator when the list only has a single element.
	 */
	@Test
	public final void verifyIteratorWhenListSize1() {
		fillList(list, 1);
		String firstDataFromIndex = list.get(0);
		String firstDataFromIterator = "";

		Iterator<String> itr = list.iterator();
		firstDataFromIterator = itr.next();
		
		assertTrue("The elemental data is different.", firstDataFromIndex.equals(firstDataFromIterator));
	}
	
	/**
	 * Tests the iterator does not have a next element to iterator over on an empty list.
	 */
	@Test
	public final void verifyIteratorWhenListSize0() {
		list.clear();
		
		Iterator<String> itr = list.iterator();
		assertFalse("The iterator claims to it has a next element on an empty list.", itr.hasNext());
	}

	/*
	 * test constructor with illegal negative input. 
	 * NOT APPLICABLE FOR LINKED LIST DATA STRUCTURES.
	 * THERE IS NO SUCH CONSTRUCTOR SINCE THE ONLY LIMITATION IS RAM.
	 */
/*	@Test(expected = IllegalArgumentException.class)
	public final void negativeCapacity() {
		list = new LinkedListUnsorted<String>(-1); // -1 argument
	}*/

	/**
	 * test remove by remove not exist element from 1 element list
	 */
	@Test
	public final void remove0False() {
		list.insert("aaa");
		list.remove("bbb");
		assertEquals("remove 0 False fail", "[aaa]", list.toString());
	}

	/**
	 * test remove by remove exist element from 1 element list
	 */
	@Test
	public final void remove0True() {

		list.insert("aaa");
		list.remove("aaa");
		assertEquals("remove 0 True fail", "[]", list.toString());

	}

	/**
	 * test remove by remove head element from 205 elements list
	 */
	@Test
	public final void remove205Head() {
		fillList(list, 5); // 205
		list.remove("str0");

		String str = "[str1";
		for (int i = 2; i < 5; i++) { // 205
			str += ", str" + i;
		}
		str += "]";

		assertEquals("remove 205 head fail", str, list.toString());
	}

	/**
	 * test remove by remove middle element from 205 elements list
	 */
	@Test
	public final void remove205Middle() {
		fillList(list, 205);
		list.remove("str101");

		String str = "[str0";
		for (int i = 1; i < 205; i++) {
			if (i != 101) {
				str += ", str" + i;
			}
		}
		str += "]";

		assertEquals("remove 205 middle fail", str, list.toString());
	}

	/**
	 * test remove by remove tail element from 205 elements list
	 */
	@Test
	public final void remove205Tail() {
		fillList(list, 205);
		list.remove("str204");

		String str = "[str0";
		for (int i = 1; i < 204; i++) {
			str += ", str" + i;
		}
		str += "]";

		assertEquals("remove 205 tail fail", str, list.toString());
	}

	/**
	 * test remove by remove NOT exist element from 5 elements list
	 */
	@Test
	public final void remove5False() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		
		list.remove("fff");
		assertEquals("remove 5 False fail", "[aaa, bbb, ccc, ddd, eee]", list.toString());
	}

	/**
	 * test remove by remove head element from 5 elements list
	 */
	@Test
	public final void remove5HeadNext() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		
		list.remove("bbb");
		assertEquals("remove 5 head next fail", "[aaa, ccc, ddd, eee]", list.toString());
	}

	/**
	 * test remove by remove tail element from 5 elements list
	 */
	@Test
	public final void remove5TailNext() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		
		list.remove("ddd");
		assertEquals("remove 5 tail next fail", "[aaa, bbb, ccc, eee]", list.toString());
	}

	/**
	 * test remove by remove exist element from 5 elements list
	 */
	@Test
	public final void remove5TrueN() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		
		list.remove("ccc");
		assertEquals("remove 5 True fail", "[aaa, bbb, ddd, eee]", list.toString());
	}

	/**
	 * test removeAtIndex by remove 0 index from 1 element list
	 */
	@Test
	public final void removeAtIndex0() {
		list.insert("aaa");
		
		list.removeAtIndex(0);
		assertEquals("removeAtIndex 0 fail", "[]", list.toString());
	}

	/**
	 * test removeAtIndex by remove 0 index from empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void removeAtIndex0False() {
		list.removeAtIndex(0);
	}

	/**
	 * test removeAtIndex by remove out of bound index from 1 element list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void removeAtIndex1False() {
		list.insert("aaa");
		list.removeAtIndex(1);
	}

	/**
	 * test removeAtIndex by remove head from 205 elements list
	 */
	@Test
	public final void removeAtIndex205Head() {
		fillList(list, 205);

		list.removeAtIndex(0);

		String str = "[str1";
		for (int i = 2; i < 205; i++)
			str += ", str" + i;
		str += "]";

		assertEquals("removeAtIndex 205 head fail", str, list.toString());
	}

	/**
	 * test removeAtIndex by remove middle from 205 elements list
	 */
	@Test
	public final void removeAtIndex205Middle() {
		final int value = 101;
		final int size = 205;
		fillList(list, size);

		list.removeAtIndex(value);

		String str = "[str0";
		for (int i = 1; i < size; i++) {
			if (i != value) {
				str += ", str" + i;
			}
		}
		str += "]";

		assertEquals("removeAtIndex 205 middle fail", str, list.toString());
	}

	/**
	 * test removeAtIndex by remove tail from 205 elements list
	 */
	@Test
	public final void removeAtIndex205Tail() {
		fillList(list, 205);

		list.removeAtIndex(204);

		String str = "[str0";
		for (int i = 1; i < 204; i++)
			str += ", str" + i;
		str += "]";

		assertEquals("removeAtIndex 205 tail fail", str, list.toString());
	}

	/**
	 * test removeAtIndex by remove head from 2 elements list
	 */
	@Test
	public final void removeAtIndex2Head() {
		list.insert("aaa");
		list.insert("bbb");
		
		list.removeAtIndex(0);
		assertEquals("removeAtIndex 2 tail fail", "[bbb]", list.toString());
	}

	/**
	 * test removeAtIndex by remove tail from 2 elements list
	 */
	@Test
	public final void removeAtIndex2Tail() {
		list.insert("aaa");
		list.insert("bbb");
		
		list.removeAtIndex(1);
		assertEquals("removeAtIndex 2 tail fail", "[aaa]", list.toString());
	}

	/**
	 * test removeAtIndex by remove head from 5 elements list
	 */
	@Test
	public final void removeAtIndex5Head() {
		fillList(list, 205);

		list.removeAtIndex(0);

		String str = "[str1";
		for (int i = 2; i < 205; i++)
			str += ", str" + i;
		str += "]";

		assertEquals("removeAtIndex 5 head fail", str, list.toString());
	}

	/**
	 * test removeAtIndex by remove position one next to head from 5 elements
	 * list
	 */
	@Test
	public final void removeAtIndex5HeadNextN() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		
		list.removeAtIndex(1);
		assertEquals("removeAtIndex 5 head next fail", "[aaa, ccc, ddd, eee]", list.toString());
	}

	/**
	 * test removeAtIndex by remove middle from 5 elements list
	 */
	@Test
	public final void removeAtIndex5Middle() {
		final int indexDelete = 2;
		final int size = 5;
		fillList(list, size);

		list.removeAtIndex(indexDelete);

		String str = "[str0";
		for (int i = 1; i < size; i++) {
			if (i != indexDelete) {
				str += ", str" + i;
			}
		}
		str += "]";

		assertEquals("removeAtIndex 5 middle fail", str, list.toString());
	}

	/**
	 * test removeAtIndex by remove position one next to tail from 5 elements
	 * list
	 */
	@Test
	public final void removeAtIndex5TailNextN() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		
		list.removeAtIndex(3);
		assertEquals("removeAtIndex 5 tail next fail", "[aaa, bbb, ccc, eee]", list.toString());
	}

	/**
	 * test removeAtIndex by remove negative index
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void removeAtIndexNegative() {
		list.removeAtIndex(-1);
	}

	/**
	 * test set by set 0 index in an empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void set0False() {
		list.set(0, "aaa");
	}

	/**
	 * test set by set 0 index in a 1 element list
	 */
	@Test
	public final void set1() {
		list.insert("aaa");
		list.set(0, "bbb");
		assertEquals("set 1 fail", "[bbb]", list.toString());
	}

	/**
	 * test set by set out of bound index in a 1 element list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void set1False() {
		list.insert("aaa");
		list.set(1, "bbb");
	}

	/**
	 * test set by set head index in a 205 elements list
	 */
	@Test
	public final void set205Head() {
		fillList(list, 205);
		list.set(0, "new");

		String str = "[new";
		for (int i = 1; i < 205; i++) {
			str += ", str" + i;
		}
		str += "]";

		assertEquals("set 205 head fail", str, list.toString());
	}

	/**
	 * test set by set middle index in a 205 elements list
	 */
	@Test
	public final void set205Middle() {
		fillList(list, 205);
		list.set(101, "new");

		String str = "[str0";
		for (int i = 1; i < 205; i++) {
			if (i == 101)
				str += ", new";
			else
				str += ", str" + i;
		}
		str += "]";

		assertEquals("set 205 middle fail", str, list.toString());
	}

	/**
	 * test set by set tail index in a 205 elements list
	 */
	@Test
	public final void set205Tail() {
		fillList(list, 205);
		list.set(204, "new");

		String str = "[str0";
		for (int i = 1; i < 204; i++) {
			str += ", str" + i;
		}
		str += ", new]";

		assertEquals("set 205 tail fail", str, list.toString());
	}

	/**
	 * test set by set one next to head index in a 5 elements list
	 */
	@Test
	public final void set5HeadNextN() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		
		list.set(1, "fff");
		assertEquals("set 5 head next fail", "[aaa, fff, ccc, ddd, eee]", list.toString());
	}

	/**
	 * test set by set one next to tail index in a 5 elements list
	 */
	@Test
	public final void set5TailNextN() {
		list.insert("aaa");
		list.insert("bbb");
		list.insert("ccc");
		list.insert("ddd");
		list.insert("eee");
		
		list.set(3, "fff");
		assertEquals("set 5 tail next fail", "[aaa, bbb, ccc, fff, eee]", list.toString());
	}

	/**
	 * test set by set negative index in an empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void setNegative() {
		list.set(-1, "aaa");
	}

	/**
	 * Before each test case, a new blank list is created for use.
	 */
	@Before
	public final void setup() {
		list = new LinkedListUnsorted<String>();
	}

	/**
	 * test constructor with illegal 0 input
	 * NOT APPLICABLE FOR LINKED LIST DATA STRUCTURES.
	 * THERE IS NO SUCH CONSTRUCTOR SINCE THE ONLY LIMITATION IS RAM.
	 */
/*	@Test(expected = IllegalArgumentException.class)
	public final void zeroCapacity() {
		list = new LinkedListUnsorted<String>(); // 0 argument
	}*/

	/**
	 * Fills the list with the specified quantity of contiguous values starting at zero.
	 * Existing data is wiped out, the new values will all be contiguous.
	 * @param theList the list to fill.
	 * @param theQuantity the maximum value to fill from zero.
	 */
	private void fillList(final MyList<String> theList, final int theQuantity) {
		//theList = new LinkedListUnsorted<String>(); // ensure the list has no pre-existing data.
		theList.clear();
		
		for (int i = 0; i < theQuantity; i++) {
			theList.insert("str" + i);
		}
	}

}
