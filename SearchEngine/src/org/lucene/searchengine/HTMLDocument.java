package org.lucene.searchengine;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.*;

public class HTMLDocument {
	static String FILE_STORE_PATH = "C:\\Documents and Settings\\wangzhiming\\workspace\\Heritrix\\jobs\\news_tsinghua-20090506075952921\\mirror";
	
	public Document getDocument(File page) {
		Document doc = new Document();
		
		doc.add(new Field("file",page.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		doc.add(new Field("lastModified",DateTools.timeToString(page.lastModified(), DateTools.Resolution.MINUTE),
				Field.Store.YES,Field.Index.NOT_ANALYZED));
		
		String url = "http:/"+page.getAbsolutePath().substring(FILE_STORE_PATH.length()).replaceAll("\\\\", "/");		
		
		HTMLParser parser = new HTMLParser(page);
		
		doc.add(new Field("url",url,Field.Store.YES,Field.Index.NOT_ANALYZED));
		doc.add(new Field("title",parser.getTitle(),Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("content",parser.getContent(),Field.Store.YES,Field.Index.ANALYZED));

		System.out.println(url);
		System.out.println(parser.getTitle());
		System.out.println(parser.getContent());
		
		printAnalysis(parser.getContent());
		return doc;
	}
	public HTMLDocument() {}
	
	private void printAnalysis(String content) {
		try {
			TokenStream stream = new MMAnalyzer().tokenStream("content", new StringReader(content));
			
			while (true) {
				Token item = stream.next();
				if (item==null) break;
				System.out.print("{"+item.termText()+"}");
			}
			System.out.println();
			
		}catch(IOException e) {}
	}
}
