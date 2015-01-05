package edu.buffalo.cse.irf14.analysis;

public class DoNothing implements Analyzer {
	TokenStream stream;
	public boolean increment(){
		return false;
	}
	
	public DoNothing(TokenStream stream)
	{
		this.stream = stream;
	}
	
	public TokenStream getStream()
	{
		return stream;
	}
		
}