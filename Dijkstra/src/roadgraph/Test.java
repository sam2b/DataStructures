package roadgraph;

import java.util.Comparator;

import geography.GeographicPoint;


public class Test {

	public static void main(String[] args) {
		Test test = new Test();

		NodeData n1 = test.new NodeData(new GeographicPoint(0,0), 6.0);
		NodeData n2 = test.new NodeData(new GeographicPoint(0,0), 6.0);
		
		
		//System.out.println("The result is " + n1.compare(n1, n2));

	}
	
	
	
	/**
	 * Represents a row of data in the table.
	 */
	private class NodeData implements Comparator<NodeData> {
		public GeographicPoint node;
		public double length;

		// Constructor.
		public NodeData(final GeographicPoint theNode, final double theLength) {
			this.length = theLength;
			this.node = theNode;
		}

		// Constructor.
		public NodeData(final GeographicPoint thePoint) {
			this(thePoint, Double.POSITIVE_INFINITY);
		}

		// Constructor.
		public NodeData() {
			this(new GeographicPoint(0,0), Double.POSITIVE_INFINITY);
		}

		@Override
		public int compare(final NodeData objectA, NodeData objectB) {
			return (int)(objectA.length - objectB.length);
		}
		
		@Override
		public String toString() {
			final String len = (length == Double.POSITIVE_INFINITY) ? "infinity" : String.valueOf(Math.round(length));
			return "[x=" + (int)node.x + ", y=" + (int)node.y + ", length=" + len + "]";
		}
	}


}
