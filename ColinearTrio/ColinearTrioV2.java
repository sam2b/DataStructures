package Colinear;


// Note: Increase your Java max heap size by specifying this VM argument: -Xmx1000000000

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * Find all possible trios of co-linear points within the provided array of Point objects.
 * This implementation is O(n^2), and includes >=3 points as a trio, so there may be more than
 * 3 points for a given LineData.
 * @author Sam Brendel, sam2b@uw.edu
 * @version 1.0 April 10, 2017
 */
public class ColinearTrioV2 {
	
	/**
	 * The initial data to be processed.
	 */
	private Point[] myPoints;

	/**
	 * All unique pairs of Points derived from the myPoints array.
	 */
	private ArrayList<PointPair> myPairs;
	
	/**
	 * Keeps tally of all the trios of co-linear points.
	 */
	private HashMap<LineData, Integer> map; 
	
	static int SIZE_OF_ORIGINAL_LIST;

	final static boolean USE_LIST = true; // Enabling this could slow everything down.
	final static boolean DEBUG = true; 
	
	/**
	 * Constructor.  You give me the array of Points.
	 * @param thePoints
	 */
	public ColinearTrioV2(Point[] thePoints) {
		 myPoints = thePoints;
		 myPairs = new ArrayList<PointPair>();
		 map = new HashMap<>(thePoints.length);
		 SIZE_OF_ORIGINAL_LIST = thePoints.length; // just a pointer to the original list.
		 start();
	}
	
	/**
	 * Constructor.  I will generate points for you.
	 */
	public ColinearTrioV2() {
		this(generateRandomPointArray());
	}

	
	private void start() {
		generatePointPairs(); // O(n^2)
		findTrios(); // O(n^2)
	}
	
	/**
	 * Unique pairs of Points.  NOT YET colinear.
	 * Runtime O(n^2) to make a pair.
	 */
	private void generatePointPairs() {
		final int mod = SIZE_OF_ORIGINAL_LIST * 8; 
		
		if(DEBUG) System.out.println("----- Generating Point Pairs...");

		int counter = 0;
		int j = 0;
		
		for(int i = 0; i < myPoints.length; i++) {
			//while(j++ < myPoints.length) {
			for( ; j < myPoints.length; j++) {
				// i and j are the same point, so ignore that pair.
				if(i != j) {
					myPairs.add(new PointPair(myPoints[i], myPoints[j]));
					if(DEBUG  && ++counter % mod == 0) System.out.print("pair ");
				}
				// Not yet colinear, just a unique pair to use later.
			}

		}
		if(DEBUG) System.out.println("\n----- ----- " + counter + " pairs found.\n");
	}

	/**
	 * Runtime O(n^2)
	 */
	private void findTrios() {
		final int mod = SIZE_OF_ORIGINAL_LIST / 10;
		if(DEBUG) System.out.println("----- Finding trios...");

		int counter = 0;
		int j = 0;
		int quantity = 0;
		
		for(int i = 0; i < myPairs.size(); i++) {
			LineData lineData = myPairs.get(i).lineData;
			
			// Finally, search for a third point with the same line data.
			//while(j++ < myPoints.length) {
			for( ; j < myPoints.length; j++) {
				
				if(!myPairs.get(i).isDuplicatePoint(myPoints[j]) 
						&& myPairs.get(i).isColinear(myPoints[j]))  {

					if(DEBUG  && ++counter % mod == 0) System.out.print("trio ");
					
					if (USE_LIST) {
						// If this line data already exists, then get its existing quantity.
						quantity = (map.containsKey(lineData)) ? map.get(lineData) : 0;
						
						// This map is keeping track of all the trios of co-linear points.
						map.put(lineData, ++quantity);
	
						// This keeps track of all the points for this line data.
						if (!lineData.allPoints.contains(myPoints[j])) {
							 lineData.addPoint(myPoints[j]);
						}
					}
				}
			}
		}
		
		if(DEBUG) System.out.println("\n----- ----- " + counter + " trios found.\n");

	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		for (LineData line : map.keySet()) {
			if (map.get(line) > 2) {         // Should this be EXACTLY 3?  Or >= 3
				string.append("\n");
				string.append(line.getPoints());
				string.append("\n");
				string.append(line);
				string.append("   Array Size: " + line.getPoints().size() + "\n");
			}
		}
		
		return string.toString();
	}
	
	public int getTrioQuantity() {
		int counter = 0;
		for (Integer quantity : map.values()) {
			if (quantity >= 3) {
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * The list of points that are all co-linear, which all have the same slope and y intercept.
	 * @param lineData the data with the desired slope and y intercept.
	 * @return the list of points.  Duh.
	 */
	public ArrayList<Point> getTrios(final LineData lineData) {
		return lineData.getPoints();
	}


	//// INNER CLASSES ///////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The Colinear data of the given point.  Treat the slope and y intercept as a unit for equality.
	 * @author Sam Brendel
	 *
	 */
  	private class LineData {

		private double yIntercept;
		private double slope;
		ArrayList<Point> allPoints; // Optional, just for display purposes.
		
		/**
		 * Constructor.
		 */
		public LineData() {
			allPoints = new ArrayList<>(SIZE_OF_ORIGINAL_LIST);
		}
		
		public void addPoint(final Point point) {
			allPoints.add((Point)point.clone());
		}
		
		public ArrayList<Point> getPoints() {
			return allPoints; // reference copy intentional.
		}
		
		@Override
		public boolean equals(Object o) {
			return ((LineData)o).yIntercept == this.yIntercept 
					 && ((LineData)o).slope == this.slope;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(yIntercept, slope);
		}
		
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder(6);
			s.append("[Slope: ");
			s.append(slope);
			s.append(", ");
			s.append("YIntercept: ");
			s.append(yIntercept);
			s.append("]");
			return s.toString();
		}
	}

	/**
	 * Contains a pair of points that are co-linear.  Each pair is used to find a third Point that
	 * may be co-linear to these.  It is possible to have more than 3 points that are co-linear.
	 * @author Sam Brendel
	 *
	 */
	private class PointPair {
		private LineData lineData;
		private Point pointA;
		private Point pointB;
		
		/**
		 * Constructor.
		 * @param pA
		 * @param pB
		 */
		public PointPair(final Point pA, final Point pB) {
			pointA = (Point) pA.clone();
			pointB = (Point) pB.clone();
			lineData = new LineData();
			
			// m = (y2 - y1) / (x2 - x1) 
			lineData.slope = ((pointB.getY() - pointA.getY()) 
					        / (pointB.getX() - pointA.getX())) + 0.0;
			// By adding 0.0, this eliminates the possibility of having a -0.0 slope.
			
			// b = y - mx
			lineData.yIntercept = pA.getY() - (lineData.slope * pA.getX());
			
			if (USE_LIST) {
				lineData.addPoint(pointA);
				lineData.addPoint(pointB);
			}
		}
		
		public Point[] getPoints() {
			Point[] duhPoints = {pointA, pointB};
			return duhPoints;
		}
		
		/**
		 * Determines if the specified point is is co-linear to the existing set of pairs,
		 * which means if all three points have the same slope and y intercept.
		 * @param pointC the third point to compare with the existing set of points.
		 * @return true if if all three points have the same slope and y intercept. 
		 */
		public boolean isColinear(final Point pointC) {
			PointPair secondPair = new PointPair(pointC, pointA);
			return this.lineData.equals(secondPair.lineData);
		}
		
		/**
		 * Determines if the specified point is already in this pair.
		 * @param pointC the point to look for.
		 * @return true if it already exists in this pair.
		 */
		public boolean isDuplicatePoint(final Point pointC) {
			return pointC.equals(pointA) || pointC.equals(pointB);
		}
		
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder(14);
			s.append("[");
			s.append(this.lineData);
			s.append(", ");
			s.append("Point[");
			s.append(pointA.x);
			s.append(", ");
			s.append(pointA.y);
			s.append("]");
			s.append(", ");
			s.append("Point[");
			s.append(pointB.x);
			s.append(", ");
			s.append(pointB.y);
			s.append("]]");
			return s.toString();
		}
	}

	//// END INNER CLASSES ///////////////////////////////////////////////////////////////////////////////////////
	
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
	
	/**
	 * Driver method.
	 * @param args First arg is quantity of points.  Second arg is either random or known.
	 */
	public static void main(String[] args) {
		Point[] testPoints;
		if (args[0] == null) {
			getTestPoints({"random"})
		} else {
			testPoints = getTestPoints(args);
		}
			
		//	Point[] testPoints = {new Point(1,1), new Point(2,2), new Point(3,3), new Point(4, 4),
		//		new Point(1, 5), new Point(2, 6), new Point(3, 7)};
		//Point[] testPoints = {new Point(1, 2), new Point(2, 3), new Point(2, 4), new Point(4, 8)};
		//testPoints = generateRandomPointArray(quantity);
		//Point[] testPoints = generateKnownColinear(50); // more than 50 takes a long time.

		////////////////////////////////////////////////////////////////////////////////////////////
		StopWatch stopWatch = new StopWatch();
		System.out.println("Running...");
		stopWatch.start();

		ColinearTrioV2 ct = new ColinearTrioV2(testPoints); //------------------------------------

		stopWatch.stop();
		System.out.println("Stopped!");
		
		if(USE_LIST) {
			System.out.println(ct);
			System.out.println("Quantity of co-linear trios: " + ct.getTrioQuantity());
		}
		System.out.println("Time ellapsed: " + stopWatch.getElapsedTimeSecs() + " seconds");
	}
	
	private Point[] getTestPoints(final String[] args) {
		Point[] testPoints = null;
		
		final int quantity = (args[1] == null) 
						   ? 200 
						   : Integer.parseInt(args[1]);
		
		switch(args[0]) {
			case "random": {
				testPoints = generateRandomPointArray(quantity);
				break;
			}
			case "known": {
				testPoints = generateKnownColinear(quantity);
				break;
			}
			default: {
				System.out.println("Please enter either random or known as the first argument.");
				return;
			}
		}
	}


}
