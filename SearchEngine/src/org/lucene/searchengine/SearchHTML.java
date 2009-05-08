package org.lucene.searchengine;

import java.io.IOException;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

public class SearchHTML {
	public SearchHTML() {}
	
	public Hits searchHTML(String words) {
		try {
			String keys = words.toLowerCase().replaceAll("(or|and)", "").trim().replaceAll("\\s+", " AND ");
			
			Searcher searcher = new IndexSearcher(IndexHTML.INDEX_STORE_PATH);
			Analyzer analyzer = new MMAnalyzer();
			//String[] fields = {"title","content"};
			//Query query = MultiFieldQueryParser.parse(keys,fields,analyzer);
			System.out.println(keys);
			Query query = new QueryParser("content",analyzer).parse(keys);
			
			Hits hits = searcher.search(query);
			
			return hits;
		}catch (IOException e) {
			System.out.println("[Error]Search index failed! KEYWORDS="+words);
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return null;
	}
}
