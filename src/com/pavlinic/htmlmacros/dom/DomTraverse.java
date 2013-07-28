package com.pavlinic.htmlmacros.dom;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
/**
 * Allows traversal of nodes with deletions and replacements. Preorder traversal. 
 * @author Tin
 *
 */
public class DomTraverse {
	public static void traverse(Node node, Visitor visitor) {
		visitor.visit(node);
		if (exists(node)) {
			for (Node child : node.childNodes()) {
				traverse(child, visitor);
			}
		}
	}
	
	public static boolean exists(Node node) {
		int index = node.siblingIndex();
		Node parent = node.parent();
		
		if (parent == null) {
			return node instanceof Document; //Only root elements have no parents, the others must have been removed from the DOM
		}
		
		return node == parent.childNode(index);
	}
}
