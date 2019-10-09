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

import mylistpackage.ArrayListSorted;
import mylistpackage.MyIterator;
import mylistpackage.MyList;

/**
 * JUnit test of ArrayListSorted.  THIS IS A COPY OF THE LINKED ARRAY LIST TEST SUITE.
 * @author Sam Brendel, Kaiyaun Shi, Monika
 * @version 4.16B
 */
public class ArrayListSortedTest {

	private MyList<String> laList;// array list

	/**
	 * test contains with 205 elements list and element does NOT exist
	 */
	@Test
	public final void arrayContain205F() {
		final String testData = "str";
		fillList(laList, 5); //205
		assertFalse("contain " + testData + "  false fail", laList.contains(testData));
	}

	/**
	 * test contains with 205 elements list and element exist at the head of the
	 * list
	 */
	@Test
	public final void arrayContain205HT() {
		fillList(laList, 205);
		assertTrue("contain 205 head true fail", laList.contains("str000"));
	}

	/**
	 * test contains with 2 elements list and element exist at the tail of the
	 * list
	 */
	@Test
	public final void arrayContain205TT() {
		fillList(laList, 205);
		assertTrue("contain 205 tail true fail", laList.contains("str204"));
	}

	/**
	 * test contains with 2 elements list and element does NOT exist
	 */
	@Test
	public final void arrayContain2F() {
		laList.insert("aaa");
		laList.insert("bbb");
		assertFalse("contain 2 false fail", laList.contains("ccc"));
	}

	/**
	 * test contains with 2 elements list and element exist at the head of the
	 * list
	 */
	@Test
	public final void arrayContain2HT() {
		laList.insert("aaa");
		laList.insert("bbb");
		assertTrue("contain 2 head true fail", laList.contains("aaa"));
	}

	/**
	 * test contains with 2 elements list and element exist at the tail of the
	 * list
	 */
	@Test
	public final void arrayContain2TT() {
		laList.insert("aaa");
		laList.insert("bbb");
		assertTrue("contain 2 tail true fail", laList.contains("bbb"));
	}

	/**
	 * test clear by clear an empty list
	 */
	@Test
	public final void clear0() {
		laList.clear();
		assertEquals("clear 0 fail", "[]", laList.toString());
	}

	/**
	 * test clear by clear a 1 element list
	 */
	@Test
	public final void clear1() {
		laList.insert("aaa");
		laList.clear();
		assertEquals("clear 1 fail", "[]", laList.toString());
	}

	/**
	 * test clear by clear a 205 elements list
	 */
	@Test
	public final void clear205() {
		fillList(laList, 205);
		laList.clear();
		assertEquals("clear 205 fail", "[]", laList.toString());
	}

	/**
	 * test clear by clear a 3 elements list
	 */
	@Test
	public final void clear3N() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.clear();
		assertEquals("clear 3 fail", "[]", laList.toString());
	}

	/**
	 * test contains with 1 element and it does NOT exist in the list
	 */
	@Test
	public final void contain1FN() {
		laList.insert("aaa");
		assertFalse("contain 1 false fail", laList.contains("bbb"));
	}

	/**
	 * test contains with 1 element and it exists in the list
	 */
	@Test
	public final void contain1TN() {
		laList.insert("aaa");
		assertTrue("contain 1 true fail", laList.contains("aaa"));
	}

	/**
	 * test contains with empty list
	 */
	@Test
	public final void containEmpty() {
		assertFalse("contain empty fail", laList.contains("aaa"));
	}

	/**
	 * test get by get 0 index from an empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void get0False() {
		laList.get(0);
	}

	/**
	 * test get by get out of bound index from a 1 element list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void get1False() {
		laList.insert("aaa");
		laList.get(1);
	}

	/**
	 * test get by get 0 index from a 1 element list
	 */
	@Test
	public final void get1N() {
		laList.insert("aaa");
		assertEquals("get 1 fail", "aaa", laList.get(0));
	}

	/**
	 * test get by get head index from a 205 elements list
	 */
	@Test
	public final void get205Head() {
		fillList(laList, 205);
		assertEquals("get 205 head fail", "str000", laList.get(0));
	}

	/**
	 * test get by get middle index from a 205 elements list
	 */
	@Test
	public final void get205Middle() {
		fillList(laList, 205);
		assertEquals("get 205 middle fail", "str101", laList.get(101));
	}

	/**
	 * test get by get tail index from a 205 elements list
	 */
	@Test
	public final void get205Tail() {
		fillList(laList, 205);
		assertEquals("get 205 tail fail", "str204", laList.get(204));
	}

	/**
	 * test get by get head index from a 2 elements list
	 */
	@Test
	public final void get2Head() {
		laList.insert("aaa");
		laList.insert("bbb");
		assertEquals("get 2 head fail", "aaa", laList.get(0));
	}

	/**
	 * test get by get tail index from a 2 elements list
	 */
	@Test
	public final void get2Tail() {
		laList.insert("aaa");
		laList.insert("bbb");
		assertEquals("get 2 tail fail", "bbb", laList.get(1));
	}

	/**
	 * test get by get one next to head index from a 5 elements list
	 */
	@Test
	public final void get5HeadNextN() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");
		assertEquals("get 5 head next fail", "bbb", laList.get(1));
	}

	/**
	 * test get by get one next to tail index from a 5 elements list
	 */
	@Test
	public final void get5TailNextN() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");
		assertEquals("get 5 tail next fail", "ddd", laList.get(3));
	}

	/**
	 * test getIndex by get NOT exist element
	 */
	@Test
	public final void getIndex0() {
		assertEquals("get 0 index fail", -1, laList.getIndex("aaa"));
	}

	/**
	 * test getIndex by get NOT exist element from 1 element list
	 */
	@Test
	public final void getIndex1FalseN() {
		laList.insert("aaa");
		assertEquals("get 1 index False fail", -1, laList.getIndex("bbb"));
	}

	/**
	 * test getIndex by get exist element from 1 element list
	 */
	@Test
	public final void getIndex1TrueN() {
		laList.insert("aaa");
		assertEquals("get 1 index True fail", 0, laList.getIndex("aaa"));
	}

	/**
	 * test getIndex by get NOT exist element from 205 elements list
	 */
	@Test
	public final void getIndex205False() {
		fillList(laList, 205);
		assertEquals("get 1 index False fail", -1, laList.getIndex("str-001"));
	}

	/**
	 * test getIndex by get head element from 205 elements list
	 */
	@Test
	public final void getIndex205Head() {
		fillList(laList, 205);
		assertEquals("get 205 index head fail", 0, laList.getIndex("str000"));
	}

	/**
	 * test getIndex by get tail element from 205 elements list
	 */
	@Test
	public final void getIndex205Tail() {
		fillList(laList, 205);
		assertEquals("get 1 index tail fail", 204, laList.getIndex("str204"));
	}

	/**
	 * test get by get negative index from an empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void getNegative() {
		laList.get(-1);
	}

	/**
	 * test getSize with 0 element
	 */
	@Test
	public final void getSize0() {
		assertEquals("empty size fail", 0, laList.getSize());
	}

	/**
	 * test getSize with 205 elements
	 */
	@Test
	public final void getSize205() {
		fillList(laList, 205);
		assertEquals("size 205 fail", 205, laList.getSize());
	}

	/**
	 * test getSize with 3 elements
	 */
	@Test
	public final void getSize3N() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		assertEquals("size 3 fail", 3, laList.getSize());
	}

	/**
	 * test insert by insert 1 element
	 */
	@Test
	public final void insert1N() {
		laList.insert("aaa");
		assertEquals("insert 1 fail", "[aaa]", laList.toString());
	}

	/**
	 * test insert by insert 205 elements
	 */
	@Test
	public final void insert205() {
		final int max = 205;
		fillList(laList, max);

		String str = "[str" + paddingZeros(0, max);
		for (int i = 1; i < max; i++)
			str += ", str" + paddingZeros(i, max);
		str += "]";

		assertEquals("insert 205 fail", str, laList.toString());
	}

	/**
	 * test insert by insert 3 elements
	 */
	@Test
	public final void insert3N() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		assertEquals("insert 3 fail", "[aaa, bbb, ccc]", laList.toString());
	}

	/**
	 * Test insert with data out of order.
	 */
	@Test
	public final void insertOutOfOrder() {
		laList.insert("ccc");
		laList.insert("bbb");
		laList.insert("aaa");
		assertEquals("insert 3 fail", "[aaa, bbb, ccc]", laList.toString());
	}

	/**
	 * Test insert with data out of order.
	 */
	@Test
	public final void insertOutOfOrder2() {
		laList.insert("ccc");
		laList.insert("aaa");
		laList.insert("bbb");
		assertEquals("insert 3 fail", "[aaa, bbb, ccc]", laList.toString());
	}

	/**
	 * Test insert with data out of order.
	 */
	@Test
	public final void insertOutOfOrder3() {
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");
		laList.insert("aaa");
		assertEquals("insert 3 fail", "[aaa, bbb, ccc, ddd, eee]", laList.toString());
	}

	/**
	 * Test insert with data out of order.
	 */
	@Test
	public final void insertOutOfOrder4() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ddd");
		laList.insert("eee");
		laList.insert("ccc");
		assertEquals("insert 3 fail", "[aaa, bbb, ccc, ddd, eee]", laList.toString());
	}

	/**
	 * Test insert with data out of order.
	 */
	@Test
	public final void insertInOrder() {
		laList.insert("aaa");
		laList.insert("eee");
		laList.insert("iii");
		laList.insert("xxx");
		laList.insert("zzz");
		assertEquals("insert 3 fail", "[aaa, eee, iii, xxx, zzz]", laList.toString());
	}
	
	/**
	 * test isEmpty with empty list
	 */
	@Test
	public final void isEmpty0() {
		assertTrue("0 empty fail", laList.isEmpty());
	}

	/**
	 * test isEmpty with 205 elements
	 */
	@Test
	public final void isEmpty205() {
		fillList(laList, 205);
		assertFalse("205 empty fail", laList.isEmpty());
	}

	/**
	 * test isEmpty with 3 elements
	 */
	@Test
	public final void isEmpty3N() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		assertFalse("3 empty fail", laList.isEmpty());
	}

	/**
	 * test iterator by iterate empty list
	 */
	@Test
	public final void iterator0() {
		final Iterator<String> itr = laList.iterator();

		String str = "[";
		while (itr.hasNext()) {
			str += ", " + itr.next();
		}
		str += "]";

		assertEquals("iterator 0 fail", str, laList.toString());
	}

	/**
	 * test iterator by iterate 1 element list
	 */
	@Test
	public final void iterator1N() {
		laList.insert("aaa");
		final Iterator<String> itr = laList.iterator();

		String str = "[" + itr.next();
		while (itr.hasNext()) {
			str += ", " + itr.next();
		}
		str += "]";

		assertEquals("iterator 1 fail", str, laList.toString());
	}

	/**
	 * test iterator by iterate 205 elements list
	 */
	@Test
	public final void iterator205() {
		fillList(laList, 205);
		final Iterator<String> itr = laList.iterator();

		String str = "[" + itr.next();
		while (itr.hasNext()) {
			str += ", " + itr.next();
		}
		str += "]";

		assertEquals("iterator 205 fail", str, laList.toString());
	}

	/**
	 * Test valid remove method of iterator.
	 */
	@Test
	public final void iteratorRemove() {
		final int max = 205;
		fillList(laList, max);
		Iterator<String> itr = laList.iterator();

		while (itr.hasNext()) {
			final String temp = itr.next();
			if (temp.equals("str101"))
				itr.remove();
		}

		itr = laList.iterator();
		String str = "[" + itr.next();
		while (itr.hasNext()) {
			str += ", " + itr.next();
		}
		str += "]";

		/*
		String testStr = "[str" + paddingZeros(0, max);
		for (int i = 1; i < max; i++) {
			if (i != 101)
				testStr += ", str" + paddingZeros(i, max);
		}
		testStr += "]";
		*/

		assertEquals("iterator remove fail", str, laList.toString());
	}

	/**
	 * Test invalid remove method of iterator by not calling next() before hand.
	 */
	@Test(expected = IllegalStateException.class)
	public final void iteratorRemoveWithoutNext() {
		final int max = 205;
		fillList(laList, max);
		Iterator<String> itr = laList.iterator();

		itr.remove(); // must call next() first.
	}

	/**
	 * Test invalid remove method of iterator by not calling remove() twice.
	 */
	@Test(expected = IllegalStateException.class)
	public final void iteratorRemoveTwice() {
		final int max = 205;
		fillList(laList, max);
		Iterator<String> itr = laList.iterator();

		itr.next();
		itr.remove();
		itr.remove(); // must call next() first.
	}

	/**
	 * Test reset method of the iterator.
	 */
	@Test
	public final void iteratorReset() {
		fillList(laList, 205);
		MyIterator<String> itr = laList.iterator();
		String firstElement = laList.get(0);
		
		for(int i = 0; i < 10; i++) {
			itr.next();
		}
		
		itr.reset();
		assertEquals("The iterator is not reset to the first element", 
				     firstElement, itr.next());
	}

	/**
	 * Tests the iterator calling next() on an empty list.
	 */
	@Test(expected = NoSuchElementException.class)
	public final void iteratorEmptyListHasNext() {
		laList.clear();
		MyIterator<String> itr = laList.iterator();
		itr.next(); // hasNext() is false;
	}
	
	/**
	 * Tests the iterator calling next() when hasNext() is false.
	 */
	@Test(expected = NoSuchElementException.class)
	public final void iteratorEndOfListHasNext() {
		fillList(laList, 5);
		MyIterator<String> itr = laList.iterator();

		for(int i = 0; i < 5; i++) {
			itr.next();
		}
		
		itr.next(); // hasNext() is false;
	}
	
	/**
	 * Tests the iterator when getting the last element's data.
	 */
	@Test
	public final void verifyIteratorStopsAtTheEnd() {
		fillList(laList, 205);

		//int counter = 0;
		Iterator<String> itr = laList.iterator();
		String lastDataFromIterator = "";
		String lastDataFromIndex = laList.get(laList.getSize() - 1);
		
		while(itr.hasNext()) {
			lastDataFromIterator = itr.next();
			//counter++;
		}

		assertTrue("Last element does not contain the same data.", lastDataFromIndex.equals(lastDataFromIterator));
	}

	/**
	 * Tests the iterator when getting the first element's data.
	 */
	@Test
	public final void verifyIteratorAtFirstElement() {
		fillList(laList, 205);
		String firstDataFromIndex = laList.get(0);
		String firstDataFromIterator = "";

		Iterator<String> itr = laList.iterator();
		firstDataFromIterator = itr.next();
		
		assertTrue("The elemental data is different.", firstDataFromIndex.equals(firstDataFromIterator));
	}

	/**
	 * Tests the iterator when the list only has a single element.
	 */
	@Test
	public final void verifyIteratorWhenListSize1() {
		fillList(laList, 1);
		String firstDataFromIndex = laList.get(0);
		String firstDataFromIterator = "";

		Iterator<String> itr = laList.iterator();
		firstDataFromIterator = itr.next();
		
		assertTrue("The elemental data is different.", firstDataFromIndex.equals(firstDataFromIterator));
	}
	
	/**
	 * Tests the iterator does not have a next element to iterator over on an empty list.
	 */
	@Test
	public final void verifyIteratorWhenListSize0() {
		laList.clear();
		
		Iterator<String> itr = laList.iterator();
		assertFalse("The iterator claims to it has a next element on an empty list.", itr.hasNext());
	}

	/**
	 * test constructor with illegal negative input
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void negativeCapacity() {
		laList = new ArrayListSorted<String>(-1);
	}

	/**
	 * Test default constructor.
	 */
	@Test
	public final void defaultConstructor() {
		laList = new ArrayListSorted<String>();
		assertEquals(0, laList.getSize());
	}

	/**
	 * test remove by remove not exist element from 1 element list
	 */
	@Test
	public final void remove0False() {
		laList.insert("aaa");

		laList.remove("bbb");
		assertEquals("remove 0 False fail", "[aaa]", laList.toString());
	}

	/**
	 * test remove by remove exist element from 1 element list
	 */
	@Test
	public final void remove0True() {
		laList.insert("aaa");

		laList.remove("aaa");
		assertEquals("remove 0 True fail", "[]", laList.toString());
	}

	/**
	 * test remove by remove head element from 205 elements list
	 */
	@Test
	public final void remove205Head() {
		final int max = 205;
		fillList(laList, max);
		laList.remove("str000");

		String str = "[str" + paddingZeros(1, max);
		for (int i = 2; i < max; i++) {
			str += ", str" + paddingZeros(i, max);
		}
		str += "]";

		assertEquals("remove 205 head fail", str, laList.toString());
	}

	/**
	 * test remove by remove middle element from 205 elements list
	 */
	@Test
	public final void remove205Middle() {
		final int max = 205;
		fillList(laList, max);
		laList.remove("str101");

		String str = "[str" + paddingZeros(0, max);
		for (int i = 1; i < max; i++) {
			if (i != 101) {
				str += ", str" + paddingZeros(i, max);
			}
		}
		str += "]";

		assertEquals("remove 205 middle fail", str, laList.toString());
	}

	/**
	 * test remove by remove tail element from 205 elements list
	 */
	@Test
	public final void remove205Tail() {
		final int max = 205;
		fillList(laList, max);
		laList.remove("str204");

		String str = "[str" + paddingZeros(0, max);
		for (int i = 1; i < 204; i++) {
			str += ", str" + paddingZeros(i, max);
		}
		str += "]";

		assertEquals("remove 205 tail fail", str, laList.toString());
	}

	/**
	 * test remove by remove NOT exist element from 5 elements list
	 */
	@Test
	public final void remove5False() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");

		laList.remove("fff");
		assertEquals("remove 5 False fail", "[aaa, bbb, ccc, ddd, eee]", laList.toString());
	}

	/**
	 * test remove by remove head element from 5 elements list
	 */
	@Test
	public final void remove5HeadNext() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");

		laList.remove("bbb");
		assertEquals("remove 5 head next fail", "[aaa, ccc, ddd, eee]", laList.toString());
	}

	/**
	 * test remove by remove tail element from 5 elements list
	 */
	@Test
	public final void remove5TailNext() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");

		laList.remove("ddd");
		assertEquals("remove 5 tail next fail", "[aaa, bbb, ccc, eee]", laList.toString());
	}

	/**
	 * test remove by remove exist element from 5 elements list
	 */
	@Test
	public final void remove5TrueN() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");

		laList.remove("ccc");
		assertEquals("remove 5 True fail", "[aaa, bbb, ddd, eee]", laList.toString());
	}

	/**
	 * test removeAtIndex by remove 0 index from 1 element list
	 */
	@Test
	public final void removeAtIndex0() {
		laList.insert("aaa");

		laList.removeAtIndex(0);
		assertEquals("removeAtIndex 0 fail", "[]", laList.toString());
	}

	/**
	 * test removeAtIndex by remove 0 index from empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void removeAtIndex0False() {
		laList.removeAtIndex(0);
	}

	/**
	 * test removeAtIndex by remove out of bound index from 1 element list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void removeAtIndex1False() {
		laList.insert("aaa");
		laList.removeAtIndex(1);
	}

	/**
	 * test removeAtIndex by remove head from 205 elements list
	 */
	@Test
	public final void removeAtIndex205Head() {
		final int max = 205;
		fillList(laList, max);
		laList.removeAtIndex(0);

		String str = "[str" + paddingZeros(1, max);
		for (int i = 2; i < max; i++)
			str += ", str" + paddingZeros(i, max);
		str += "]";

		assertEquals("removeAtIndex 205 head fail", str, laList.toString());
	}

	/**
	 * test removeAtIndex by remove middle from 205 elements list
	 */
	@Test
	public final void removeAtIndex205Middle() {
		final int max = 205;
		fillList(laList, max);
		laList.removeAtIndex(101);

		String str = "[str" + paddingZeros(0, max);
		for (int i = 1; i < max; i++) {
			if (i != 101) {
				str += ", str" + paddingZeros(i, max);
			}
		}
		str += "]";

		assertEquals("removeAtIndex 205 middle fail", str, laList.toString());
	}

	/**
	 * test removeAtIndex by remove tail from 205 elements list
	 */
	@Test
	public final void removeAtIndex205Tail() {
		final int max = 205;
		fillList(laList, max);
		laList.removeAtIndex(204);

		String str = "[str" + paddingZeros(0, max);
		for (int i = 1; i < 204; i++)
			str += ", str" + paddingZeros(i, max);
		str += "]";

		assertEquals("removeAtIndex 205 tail fail", str, laList.toString());
	}

	/**
	 * test removeAtIndex by remove head from 2 elements list
	 */
	@Test
	public final void removeAtIndex2Head() {
		laList.insert("aaa");
		laList.insert("bbb");

		laList.removeAtIndex(0);
		assertEquals("removeAtIndex 2 tail fail", "[bbb]", laList.toString());
	}

	/**
	 * test removeAtIndex by remove tail from 2 elements list
	 */
	@Test
	public final void removeAtIndex2Tail() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.removeAtIndex(1);
		assertEquals("removeAtIndex 2 tail fail", "[aaa]", laList.toString());
	}

	/**
	 * test removeAtIndex by remove position one next to head from 5 elements
	 * list
	 */
	@Test
	public final void removeAtIndex5HeadNextN() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");
		laList.removeAtIndex(1);
		assertEquals("removeAtIndex 5 head next fail", "[aaa, ccc, ddd, eee]", laList.toString());
	}

	/**
	 * test removeAtIndex by remove position one next to tail from 5 elements
	 * list
	 */
	@Test
	public final void removeAtIndex5TailNextN() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");
		laList.removeAtIndex(3);
		assertEquals("removeAtIndex 5 tail next fail", "[aaa, bbb, ccc, eee]", laList.toString());
	}

	/**
	 * test removeAtIndex by remove negative index
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void removeAtIndexNegative() {
		laList.removeAtIndex(-1);
	}

	/**
	 * test set by set 0 index in an empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void set0False() {
		laList.clear();
		laList.set(0, "aaa");
	}

	/**
	 * test set by set 0 index in a 1 element list
	 */
	@Test
	public final void set1() {
		laList.insert("aaa");
		laList.set(0, "bbb");
		assertEquals("set 1 fail", "[bbb]", laList.toString());
	}

	/**
	 * Test set by set 0 index in a multi-element list.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void set2() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		
		laList.set(0, "ddd");
	}

	/**
	 * Test set by set 1 index in a multi-element list, in between two wrong elements.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void set3() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		
		laList.set(1, "ddd");
	}

	/**
	 * Test set by set 1 index in a multi-element list, next to a wrong element.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void set4() {
		laList.insert("bbb");
		laList.insert("ccc");
		
		laList.set(1, "aaa");
	}

	/**
	 * Test set by set an index where the data is the same.
	 */
	@Test
	public final void set5() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		
		laList.set(1, "bbb");
	}
	
	/**
	 * Test set by set an index where the data is the same.
	 */
	@Test
	public final void set6() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		
		laList.set(0, "aaa");
		assertEquals("aaa", laList.get(0));
	}

	/**
	 * Test set by set an index where the data is the same.
	 */
	@Test
	public final void set7() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		
		laList.set(1, "bbb");
		assertEquals("bbb", laList.get(1));
	}
	
	/**
	 * test set by set out of bound index in a 1 element list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void set1False() {
		laList.insert("aaa");
		laList.set(1, "bbb");
	}

	/**
	 * test set by set head index in a 205 elements list
	 */
	@Test
	public final void set205Head() {
		final int max = 205;
		fillList(laList, max);
		laList.set(0, "new");

		String str = "[new";
		for (int i = 1; i < max; i++) {
			str += ", str" + paddingZeros(i, max);
		}
		str += "]";

		assertEquals("set 205 head fail", str, laList.toString());
	}

	/**
	 * test set by set middle index in a 205 elements list
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void set205Middle() {
		final int max = 205;
		fillList(laList, max);
		laList.set(101, "new"); // 'n' is less than 's' in ("new").compare("str101")

		/*
		 * String str = "[str" + paddingZeros(0, max); for (int i = 1; i < max;
		 * i++) { if (i == 101) str += ", new"; else str += ", str" +
		 * paddingZeros(i, max);
		 * 
		 * } str += "]";
		 */

		// assertEquals("set 205 middle fail", str, al.toString());
	}

	/**
	 * test set by set tail index in a 205 elements list
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void set205Tail() {
		final int max = 205;
		fillList(laList, max);
		laList.set(204, "new"); // 'n' is less than 's' in ("new").compare("str204")

		/*
		 * String str = "[str" + paddingZeros(0, max); for (int i = 1; i < 204;
		 * i++) { str += ", str" + paddingZeros(i, max); } str += ", new]";
		 */

		// assertEquals("set 205 tail fail", str, al.toString());
	}

	/**
	 * test set by set one next to head index in a 5 elements list
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void set5HeadNextN() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");

		laList.set(1, "fff");
		// assertEquals("set 5 head next fail", "[aaa, fff, ccc, ddd, eee]",
		// al.toString());
	}

	/**
	 * test set by set one next to tail index in a 5 elements list
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void set5TailNextN() {
		laList.insert("aaa");
		laList.insert("bbb");
		laList.insert("ccc");
		laList.insert("ddd");
		laList.insert("eee");

		laList.set(3, "fff");
		// assertEquals("set 5 tail next fail", "[aaa, bbb, ccc, fff, eee]",
		// al.toString());
	}

	/**
	 * test set by set negative index in an empty list
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public final void setNegative() {
		laList.set(-1, "aaa");
	}

	@Before
	public final void setup() {
		// al = new ArrayListSorted<String>();
		laList = new ArrayListSorted<String>(1);
	}

	/**
	 * test constructor with illegal 0 input
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void zeroCapacity() {
		laList = new ArrayListSorted<String>(0);
	}

	/**
	 * Fills the list with String data containing values 0 through the specified maximum.
	 * Each string will be prefixed with "str" followed by the appropriate quantity of 
	 * leading zeros relative to theMax value.
	 * Example: str001
	 * @param theList the list to fill with the data.
	 * @param theMax the maximum quantity of elements.
	 */
	private void fillList(final MyList<String> theList, final int theMax) {
		for (int i = 0; i < theMax; i++) {
			theList.insert("str" + paddingZeros(i, theMax));
		}
	}

	/**
	 * Provides appropriate quantity of leading zeros (a String prefix) needed according to
	 * theMax value.  For example, for values 0 - 100, all the single digits will need two 
	 * leading zeros, and all the two digit numbers will need one leading zero, etc.
	 * The maximum can be as large as the maximum value for an int is.
	 * @param theNumber the particular number that needs padding.
	 * @param theMax the maximum number in the list, which determines the padding.
	 * @return the String with the number now padded with the appropriate quantity of
	 * 		   leading zeros.
	 */
	private String paddingZeros(final int theNumber, final int theMax) {
		String zeros = "";
		double maxZeros = 0.0; // serves as the exponent for divisor.
		int max = theMax;
		int num = (theNumber == 0) ? 1 : theNumber; // zero is a special case.
		double divisor = 10.0; // initial value;
		final int multiplier = 10;

		// This determines the quantity of times it is divisible by 10, which
		// later will be the exponent of 10.
		while ((max /= multiplier) > 0) {
			maxZeros++;
		}

		divisor = (int) Math.pow(divisor, maxZeros);

		// This determines the quantity of zeros use for padding for theNumber.
		while ((num / divisor) < 1) {
			num *= multiplier;
			zeros += "0";
		}

		return zeros + theNumber;
	}

}
