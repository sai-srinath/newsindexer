package edu.buffalo.cse.irf14;

import java.io.File;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;

public class SingleFileTest{
	public static void main(String[] args) {
		String ipDir = "/Users/Girish/Projects/newsindexer/training/alum/0000263";
		String indexDir = "/Users/Girish/Projects/newsindexer/Indexes";
		//more? idk!
		
		File ipDirectory = new File(ipDir);
		
		
		
		
		Document d = null;
		IndexWriter writer = new IndexWriter(indexDir);
		
		try {
			
				try {
					d = Parser.parse(ipDir);
					writer.addDocument(d);
					System.out.println("doing");
				} catch (ParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
					
				
				
			
			
			writer.close();
			IndexReader reader = new IndexReader(indexDir, IndexType.TERM);
			
			System.out.println(reader.getDocLength("0000263"));
			reader.getPostingsTF("aluminium");
			
			
			
		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}