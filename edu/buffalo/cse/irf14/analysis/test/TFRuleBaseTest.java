package edu.buffalo.cse.irf14.analysis.test;

import java.util.ArrayList;
import java.util.List;

import edu.buffalo.cse.irf14.analysis.TokenFilter;
import edu.buffalo.cse.irf14.analysis.TokenFilterFactory;
import edu.buffalo.cse.irf14.analysis.TokenFilterType;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class TFRuleBaseTest {
	public final String[] runTest(String str) throws TokenizerException {
		// create a new instance of tokenizer
		Tokenizer tkizer = new Tokenizer();
		// the tokenizer object tkizer consumes the String I/P and returns a TokenStream tstream
		// tokenizer -> token -> tokenStream
		TokenStream tstream = tkizer.consume(str);
		// create an instance of the TokenfilterFactory class
		TokenFilterFactory factory = TokenFilterFactory.getInstance();
		// get the fully constructed tokenfilter instance for Special chars type
		TokenFilter filter = factory.getFilterByType(TokenFilterType.SPECIALCHARS, tstream);
		// method to bring the iterator back to the beginning of the stream
		tstream.reset();
		
		// cycle through tokens in the current token stream and processes them
		while (tstream.hasNext()) {
			filter.increment();
		}
		
		// get the processed stream back and reset the iterator to the start position
		tstream = filter.getStream();
		tstream.reset();
		
		// declare a new arraylist list
		ArrayList<String> list = new ArrayList<String>();
		
		
		while (tstream.hasNext()) {
			list.add(tstream.next().toString());
		}
		
		// convert the arraylist of tokens into an array and return it
		String[] rv = new String[list.size()];
		rv = list.toArray(rv);
		return rv;
	}

}
