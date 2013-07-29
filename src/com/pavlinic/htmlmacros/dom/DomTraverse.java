package com.pavlinic.htmlmacros.dom;

import org.jsoup.nodes.Element;
/**
 * Allows traversal of nodes with deletions and replacements. Preorder traversal. 
 * @author Tin
 *
 */
public class DomTraverse {
	public static void traverse(Element node, Visitor visitor) {
		visitor.visit(node);
		node = reload(node);
		for (int i = 0; i < node.children().size();) {
			int prevSize = node.children().size();
			Element child = node.child(i);
			traverse(child, visitor);
			int afterSize = node.children().size();
			if (prevSize == afterSize) {
				i++; //Node has been updated, move along
			} else if (prevSize > afterSize) {
				//A node deleted itself. i is now pointing at the next thing so no need to increment
			} else {
				throw new RuntimeException("A macro has added a sibling. This is not allowed");
			}
		}
	}
	
	public static Element reload(Element node) {
		int index = node.elementSiblingIndex();
		Element parent = node.parent();
		if (parent == null) {
			return node; //this is the best we can do
		}
		return parent.child(index);
	}
}
