/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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