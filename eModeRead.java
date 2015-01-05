package edu.buffalo.cse.irf14;

import java.util.ArrayList;

public class eModeRead {
	String query;
	String queryID;
	ArrayList<String> orderedResults = new ArrayList<String>();
	
	
	public eModeRead(String queryID, String query) {
		this.queryID = queryID;
		this.query = query;
	}
	
	public void addToArray(String fileID, String relevance) {
		String arrayInput = fileID + relevance;
		orderedResults.add(arrayInput);
	}
	
	public ArrayList<String> getOrderedResults() {
		return orderedResults;
	}
	
}