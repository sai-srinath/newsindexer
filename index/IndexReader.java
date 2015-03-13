/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;



/**
 * @author nikhillo
 * Class that emulates reading data back from a written index
 */
public class IndexReader {
	/**
	 * Default constructor
	 * @param indexDir : The root directory from which the index is to be read.
	 * This will be exactly the same directory as passed on IndexWriter. In case 
	 * you make subdirectories etc., you will have to handle it accordingly.
	 * @param type The {@link IndexType} to read from
	 */
	// HashMaps that get the index as well as Top K terms from disk
	private HashMap<Integer, LinkedList<Postings>> Index;
	private HashMap<String, Integer> Dictionary;
	private HashMap<String, Integer> kIndex;
	private HashMap<String, Integer> fileIDDictionary;
	private HashMap<Integer, String> inverseFileIDDictionary;
	private HashMap<Integer, Integer> docLengthDictionary;
	
	public IndexReader(String indexDir, IndexType type) {
		//TODO
		if (type == IndexType.AUTHOR)
		{
			
			try
			{
				FileInputStream fis = new FileInputStream(indexDir + File.separator + "authorIndex.ser");
				
		        ObjectInputStream ois = new ObjectInputStream(fis);
		        Index = (HashMap<Integer, LinkedList<Postings>>) ois.readObject();
		        Dictionary = (HashMap<String, Integer>) ois.readObject();
		        this.kIndex = (HashMap<String, Integer>) ois.readObject();
		        
		        fis = new FileInputStream(indexDir + File.separator + "fileIDDict.ser");
		        ois = new ObjectInputStream(fis);
		        fileIDDictionary = (HashMap<String, Integer>) ois.readObject();
		        this.inverseFileIDDictionary = (HashMap<Integer, String>) ois.readObject();
		        this.docLengthDictionary = (HashMap<Integer, Integer>) ois.readObject();
		        
		        
		        ois.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
		}
		else if (type == IndexType.TERM)
		{
			
			try
			{
				FileInputStream fis = new FileInputStream(indexDir + File.separator + "termIndex.ser");
				
		        ObjectInputStream ois = new ObjectInputStream(fis);
		        Index = (HashMap<Integer, LinkedList<Postings>>) ois.readObject();
		        Dictionary = (HashMap<String, Integer>) ois.readObject();
		        this.kIndex = (HashMap<String, Integer>) ois.readObject();
		        
		        fis = new FileInputStream(indexDir + File.separator + "fileIDDict.ser");
		        ois = new ObjectInputStream(fis);
		        fileIDDictionary = (HashMap<String, Integer>) ois.readObject();
		        this.inverseFileIDDictionary = (HashMap<Integer, String>) ois.readObject();
		        this.docLengthDictionary = (HashMap<Integer, Integer>) ois.readObject();
		        
		        
		        
		        
		        
		        ois.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
		}
		else if (type == IndexType.PLACE)
		{
			
			try
			{
				FileInputStream fis = new FileInputStream(indexDir + File.separator + "placeIndex.ser");
				
		        ObjectInputStream ois = new ObjectInputStream(fis);
		        Index = (HashMap<Integer, LinkedList<Postings>>) ois.readObject();
		        Dictionary = (HashMap<String, Integer>) ois.readObject();
		        this.kIndex = (HashMap<String, Integer>) ois.readObject();
		        
		        fis = new FileInputStream(indexDir + File.separator + "fileIDDict.ser");
		        ois = new ObjectInputStream(fis);
		        fileIDDictionary = (HashMap<String, Integer>) ois.readObject();
		        this.inverseFileIDDictionary = (HashMap<Integer, String>) ois.readObject();
		        this.docLengthDictionary = (HashMap<Integer, Integer>) ois.readObject();
		        
		        ois.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
		}
		else if (type == IndexType.CATEGORY)
		{
			
			try
			{
				FileInputStream fis = new FileInputStream(indexDir + File.separator + "categoryIndex.ser");
				
		        ObjectInputStream ois = new ObjectInputStream(fis);
		        Index = (HashMap<Integer, LinkedList<Postings>>) ois.readObject();
		        Dictionary = (HashMap<String, Integer>) ois.readObject();
		        this.kIndex = (HashMap<String, Integer>) ois.readObject();
		        
		        fis = new FileInputStream(indexDir + File.separator + "fileIDDict.ser");
		        ois = new ObjectInputStream(fis);
		        fileIDDictionary = (HashMap<String, Integer>) ois.readObject();
		        this.inverseFileIDDictionary = (HashMap<Integer, String>) ois.readObject();
		        this.docLengthDictionary = (HashMap<Integer, Integer>) ois.readObject();
		        
		        
		        ois.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * Get total number of terms from the "key" dictionary associated with this 
	 * index. A postings list is always created against the "key" dictionary
	 * @return The total number of terms
	 */
	public int getTotalKeyTerms() {
		//TODO : YOU MUST IMPLEMENT THIS
		return this.Dictionary.size();
	}
	
	/**
	 * Get total number of terms from the "value" dictionary associated with this 
	 * index. A postings list is always created with the "value" dictionary
	 * @return The total number of terms
	 */
	public int getTotalValueTerms() {
		//TODO: YOU MUST IMPLEMENT THIS
		return this.fileIDDictionary.size();
	}
	
	/**
	 * Method to get the postings for a given term. You can assume that
	 * the raw string that is used to query would be passed through the same
	 * Analyzer as the original field would have been.
	 * @param term : The "analyzed" term to get postings for
	 * @return A Map containing the corresponding fileid as the key and the 
	 * number of occurrences as values if the given term was found, null otherwise.
	 */
	public Map<String, Integer> getPostings(String term) {
		//TODO:YOU MUST IMPLEMENT THIS
		// get the id from the dictionary for the term
		// then get the postings for the term and save them into a hashmap
		// that contains (fileID: noOfOccurences
		
		LinkedList<Postings> postingsList = new LinkedList<Postings>();
		HashMap<String, Integer> postings = new HashMap<String, Integer>();
		int termID;
		String fileID;
		int fileIDCompressed;
		int occurences;
		
		if (this.Dictionary.containsKey(term))
		{
			termID = this.Dictionary.get(term);
			postingsList = this.Index.get(termID);
			for (Postings post:postingsList)
			{
				// get the compressed file ID of the file
				fileIDCompressed = post.getFileID();
				occurences = post.getOccurences();
				// get the main file ID
				fileID = this.inverseFileIDDictionary.get(fileIDCompressed);
				postings.put(fileID, occurences);
			}
			return postings;
		}
		else
		{
			return null;
		}
		
		
		
		
	}
	
	/**
	 * Method to get the top k terms from the index in terms of the total number
	 * of occurrences.
	 * @param k : The number of terms to fetch
	 * @return : An ordered list of results. Must be <=k fr valid k values
	 * null for invalid k values
	 */
	public List<String> getTopK(int k) {
		//TODO YOU MUST IMPLEMENT THIS
		// junk k value , return null
		ArrayList<String> topKList = new ArrayList<String>();
		if (k <= 0)
		{	
			return null;	
		}
		else {
			QueryValueComparator compPattern = new QueryValueComparator(this.kIndex);
			TreeMap<String, Integer> topKTree = new TreeMap<String, Integer>(compPattern);
			topKTree.putAll(this.kIndex);

			int i = 1;
			for (Entry<String, Integer> etr : topKTree.entrySet()) 
			{
				if (i > k)	break;
				else 
				{
					topKList.add(etr.getKey());
					i++;
				}
			}
			return topKList;
		}
		
	}
	
	/**
	 * Method to implement a simple boolean AND query on the given index
	 * @param terms The ordered set of terms to AND, similar to getPostings()
	 * the terms would be passed through the necessary Analyzer.
	 * @return A Map (if all terms are found) containing FileId as the key 
	 * and number of occurrences as the value, the number of occurrences 
	 * would be the sum of occurrences for each participating term. return null
	 * if the given term list returns no results
	 * BONUS ONLY
	 */
	public Map<String, Integer> query(String...terms) {
		//TODO : BONUS ONLY
		// first check if all the terms are in the dictionary, else return null 
		ArrayList<LinkedList<Postings>> arrayOfTerms = new ArrayList<LinkedList<Postings>>();
		LinkedList<Postings> postingsList = new LinkedList<Postings>();
		LinkedList<Postings> toConvertMap ;
		LinkedList<Postings> elementToCompare;
		LinkedList<Postings> tempMap;
		Postings tempPostings;
		
		HashMap<String, Integer> mapToReturn = new HashMap<String, Integer>();
		int termID;
		int fileIDCompressed;
		int occurences;
		String fileID;
		
		
		
		
		System.out.println(Arrays.toString(terms));
		// checking if all the terms are in the index, else return null
		for (String term:terms)
		{
			if(Dictionary.containsKey(term))
			{
				
				continue;
				
			}
			else
			{
				return null;
			}
		}
		
		// retrieve the postings list for all of the terms and store them in a array list
		for (String term:terms)
		{
			termID = this.Dictionary.get(term);
			postingsList = this.Index.get(termID);
			arrayOfTerms.add(postingsList);
		}
		
		// if array is of only 1 term, then return its postings list
		if (arrayOfTerms.size() == 1)
		{
			for (Postings p:postingsList)
			{	
				fileIDCompressed = p.getFileID();
				occurences = p.getOccurences();
				// get the main file ID
				fileID = this.inverseFileIDDictionary.get(fileIDCompressed);
				mapToReturn.put(fileID, occurences);
			}
			return mapToReturn;
		}
		else // more than one term exists in and query
		{	
			// retrieve first elements postings list
			toConvertMap = arrayOfTerms.get(0);
			int sumOfOccurences = 0;
			
			for (int i=1; i<arrayOfTerms.size(); i++)
			{	
				
				elementToCompare = arrayOfTerms.get(i);
				
				
				
				tempMap = new LinkedList<Postings>();
				
				for (Postings xyz:elementToCompare)
				{
					for (Postings temp:toConvertMap)
					{
						if (temp.getFileID() == xyz.getFileID())
						{	
							sumOfOccurences = temp.getOccurences() + xyz.getOccurences();
							tempPostings = temp;
							tempPostings.setOccurences(sumOfOccurences);
							System.out.println(sumOfOccurences);
							tempMap.add(tempPostings);
							break;
						}
					}
					
				}
				if (tempMap.size() == 0)
				{
					return null;
				}
				else
				{
					toConvertMap = tempMap;
					
				}
				
			}
			
			
			
			for (Postings p:toConvertMap)
			{
				fileID = this.inverseFileIDDictionary.get(p.getFileID());
				occurences = p.getOccurences();
				mapToReturn.put(fileID, occurences);
			}
			
			return mapToReturn;
		}
		
		
		
		
		

	}
	
	
	class QueryValueComparator implements Comparator<String> {
		
	    Map<String, Integer> base;
	    public QueryValueComparator(Map<String, Integer> map) {
	        this.base = map;
	    }

	       
	    public int compare(String a, String b) {
	        Integer x = base.get(a);
	        Integer y = base.get(b);
	        if (x.equals(y)) {
	            return b.compareTo(a);
	        }
	        return y.compareTo(x);
	    }
    }
	
	/**
	 * Method gets you the file document length
	 * FOR TESTING PURPOSE ONLY
	 * @param fileID - ID of the file (7 digit number)
	 * @return
	 */
	public int getDocLength(String fileID){
		
		int mappedID = this.fileIDDictionary.get(fileID);
		return this.docLengthDictionary.get(mappedID);
		
	}
	
	/**
	 * Method that gets you the postings list and the positions of the terms
	 */
	public void getPostingsTF(String term) {
		//TODO:YOU MUST IMPLEMENT THIS
		// get the id from the dictionary for the term
		// then get the postings for the term and save them into a hashmap
		// that contains (fileID: noOfOccurences
		
		LinkedList<Postings> postingsList = new LinkedList<Postings>();
		HashMap<String, ArrayList<Integer>> postings = new HashMap<String, ArrayList<Integer>>();
		int termID;
		String fileID;
		int fileIDCompressed;
		HashMap<String, ArrayList<Integer>> termPositions;
		ArrayList<Integer> tempList;
		
		if (this.Dictionary.containsKey(term))
		{
			termID = this.Dictionary.get(term);
			postingsList = this.Index.get(termID);
			for (Postings post:postingsList)
			{
				// get the compressed file ID of the file
				fileIDCompressed = post.getFileID();
				termPositions = post.getTfMap();
				// get the main file ID
				fileID = this.inverseFileIDDictionary.get(fileIDCompressed);
				System.out.println(fileID);
				for (String key: termPositions.keySet()){
					tempList = termPositions.get(key);
					for (int temp:tempList){
						System.out.println(temp);
					}
				}
			}
			
		}
		
		
	}
}
