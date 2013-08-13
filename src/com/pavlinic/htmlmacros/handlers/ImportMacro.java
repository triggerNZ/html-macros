package com.pavlinic.htmlmacros.handlers;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.pavlinic.htmlmacros.Macro;
import com.pavlinic.htmlmacros.PropertyProvider;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class ImportMacro implements Macro {
	private ReadableFileSystem fileProvider;

	public ImportMacro(ReadableFileSystem fileProvider) {
		this.fileProvider = fileProvider;
	}

	@Override
	public void handle(Node node, Context ctx, ScriptableObject scope, PropertyProvider props) {
		Element el = (Element) node;
		String filename = node.attr("data-macro-import");
		String contents = fileProvider.contents(filename);
		Element parent = el.parent();
		Integer index = el.elementSiblingIndex();
		List<Node> childs = Parser.parseXmlFragment(contents, "");
		el.remove();
		parent.insertChildren(index, childs);
	}

}
