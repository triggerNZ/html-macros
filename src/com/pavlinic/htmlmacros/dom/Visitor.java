package com.pavlinic.htmlmacros.dom;

import org.jsoup.nodes.Node;

public interface Visitor {
	public void visit(Node node);
}
