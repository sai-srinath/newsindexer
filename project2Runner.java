package edu.buffalo.cse.irf14;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;

import edu.buffalo.cse.irf14.SearchRunner.ScoringModel;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;

public class project2Runner {
	public static void main(String[] args) {
		String ipDir1 = "/Users/Girish/Projects/newsindexer/training/alum/0000263";
		String ipDir2 = "/Users/Girish/Projects/newsindexer/training/alum/0000191";
		String ipDir3 = "/Users/Girish/Projects/newsindexer/training/alum/0000563";
		String ipDir4 = "/Users/Girish/Projects/newsindexer/training/alum/0000577";
		String ipDir5 = "/Users/Girish/Projects/newsindexer/training/alum/0000795";
		
		String indexDir = "/Users/Girish/Projects/newsindexer/Indexes";
		String corpusDir = "/Users/Girish/Projects/newsindexer/CorpusDir";
		//more? idk!
		
		/*File ipDirectory1 = new File(ipDir1);
		File ipDirectory2 = new File(ipDir2);
		File ipDirectory3 = new File(ipDir3);
		File ipDirectory4 = new File(ipDir4);
		File ipDirectory5 = new File(ipDir5);*/
		
		
		
		Document d = null;
		IndexWriter writer = new IndexWriter(indexDir);
		
		try {
			
				try {
					d = Parser.parse(ipDir1);
					writer.addDocument(d);
					d = Parser.parse(ipDir2);
					writer.addDocument(d);
					d = Parser.parse(ipDir3);
					writer.addDocument(d);
					d = Parser.parse(ipDir4);
					writer.addDocument(d);
					d = Parser.parse(ipDir5);
					writer.addDocument(d);
					System.out.println("doing");
				} catch (ParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
					
				writer.close();
				
			
				PrintStream p = new PrintStream(System.out);
				
				SearchRunner se = new SearchRunner(indexDir, corpusDir, 'Q', p);
				LinkedList<String> pList = new LinkedList<String>();
				
				
				se.query("aluminium OR company", ScoringModel.OKAPI);
				
			
			
			
			
			
			
			
		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}