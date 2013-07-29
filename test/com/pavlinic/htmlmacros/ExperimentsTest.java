package com.pavlinic.htmlmacros;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class ExperimentsTest {
	@Test
	public void replaceDoc() {
		Document doc = Jsoup.parse("<html></html>");
		try {
			doc.replaceWith(doc); 
		} catch (IllegalArgumentException e) {
			//root cannot be replaced
			
		}
	}
}
