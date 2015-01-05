package edu.buffalo.cse.irf14;
import java.util.LinkedList;


/**
 * Inner class to store various information about a term (Like postingsList, term frequency, etc)
 * @author Girish
 *
 */
class ResultType{
	
	
	public enum IndexType {
		TERM, PLACE, CATEGORY, AUTHOR
	}
	
	public enum QueryType {
		PHRASE, SINGLE
	}
	
	public enum OperatorType {
		AND, OR
	}
	
	public enum ISNot {
		YES, NO
	}

	
}