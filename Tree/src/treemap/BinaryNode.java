package treemap;

//Basic node stored in unbalanced binary search trees
//Note that this class is not accessible outside
//of this package.

class BinaryNode<AnyKey, AnyValue> {

	// Constructor
	BinaryNode(AnyKey theElement) {
		key = theElement;
		left = right = null;
		value = null;
	}

	// Constructor
	BinaryNode(AnyKey theElement, AnyValue theValue) {
		this(theElement);
		this.value = theValue;
	}

	// Data; accessible by other package routines
	AnyKey key; // The data in the node
	BinaryNode<AnyKey, AnyValue> left; // Left child
	BinaryNode<AnyKey, AnyValue> right; // Right child
	AnyValue value;
}
