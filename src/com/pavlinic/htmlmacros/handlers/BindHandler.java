package com.pavlinic.htmlmacros.handlers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.pavlinic.htmlmacros.Macro;
import com.pavlinic.htmlmacros.PropertyProvider;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class BindHandler implements Macro {

	public BindHandler(ReadableFileSystem fileSystem) {
	}

	@Override
	public void handle(Node node, Context ctx, ScriptableObject scope, PropertyProvider props) {
		final Element el = (Element) node;
		final String expr = el.attr("data-macro-bind");
		final Object value = ctx.evaluateString(scope, expr, "", 0, null);
		if (value != null) {
			el.text(value.toString());
		}
	}

}
