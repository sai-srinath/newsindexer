package edu.buffalo.cse.irf14;

import java.util.HashMap;

public class Stopwords{
	HashMap<String, Boolean> stopwordsList;
	
	public boolean isStopword(String term){
		if (stopwordsList.get(term) == true){
			return true;
		} else {
			return false;
		}
	}
	
	
	public Stopwords(){
		stopwordsList = new HashMap<String, Boolean>();
		stopwordsList.put("ever", true);
		stopwordsList.put("every", true);
		stopwordsList.put("for", true);
		stopwordsList.put("from", true);
		stopwordsList.put("get", true);
		stopwordsList.put("got", true);
		stopwordsList.put("had", true);
		stopwordsList.put("has", true);
		stopwordsList.put("have", true);
		stopwordsList.put("he", true);
		stopwordsList.put("her", true);
		stopwordsList.put("hers", true);
		stopwordsList.put("him", true);
		stopwordsList.put("his", true);
		stopwordsList.put("how", true);
		stopwordsList.put("however", true);
		stopwordsList.put("i", true);
		stopwordsList.put("if", true);
		stopwordsList.put("in", true);
		stopwordsList.put("into", true);
		stopwordsList.put("is", true);
		stopwordsList.put("it", true);
		stopwordsList.put("its", true);
		stopwordsList.put("just", true);
		stopwordsList.put("least", true);
		stopwordsList.put("let", true);
		stopwordsList.put("like", true);
		stopwordsList.put("likely", true);
		stopwordsList.put("may", true);
		stopwordsList.put("me", true);
		stopwordsList.put("might", true);
		stopwordsList.put("most", true);
		stopwordsList.put("must", true);
		stopwordsList.put("my", true);
		stopwordsList.put("neither", true);
		stopwordsList.put("no", true);
		stopwordsList.put("nor", true);
		stopwordsList.put("not", true);
		stopwordsList.put("of", true);
		stopwordsList.put("off", true);
		stopwordsList.put("often", true);
		stopwordsList.put("on", true);
		stopwordsList.put("only", true);
		stopwordsList.put("or", true);
		stopwordsList.put("other", true);
		stopwordsList.put("our", true);
		stopwordsList.put("own", true);
		stopwordsList.put("rather", true);
		stopwordsList.put("said", true);
		stopwordsList.put("say", true);
		stopwordsList.put("says", true);
		stopwordsList.put("she", true);
		stopwordsList.put("should", true);
		stopwordsList.put("since", true);
		stopwordsList.put("so", true);
		stopwordsList.put("some", true);
		stopwordsList.put("than", true);
		stopwordsList.put("that", true);
		stopwordsList.put("the", true);
		stopwordsList.put("their", true);
		stopwordsList.put("them", true);
		stopwordsList.put("then", true);
		stopwordsList.put("there", true);
		stopwordsList.put("these", true);
		stopwordsList.put("they", true);
		stopwordsList.put("this", true);
		stopwordsList.put("tis", true);
		stopwordsList.put("to", true);
		stopwordsList.put("too", true);
		stopwordsList.put("twas", true);
		stopwordsList.put("us", true);
		stopwordsList.put("wants", true);
		stopwordsList.put("was", true);
		stopwordsList.put("we", true);
		stopwordsList.put("were", true);
		stopwordsList.put("what", true);
		stopwordsList.put("when", true);
		stopwordsList.put("where", true);
		stopwordsList.put("which", true);
		stopwordsList.put("while", true);
		stopwordsList.put("who", true);
		stopwordsList.put("whom", true);
		stopwordsList.put("why", true);
		stopwordsList.put("will", true);
		stopwordsList.put("with", true);
		stopwordsList.put("would", true);
		stopwordsList.put("yet", true);
		stopwordsList.put("you", true);
		stopwordsList.put("your", true);
		stopwordsList.put("a", true);
		stopwordsList.put("able" , true);
		stopwordsList.put("about" , true);
		stopwordsList.put("across" , true);
		stopwordsList.put("after", true);
		stopwordsList.put( "all" , true);
		stopwordsList.put("almost" , true);
		stopwordsList.put("also" , true);
		stopwordsList.put("am" , true);
		stopwordsList.put("among" , true);
		stopwordsList.put( "an" , true);
		stopwordsList.put("and" , true);
		stopwordsList.put("any" , true);
		stopwordsList.put("are" , true);
		stopwordsList.put("as" , true);
		stopwordsList.put( "at" , true);
		stopwordsList.put("be" , true);
		stopwordsList.put("because" , true);
		stopwordsList.put("been" , true);
		stopwordsList.put("but" , true);
		stopwordsList.put( "by" , true);
		stopwordsList.put("can" , true);
		stopwordsList.put("cannot" , true);
		stopwordsList.put("could" , true);
		stopwordsList.put("dear", true);
		stopwordsList.put("did" , true);
		stopwordsList.put("do" , true);
		stopwordsList.put("does" , true);
		stopwordsList.put("either" , true);
		stopwordsList.put("else", true);
	}
	
}