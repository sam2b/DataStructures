package treemap;

//BinarySearchTree class
//
//CONSTRUCTION: with no initializer
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//void remove( x )       --> Remove x
//void removeMin( )      --> Remove minimum item
//Comparable find( x )   --> Return item that matches x
//Comparable findMin( )  --> Return smallest item
//Comparable findMax( )  --> Return largest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Exceptions are thrown by insert, remove, and removeMin if warranted

/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 * 
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyKey extends Comparable<? super AnyKey>, AnyValue>
								     implements MyTreeMap<AnyKey, AnyValue> {

	/** The tree root. */
	protected BinaryNode<AnyKey, AnyValue> root;

	/**
	 * Construct the tree.
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Insert into the tree.
	 * @param x the item to insert.
	 * @throws DuplicateItemException if x is already present.
	 */
	public void insert(AnyKey x) {
		root = insert(x, root);
	}

    /**
     * Removes a key-value pair from the tree.
     * @param x is the key to be removed
     */
	@Override
	public void remove(AnyKey x) {
		root = remove(x, root);
	}

	/**
	 * Remove minimum item from the tree.
	 * @throws ItemNotFoundException if tree is empty.
	 */
	public void removeMin() {
		root = removeMin(root);
	}

	/**
	 * Find the smallest item in the tree.
	 * @return smallest item or null if empty.
	 */
	public AnyKey findMin() {
		return elementAt(findMin(root));
	}

	/**
	 * Find the largest item in the tree.
	 * @return the largest item or null if empty.
	 */
	public AnyKey findMax() {
		return elementAt(findMax(root));
	}

	/**
	 * Find an item in the tree.
	 * @param x the item to search for.
	 * @return the matching item or null if not found.
	 */
	public AnyKey find(AnyKey x) {
		return elementAt(find(x, root));
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Internal method to get key field.
	 * @param t the node.
	 * @return the key field or null if t is null.
	 */
	private AnyKey elementAt(BinaryNode<AnyKey, AnyValue> t) {
		return t == null ? null : t.key;
	}

	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws DuplicateItemException if x is already present.
	 */
	protected BinaryNode<AnyKey, AnyValue> insert(AnyKey x, BinaryNode<AnyKey, AnyValue> t) {
		if (t == null)
			t = new BinaryNode<AnyKey, AnyValue>(x);
		else if (x.compareTo(t.key) < 0)
			t.left = insert(x, t.left);
		else if (x.compareTo(t.key) > 0)
			t.right = insert(x, t.right);
		else {
			throw new IllegalArgumentException(x.toString()); // duplicate
		}
		return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws ItemNotFoundException if x is not found.
	 */
	protected BinaryNode<AnyKey, AnyValue> remove(AnyKey x, BinaryNode<AnyKey, AnyValue> t) {
		if (t == null)
			throw new IllegalArgumentException(x.toString());
		if (x.compareTo(t.key) < 0)
			t.left = remove(x, t.left);
		else if (x.compareTo(t.key) > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.key = findMin(t.right).key;
			t.right = removeMin(t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return t;
	}

	/**
	 * Internal method to remove minimum item from a subtree.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws ItemNotFoundException if t is empty.
	 */
	protected BinaryNode<AnyKey, AnyValue> removeMin(BinaryNode<AnyKey, AnyValue> t) {
		if (t == null)
			throw new IllegalArgumentException();
		else if (t.left != null) {
			t.left = removeMin(t.left);
			return t;
		} else
			return t.right;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the smallest item.
	 */
	protected BinaryNode<AnyKey, AnyValue> findMin(BinaryNode<AnyKey, AnyValue> t) {
		if (t != null)
			while (t.left != null)
				t = t.left;

		return t;
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<AnyKey, AnyValue> findMax(BinaryNode<AnyKey, AnyValue> t) {
		if (t != null)
			while (t.right != null)
				t = t.right;

		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the tree.
	 * @return node containing the matched item.
	 */
	private BinaryNode<AnyKey, AnyValue> find(AnyKey x, BinaryNode<AnyKey, AnyValue> t) {
		while (t != null) {
			if (x.compareTo(t.key) < 0)
				t = t.left;
			else if (x.compareTo(t.key) > 0)
				t = t.right;
			else
				return t; // Match
		}

		return null; // Not found
	}

	/////// START SAM CODE ///////////////////////////////////////////////////

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
	private String treeToString(BinaryNode<AnyKey, AnyValue> t) {
		//StringBuilder s = new StringBuilder();
		String string = "";
		if (t != null) {
			string += treeToString(t.left);
			string += t.key + "=" + t.value + ", ";
			string += treeToString(t.right);
		}
		return string;
	}

    /**
     * Insert into the tree.
     * @param x the key to insert.
     * @param y the value to insert.
     */
	@Override
	public void put(AnyKey x, AnyValue y) {
		//this.insert(x, new BinaryNode<AnyKey, AnyValue>(x));
		root = putMe(x, y, root);
	}

	/**
	 * Helper method for the put() method, used recursively.
	 * @param x the key to put.
	 * @param y the value to put.
	 * @param t the root node for recursively traversing the tree.
	 * @return the node that was put.
	 */
	private BinaryNode<AnyKey, AnyValue> putMe(AnyKey x, AnyValue y, 
									BinaryNode<AnyKey, AnyValue> t) {
		if (t == null)
			t = new BinaryNode<AnyKey, AnyValue>(x, y);
		else if (x.compareTo(t.key) < 0)
			t.left = putMe(x, y, t.left);
		else if (x.compareTo(t.key) > 0)
			t.right = putMe(x, y, t.right);
		else {
			t.value = y; // Update the existing node with the new value.
		}
		return t;
	}

    /**
     * Returns a value associated with a key.
     * @param x is the key
     * @return the value associated with the key
     */
	@Override
	public AnyValue get(AnyKey x) {
		BinaryNode<AnyKey, AnyValue> node = this.find(x, root);
		AnyValue value = null;
		
		if (node != null)
			value = node.value;
		
		return value;
	}

	// The remove() method already exists, so I just annotated with @Override.

	///////// END SAM
	///////// CODE//////////////////////////////////////////////////////////

	// Test program
	public static void main(String[] args) {
		BinarySearchTree<Integer, Object> t = new BinarySearchTree<>();
		final int NUMS = 4000;
		final int GAP = 37;

		System.out.println("Checking... (no more output means success)");

		for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
			t.insert(i);
			//t.put(i, 99);

		for (int i = 1; i < NUMS; i += 2)
			t.remove(i);

		if (t.findMin() != 2 || t.findMax() != NUMS - 2)
			System.out.println("FindMin or FindMax error!");

		for (int i = 2; i < NUMS; i += 2)
			if (t.find(i) != i)
				System.out.println("Find error1!");

		for (int i = 1; i < NUMS; i += 2)
			if (t.find(i) != null)
				System.out.println("Find error2!");

		System.out.println("Done.");
	}

}
