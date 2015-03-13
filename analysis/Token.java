/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

/**
 * @author nikhillo
 * This class represents the smallest indexable unit of text.
 * At the very least it is backed by a string representation that
 * can be interchangeably used with the backing char array.
 * 
 * You are encouraged to add more data structures and fields as you deem fit. 
 */
public class Token {
	//The backing string representation -- can contain extraneous information
	private String termText;
	//The char array backing termText
	private char[] termBuffer;
	private boolean isSentenceBoundary = false;
	private boolean isFirstToken = false;
	
	/**
	 * Method to set the termText to given text.
	 * This is a sample implementation and you CAN change this
	 * to suit your class definition and data structure needs.
	 * @param text
	 */
	protected void setTermText(String text) {
		termText = text;
		termBuffer = (termText != null) ? termText.toCharArray() : null;
		if (text.matches("(([^\\.]+)(\\.|\\?|!)+)"))
		{
			this.isSentenceBoundary = true;
		}
	}
	
	/**
	 * Getter for termText
	 * This is a sample implementation and you CAN change this
	 * to suit your class definition and data structure needs.
	 * @return the underlying termText
	 */
	public String getTermText() {
		return termText;
	}
	
	/**
	 * Method to set the termBuffer to the given buffer.
	 * This is a sample implementation and you CAN change this
	 * to suit your class definition and data structure needs.
	 * @param buffer: The buffer to be set
	 */
	protected void setTermBuffer(char[] buffer) {
		termBuffer = buffer;
		termText = new String(buffer);
	}
	
	/**
	 * Getter for the field termBuffer
	 * @return The termBuffer
	 */
	protected char[] getTermBuffer() {
		return termBuffer;
	}
	
	/**
	 * Method to merge this token with the given array of Tokens
	 * You are free to update termText and termBuffer as you please
	 * based upon your Token implementation. But the toString() method
	 * below must return whitespace separated value for all tokens combined
	 * Also the token order must be maintained.
	 * @param tokens The token array to be merged
	 */
	protected void merge(Token...tokens) {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		// if there are no tokens , do NOTHING. else, check if there are junk tokens having only
		// "", else update termText
		if (tokens == null){
			return;
		}
		else
		{
			for (Token singleToken:tokens)
			{
				if (singleToken.getTermText().trim() == "")
				{
					continue;
				}
				else
				{
					this.termText = this.termText + " " + singleToken.getTermText();
				}
			}
		}
	}
	
	/**
	 * Returns the string representation of this token. It must adhere to the
	 * following rules:
	 * 1. Return only the associated "text" with the token. Any other information 
	 * must be suppressed.
	 * 2. Must return a non-empty value only for tokens that are going to be indexed
	 * If you introduce special token types (sentence boundary for example), return
	 * an empty string
	 * 3. IF the original token A (with string as "a") was merged with tokens B ("b"),
	 * C ("c") and D ("d"), toString should return "a b c d"
	 * @return The raw string representation of the token
	 */
	@Override
	public String toString() {
		//TODO: YOU MUST IMPLEMENT THIS METHOD
		return termText;
	}
	
	/**
	 * To get the value of the isSentenceBoundary Flag
	 */
	public boolean getSentenceBoundary()
	{
		return this.isSentenceBoundary;
	}
	
	/**
	 * SETTER for first Token in stream
	 */
	public void setFirstToken(boolean a)
	{
		this.isFirstToken = true;
	}
	
	/**
	 * GETTER method for whether first token or not
	 */
	public boolean getFirstToken()
	{
		return this.isFirstToken;
	}
	
}
