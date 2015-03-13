package edu.buffalo.cse.irf14.analysis;
public class tokentest {
	public static void main(String[] args) {
		Tokenizer tkizer = new Tokenizer();
		try
		{
			TokenStream tstream = tkizer.consume("Girish is a boy");
			
			
			
			/* while (tstream.hasNext())
			{
				tstream.next();
				System.out.println("success");
			}*/
			//tstream.next();
			System.out.println(tstream.next().getTermText());
			
			System.out.println(tstream.next().getTermText());
			tstream.reset();
			System.out.println(tstream.next().getTermText());
			System.out.println(tstream.next().getTermText());
			System.out.println(tstream.getCurrent().getTermText());
			System.out.println(tstream.getCurrent().getTermText());
			
			//tstream.remove();
			/* for (Token data: tstream.getTokenStream())
			{
				System.out.println(data.getTermText());
			} */

			
			
		}
		catch (TokenizerException e)
		{
			System.out.println("Exception encountered");
			e.printStackTrace();
		}
		
		
		
	}
	
}