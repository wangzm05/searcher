package org.lucene.searchengine;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.Hits;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new IndexHTML().createIndexes();
		Hits hits = new SearchHTML().searchHTML("ด๓ัง");
		
		System.out.println("Search Results:");
		
		for (int i=0;i<hits.length();i++) {
			try {
				System.out.println(hits.doc(i).getField("content"));
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
