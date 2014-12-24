package edu.buffalo.cse.irf14.index;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Postings implements Serializable
{
	private int noOfOccurences = 1;
	private int fileMappedID;
	private HashMap<String, ArrayList<Integer>> tfMap = new HashMap<String, ArrayList<Integer>>();
	
	public Postings(int fileID)
	{
		this.fileMappedID = fileID; 
	}
	
	public int getOccurences()
	{
		return this.noOfOccurences;
	}
	
	public void incrementOccurences()
	{
		noOfOccurences++;
	}
	
	public int getFileID()
	{
		return this.fileMappedID;
	}
	
	public void setOccurences(int occur)
	{
		this.noOfOccurences = occur;
	}
	
	public void setTfMap(String term, int position){
		// if the tfMap contains the key, then just add the position to the Arraylist
		// else put a new element into the hashmap which contains the (term, position)
		if (this.tfMap.containsKey(term)){
			
			ArrayList<Integer> tempList = this.tfMap.get(term);
			tempList.add(position);
			this.tfMap.put(term, tempList);
			
		} else {
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			tempList.add(position);
			this.tfMap.put(term, tempList);
		}
	}
	
	/**
	 * method to set the tfMap for a phrase after manipulation
	 * @param term
	 * @param position
	 */
	public void setTfMapForPhrase(String term, ArrayList<Integer> position) {
		this.tfMap = new HashMap<String, ArrayList<Integer>>();
		this.tfMap.put(term, position);
	}
	
	/**
	 * Overloaded method to put all the elements into this Hashmap
	 * @param mapToPut
	 */
	public void setTfMap(HashMap<String, ArrayList<Integer>> mapToPut){
		this.tfMap.putAll(mapToPut);
	}
	/**
	 * METHOD THAT CLEARS THE MAP WHEN THE RESULT OF AN EVALUATION IS 0
	 */
	public void clearMap(){
		this.tfMap = new HashMap<String,ArrayList<Integer>>();	// CLEARING THE MAP
	}
	
	
	public HashMap<String, ArrayList<Integer>> getTfMap(){
		return this.tfMap;
	}
	
	
}