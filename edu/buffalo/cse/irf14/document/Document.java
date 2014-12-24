/**
 * 
 */
package edu.buffalo.cse.irf14.document;

import java.util.HashMap;

/**
 * @author nikhillo
 * Wrapper class that holds {@link FieldNames} to value mapping
 */
public class Document {
	//Sample implementation - you can change this if you like
	private HashMap<FieldNames, String[]> map;
	private int docLength = 0;
	
	/**
	 * SETTER method for the document length (set this during the parsing stage itself)
	 * @param docLength
	 */
	public void setDocLength(int docLength){
		this.docLength = docLength;
	}
	
	/**
	 * GETTER method for the document length
	 * @return - the length of the respective document
	 */
	public int getDocLength(){
		return this.docLength;
	}
	
	/**
	 * Default constructor
	 */
	public Document() {
		map = new HashMap<FieldNames, String[]>();
	}
	
	/**
	 * Method to set the field value for the given {@link FieldNames} field
	 * @param fn : The {@link FieldNames} to be set
	 * @param o : The value to be set to
	 */
	public void setField(FieldNames fn, String... o) {
		map.put(fn, o);
	}
	
	/**
	 * Method to get the field value for a given {@link FieldNames} field
	 * @param fn : The field name to query
	 * @return The associated value, null if not found
	 */
	public String[] getField(FieldNames fn) {
		return map.get(fn);
	}
}
