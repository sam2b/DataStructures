/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import correlator.HashMap;
import correlator.HeapEntry;
import correlator.HeapPriorityQueue;

/**
 * Test suite for the correlator.HashMap class.
 * @author Sam Brendel
 * @version 5.28A
 */
public class HashMapTest {

	// Fields commonly used for test cases.
	HashMap<String, Integer> table;
	HeapPriorityQueue<String, Integer> heap;
	List<HeapEntry> list;
	String[] stringArray;
	Integer[] intArray;
	final static String[] TABLE_ARRAY = {"one", "two", "two", "three", "three", "three", "four", "four", "four", "four"};
	final static int QUANTITY = 4;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		table = new HashMap<>();
		heap  = new HeapPriorityQueue<>();
        fillTable(TABLE_ARRAY);
	}

	/**
	 * Helper method to fill the hash table.
	 * @param arrayString the String.
	 * @param arrayInteger the Integer.
	 */
	private void fillTable(String[] s) {
	    Integer wordCount; 
	    String word;
	
	    // Fill the hash table.
        for(int i = 0; i < s.length; i++) {
            word = s[i];        
            wordCount = table.get(word);

            // Only keep words that are at least 3 characters long
            if (word != null && word.length() > 2) {
                wordCount = (wordCount == null) 
                        ? 1 
                        : wordCount.intValue() + 1;

                table.put(word, wordCount);
            }
        }
	}
	
	@Test
	public void tableToStringFull() {
	    assertEquals("[one, four, three, two]", table.toString());
	}

   @Test
    public void tableToStringEmpty() {
       table.clear();
       assertEquals("[]", table.toString());
    }

    @Test
    public void iteratorHasNextEmpty() {
        table = new HashMap<>();
        assertFalse(table.hasNext());
    }

	@Test
	public void iteratortableClear() {
	    table.clear();
		assertFalse(table.hasNext());
	}

    @Test
	public void iteratorHasNextSingle() {
	    String[] array = {"hello"};
	    fillTable(array);
	    assertTrue(table.hasNext());
	}
	
	@Test
	public void iteratorHasNextEnd() {
	    fillTable(TABLE_ARRAY);
	    
	    while(table.hasNext()) {
	        table.next();
	    }
	    
	    assertFalse(table.hasNext());
	}
	
	@Test
	public void iteratorReset() {
	    fillTable(TABLE_ARRAY);
	    String first = table.next();
        table.next();
        table.next();
        table.next();
        
        table.iteratorReset();
        assertEquals(first, table.next());
	}
	
	@Test
	public void iteratorFull() {
	    fillTable(TABLE_ARRAY);
	    int tableSize = table.size();
	    int counter = 0;
	    
	    while(table.hasNext()) {
	        table.next();
	        counter++;
	    }
	    
	    assertEquals(counter, tableSize);
	}
	
	@Test
	public void iteratorContents() {
	    fillTable(TABLE_ARRAY);
	    List<String> list = new ArrayList<>();
	    
	    while(table.hasNext()) {
	        list.add(table.next());
	    }
	    
	    list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2); // alphabetical order.
            }
        });

	    assertEquals("[four, one, three, two]", list.toString());
	    
	}
	
    @Test
    public void tableSizeClear() {
        table.clear();
        assertEquals(0, table.size());
    }
    
    @Test
    public void tableSizeIsEmpty1() {
        table.clear();
        assertTrue(table.isEmpty());
    }
    
    @Test
    public void tableSizeIsEmpty2() {
        for(int i = 0; i < TABLE_ARRAY.length; i++) {
            table.remove(TABLE_ARRAY[i]);
        }

        assertTrue(table.isEmpty());
    }
    
    @Test
    public void tableIsEmptyNot() {
        assertFalse(table.isEmpty());
    }
    
    @Test
    public void tableIsEmptyPutRemove() {
        table = new HashMap<>();
        assertTrue(table.isEmpty());
        table.put("hi", 23);
        table.remove("hi");
        assertTrue(table.isEmpty());
    }

    @Test
    public void tableIsEmptyRemovePut() {
        table.remove("one");
        table.remove("two");
        table.remove("three");
        table.remove("four");
        assertTrue(table.isEmpty());
        table.put("hola", 342);
        assertFalse(table.isEmpty());
    }

    @Test
	public void tableSize() {
	    assertEquals(4,  table.size());
	}
	
    @Test
	public void tableSizeRemove() {
	    table.remove("one");
	    assertEquals(3,  table.size());
	}
	
    @Test
    public void tableGet1() {
        Integer value = table.get("one");
        assertEquals(new Integer(1), value);
    }
    
    @Test
    public void tableGet2() {
        Integer value = table.get("two");
        assertEquals(new Integer(2), value);
    }

    @Test
    public void tableGet3() {
        Integer value = table.get("three");
        assertEquals(new Integer(3), value);
    }
    
    @Test
    public void tableGet4() {
        Integer value = table.get("four");
        assertEquals(new Integer(4), value);
    }

    @Test
    public void tablePutGet() {
        table.put("alpha", 98);
        assertEquals(new Integer(98), table.get("alpha"));
    }
    
    @Test
    public void tablePut2Get() {
        table.put("alpha", 98);
        table.put("alpha", 100);
        assertEquals(new Integer(100), table.get("alpha"));
    }
    
    @Test
    public void tablePutSize1() {
        table.put("Hello", 99);
        assertEquals(5, table.size());
    }
    
    @Test
    public void tablePutSize5() {
        table.put("Hello", 5);
        table.put("Hello", 6);
        table.put("Hello", 7);
        table.put("Hello", 8);
        table.put("Hello", 9);
        assertEquals(5, table.size());
    }
    
    @Test
    public void tableContainsEmpty() {
        table = new HashMap<>();
        assertFalse(table.contains("one"));
        assertFalse(table.contains("two"));
        assertFalse(table.contains("three"));
        assertFalse(table.contains("four"));
    }

    @Test
    public void tableContains() {
        assertTrue(table.contains("one"));
        assertTrue(table.contains("two"));
        assertTrue(table.contains("three"));
        assertTrue(table.contains("four"));
    }

    @Test
    public void tableContainsPut() {
        table.put("beta", 0);
        assertTrue(table.contains("beta"));
    }

    @Test
    public void tableContainsRemove() {
        table.remove("two");
        assertFalse(table.contains("two"));
    }

    @Test
    public void tableIntersection1() {
        HashMap<String, Integer> table2 = new HashMap<>();
        table2.put("sam", 3);
        table2.put("brendel", 10);
        table2.put("two", 8);
        
        double intersectSize = table.intersection(table2);
        
        assertTrue(1 == intersectSize);
    }
    
    @Test
    public void tableIntersectionNone() {
        HashMap<String, Integer> table2 = new HashMap<>();
        table2.put("sam", 3);
        table2.put("brendel", 10);
        table2.put("data", 8);
        table2.put("structures", 36);
        
        assertTrue(0 == table.intersection(table2));
        
    }
    
    @Test
    public void tableIntersectionSelf() {
        double tableSize = table.size();
        assertTrue(tableSize == table.intersection(table));
    }

    @Test
    public void tableIntersectionZero() {
        HashMap<String, Integer> table2 = new HashMap<>();
        assertTrue(0 == table.intersection(table2));

    }
    
    @Test
    public void tableUnionNoIntersection() {
        HashMap<String, Integer> table2 = new HashMap<>();
        table2.put("sam", 3);
        table2.put("brendel", 10);
        table2.put("data", 8);
        table2.put("structures", 36);

        assertTrue(8 == table.union(table2));
    }
    
    @Test
    public void tableUnionWithIntersection() {
        HashMap<String, Integer> table2 = new HashMap<>();
        table2.put("sam", 3);
        table2.put("two", 43);
        table2.put("brendel", 10);
        table2.put("data", 8);
        table2.put("structures", 36);
        table2.put("four", 45);

        assertTrue(8 == table.union(table2));
    }
    
    @Test
    public void tableUnionZero() {
        HashMap<String, Integer> table2 = new HashMap<>();
        assertTrue(4 == table.union(table2));
    }
    
    
    
}












































