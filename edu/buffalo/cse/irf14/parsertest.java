/**
 * 
 */
package edu.buffalo.cse.irf14;

import java.io.File;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;

/**
 * @author nikhillo
 *
 */
public class parsertest {

	/**
	 * 
	 */
	public parsertest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ipDir = "/Users/Girish/Projects/newsindexer/training";
		String indexDir =  "/Users/Girish/Projects/newsindexer/Indexes";
		//more? idk!
		
		File ipDirectory = new File(ipDir);
		String[] catDirectories = ipDirectory.list();
		
		String[] files;
		File dir;
		
		Document d = null;
		IndexWriter writer = new IndexWriter(indexDir);
		int counter=0;
		
		
			for (String cat : catDirectories) {
				dir = new File(ipDir+ File.separator+ cat);
				files = dir.list();
				
				if (files == null)
					continue;
				
				for (String f : files) {
					try {
						d = Parser.parse(dir.getAbsolutePath() + File.separator +f);
						/*if(d.getField(FieldNames.AUTHOR)[0].equals(""))
						{
							//System.out.println(dir.getAbsolutePath() + File.separator +f);
							counter++;
						}*/
						//System.out.println(dir.getAbsolutePath() + File.separator +f + "\t" + d.getField(FieldNames.AUTHOR)[0]);
						/*if (d.getField(FieldNames.AUTHOR) != null)
						{
							counter++;
							System.out.println(dir.getAbsolutePath() + File.separator +f);
							String[] authors = d.getField(FieldNames.AUTHOR);
							for (String author:authors)
							{
								System.out.println(author);
							}
							
						}*/
						/*if(d.getField(FieldNames.NEWSDATE)[0].equals(""))
						{
							System.out.println(dir.getAbsolutePath() + File.separator +f);
							counter++;
						}*/
						/* if(d.getField(FieldNames.PLACE)!= null)
						{
						System.out.println(dir.getAbsolutePath() + File.separator +f + "\t" + d.getField(FieldNames.NEWSDATE)[0] + " " + d.getField(FieldNames.PLACE)[0]);
						}
						else
						{
							counter++;
							System.out.println(dir.getAbsolutePath() + File.separator +f);
						}*/
						
						 if(d.getField(FieldNames.CONTENT)!= null)
						{
						System.out.println(dir.getAbsolutePath() + File.separator +f + "\t" + d.getField(FieldNames.CONTENT)[0]);
						
						}
						else
						{
							counter++;
							System.out.println(dir.getAbsolutePath() + File.separator +f);
						}

						
						//writer.addDocument(d);
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
				//System.out.println(counter);
				
			}
			System.out.println(counter);
			//writer.close();
		}	

}
