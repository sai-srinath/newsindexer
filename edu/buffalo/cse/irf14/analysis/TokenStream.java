/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author nikhillo
 * Class that represents a stream of Tokens. All {@link Analyzer} and
 * {@link TokenFilter} instances operate on this to implement their
 * behavior
 */
public class TokenStream implements Iterator<Token>{
	// Linked list that holds the stream of tokens
	private LinkedList<Token> tokenStreamData = new LinkedList<Token>();
	private ListIterator<Token> li;
	
	/**
	 * Method that checks if there is any Token left in the stream
	 * with regards to the current pointer.
	 * DOES NOT ADVANCE THE POINTER
	 * @return true if at least one Token exists, false otherwise
	 */
	@Override
	public boolean hasNext() {
		// TODO YOU MUST IMPLEMENT THIS
		if (li.hasNext() == true)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	/**
	 * Method to return the next Token in the stream. If a previous
	 * hasNext() call returned true, this method must return a non-null
	 * Token.
	 * If for any reason, it is called at the end of the stream, when all
	 * tokens have already been iterated, return null
	 */
	@Override
	public Token next() {
		// TODO YOU MUST IMPLEMENT THIS
		if (li.hasNext() != true)
		{
			return null;
		}
		else 
		{
			return li.next();
		}
		
	}
	
	/**
	 * Method to remove the current Token from the stream.
	 * Note that "current" token refers to the Token just returned
	 * by the next method. 
	 * Must thus be NO-OP when at the beginning of the stream or at the end
	 */
	@Override
	public void remove() {
		// TODO YOU MUST IMPLEMENT THIS
		if (li.hasPrevious() == false || li.hasNext() == false)
		{
			return;
		}
		else
		{
			li.remove();
		}
		
		
	}
	
	/**
	 * Method to reset the stream to bring the iterator back to the beginning
	 * of the stream. Unless the stream has no tokens, hasNext() after calling
	 * reset() must always return true.
	 */
	public void reset() {
		//TODO : YOU MUST IMPLEMENT THIS
		/* while (li.hasPrevious())
		{
			li.previous();
		}*/
		li = tokenStreamData.listIterator();
	}
	
	/**
	 * Method to append the given TokenStream to the end of the current stream
	 * The append must always occur at the end irrespective of where the iterator
	 * currently stands. After appending, the iterator position must be unchanged
	 * Of course this means if the iterator was at the end of the stream and a 
	 * new stream was appended, the iterator hasn't moved but that is no longer
	 * the end of the stream.
	 * @param stream : The stream to be appended
	 */
	public void append(TokenStream stream) {
		//TODO : YOU MUST IMPLEMENT THIS
		int nextIndex;
		nextIndex = li.nextIndex();
		
		while(li.hasPrevious())
		{
			li.previous();
		}
		for (Token temp: stream.getTokenStream())
		{
			li.add(temp);
		}
		while(li.nextIndex() != nextIndex)
		{
			li.next();
		}
		
	}
	
	/**
	 * Method to get the current Token from the stream without iteration.
	 * The only difference between this method and {@link TokenStream#next()} is that
	 * the latter moves the stream forward, this one does not.
	 * Calling this method multiple times would not alter the return value of {@link TokenStream#hasNext()}
	 * @return The current {@link Token} if one exists, null if end of stream
	 * has been reached or the current Token was removed
	 */
	public Token getCurrent() {
		//TODO: YOU MUST IMPLEMENT THIS
		
		int nextIndex;
		
		if (li.hasNext()){
			nextIndex = li.nextIndex() - 1;
			return tokenStreamData.get(nextIndex);
		}
		else
		{
			return null;
		}
		
		
	}
	
	/**
	 * Setter Method to add tokens to the list
	 * @param String tokenString: the string to be added to the list tokenStreamData as a new token 
	 */
	public void setToken(String tokenString){
		// creating a token object and adding it to the end of linked list
		Token tempToken = new Token();
		tempToken.setTermText(tokenString);
		this.tokenStreamData.add(tempToken);
	}
	
	/**
	 * Getter Method to retrieve Token Stream
	 * @return tokenStreamData - linked list of the token objects
	 */
	public LinkedList<Token> getTokenStream()
	{
		return tokenStreamData;
	}
	
	
	public void initIterator(){
		li = tokenStreamData.listIterator();
	}
}
