/**
 * 
 */
package edu.buffalo.cse.irf14;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

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
public class runnergirish {

	/**
	 * 
	 */
	public runnergirish() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String ipDir = args[0];
		String ipDir = "/Users/Girish/Projects/newsindexer/training";
		//String indexDir = args[1];
		String indexDir = "/Users/Girish/Projects/newsindexer/Indexes";
		//more? idk!
		
		File ipDirectory = new File(ipDir);
		String[] catDirectories = ipDirectory.list();
		
		String[] files;
		File dir;
		
		Document d = null;
		IndexWriter writer = new IndexWriter(indexDir);
		
		long start = System.currentTimeMillis();
		
		try {
			for (String cat : catDirectories) {
				dir = new File(ipDir+ File.separator+ cat);
				files = dir.list();
				
				if (files == null || cat.equals(".DS_Store"))
					continue;
				
				for (String f : files) {
					if (f.equals(".DS_Store"))
					{
						continue;
					}
					try {
						d = Parser.parse(dir.getAbsolutePath() + File.separator +f);
						writer.addDocument(d);
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
				
			}
			
			Runtime a = Runtime.getRuntime();
			long total = a.totalMemory();
			long free = a.freeMemory();
			long used = total - free;
			System.out.println(used);
			
			
			writer.close();
			IndexReader reader = new IndexReader(indexDir, IndexType.TERM);
			
			/*System.out.println(reader.getTopK(50));
			System.out.println(reader.getTotalKeyTerms());*/
			/*for (Entry<String, Integer> map:reader.getPostings("compani").entrySet())
			{
				System.out.println(map);
			}*/
			
			
			/*System.out.println("now place");
			System.out.println();
			
			reader = new IndexReader(indexDir, IndexType.PLACE);
			System.out.println(reader.getTopK(50));
			System.out.println(reader.getTotalKeyTerms());
			
			System.out.println("now author");
			System.out.println();
			
			reader = new IndexReader(indexDir, IndexType.AUTHOR);
			System.out.println(reader.getTopK(50));
			System.out.println(reader.getTotalKeyTerms());
			
			System.out.println("now category");
			System.out.println();
			
			reader = new IndexReader(indexDir, IndexType.CATEGORY);
			System.out.println(reader.getTopK(50));
			System.out.println(reader.getTotalKeyTerms());
			*/
			
			long end = System.currentTimeMillis();
			long time = (end - start) / 1000;
			System.out.println(time);
			
		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
