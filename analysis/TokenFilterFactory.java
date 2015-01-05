/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;


/**
 * Factory class for instantiating a given TokenFilter
 * @author nikhillo
 *
 */
public class TokenFilterFactory {
	/**
	 * Static method to return an instance of the factory class.
	 * Usually factory classes are defined as singletons, i.e. 
	 * only one instance of the class exists at any instance.
	 * This is usually achieved by defining a private static instance
	 * that is initialized by the "private" constructor.
	 * On the method being called, you return the static instance.
	 * This allows you to reuse expensive objects that you may create
	 * during instantiation
	 * @return An instance of the factory
	 */
	private static TokenFilterFactory firstInstance = null;
	
	public static TokenFilterFactory getInstance() {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		if (firstInstance == null)
		{
			firstInstance = new TokenFilterFactory();
			
		}
		return firstInstance;
		
	}
	
	/**
	 * Returns a fully constructed {@link TokenFilter} instance
	 * for a given {@link TokenFilterType} type
	 * @param type: The {@link TokenFilterType} for which the {@link TokenFilter}
	 * is requested
	 * @param stream: The TokenStream instance to be wrapped
	 * @return The built {@link TokenFilter} instance
	 */
	public TokenFilter getFilterByType(TokenFilterType type, TokenStream stream) {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		
		// below is just sample code for my own reference
		TokenFilter a;
		if (type == TokenFilterType.ACCENT)
		{
			a = new AccentFilter(stream);
		}
		else if (type == TokenFilterType.STOPWORD)
		{
			a = new StopwordsFilter(stream);
		}
		else if (type == TokenFilterType.STEMMER)
		{
			a = new PorterStemmerFilter(stream);
		}
		else if (type == TokenFilterType.DATE)
		{
			a = new DatesFilter(stream);
		}
		else if (type == TokenFilterType.SYMBOL)
		{
			a = new SymbolFilter(stream);
		}
		else if (type == TokenFilterType.CAPITALIZATION)
		{
			a = new CapitalizationFilter(stream);
		}
		else if (type == TokenFilterType.SPECIALCHARS) 
		{
			a = new SpecialCharactersFilter(stream);
		}
		else if (type == TokenFilterType.NUMERIC) 
		{
			a = new NumbersFilter(stream);
		}
		else
		{
			a = null;
		}
		
		
		
		return a;
	}
}
