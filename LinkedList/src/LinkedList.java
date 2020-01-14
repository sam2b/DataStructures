import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class LinkedList<T> {
	
	private Node head, tail, current, temp;
	private int size;

	// Constructor.
	public LinkedList() {
		size = 0;
	}

	// Constructor.
	public LinkedList(T t) {
		head = tail = new Node(t);
		size = 1;
	}

	public static void main(String[] args) {
//		LinkedList<Point> linkedListA = new LinkedList<Point>();
//		linkedListA.add(new Point());
//		System.out.println(linkedListA.get(0).getClass().toString());
//		linkedListA.add(new Point());
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (head == null);
	}

	public boolean contains(T t) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public T[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public T[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean add(T t) {
		if (size == 0) head = tail = new Node(t);
		else {
			tail.next = new Node(t);
			tail = tail.next;			
		}
		size++;
		return true;
	}

	public boolean add(int index, T t) {
		current = head;
		if (index >= size || index < 0) return false;
		else {
			if (index == 0) {
				temp = head.next;
				head = new Node(t);
				head.next = temp;
			} else if (size == 1) {
				head.next = new Node(t);
				tail = head.next;
			} else {
				for(int i=0; i<index-1; i++) {
					temp = current.next;
					current.next = new Node(t);
					current.next.next = temp;
				}
			}
			size++;
		}
		return true;
	}

	public boolean remove(T t) {
		current = head;
		if (size == 1) {
			clear();
			return true;
		} else {
			for(int i=0; i < size-1; i++) {
				if(current.next.data.equals(t)) {
					temp = current.next;
					current.next = temp.next;
					temp = null;
					size--;
					return true; // stop at the first occurrence.
				}
			}
		}
		return false;
	}

	public boolean containsAll(Collection<T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(Collection<T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(int index, Collection<T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection<T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection<T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	// Ensure every object is null.
	public void clear() {
		if (size == 1) {
			head = tail = null;
			size = 0;
		}
		else if (size > 1) {
			current = head;
			temp = head.next;
			while (current.next != null) {
				current = null;
				current = temp;
				temp = current.next;
			}
			current = head = null;
			size = 0;
		}
	}

	// Get the Kth element.
	public T get(int index) {
		current = head;
		if (size == 0) return null;
		else if (index == 0) return head.data;
		else {
			for(int i=0; i<index; i++) {
				current = current.next;
			}
			return current.data;
		}
	}

	public T set(int index, T t) {
		current = head;
		T oldData = null;
		if (size == 0 && (index > 0 || index < 0)) return null;
		else if (index == 0) {
			oldData = head.data;
			head.data = t;
		}
		else {
			for(int i=0; i<index; i++) {
				current = current.next;
			}
			oldData = current.data;
			current.data = t;
		}
		return oldData;
	}

	public T remove(int index) {
		current = head;
		T oldData = null;
		if (size == 0) return null;
		else if (size == 1) {
			oldData = head.data;
			head = null;
		} else {
			for(int i=0; i<index-1; i++) {
				current = current.next;
			}
			temp = current.next;
			current.next = temp.next;
			temp = null;
		}
		size--;
		return oldData;
	}

	public int indexOf(T t) {
		current = head;
		int index = 0;
		while (index < size) {
			if (current.equals(t)) return index; 
			current = current.next;
			index++;
		}
		return -1;
	}

	public int lastIndexOf(T t) {
		current = head;
		int index = -1;
		for(int i=0; index < size; i++) {
			if (current.equals(t)) {
				index = i;
			}
			current = current.next;
			index++;
		}
		return index;
	}

	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<T> subList(int fromIndex, int toIndex) {
		LinkedList<T> list = new LinkedList<T>();
		if (fromIndex > toIndex) return null;
		else if (fromIndex == toIndex) return new LinkedList<T>(get(fromIndex));
		else {
			// Make the current be the Node at fromIndex.
			for (int i=0; i <= fromIndex; i++) {
				current = current.next;
			}
			// The add
			for (int i = 0; i <= toIndex; i++) {
				list.add(current.data);
				current = current.next;
			}
		}
		return list;
	}
	
	@Override
	public String toString() {
		current = head;
		StringBuilder sb = new StringBuilder();
		sb.append("[size=");
		sb.append(size);
		sb.append(", ");
		for (int i=0; i <= size-1; i++) { // Fence post.
			sb.append(current.toString());
			sb.append(", ");
			current = current.next;
		}
		sb.append(current.toString()); // The last append will not have a comma.
		sb.append("]");
		return sb.toString();
	}
	
	
	// Inner Class.
	private class Node {
		Node next;
		T data;
		// Constructor.
		protected Node(T t) {
			this.data = t;
			this.next = null;
		}
		
		@Override
		public String toString() {
			return this.data.toString();
		}
	}

}
