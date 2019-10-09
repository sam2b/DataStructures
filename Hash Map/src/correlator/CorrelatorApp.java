/*
 * Assignment 4, brendel_pr4
 */

package correlator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Driver class.
 * @author Sam Brendel
 * @version 5.27C
 */
public class CorrelatorApp {

	/**
	 * Driver method.
	 * @param args not used.
	 */
	public static void main(String[] args) {

	    File testFile = new File("myfiles//sam-the-big-ham.txt"); // TODO remove this.
		File file1 = new File("myfiles//hamlet.txt");
		File file2 = new File("myfiles//the-new-atlantis.txt");
		File outFile = new File("myfiles//stats.csv");
		Scanner scanner1 = null, scanner2 = null;
		//Scanner testScanner = null; // debugging
		FileWriter writer = null;
		final String header = "words,count,frequency";
		
		try {
			//testScanner = new Scanner(testFile); // debugging
			scanner1 = new Scanner(file1);
			scanner2 = new Scanner(file2);
			writer = new FileWriter(outFile, true);
		} catch (IOException e) {
            System.err.println("woopsy!");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		//HashMap<String, Integer> testTable = fillTable(testScanner); // debugging
		HashMap<String, Integer> table1 = fillTable(scanner1);
		HashMap<String, Integer> table2 = fillTable(scanner2);

		//HeapPriorityQueue<String, Integer> testHeap = fillHeap(testTable); // debugging
		HeapPriorityQueue<String, Integer> heap1 = fillHeap(table1);
		HeapPriorityQueue<String, Integer> heap2 = fillHeap(table2);  

		// Data for the Jaccard Index computation.
		double intersection = table1.intersection(table2);
		double union = table1.union(table2); // - intersection;
		double jaccardIndex = intersection / union;
		HashMap<String, Double> freqTable1 = normalizedFrequency(table1);
		HashMap<String, Double> freqTable2 = normalizedFrequency(table2);
		String temp = "";
		
		wipeFile(outFile); // A new data file is created each time you run this.
		
		// Write report to myfiles/stats.csv
		try {
			// Writer's Signature for Hamlet
			writer.write(file1.getName() + "\n");
			writer.write(header + "\n");
			for (HeapEntry entry : heap1.getTopTen()) {
				temp = entry.toString().replace('[', ' ').replace(']', ' ');
				temp += "," + freqTable1.get(entry.getKey()) + "\n";
				writer.write(temp);
			}
			writer.write("\n");

			// Writer's Signature for Atlantis
			writer.write(file2.getName() + "\n");
			writer.write(header + "\n");
			for (HeapEntry entry : heap2.getTopTen()) {
				temp = entry.toString().replace('[', ' ').replace(']', ' ');
				temp += "," + freqTable2.get(entry.getKey()) + "\n";
				writer.write(temp);
			}
			writer.write("\n");

			writer.write("intersection size," + intersection + "\n");
			writer.write("union size," + union + "\n");
			writer.write("correlation," + jaccardIndex + "\n");
			writer.close();
			
		} catch (IOException e) {
            System.err.println("woopsy!");
            System.err.println(e.getMessage());
			e.printStackTrace();
		}

		
		//////// WHITE BOX TESTS //////////////////////////////////////////////////////////////////////
//		test2_peek(heap1, "Hamlet");
//		test2_peek(heap2, "Atlantis");
////		test2_peek(testHeap, "TestHeap");
////		System.out.println(testHeap.getTopTen());
//		System.out.println(heap1.getTopTen());
//		System.out.println(heap2.getTopTen());
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		// Clean up.
		scanner1.close();
		scanner2.close();
		
		try {
			writer.close();
		} catch (IOException e) {
            System.err.println("woopsy!");
            System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Clobbers the file to be zero bytes.  Does not delete the file.  Just clobber.
	 * @param theFile the file to clobber.
	 */
	private static void wipeFile(final File theFile) {
		FileWriter writer;
		try {
			writer = new FileWriter(theFile, false);
			writer.write("");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper method to fill the table with data from the provided scanner.
	 * @param map the table to fill.
	 * @param scanner the scanner to read from.
	 */
	private static HashMap<String, Integer> fillTable(final Scanner scanner) {
		String word;
		Integer wordCount = null;
		final HashMap<String, Integer> map = new HashMap<>();
		
		while(scanner.hasNext()) {
			word = scanner.next();
			word = word.replaceAll("\\W", "").toLowerCase(); // Discard all non-word characters.
			wordCount = map.get(word);

			// Only keep words that are at least 3 characters long
			if (word != null && word.length() > 2) {
				wordCount = (wordCount == null) 
						? 1 
						: wordCount.intValue() + 1;
				map.put(word, wordCount);
			}
		}
		return map;
	}
	
	/**
	 * Creates a map of each words' normalized frequency, but excludes outliers of a frequency <0.0001 or >0.01.<BR>
	 * Used for generating a report.
	 * @param theTable the table with the word and count of occurrence.
	 * @return the map of normalized frequencies, which may be smaller than the original table.
	 */
	private static HashMap<String, Double> normalizedFrequency(final HashMap<String, Integer> theTable) {
		HashMap<String, Double> map = new HashMap<>();

		Double freq;
		Double tableSize = (double)theTable.size();
		String word = "";
		theTable.iteratorReset();
		while(theTable.hasNext()) {
			word = theTable.next();
			freq = (double)theTable.get(word) / tableSize;
			if (freq >= 0.0001 || freq <= 0.01) {
				map.put(word, freq);
			}
		}
		
		return map;
	}
	
	/**
	 * Creates a heap priority queue from the table HashMap provided.
	 * @param theTable the HashMap object.
	 * @return the heap.
	 */
	private static HeapPriorityQueue<String, Integer> fillHeap(final HashMap<String, Integer> theTable) {
		String keyString = null;
		HeapPriorityQueue<String, Integer> heap = new HeapPriorityQueue<>();
		theTable.iteratorReset();
		while(theTable.hasNext()) {
			keyString = theTable.next();
			heap.put(new HeapEntry(keyString, theTable.get(keyString)));
		}
		return heap;
	}
	
	/////////// WHITE BOX TESTS BELOW. //////////////////////////////////////////////////////////////////////////
	
	/**
	 * White box testing to ensure peek is the entry with the greatest frequency.
	 * @param theHeap the heap.
	 * @param theName the name of the this data set.
	 */
	private static void test2_peek(final HeapPriorityQueue<String, Integer> theHeap, final String theName) {
		System.out.println(theName + " peek = " + theHeap.peek());
		System.out.println("");
		
		/*
		 *            hamlet.txt peek = [the, 1162]
			the-new-atlantis.txt peek = [the, 886]
		 */
	}

}
