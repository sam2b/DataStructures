import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;

class LinkedListTest {
	Point pointA;
	Point pointB;
	LinkedList<Point> linkedListA;
	LinkedList<Point> linkedListB;

	@BeforeEach
	void setUp() throws Exception {
		linkedListA = new LinkedList<Point>();
		linkedListB = new LinkedList<Point>();
		fillList(linkedListB, 20);
	}
	
	private void fillList(LinkedList<Point> list, int n) {
		list.add(new Point(1, 2));
		list.add(new Point(3, 4));
		list.add(new Point(5, 6));
		for (int i = 0; i < n-3; i++) {
			list.add(new Point());
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		pointA = null;
		pointB = null;
		if (linkedListA != null) linkedListA.clear();
		if (linkedListB != null) linkedListB.clear();
		linkedListA = null;
		linkedListB = null;
	}

	@Test
	void testDataType() {
		linkedListA.add(new Point());
		assertTrue(linkedListA.get(0) instanceof Point);
	}

	@Test
	void testSizeAdd1() {
		linkedListA.add(new Point());
		assertEquals(linkedListA.size(), 1);
	}

	@Test
	void testSizeAdd20() {
		assertEquals(linkedListB.size(), 20);
	}

	@Test
	void testSizeClear() {
		linkedListA.add(new Point());
		linkedListA.clear();
		assertEquals(linkedListA.size(), 0);
	}

	@Test
	void testEmpty() {
		linkedListB.clear();
		assertTrue(linkedListB.isEmpty());
	}

	@Test
	void testSizeZero() {
		linkedListB.clear();
		assertEquals(linkedListB.size(), 0);
	}

	@Test
	void testEqualData() {
		assertEquals(linkedListB.get(0), linkedListB.get(0));
	}

	@Test
	void testNotEqualNeighborData() {
		LinkedList<Point> linkedListC = new LinkedList<Point>();
		linkedListC.add(new Point(1, 2));
		linkedListC.add(new Point(3, 4));
		linkedListC.add(new Point(5, 6));
		Point point0 = linkedListC.get(0);
		Point point1 = linkedListC.get(1);
		assertFalse(point0.getX() == point1.getX(), "Should not be the same value.");
		
	}
	
	@Test
	void testNotEqualAdjacentData() {
		LinkedList<Point> linkedListC = new LinkedList<Point>();
		linkedListC.add(new Point(1, 2));
		linkedListC.add(new Point(3, 4));
		linkedListC.add(new Point(5, 6));
		Point point0 = linkedListC.get(0);
		Point point1 = linkedListC.get(2);
		assertFalse(point0.getX() == point1.getX(), "Should not be the same value.");
		
	}
	
	@Test
	void testNotEqualAddress() {
		LinkedList<Point> linkedListC = new LinkedList<Point>();
		linkedListC.add(new Point(1, 2));
		linkedListC.add(new Point(3, 4));
		linkedListC.add(new Point(5, 6));
		Point point0 = linkedListC.get(0);
		Point point1 = linkedListC.get(2);
		assertFalse(point0 == point1, "Should not be the same memory address.");
		
	}
}
