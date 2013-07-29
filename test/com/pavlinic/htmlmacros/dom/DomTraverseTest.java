package com.pavlinic.htmlmacros.dom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.junit.Test;


public class DomTraverseTest {
	@Test
	public void traverseNoChildren() {
		Document parse = Jsoup.parseBodyFragment("<title>Empty document</title>");
		DomTraverse.traverse(parse, new Visitor() {
			
			@Override
			public void visit(Node node) {
				
			}
		});
	}

}
