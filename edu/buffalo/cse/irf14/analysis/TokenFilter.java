/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.org.mozilla.javascript.internal.json.JsonParser.ParseException;

/**
 * The abstract class that you must extend when implementing your 
 * TokenFilter rule implementations.
 * Apart from the inherited Analyzer methods, we would use the 
 * inherited constructor (as defined here) to test your code.
 * @author nikhillo
 *
 */
public abstract class TokenFilter implements Analyzer {
	/**
	 * Default constructor, creates an instance over the given
	 * TokenStream
	 * @param stream : The given TokenStream instance
	 */
	TokenStream stream;
	public TokenFilter(TokenStream stream) {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		this.stream = stream;
	}
}

// NUMBERS REMOVAL
class NumbersFilter extends TokenFilter
{
	public NumbersFilter(TokenStream stream)
	{
		super(stream);
	}
	
	public TokenStream getStream(){
		
		return stream;
	}
	
	public boolean increment()
	{
		String contentToken;
		if(stream.hasNext())
		{
			stream.next();
			contentToken = stream.getCurrent().getTermText();
			
			// if date retain token
			if(contentToken.matches("([0-2][0-9]{3}[0-1][0-9][0-3][0-9])|([0-2][0-9]:[0-6][0-9]:[0-6][0-9])"))
			{
				return true;
			}
			
			// if number, remove it
			if (contentToken.matches("[\\W[0-9]]+"))
			{	
				contentToken = contentToken.replaceAll("[0-9,\\.]", "");
				if (contentToken.equals(""))
				{
					stream.remove();
				}
				else
				{
					stream.getCurrent().setTermText(contentToken);
				}
			}
			
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
}

// SPECIAL CHARACTERS
class SpecialCharactersFilter extends TokenFilter
{
	public SpecialCharactersFilter(TokenStream stream)
	{
		super(stream);
	}
	
	public TokenStream getStream(){
		
		return stream;
	}
	
	public boolean increment()
	{
		String tokenContent;
		if (stream.hasNext())
		{
			stream.next();
			tokenContent = stream.getCurrent().getTermText();
			// first check for two token hyphens and treat accordingly
			// if its num-num or num-alp or alp-num retain, else remove "-"
			
			
			// then devour all normal special characters except .!?- (ignore words, numbers)
			
			tokenContent = tokenContent.replaceAll("[^\\w\\s\\-\\.\\?!_]", "");
			tokenContent = tokenContent.replaceAll("_", "");
			
			
			if (tokenContent.matches("([0-9]+\\-[A-Za-z]+)|([A-Za-z]+\\-[0-9]+)|([0-9]+\\-[0-9]+)|(\\-[0-9A-Za-z]+)"))
			{	
				
				stream.getCurrent().setTermText(tokenContent);
				return true;
			}
			else
			{	
				
				tokenContent = tokenContent.replaceAll("-", "");
				System.out.println(tokenContent);
			}
			
			
			
			
			
			
			if (tokenContent.equals(""))
			{
				stream.remove();
				return true;
			}
			
			// set the newly devoured token
			stream.getCurrent().setTermText(tokenContent);
			
			
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
	
	
	
}



// CAPITALIZATION FILTER

class CapitalizationFilter extends TokenFilter
{
	boolean isSentenceCaps = false;
	String tokenStreamContent;
	public CapitalizationFilter(TokenStream stream)
	{
		super(stream);
		
		// First check if the whole word is in caps and the whole sentence is not in caps
		// for that make use of the TokenStream.toString() method
		this.tokenStreamContent = stream.toString();
		
		Pattern checkRegex1 = Pattern.compile("[^a-z]+");
		Matcher regexMatcher1 = checkRegex1.matcher(this.tokenStreamContent);
		
		if (regexMatcher1.matches())
		{
			this.isSentenceCaps = true;
		}
		else
		{
			this.isSentenceCaps = false;
		}
	}
	
	public TokenStream getStream(){
		
		return stream;
	}

	
	public static boolean isUpper(String s)
	{
	    for(char c : s.toCharArray())
	    {
	        if(! Character.isUpperCase(c))
	            return false;
	    }

	    return true;
	}
	
	public static boolean isCamelCase(String s)
	{
		
		if (Character.isUpperCase(s.charAt(0)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean increment()
	{
		String contentToken, adjacentToken;
		
		Boolean isFirstWord = false;
		if (stream.hasNext())
		{
			stream.next();
			String tokenContent = stream.getCurrent().getTermText();
			
			
			// if entire sentence is in caps automatically convert to Lowercase
			if (this.isSentenceCaps == true)
			{	
				
				contentToken = stream.getCurrent().getTermText().toLowerCase();
				stream.getCurrent().setTermText(contentToken);
				return true;
			}
			
			// if sentence has lower case and a word is fully upper case, leave it as it is
			if (this.isSentenceCaps == false)
			{
				if (isUpper(tokenContent) == true)
				{
					return true;
				}
			}
			// Checking if the token is the first word in the stream or the first word in the sentence
			stream.previous();
			if (stream.hasPrevious())
			{
				Token previous = stream.previous();
				if (previous.getSentenceBoundary() == true) // case when previous word ends with a period
				{
					isFirstWord = true;
				}
				else // case when it does not end with a punctuation (full stop or ! or ?)
				{
					isFirstWord = false;
				}
				stream.next();
				stream.next();
				
			}
			else // case when its the beginning of the stream
			{
				isFirstWord = true;
				stream.next();
			}
			
			// if token is first word of a sentence convert to lower case
			if (isFirstWord == true)
			{
				tokenContent = tokenContent.toLowerCase();
				stream.getCurrent().setTermText(tokenContent);
				return true;
			}
			else // check if current token and adjacent tokens are camel case, if so leave else lcase it
			{
				// first checking if current token is in Camel Case format
				if (isCamelCase(tokenContent))
				{	
					System.out.println("loop entered");
					// if there is an adjacent token
					if(stream.hasNext())
					{
						adjacentToken = stream.next().getTermText();
						System.out.println(adjacentToken);
						if (isCamelCase(adjacentToken))
						{	
							
							tokenContent = tokenContent + " " + adjacentToken;
							stream.remove();
							stream.previous();
							stream.getCurrent().setTermText(tokenContent);
							stream.next();
							return true;
						}
						else
						{	
							stream.previous();
							stream.previous();
							stream.getCurrent().setTermText(tokenContent);
							stream.next();
							return true;
						}
					}
					else // if there is no adjacent token
					{
						stream.getCurrent().setTermText(tokenContent);
						return true;
					}
				}
				else
				{
					stream.getCurrent().setTermText(tokenContent.toLowerCase());
					return true;
				}
			}
			
			
			
			
		}
		else
		{
			return false;
		}
		
	}
}


// SYMBOLS FILTER
class SymbolFilter extends TokenFilter
{	
	String content;
	static HashMap<String, String> contractionList = new HashMap<String, String>();
	public SymbolFilter(TokenStream stream)
	{
		super(stream);
		contractionList.put("ain't","am not");
		contractionList.put("aren't","are not");
		contractionList.put("can't","cannot");
		contractionList.put("could've","could have");
		contractionList.put("couldn't","could not");
		contractionList.put("couldn't've","could not have");
		contractionList.put("didn't","did not");
		contractionList.put("doesn't","does not");
		contractionList.put("don't","do not");
		contractionList.put("hadn't","had not");
		contractionList.put("hadn't've","had not have");
		contractionList.put("hasn't","has not");
		contractionList.put("haven't","have not");
		contractionList.put("he'd","he had");
		contractionList.put("he'd've","he would have");
		contractionList.put("he'll","he shall");
		contractionList.put("he's","he has");
		contractionList.put("how'd","how did");
		contractionList.put("how'll","how will");
		contractionList.put("how's","how has");
		contractionList.put("i'd","i had");
		contractionList.put("i'd've","i would have");
		contractionList.put("i'll","i shall");
		contractionList.put("i'm","i am");
		contractionList.put("i've","i have");
		contractionList.put("isn't","is not");
		contractionList.put("it'd","it had");
		contractionList.put("it'd've","it would have");
		contractionList.put("it'll","it shall");
		contractionList.put("it's","it has");
		contractionList.put("let's","let us");
		contractionList.put("ma'am","madam");
		contractionList.put("mightn't","might not");
		contractionList.put("mightn't've","might not have");
		contractionList.put("might've","might have");
		contractionList.put("mustn't","must not");
		contractionList.put("must've","must have");
		contractionList.put("needn't","need not");
		contractionList.put("not've","not have");
		contractionList.put("o'clock","of the clock");
		contractionList.put("shan't","shall not");
		contractionList.put("she'd","she had");
		contractionList.put("she'd've","she would have");
		contractionList.put("she'll","she will");
		contractionList.put("she's","she has");
		contractionList.put("should've","should have");
		contractionList.put("shouldn't","should not");
		contractionList.put("shouldn't've","should not have");
		contractionList.put("that's","that has");
		contractionList.put("there'd","there had");
		contractionList.put("there'd've","there would have");
		contractionList.put("there're","there are");
		contractionList.put("there's","there has");
		contractionList.put("they'd","they would");
		contractionList.put("they'd've","they would have");
		contractionList.put("they'll","they shall");
		contractionList.put("they're","they are");
		contractionList.put("they've","they have");
		contractionList.put("wasn't","was not");
		contractionList.put("we'd","we had");
		contractionList.put("we'd've","we would have");
		contractionList.put("we'll","we will");
		contractionList.put("we're","we are");
		contractionList.put("we've","we have");
		contractionList.put("weren't","were not");
		contractionList.put("what'll","what shall");
		contractionList.put("what're","what are");
		contractionList.put("what's","what has");
		contractionList.put("what've","what have");
		contractionList.put("when's","when has");
		contractionList.put("where'd","where did");
		contractionList.put("where's","where has");
		contractionList.put("where've","where have");
		contractionList.put("who'd","who would");
		contractionList.put("who'll","who shall");
		contractionList.put("who're","who are");
		contractionList.put("who's","who has");
		contractionList.put("who've","who have");
		contractionList.put("why'll","why will");
		contractionList.put("why're","why are");
		contractionList.put("why's","why has");
		contractionList.put("won't","will not");
		contractionList.put("would've","would have");
		contractionList.put("wouldn't","would not");
		contractionList.put("wouldn't've","would not have");
		contractionList.put("y'all","you all");
		contractionList.put("y'all'd've","you all should have");
		contractionList.put("you'd","you had");
		contractionList.put("you'd've","you would have");
		contractionList.put("you'll","you shall");
		contractionList.put("you're","you are");
		contractionList.put("you've","you have");
		contractionList.put("'em", "them");
		
	}
	
	public TokenStream getStream(){
		
		return stream;
	}

	public boolean increment()
	{
		if (stream.hasNext())
		{	
			
			content = stream.next().getTermText();
			
			// First removing the punctuations
			Pattern checkRegex1 = Pattern.compile("((\\?|\\.|!)+)");
			Pattern checkRegex2 = Pattern.compile("((?:.*)(?:[^\\?\\.!]))(?:\\?|!|\\.)+");
			
			Matcher regexMatcher1 = checkRegex1.matcher(content);
			Matcher regexMatcher2 = checkRegex2.matcher(content);
			
			if (regexMatcher1.matches() == true)
			{	
				
				content = "";
				stream.remove();
				return true;
			}
			else if (regexMatcher2.matches() == true)
			{
				content = regexMatcher2.group(1).toString();
			}
			
			
			
			// checking the string for common contractions
			if (contractionList.containsKey(content.toLowerCase()))
			{
				char[] contraction = content.toCharArray();
				
				content = contractionList.get(content.toLowerCase());
				if (Character.isUpperCase(contraction[0]))
				{	
					
					char[] contraction2 = content.toCharArray();
					
					contraction2[0] = Character.toUpperCase(contraction2[0]);
					
					String s = new String(contraction2);
					content = s;
				}
			}
			
			// checking and removing ' (apostrophes) from the string
			
			checkRegex1 = Pattern.compile("(.*)(?:'s)");
			regexMatcher1 = checkRegex1.matcher(content);
			
			checkRegex2 = Pattern.compile("'");
			regexMatcher2 = checkRegex2.matcher(content);
			
			if (regexMatcher1.matches() == true)
			{
				content = regexMatcher1.group(1).toString();
			}
			else if (regexMatcher2.find() == true)
			{
				content = content.replaceAll("'", "");
				if (content.equals(""))
				{
					stream.remove();
					return true;
				}
			}
			
			
			
			// checking if the string contains - (hyphens) 
			if (content.matches("([A-Za-z]+)-([A-Za-z]+)"))
			{
				content = content.replaceAll("-", " ");
			}
			else if (content.matches("([\\S&&[^\\-]]+)-([\\S&&[^\\-]]+)"))
			{
				
			}
			else if (content.contains("-"))
			{
				content = content.replaceAll("-", "");
				if (content.equals(""))
				{
					stream.remove();
					return true;
				}
			}
			
			
			
			stream.getCurrent().setTermText(content);
			return true;
			
			
			
			
			
		}
		else
		{
			return false;
		}
		
	}
	
	
}


// DATE FILTER
class DatesFilter extends TokenFilter
{	
	String firstToken;
	String secondToken;
	String thirdToken;
	
	public DatesFilter(TokenStream stream)
	{
		super(stream);
	}
	
	public TokenStream getStream(){
		
		return stream;
	}

	public boolean increment()
	{	
		String regexData = "";
		String transformedData = "";
		
		
		try
		{
		Date date;
		Calendar cal = Calendar.getInstance();	
		
		// get the first token
		if(stream.hasNext())
		{	
			// storing the data of the first token
			// INSIDE the first token
			// get the first term
			firstToken = stream.next().getTermText();
			System.out.println("has first token");
			if (firstToken.matches("[0-9]{1,4}") // for day of month 
				|| firstToken.matches("((?i)((jan|feb)(.*)(ary)?)|((sep|oct|nov|dec)(.*)(ber)?)|((apr)(il)?|(mar)(ch)?|may|(jun)(e)?|(jul)(y)?))") // for month name
				|| firstToken.matches("[0-1]?[0-9]:[0-6]?[0-9]") // for time in hh:mm format
				|| firstToken.matches("((?i)[0-1]?[0-9]:[0-6]?[0-9](pm|am)\\.?)") // for time in hh:mm AM/PM format
				|| firstToken.matches("((?i)[0-9]{1,}(ad|bc)\\.)") // check for 847AD. like cases
				|| firstToken.matches("[0-9]{4}(-)[0-9]{2}\\.")) // to check for cases like 2011-12
			{	
				System.out.println("entered 1");
				// NOW making sure that there is a second token still in the stream,
				// if not , then check the one token cases
				if(stream.hasNext())
				{	// NOW inside the Second Token
					// pointer is at A  B | C
					System.out.println("has second term");
					secondToken = stream.next().getTermText();
					if(secondToken.matches("((?i)((jan|feb)(.*)(ary)?)|((sep|oct|nov|dec)(.*)(ber)?)|((apr)(il)?|(mar)(ch)?|may|(jun)(e)?|(jul)(y)?))")
						|| secondToken.matches("[0-9]{1,4},?")
						|| secondToken.matches("(?i)(ad|bc)")
						|| secondToken.matches("((?i)(am|pm))\\."))
					{	
						System.out.println("entered 2");
						if(stream.hasNext()) // check if there is a third token
						{	
							System.out.println("has third term");
							thirdToken = stream.next().getTermText();
							// there is a third token so pointer is at A  B  C |
							if(thirdToken.matches("[0-9]{4}")
									|| thirdToken.matches("[0-9]{4},"))
							{	
								System.out.println("entered 3");
								// if regex matches these three if cases then it must be a 3 token
								// expression
								stream.remove();
								stream.previous();
								stream.remove();
								stream.previous();
								regexData = firstToken + " " + secondToken + " " + thirdToken;
								// check the two cases where 3 tokens come into play
								if (regexData.matches("(.+),(.+),?"))
								{
									if(regexData.matches("(.+),"))
									{
										date = new SimpleDateFormat("MMMM dd, yyyy,", Locale.ENGLISH).parse(regexData);
										cal.setTime(date);
										SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
										simple.setCalendar(cal);
										transformedData = simple.format(cal.getTime()) + ",";
									}
									else
									{
									date = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).parse(regexData);
									cal.setTime(date);
									SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
									simple.setCalendar(cal);
									transformedData = simple.format(cal.getTime());
									}
									
								}
								else
								{
									date = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).parse(regexData);
									cal.setTime(date);
									SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
									simple.setCalendar(cal);
									transformedData = simple.format(cal.getTime());
									
								}
							
							}
							else // if their is a third token and no match pointer is at A  B  C|
							{
								System.out.println("entered else after not matching third token");
								regexData = firstToken + " " + secondToken;
								if (regexData.matches("(?i)([0-9]{1,4}\\s(bc|ad))")) // for cases like 84 BC
								{
									date = new SimpleDateFormat("yyyy G", Locale.ENGLISH).parse(regexData);
									cal.setTime(date);
									SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
									simple.setCalendar(cal);
									if (regexData.matches("(.+)(?i)(bc)"))
									{
										transformedData = "-" + simple.format(cal.getTime());
									}
									else
									{
										transformedData = simple.format(cal.getTime());
									}

									
								}
								else if (regexData.matches("(?i)([0-1]?[0-9]:[0-6]?[0-9]\\s(am|pm)\\.)"))
								{
									date = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse(regexData);
									cal.setTime(date);
									SimpleDateFormat simple = new SimpleDateFormat("HH:mm:ss");
									simple.setCalendar(cal);
									transformedData = simple.format(cal.getTime()) + ".";
									
								}
								else if (regexData.matches("((?i)((jan|feb)(.*)(ary)?)|((sep|oct|nov|dec)(.*)(ber)?)|((apr)(il)?|(mar)(ch)?|may|(jun)(e)?|(jul)(y)?))\\s[0-3]?[0-9]"))
								{	
									System.out.println("entered April 11 case");
									date = new SimpleDateFormat("MMMM dd", Locale.ENGLISH).parse(regexData);
									cal.setTime(date);
									cal.set(Calendar.YEAR, 1900);
									SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
									simple.setCalendar(cal);
									transformedData = simple.format(cal.getTime());
									
								}
								stream.previous();
								stream.previous();
								stream.remove();
								stream.previous();
								
							} // end of ELSE for pointer with at end of third token but the term is a 2 token one
						}
						
						else // if there is no third token pointer is at A  B | C
						{
						regexData = firstToken + " " + secondToken;
						if (regexData.matches("(?i)([0-9]{1,4}\\s(bc|ad))")) // for cases like 84 BC
						{
							date = new SimpleDateFormat("yyyy G", Locale.ENGLISH).parse(regexData);
							cal.setTime(date);
							SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
							simple.setCalendar(cal);
							if (regexData.matches("(.+)(?i)(bc)"))
							{
								transformedData = "-" + simple.format(cal.getTime());
							}
							else
							{
								transformedData = simple.format(cal.getTime());
							}
							
							
						}
						else if (regexData.matches("(?i)([0-1]?[0-9]:[0-6]?[0-9]\\s(am|pm)\\.)"))
						{
							date = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse(regexData);
							cal.setTime(date);
							SimpleDateFormat simple = new SimpleDateFormat("HH:mm:ss");
							simple.setCalendar(cal);
							transformedData = simple.format(cal.getTime()) + ".";
							
						}
						else if (regexData.matches("(((jan|feb)(.*)(ary)?)|((sep|oct|nov|dec)(.*)(ber)?)|((apr)(il)?|((mar)(ch)?)|may|((jun)(e)?)|((jul)(y)?)))\\s[0-3]?[0-9]"))
						{
							date = new SimpleDateFormat("MMMM dd", Locale.ENGLISH).parse(regexData);
							cal.setTime(date);
							cal.set(Calendar.YEAR, 1900);
							SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
							simple.setCalendar(cal);
							transformedData = simple.format(cal.getTime());
							
						}
						stream.previous();
						stream.remove();
						stream.previous();
						}
					}
					else // in case the second token pattern doesnt match - pointer is A  B | C
						// match all the one token rules
					{
						regexData = firstToken;
						System.out.println(firstToken);
						if (regexData.matches("[0-9]{4}")) // for cases like 1948
						{
							date = new SimpleDateFormat("yyyy", Locale.ENGLISH).parse(regexData);
							cal.setTime(date);
							SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
							simple.setCalendar(cal);
							transformedData = simple.format(cal.getTime());
						}
						else if (regexData.matches("((?i)[0-1]?[0-9]:[0-6]?[0-9](pm|am)\\.?)")) // for cases like 5:15PM.
						{
							date = new SimpleDateFormat("hh:mma", Locale.ENGLISH).parse(regexData);
							cal.setTime(date);
							SimpleDateFormat simple = new SimpleDateFormat("HH:mm:ss");
							simple.setCalendar(cal);
							transformedData = simple.format(cal.getTime()) + ".";
						}
						else if (regexData.matches("((?i)[0-9]{1,}(ad|bc)\\.)")) // for cases like 847AD
						{
							date = new SimpleDateFormat("yyyyG", Locale.ENGLISH).parse(regexData);
							cal.setTime(date);
							SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
							simple.setCalendar(cal);
							transformedData = simple.format(cal.getTime()) + ".";
						}
						else if (regexData.matches("[0-9]{4}(-)[0-9]{2}\\.")) // for cases like 2011-12
						{	
							System.out.println("enters dreaded DRACULA");
							String[] twoyears;
							twoyears = regexData.split("-");
							
							date = new SimpleDateFormat("yyyy", Locale.ENGLISH).parse(twoyears[0]);
							cal.setTime(date);
							SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
							simple.setCalendar(cal);
							twoyears[0] = simple.format(cal.getTime());
							
							date = new SimpleDateFormat("yy", Locale.ENGLISH).parse(twoyears[1]);
							cal.setTime(date);
							simple = new SimpleDateFormat("yyyyMMdd");
							simple.setCalendar(cal);
							twoyears[1] = simple.format(cal.getTime());
							transformedData = twoyears[0] + "-" + twoyears[1] + ".";
						}
						stream.previous();
						stream.previous();
						
					}
				}
				else // in case there is a match and its only the last token in the stream
					//pointer is current at A |
				{
					regexData = firstToken;
					if (regexData.matches("[0-9]{4}")) // for cases like 1948
					{
						date = new SimpleDateFormat("yyyy", Locale.ENGLISH).parse(regexData);
						cal.setTime(date);
						SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
						simple.setCalendar(cal);
						transformedData = simple.format(cal.getTime());
					}
					else if (regexData.matches("((?i)[0-1]?[0-9]:[0-6]?[0-9](pm|am)\\.?)")) // for cases like 5:15PM.
					{
						date = new SimpleDateFormat("hh:mma", Locale.ENGLISH).parse(regexData);
						cal.setTime(date);
						SimpleDateFormat simple = new SimpleDateFormat("HH:mm:ss");
						simple.setCalendar(cal);
						transformedData = simple.format(cal.getTime()) + ".";
					}
					else if (regexData.matches("((?i)[0-9]{1,}(ad|bc)\\.)")) // for cases like 847AD
					{
						date = new SimpleDateFormat("yyyyG", Locale.ENGLISH).parse(regexData);
						cal.setTime(date);
						SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
						simple.setCalendar(cal);
						transformedData = simple.format(cal.getTime()) + ".";
					}
					else if (regexData.matches("[0-9]{4}(-)[0-9]{2}\\.")) // for cases like 2011-12
					{
						String[] twoyears;
						twoyears = regexData.split("-");
						
						
						date = new SimpleDateFormat("yyyy", Locale.ENGLISH).parse(twoyears[0]);
						cal.setTime(date);
						SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
						simple.setCalendar(cal);
						twoyears[0] = simple.format(cal.getTime());
						
						date = new SimpleDateFormat("yy", Locale.ENGLISH).parse(twoyears[1]);
						cal.setTime(date);
						simple = new SimpleDateFormat("yyyyMMdd");
						simple.setCalendar(cal);
						twoyears[1] = simple.format(cal.getTime());
						transformedData = twoyears[0] + "-" + twoyears[1] + ".";
					}
				}
				stream.getCurrent().setTermText(transformedData);
				stream.previous();
				stream.next();
			}
			
			return true;
		} // Stream had tokens , so increment() returns true
		
		
		}
		catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false; // Stream doesnt have tokens, so increment() returns false
	}
	
}



// STEMMING FILTER
class PorterStemmerFilter extends TokenFilter {
	char[] temp ={};
	String content = "";
	String data;
	public PorterStemmerFilter(TokenStream stream)
	{
		super(stream);
	}
	
	public TokenStream getStream(){
		
		return stream;
	}

	public boolean increment()
	{	
		if (stream.hasNext())
		{	
			
			stream.next();
			temp =  stream.getCurrent().getTermBuffer();
			data = 	stream.getCurrent().getTermText();
			
			PorterStemmer s = new PorterStemmer();
			if (data.matches("[a-zA-Z]+"))
			{
				
				s.add(temp, temp.length);
			    s.stem();
			    content = s.toString();
			    stream.getCurrent().setTermText(content);
			}
		    
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	
}



// STOPWORDS FILTER
class StopwordsFilter extends TokenFilter {
	
	String temp;
	
	public StopwordsFilter(TokenStream stream)
	{
		super(stream);
	}
	
	public TokenStream getStream(){
		
		return stream;
	}

	public boolean increment()
	{
		if(stream.hasNext())
		{	
			stream.next();
			temp = stream.getCurrent().getTermText();
				
			if (temp.equals("a")|| 	temp.equals("able")|| 	temp.equals("about")|| 	temp.equals("across")|| 	temp.equals("after")
				|| temp.equals("all")|| 	temp.equals("almost")|| 	temp.equals("also")|| 	temp.equals("am")|| 	temp.equals("among") 
				|| temp.equals("an")|| 	temp.equals("and")|| 	temp.equals("any")|| 	temp.equals("are")|| 	temp.equals("as") 	
				|| temp.equals("at")|| 	temp.equals("be")|| 	temp.equals("because")|| 	temp.equals("been")|| 	temp.equals("but") 	
				|| temp.equals("by")|| 	temp.equals("can")|| 	temp.equals("cannot")|| 	temp.equals("could")|| 	temp.equals("dear")
				|| 	temp.equals("did")|| 	temp.equals("do")|| 	temp.equals("does")|| 	temp.equals("either")|| 	temp.equals("else")
				|| 	temp.equals("ever")|| 	temp.equals("every")|| 	temp.equals("for")|| 	temp.equals("from")|| 	temp.equals("get")
				|| 	temp.equals("got")|| 	temp.equals("had")|| 	temp.equals("has")|| 	temp.equals("have")|| 	temp.equals("he")
				|| 	temp.equals("her")|| 	temp.equals("hers")|| 	temp.equals("him")|| 	temp.equals("his")|| 	temp.equals("how")
				|| 	temp.equals("however")|| 	temp.equals("i")|| 	temp.equals("if")|| 	temp.equals("in")|| 	temp.equals("into")
				|| 	temp.equals("is")|| 	temp.equals("it")|| 	temp.equals("its")|| 	temp.equals("just")|| 	temp.equals("least")
				|| 	temp.equals("let")|| 	temp.equals("like")|| 	temp.equals("likely")|| 	temp.equals("may")|| 	temp.equals("me")
				|| 	temp.equals("might")|| 	temp.equals("most")|| 	temp.equals("must")|| 	temp.equals("my")|| 	temp.equals("neither")
				|| 	temp.equals("no")|| 	temp.equals("nor")|| 	temp.equals("not")|| 	temp.equals("of")|| 	temp.equals("off")
				|| 	temp.equals("often")|| 	temp.equals("on")|| 	temp.equals("only")|| 	temp.equals("or")|| 	temp.equals("other")
				|| 	temp.equals("our")|| 	temp.equals("own")|| 	temp.equals("rather")|| 	temp.equals("said")|| 	temp.equals("say")
				|| 	temp.equals("says")|| 	temp.equals("she")|| 	temp.equals("should")|| 	temp.equals("since")|| 	temp.equals("so")
				|| 	temp.equals("some")|| 	temp.equals("than")|| 	temp.equals("that")|| 	temp.equals("the")|| 	temp.equals("their")
				|| 	temp.equals("them")|| 	temp.equals("then")|| 	temp.equals("there")|| 	temp.equals("these")|| 	temp.equals("they")
				|| 	temp.equals("this")|| 	temp.equals("tis")|| 	temp.equals("to")|| 	temp.equals("too")|| 	temp.equals("twas")
				|| 	temp.equals("us")|| 	temp.equals("wants")|| 	temp.equals("was")|| 	temp.equals("we")|| 	temp.equals("were")
				|| 	temp.equals("what")|| 	temp.equals("when")|| 	temp.equals("where")|| 	temp.equals("which")|| 	temp.equals("while")
				|| 	temp.equals("who")|| 	temp.equals("whom")|| 	temp.equals("why")|| 	temp.equals("will")|| 	temp.equals("with")
				|| 	temp.equals("would")|| 	temp.equals("yet")|| 	temp.equals("you")|| 	temp.equals("your"))
			{
				stream.remove();
			}
		
				
				return true;
		}
		else
		{
			return false;
		}
	}
	
	
}





// ACCENT FILTER
class AccentFilter extends TokenFilter {
	char[] termBuffer;
	String content ="";
	String temp = "";
	
	public AccentFilter(TokenStream stream)
	{
		super(stream);
	}
	public boolean increment(){
		content = "";
		temp = "";
		if(stream.hasNext())
		{
			stream.next();
			termBuffer = stream.getCurrent().getTermBuffer();
			for(char buffer:termBuffer)
			{	
				
				switch(buffer){
					case '\u00C0' :
					temp = "A";
					content = content + temp;
					break;
					case '\u00C1' :
					temp = "A";
					content = content + temp;
					break;
					case '\u00C2' :
					temp = "A";
					content = content + temp;
					break;
					case '\u00C3' :
					temp = "A";
					content = content + temp;
					break;
					case '\u00C4' :
					temp = "A";
					content = content + temp;
					break;
					case '\u00C5' :
					temp = "A";
					content = content + temp;
					break;
					case '\u00C6' :
					temp = "AE";
					content = content + temp;
					break;
					case '\u00C7' :
					temp = "C";
					content = content + temp;
					break;
					case '\u00C8' :
					temp = "E";
					content = content + temp;
					break;
					case '\u00C9' :
					temp = "E";
					content = content + temp;
					break;
					case '\u00CA' :
					temp = "E";
					content = content + temp;
					break;
					case '\u00CB' :
					temp = "E";
					content = content + temp;
					break;
					case '\u00CC' :
					temp = "I";
					content = content + temp;
					break;
					case '\u00CD' :
					temp = "I";
					content = content + temp;
					break;
					case '\u00CE' :
					temp = "I";
					content = content + temp;
					break;
					case '\u00CF' :
					temp = "I";
					content = content + temp;
					break;
					case '\u0132' :
					temp = "IJ";
					content = content + temp;
					break;
					case '\u00D0' :
					temp = "D";
					content = content + temp;
					break;
					case '\u00D1' :
					temp = "N";
					content = content + temp;
					break;
					case '\u00D2' :
					temp = "O";
					content = content + temp;
					break;
					case '\u00D3' :
					temp = "O";
					content = content + temp;
					break;
					case '\u00D4' :
					temp = "O";
					content = content + temp;
					break;
					case '\u00D5' :
					temp = "O";
					content = content + temp;
					break;
					case '\u00D6' :
					temp = "O";
					content = content + temp;
					break;
					case '\u00D8' :
					temp = "O";
					content = content + temp;
					break;
					case '\u0152' :
					temp = "OE";
					content = content + temp;
					break;
					case '\u00DE' :
					temp = "TH";
					content = content + temp;
					break;
					case '\u00D9' :
					temp = "U";
					content = content + temp;
					break;
					case '\u00DA' :
					temp = "U";
					content = content + temp;
					break;
					case '\u00DB' :
					temp = "U";
					content = content + temp;
					break;
					case '\u00DC' :
					temp = "U";
					content = content + temp;
					break;
					
					case '\u00DD' :
					temp = "Y";
					content = content + temp;
					break;
					
					case '\u0178' :
					temp = "Y";
					content = content + temp;
					break;
					
					case '\u00E0' :
					temp = "a";
					content = content + temp;
					break;
					
					case '\u00E1' :
					temp = "a";
					content = content + temp;
					break;
					
					case '\u00E2' :
					temp = "a";
					content = content + temp;
					break;
					
					case '\u00E3' :
					temp = "a";
					content = content + temp;
					break;
					
					case '\u00E4' :
					temp = "a";
					content = content + temp;
					break;
					
					case '\u00E5' :
					temp = "a";
					content = content + temp;
					break;
					
					case '\u00E6' :
					temp = "ae";
					content = content + temp;
					break;
					
					case '\u00E7' :
					temp = "c";
					content = content + temp;
					break;
					
					case '\u00E8' :
					temp = "e";
					content = content + temp;
					break;
					
					case '\u00E9' :
					temp = "e";
					content = content + temp;
					break;
					
					case '\u00EA' :
					temp = "e";
					content = content + temp;
					break;
					
					case '\u00EB' :
					temp = "e";
					content = content + temp;
					break;
					
					case '\u00EC' :
					temp = "i";
					content = content + temp;
					break;
					
					case '\u00ED' :
					temp = "i";
					content = content + temp;
					break;
					
					case '\u00EE' :
					temp = "i";
					content = content + temp;
					break;
					
					case '\u00EF' :
					temp = "i";
					content = content + temp;
					break;
					
					case '\u0133' :
					temp = "ij";
					content = content + temp;
					break;
					
					case '\u00F0' :
					temp = "d";
					content = content + temp;
					break;
					
					case '\u00F1' :
					temp = "n";
					content = content + temp;
					break;
					
					case '\u00F2' :
					temp = "o";
					content = content + temp;
					break;
					
					case '\u00F3' :
					temp = "o";
					content = content + temp;
					break;
					
					case '\u00F4' :
					temp = "o";
					content = content + temp;
					break;
					
					case '\u00F5' :
					temp = "o";
					content = content + temp;
					break;
					
					case '\u00F6' :
					temp = "o";
					content = content + temp;
					break;
					
					case '\u00F8' :
					temp = "o";
					content = content + temp;
					break;
					
					case '\u0153' :
					temp = "oe";
					content = content + temp;
					break;
					
					case '\u00DF' :
					temp = "ss";
					content = content + temp;
					break;
					
					case '\u00FE' :
					temp = "th";
					content = content + temp;
					break;
					
					case '\u00F9' :
					temp = "u";
					content = content + temp;
					break;
					case '\u00FA' :
					temp = "u";
					content = content + temp;
					break;
					case '\u00FB' :
					temp = "u";
					content = content + temp;
					break;
					case '\u00FC' :
					temp = "u";
					content = content + temp;
					break;
					case '\u00FD' :
					temp = "y";
					content = content + temp;
					break;
					case '\u00FF' :
					temp = "y";
					content = content + temp;
					break;
					case '\uFB00' :
					temp = "ff";
					content = content + temp;
					break;
					
					case '\uFB01' :
					temp = "fi";
					content = content + temp;
					break;
					
					case '\uFB02' :
					temp = "fl";
					content = content + temp;
					break;
					
					case '\uFB03' :
					temp = "ffi";
					content = content + temp;
					break;
					
					case '\uFB04' :
					temp = "ffl";
					content = content + temp;
					break;
					
					case '\uFB05' :
					temp = "ft";
					content = content + temp;
					break;
					
					case '\uFB06' :
					temp = "st";
					content = content + temp;
					break;
					
					default:
					content = content + buffer;
					break;
				
				
				
				
				} // END of SWITCH
				
				
			} // END of FOR
			
			stream.getCurrent().setTermText(content);
			return true;
			
		}
		else
		{
			return false;
		}
		
	}
	
	public TokenStream getStream(){
		
		return stream;
	}
	
	
	
	
	
}