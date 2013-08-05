package com.pavlinic.htmlmacros;

import org.jsoup.nodes.Node;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class PropertyHandler implements Macro {

	public PropertyHandler(ReadableFileSystem fileProvider) {
	}

	@Override
	public void handle(Node node, Context ctx, ScriptableObject scope, PropertyProvider props) {
		String name = node.attr("name");
		String value = node.attr("content");
		props.setProperty(name, value);
		node.remove();
	}

}
