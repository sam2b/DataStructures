package mylistpackage;

import java.util.Date;
import java.util.Random;

public class RunnerLinkedList {
	
		private static LinkedListUnsorted<Integer> mylist;
		private static Random myrandom;
        final private static int LOW = 25000;
		final private static int MED = 100000;
		final private static int HIMED = 250000;
		final private static int HI = 500000;
		final private static int MAX_ITERATION = 3;
		
		public static void sortInsertionSorted(int capacity) {
			mylist = new LinkedListUnsorted<Integer>();
			for (int i = 1; i <= capacity; i++)
				mylist.insert( i );
					
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
			mylist = new LinkedListUnsorted<Integer>();
			for (int i = capacity; i > 0; i--)
				mylist.insert(i);
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
			mylist = new LinkedListUnsorted<Integer>();
			int r;
			myrandom = new Random();
			for (int i = 1; i <= capacity; i++) {
				r = myrandom.nextInt(); //getRandomSmall(capacity);
				mylist.insert(r);
			}
			
			// debugging with known offending data in prior buggy code.
/*			mylist.clear();
			mylist.insert(4);
			mylist.insert(7);
			mylist.insert(7);
			mylist.insert(4);
			mylist.insert(2);
			mylist.insert(0);
			mylist.insert(1);
			mylist.insert(4);
			mylist.insert(9);
			mylist.insert(10);
*/
			
/*			mylist.insert(7);
			mylist.insert(1);
			mylist.insert(3);
			mylist.insert(4);
			mylist.insert(2);
			mylist.insert(6);
			mylist.insert(1);
			mylist.insert(1);
			mylist.insert(1);
			mylist.insert(9);
*/
			
/*			mylist.insert(5);
			mylist.insert(2);
			mylist.insert(6);
			mylist.insert(3);
			mylist.insert(2);
			mylist.insert(7);
			mylist.insert(0);
			mylist.insert(4);
			mylist.insert(3);
			mylist.insert(7);
*/			
			
/*			mylist.insert(6);
			mylist.insert(4);
			mylist.insert(1);
			mylist.insert(6);
			mylist.insert(2);
			mylist.insert(1);			
*/
			
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myinsertion();
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		/**
		 * This temporarily gives me smaller and positive random integers to work 
		 * with when confirming a small list is truly being sorted.  
		 * Best used with a max of about 6 or more.  
		 * @author Sam Brendel, sam2b@uw.edu
		 * @param max the maximum quantity or value.
		 * @return a random integer inclusively between 0 and max. 
		 */
		@SuppressWarnings("unused")
		private static int getRandomSmall(int max) {
			int min = 0;
			return myrandom.nextInt(max + 1 - min) + min;
		}
		
		public static void sortSelectionSorted(int capacity) {
			mylist = new LinkedListUnsorted<Integer>();
			for (int i = 1; i <= capacity; i++)
				mylist.insert(i);		
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myselection();						
			//System.out.println(mylist.toString());
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}
		
		public static void sortSelectionOppositeSorted(int capacity) {
			mylist = new LinkedListUnsorted<Integer>();
			for (int i = capacity; i > 0; i--)
				mylist.insert(i);
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myselection();						
			//System.out.println(mylist.toString());
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);    
	        System.out.println(capacity + "\t" + totmylistTime);    		
		}
		
		public static void sortSelectionRandom(int capacity) {
			mylist = new LinkedListUnsorted<Integer>();
			int r;
			myrandom = new Random();
			for (int i = 1; i <= capacity; i++) {
				r = myrandom.nextInt(); // getRandomSmall(capacity);
				mylist.insert(r);
			}
			
			// debugging with known offending data in prior buggy code.
/*			mylist.clear();
			mylist.insert(5);
			mylist.insert(0);
			mylist.insert(10);
			mylist.insert(1);
			mylist.insert(4);
			mylist.insert(7);
			mylist.insert(2);
			mylist.insert(6);
			mylist.insert(5);
			mylist.insert(1);
*/			
			//System.out.println(mylist.toString());
			Date startDate = new Date();
			long startTime = startDate.getTime();
			mylist.myselection();					// change call
			Date finishDate = new Date();
	        long finishTime = finishDate.getTime();
	        long totmylistTime = (finishTime - startTime);   
	        System.out.println(capacity + "\t" + totmylistTime);      		
		}

		public static void main(String[] args) {
			System.out.println("\nNumber of elements:\tTime to sort (ms):");

			System.out.println("Label: insertion sort of sorted data");
			for (int i = 0; i < MAX_ITERATION; i++) {
				sortInsertionSorted(LOW);
				sortInsertionSorted(MED);
				sortInsertionSorted(HIMED);
				sortInsertionSorted(HI);
			}
			
   			System.out.println("Label: insertion sort of data sorted in opposite order");
			for (int i = 0; i < MAX_ITERATION; i++) {
				sortInsertionOppositeSorted(LOW);
				sortInsertionOppositeSorted(MED);
				sortInsertionOppositeSorted(HIMED);
				sortInsertionOppositeSorted(HI);
			}

			System.out.println("Label: insertion sort of random data");
			for (int i = 0; i < MAX_ITERATION; i++) {
				sortInsertionRandom(LOW);
				sortInsertionRandom(MED);
				sortInsertionRandom(HIMED);
				sortInsertionRandom(HI);
			}
            
			System.out.println("Label: selection sort of sorted data");
			for (int i = 0; i < MAX_ITERATION; i++) {
				sortSelectionSorted(LOW);
				sortSelectionSorted(MED);
				sortSelectionSorted(HIMED);
				sortSelectionSorted(HI);
			}
			
			System.out.println("Label: selection sort of data sorted in opposite order");
			for (int i = 0; i < MAX_ITERATION; i++) {
				sortSelectionOppositeSorted(LOW);
				sortSelectionOppositeSorted(MED);
				sortSelectionOppositeSorted(HIMED);
				sortSelectionOppositeSorted(HI);
			}

			System.out.println("Label: selection sort of random data");
			for (int i = 0; i < MAX_ITERATION; i++) {
				sortSelectionRandom(LOW);
				sortSelectionRandom(MED);
				sortSelectionRandom(HIMED);
				sortSelectionRandom(HI);
			}
		
			System.out.println("DONE!");
		}
		
}
