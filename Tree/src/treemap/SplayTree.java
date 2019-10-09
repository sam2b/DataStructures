package treemap;

// SplayTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is found
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements a top-down splay tree. Note that all "matching" is based on the
 * compareTo method.
 * 
 * @author Mark Allen Weiss
 */
public class SplayTree<AnyKey extends Comparable<? super AnyKey>, AnyValue> 
                              implements MyTreeMap<AnyKey, AnyValue> {

	private SplayTree.BinaryNode<AnyKey, AnyValue> root;
	private SplayTree.BinaryNode<AnyKey, AnyValue> nullNode;

	/**
	 * Construct the tree.
	 */
	public SplayTree() {
		nullNode = new BinaryNode<AnyKey, AnyValue>(null);
		nullNode.left = nullNode.right = nullNode;
		root = nullNode;
	}

	private SplayTree.BinaryNode<AnyKey, AnyValue> newNode = null; // Used between different
												// inserts

    /**
     * Puts into the tree, and each time splay's the put node to the top.
     * @param x the key to insert.
     * @param y the value to insert.
     */
	@Override
	public void put(AnyKey x, AnyValue y) {
		if (newNode == null)
			newNode = new BinaryNode<AnyKey, AnyValue>(null);
		newNode.key = x;
		newNode.value = y;

		if (root == nullNode) {
			newNode.left = newNode.right = nullNode;
			root = newNode;
		} else {
			root = splay(x, root);

			int compareResult = x.compareTo(root.key);

			if (compareResult < 0) {
				newNode.left = root.left;
				newNode.right = root;
				root.left = nullNode;
				root = newNode;
			} else if (compareResult > 0) {
				newNode.right = root.right;
				newNode.left = root;
				root.right = nullNode;
				root = newNode;
			} else
				//return; // No duplicates.  OLD CODE.
				newNode.value = y;
		}
		newNode = null; // So next insert will call new
	}

    /**
     * Removes a key-value pair from the tree.
     * @param x is the key to be removed
     */
	@Override
	public void remove(AnyKey x) {
		BinaryNode<AnyKey, AnyValue> newTree;

		// If x is found, it will be at the root
		root = splay(x, root);
		if (root.key.compareTo(x) != 0)
			return; // Item not found; do nothing

		if (root.left == nullNode)
			newTree = root.right;
		else {
			// Find the maximum in the left subtree
			// Splay it to the root; and then attach right child
			newTree = root.left;
			newTree = splay(x, newTree);
			newTree.right = root.right;
		}
		root = newTree;
	}

	/**
	 * Find the smallest item in the tree. Not the most efficient implementation
	 * (uses two passes), but has correct amortized behavior. A good alternative
	 * is to first call find with parameter smaller than any item in the tree,
	 * then call findMin.
	 * 
	 * @return the smallest item or throw UnderflowException if empty.
	 */
	public AnyKey findMin() {
		if (isEmpty())
			throw new IllegalStateException();

		BinaryNode<AnyKey, AnyValue> ptr = root;

		while (ptr.left != nullNode)
			ptr = ptr.left;

		root = splay(ptr.key, root);
		return ptr.key;
	}

	/**
	 * Find the largest item in the tree. Not the most efficient implementation
	 * (uses two passes), but has correct amortized behavior. A good alternative
	 * is to first call find with parameter larger than any item in the tree,
	 * then call findMax.
	 * 
	 * @return the largest item or throw UnderflowException if empty.
	 */
	public AnyKey findMax() {
		if (isEmpty())
			throw new IllegalStateException();

		BinaryNode<AnyKey, AnyValue> ptr = root;

		while (ptr.right != nullNode)
			ptr = ptr.right;

		root = splay(ptr.key, root);
		return ptr.key;
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return true if x is found; otherwise false.
	 */
	public boolean contains(AnyKey x) {
		if (isEmpty())
			return false;

		root = splay(x, root);

		return root.key.compareTo(x) == 0;
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = nullNode;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == nullNode;
	}

	private SplayTree.BinaryNode<AnyKey, AnyValue> header = new BinaryNode<AnyKey, AnyValue>(null); // For
																		// splay

	/**
	 * Internal method to perform a top-down splay. The last accessed node
	 * becomes the new root.
	 * 
	 * @param x
	 *            the target item to splay around.
	 * @param t
	 *            the root of the subtree to splay.
	 * @return the subtree after the splay.
	 */
	private SplayTree.BinaryNode<AnyKey, AnyValue> splay(AnyKey x, BinaryNode<AnyKey, AnyValue> t) {
		SplayTree.BinaryNode<AnyKey, AnyValue> leftTreeMax, rightTreeMin;

		header.left = header.right = nullNode;
		leftTreeMax = rightTreeMin = header;

		nullNode.key = x; // Guarantee a match

		for (;;) {
			int compareResult = x.compareTo(t.key);

			if (compareResult < 0) {
				if (x.compareTo(t.left.key) < 0)
					t = rotateWithLeftChild(t);
				if (t.left == nullNode)
					break;
				// Link Right
				rightTreeMin.left = t;
				rightTreeMin = t;
				t = t.left;
			} else if (compareResult > 0) {
				if (x.compareTo(t.right.key) > 0)
					t = rotateWithRightChild(t);
				if (t.right == nullNode)
					break;
				// Link Left
				leftTreeMax.right = t;
				leftTreeMax = t;
				t = t.right;
			} else
				break;
		}

		leftTreeMax.right = t.left;
		rightTreeMin.left = t.right;
		t.left = header.right;
		t.right = header.left;
		return t;
	}

	/**
	 * Rotate binary tree node with left child. For AVL trees, this is a single
	 * rotation for case 1.
	 */
	private static <AnyKey, AnyValue> SplayTree.BinaryNode<AnyKey, AnyValue> rotateWithLeftChild(SplayTree.BinaryNode<AnyKey, AnyValue> k2) {
		SplayTree.BinaryNode<AnyKey, AnyValue> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		return k1;
	}

	/**
	 * Rotate binary tree node with right child. For AVL trees, this is a single
	 * rotation for case 4.
	 */
	private static <AnyKey, AnyValue> SplayTree.BinaryNode<AnyKey, AnyValue> rotateWithRightChild(SplayTree.BinaryNode<AnyKey, AnyValue> k1) {
		SplayTree.BinaryNode<AnyKey, AnyValue> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		return k2;
	}

	// Basic node stored in unbalanced binary search trees
	private static class BinaryNode<AnyKey, AnyValue> {
		// Constructor
		BinaryNode(AnyKey theElement) {
			this(theElement, null, null);
		}

		// Constructor
		BinaryNode(AnyKey theElement, SplayTree.BinaryNode<AnyKey, AnyValue> lt, SplayTree.BinaryNode<AnyKey, AnyValue> rt) {
			key = theElement;
			left = lt;
			right = rt;
			value = null;
		}

		AnyKey key; // The data in the node
		SplayTree.BinaryNode<AnyKey, AnyValue> left; // Left child
		SplayTree.BinaryNode<AnyKey, AnyValue> right; // Right child
		AnyValue value;
	}

	/////// START SAM CODE
	/////// /////////////////////////////////////////////////////////

    /**
     * Returns tree contents as a string.
     * @return tree contents as a string
     */
	@Override
	public String toString() {
		String result = "";
		//StringBuilder s = new StringBuilder();
		if (isEmpty())
			//result = "Empty tree";
			result = "Empty tree";
		else
			result = treeToString(root);

		return "{" + result + "}";
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the tree.
	 */
	private String treeToString(SplayTree.BinaryNode<AnyKey, AnyValue> t) {
		//StringBuilder s = new StringBuilder();
		String string = "";
		if (t != nullNode) {
			string += treeToString(t.left);
			string += t.key + "=" + t.value + ", ";
			string += treeToString(t.right);
		}
		return string;
	}

    /**
     * Returns a value associated with a key.
     * @param x is the key
     * @return the value associated with the key
     */
	@Override
	public AnyValue get(AnyKey x) {
		SplayTree.BinaryNode<AnyKey, AnyValue> t = null;

		if (!isEmpty())
			root = splay(x, root);
			
		t = root;
		AnyValue value = null;
		
		//if (t != null)
			value = t.value;
		
		return value;
	}

	// The remove() method already exists, so I just annotated with @Override.

	///////// END SAM CODE
	///////// ///////////////////////////////////////////////////////////

	// Test program; should print min and max and nothing else
	public static void main(String[] args) {
		SplayTree<Integer, Object> t = new SplayTree<>();
		final int NUMS = 40000;
		final int GAP = 307;

		System.out.println("Checking... (no bad output means success)");

		for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
			//t.insert(i);
			t.put(i, 99);
		System.out.println("Inserts complete");

		for (int i = 1; i < NUMS; i += 2)
			t.remove(i);
		System.out.println("Removes complete");

		if (t.findMin() != 2 || t.findMax() != NUMS - 2)
			System.out.println("FindMin or FindMax error!");

		for (int i = 2; i < NUMS; i += 2)
			if (!t.contains(i))
				System.out.println("Error: find fails for " + i);

		for (int i = 1; i < NUMS; i += 2)
			if (t.contains(i))
				System.out.println("Error: Found deleted item " + i);

		System.out.println("Done.");
	}

}







