/**
 * 
 */
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

/**
 * @author nikhillo
 *
 */
public class Runner {

	/**
	 * 
	 */
	public Runner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ipDir = "/Users/Girish/Projects/newsindexer/training/";
		String indexDir = "/Users/Girish/Projects/newsindexer/Indexes";
		//more? idk!
		
		File ipDirectory = new File(ipDir);
		String[] catDirectories = ipDirectory.list();
		String corpusDir = "/Users/Girish/Projects/newsindexer/CorpusDir";
		
		String[] files;
		File dir;
		
		/*Document d = null;
		IndexWriter writer = new IndexWriter(indexDir);
		
		try {
			for (String cat : catDirectories) {
				dir = new File(ipDir+ File.separator+ cat);
				files = dir.list();
				
				if (files == null)
					continue;
				
				for (String f : files) {
					try {
						d = Parser.parse(dir.getAbsolutePath() + File.separator +f);
						writer.addDocument(d);
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
				
			}
			
			writer.close();*/
			try{
			PrintStream p = new PrintStream(new File("/Users/Girish/Projects/outputFile"));
			
			SearchRunner se = new SearchRunner(indexDir, corpusDir, 'Q', p);
			LinkedList<String> pList = new LinkedList<String>();
			
			/*System.out.println("TFIDF");
			File f = new File("/Users/Girish/Projects/inputfile");
			se.query(f);
			System.out.println();*/
			
			System.out.println("OKAPI");
			se.query("blah blah blah", ScoringModel.TFIDF);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
		/*} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
