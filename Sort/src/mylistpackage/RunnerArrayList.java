package mylistpackage;

import java.util.Date;
import java.util.Random;

public class RunnerArrayList {
	
	
		private static ArrayListUnsorted<Integer> mylist;//array list
		private static Random myrandom;
		final private static int LOW = 25000;
		final private static int MED = 100000;
		final private static int HIMED = 250000;
		final private static int HI = 500000;
		
		public static void sortInsertionSorted(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			for (int i = 1; i <= capacity; i++)
				mylist.insert(i);
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myinsertion();
			//System.out.println(mylist.toString());
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		public static void sortInsertionOppositeSorted(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			for (int i = capacity; i > 0; i--)
				mylist.insert( i );
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myinsertion();
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);    
	        System.out.println(capacity + "\t" + totmylistTime);    		
		}
		
		public static void sortInsertionRandom(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			int r;
			myrandom = new Random();
			for (int i = 1; i <= capacity; i++) {
				r = myrandom.nextInt();
				mylist.insert(r);
			}
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myinsertion();
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		public static void sortSelectionSorted(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			for (int i = 1; i <= capacity; i++)
				mylist.insert(i);
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myselection();						
			//System.out.println(mylist.toInteger());
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		public static void sortSelectionOppositeSorted(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			for (int i = capacity; i > 0; i--)
				mylist.insert(i);
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myselection();						
			//System.out.println(mylist.toInteger());
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);    
	        System.out.println(capacity + "\t" + totmylistTime);    		
		}
		
		public static void sortSelectionRandom(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			int r;
			myrandom = new Random();
			for (int i = 1; i <= capacity; i++) {
				r = myrandom.nextInt();
				mylist.insert(r);
			}
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myselection();					
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		public static void sortQuickSorted(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			for (int i = 1; i <= capacity; i++)
				mylist.insert(i);
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myquick();						
			//System.out.println(mylist.toInteger());
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		public static void sortQuickOppositeSorted(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			for (int i = capacity; i > 0; i--)
				mylist.insert(i);
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myquick();
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);    
	        System.out.println(capacity + "\t" + totmylistTime);    		
		}
		
		public static void sortQuickRandom(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			int r;
			myrandom = new Random();
			for (int i = 1; i <= capacity; i++) {
				r = myrandom.nextInt();
				mylist.insert(r);
			}
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myquick();
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		public static void sortMergeSorted(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			for (int i = 1; i <= capacity; i++)
				mylist.insert(i);
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.mymerge();						
			//System.out.println(mylist.toInteger());
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		public static void sortMergeOppositeSorted(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			for (int i = capacity; i > 0; i--)
				mylist.insert(i);
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.mymerge();	
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);    
	        System.out.println(capacity + "\t" + totmylistTime);    		
		}
		
		public static void sortMergeRandom(int capacity) {
			mylist = new ArrayListUnsorted<Integer>(capacity);
			int r;
			myrandom = new Random();
			for (int i = 1; i <= capacity; i++) {
				r = myrandom.nextInt();
				mylist.insert(r);
			}
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.mymerge();	
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		public static void main(String[] args) {
			System.out.println("Number of elements:\tTime to sort (ms):");
			System.out.println("Label: insertion sort of sorted data");
			for (int i = 0; i < 3; i++) {
				sortInsertionSorted(LOW);
				sortInsertionSorted(MED);
				sortInsertionSorted(HIMED);
				sortInsertionSorted(HI);
			}
			System.out.println("Label: insertion sort of data sorted in opposite order");
			for (int i = 0; i < 3; i++) {
				sortInsertionOppositeSorted(LOW);
				sortInsertionOppositeSorted(MED);
				sortInsertionOppositeSorted(HIMED);
				sortInsertionOppositeSorted(HI);
			}
			System.out.println("Label: insertion sort of random data");
			for (int i = 0; i < 3; i++) {
				sortInsertionRandom(LOW);
				sortInsertionRandom(MED);
				sortInsertionRandom(HIMED);
				sortInsertionRandom(HI);
			}
			System.out.println("Label: selection sort of sorted data");
			for (int i = 0; i < 3; i++) {
				sortSelectionSorted(LOW);
				sortSelectionSorted(MED);
				sortSelectionSorted(HIMED);
				sortSelectionSorted(HI);
			}
			System.out.println("Label: selection sort of data sorted in opposite order");
			for (int i = 0; i < 3; i++) {
				sortSelectionOppositeSorted(LOW);
				sortSelectionOppositeSorted(MED);
				sortSelectionOppositeSorted(HIMED);
				sortSelectionOppositeSorted(HI);
			}
			System.out.println("Label: selection sort of random data");
			for (int i = 0; i < 3; i++) {
				sortSelectionRandom(LOW);
				sortSelectionRandom(MED);
				sortSelectionRandom(HIMED);
				sortSelectionRandom(HI);
			}
			System.out.println("Label: quick sort of sorted data");
			for (int i = 0; i < 3; i++) {
				sortQuickSorted(LOW);
				sortQuickSorted(MED);
				sortQuickSorted(HIMED);
				sortQuickSorted(HI);
			}
			System.out.println("Label: quick sort of data sorted in opposite order");
			for (int i = 0; i < 3; i++) {
				sortQuickOppositeSorted(LOW);
				sortQuickOppositeSorted(MED);
				sortQuickOppositeSorted(HIMED);
				sortQuickOppositeSorted(HI);
			}
			System.out.println("Label: quick sort of random data");
			for (int i = 0; i < 3; i++) {
				sortQuickRandom(LOW);
				sortQuickRandom(MED);
				sortQuickRandom(HIMED);
				sortQuickRandom(HI);
			}
			System.out.println("Label: merge sort of sorted data");
			for (int i = 0; i < 3; i++) {
				sortMergeSorted(LOW);
				sortMergeSorted(MED);
				sortMergeSorted(HIMED);
				sortMergeSorted(HI);
			}
			System.out.println("Label: merge sort of data sorted in opposite order");
			for (int i = 0; i < 3; i++) {
				sortMergeOppositeSorted(LOW);
				sortMergeOppositeSorted(MED);
				sortMergeOppositeSorted(HIMED);
				sortMergeOppositeSorted(HI);
			}
			System.out.println("Label: merge sort of random data");
			for (int i = 0; i < 3; i++) {
				sortMergeRandom(LOW);
				sortMergeRandom(MED);
				sortMergeRandom(HIMED);
				sortMergeRandom(HI);
			}
		}

}
