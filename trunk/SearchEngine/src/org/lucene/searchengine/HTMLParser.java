package org.lucene.searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLParser {
	String title = new String();
	String content = new String();
	String summary = new String();
	String charset = new String();
	
	private HTMLParser() {}
	
	public HTMLParser(File f) {
		try {
			/**
			 * Read html file
			 */
			BufferedReader reader = new BufferedReader(new FileReader(f));
			
			String buf = new String();
			
			while((buf=reader.readLine())!=null) {
				content += buf;
			}
			reader.close();			
			
			/**
			 *  Get the charset for this html file: 
			 *  default value : gb2312 
			 */
			Pattern regx = Pattern.compile("charset=.+?\">",Pattern.CASE_INSENSITIVE);
			Matcher m = regx.matcher(content);
			
			if (m.find()) {
				charset = m.group().split("\"")[0].substring(8);
			}
			else
				charset = "gb2312";
			
			content = new String(content.getBytes(),charset);
			
			regx = Pattern.compile("<title>.+?</title>",Pattern.CASE_INSENSITIVE);
			m = regx.matcher(content); 
			
			if (m.find()) 
				title = m.group().replaceAll("<\\/?[tT][iI][tT][lL][eE]>", "");
			else
				title = "";
			
			/**
			 *  Filter tags
			 */
			regx = Pattern.compile("<script.+?</script>|<style.+?</style>",Pattern.CASE_INSENSITIVE);			
			content = regx.matcher(content).replaceAll("");
			content = content.replaceAll("<.+?>|[^\u4e00-\u9fa5a-zA-Z0-9 @]|nbsp|lt|gt|quot", "");
			content = content.replaceAll(" +", " ");
			
			//System.out.println(charset);
			
		}catch(IOException e){
			System.out.println("[Error] Cannot load this file: "+f.getName());
		}
	}
	
	private String getCharSet() {		
		return charset;
	}

	public String getTitle(){
		return title;
	}
	public String getContent() {
		return content;	
	}
	public String getSummary() {
		return summary;
	}
}
