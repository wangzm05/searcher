package org.lucene.searchengine;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

public class IndexHTML {
	public static final String INDEX_STORE_PATH = "D:\\Index";

	public IndexHTML(){}
	
	public void createIndexes() {
		try {
			Date start = new Date();
		
			RAMDirectory ramDir = new RAMDirectory();
			FSDirectory fsDir = FSDirectory.getDirectory(INDEX_STORE_PATH,true);
			
			IndexWriter fsWriter = new IndexWriter(fsDir,new MMAnalyzer(),true);
			IndexWriter ramWriter = new IndexWriter(ramDir,new MMAnalyzer(),true);
			
			indexDocs(ramWriter,new File(HTMLDocument.FILE_STORE_PATH));
			ramWriter.setMergeFactor(500);
			ramWriter.optimize();
			ramWriter.close();
			
			fsWriter.addIndexes(new Directory[]{ramDir});
			fsWriter.optimize();
			fsWriter.close();
			
			Date end = new Date();
			
			System.out.println("Time cost: "+(end.getTime()-start.getTime())/1000.0/60.0+" m");
			
			
		}catch(IOException e) {
			System.out.println("[Error] Index Document failed!");
		}
	}

	private void indexDocs(IndexWriter writer, File file) throws CorruptIndexException, IOException {
		if (file.isDirectory()) {
			String []files = file.list();
			for (String f:files) {
				indexDocs(writer,new File(file,f));
			}
		}
		else if (file.getName().endsWith(".html")||
				 file.getName().endsWith(".htm")||
				 file.getName().endsWith(".shtml")) {
			writer.addDocument(new HTMLDocument().getDocument(file));			
		}
		
	}

}
