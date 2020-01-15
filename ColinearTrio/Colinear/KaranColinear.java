package Colinear;


import java.awt.Point;
import java.util.*;

/**
 * 
 * @author Karan Toor, Spring 2017, TCSS390 Data Structures.
 *
 */
public class KaranColinear {

	public static void main(final String[] args) {
		
		/*final Point one = new Point(1, 2);
		final Point two = new Point(2, 3);
		final Point thr = new Point(2, 4);
		final Point fou = new Point(4, 8);
		final List<Point> list = new ArrayList<>();
		
		list.add(one);
		list.add(two);
		list.add(thr);
		list.add(fou);*/
		
		final boolean USE_LIST = false; // Enabling this could slow everything down.
		
//		Point[] testPoints = {new Point(1,1), new Point(2,2), new Point(3,3), new Point(4, 4),
//				new Point(1, 5), new Point(2, 6), new Point(3, 7)};
		
		//Point[] testPoints = {new Point(1, 2), new Point(2, 3), new Point(2, 4), new Point(4, 8)};
		
		//Point[] testPoints = generateRandomPointArray(400);
		
		Point[] testPoints = generateKnownColinear(50); // more than 50 takes a long time.

		
		////////////////////////////////////////////////////////////////////////////
		StopWatch stopWatch = new StopWatch();
		System.out.println("Running...");
		stopWatch.start();
		
		final Point[] points = testPoints; //---------------------------------------

		final List<Point> list = Arrays.asList(points);
		
		final List<List<Point>> trios = new ArrayList<>();
		
		for (int i = 0; i < list.size(); i++) {
			final Point point0 = list.get(i);
			
			for (int j = 0; j < list.size(); j++) {
				final Point point1 = list.get(j);
				
				if (!point0.equals(point1)) {
					//find m
					//m = (y2-y1)/(x2-x1)
					double m;
					boolean undefined = false;
					
					if (point1.x == point0.x) {
						undefined = true;
						m = 0;
					} else {
						m = ((point1.y - point0.y)*1.0) / (point1.x - point0.x);
					}
					
					//find b
					//y = mx + b
					//b = y - mx
					final double b = point1.y - m * point1.x;
					
					for (int k = 0; k < list.size(); k++) {
						final Point point2 = list.get(k);
						
						if (!point2.equals(point0) && !point2.equals(point1)) {
							if (undefined) {
								if (point2.x == point0.x) {
									final List<Point> trio = createTrio(point0, point1, point2);
									if (trioIsUnique(trio, trios)) {
										trios.add(trio);
									}
								}
								
							} else {
								final double y = (m * point2.x) + b;
								if (y == point2.y) {
									final List<Point> trio = createTrio(point0, point1, point2);
									if (trioIsUnique(trio, trios)) {
										trios.add(trio);
									}
								}
							}
						}
					}
				}
			}
		}
		
		stopWatch.stop();
		System.out.println("Stopped!");
		if (USE_LIST) System.out.println(trios.toString());
		System.out.println("\nTime Elapsed: " + stopWatch.getElapsedTimeSecs() + " seconds");
		System.out.println("List Size: " + trios.size());
	}

	public static boolean trioIsUnique(final List<Point> theList, final List<List<Point>> theTrios) {
		boolean toReturn = true;
		if (theTrios != null) {
			for (int i = 0; i < theTrios.size(); i++) {
				final List<Point> temp = theTrios.get(i);
				if (temp.contains(theList.get(0)) && temp.contains(theList.get(1)) && temp.contains(theList.get(2))) {
					toReturn = false;
				}
			}
		}
		return toReturn;
	}

	public static List<Point> createTrio(final Point theA, final Point theB, final Point theC) {
		final List<Point> list = new ArrayList<>();
		list.add(theA);
		list.add(theB);
		list.add(theC);
		return list;
	}

	// Below are helper methods to generates points gallore.
	
	/**
	 * Generates an array of random points.
	 * Duplicate of the empty constructor.
	 * @return the Point array.
	 */
	private static Point[] generateRandomPointArray(final int quantity) {
		final int min = -200;
		final int max = 200;
		final Random random = new Random();
		Point[] pointArray = new Point[quantity];
		
		System.out.println("Using " + quantity + " of random co-linear points.");
		
		for(int i = 0; i < quantity; i++) {
			int x = random.nextInt(max + 1 - min) + min;
			int y = random.nextInt(max + 1 - min) + min;
			pointArray[i] = new Point(x, y);
		}
		
		return pointArray;
	}
	
	private static Point[] generateRandomPointArray() {
		return generateRandomPointArray(100);
	}

	/**
	 * Generates an array of all co-linear points.
	 * @return um the array.
	 */
	private static Point[] generateKnownColinear(final int quantity) {
		int xStart = 0;
		int yStart = 0;
		Point[] pointArray = new Point[quantity];
		
		System.out.println("Using " + quantity + " of known co-linear points.");
		
		for(int i = 0; i < quantity; i++) {
			pointArray[i] = new Point(xStart++, yStart++);
		}
		
		return pointArray;
	}

	private static Point[] generateKnownColinear() {
		return generateKnownColinear(50);
	}
	
}


