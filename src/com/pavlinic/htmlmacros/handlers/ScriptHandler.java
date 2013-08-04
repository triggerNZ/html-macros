package com.pavlinic.htmlmacros.handlers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.pavlinic.htmlmacros.Macro;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class ScriptHandler implements Macro {

	public ScriptHandler(ReadableFileSystem fileProvider) {
	}

	@Override
	public void handle(Node node, Context ctx, ScriptableObject scope) {
		Element el = (Element) node;
		String text = el.data();
		ctx.evaluateString(scope, text, "", 0, null);
		el.remove();
	}

}
