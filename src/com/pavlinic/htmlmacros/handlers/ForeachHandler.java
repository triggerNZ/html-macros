package com.pavlinic.htmlmacros.handlers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.ScriptableObject;


import com.pavlinic.htmlmacros.Macro;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class ForeachHandler implements Macro {
	public ForeachHandler(ReadableFileSystem fileProvider) {
	}

	@Override
	public void handle(Node node, Context ctx, ScriptableObject scope) {
		Element el = (Element) node;
		String expr = el.attr("data-macro-foreach");
		Object o = ctx.evaluateString(scope, expr, "", 0, null);
		if (!(o instanceof NativeArray)) {
			throw new RuntimeException(
					"foreach must evaluate to an array, got " + o.getClass());
		}
		Element tmp = el.clone();
		el.empty();
		el.append("<script type=\"text/javascript\" data-macro-script> var $data; </script>");
		NativeArray array = (NativeArray) o;
		int size = array.size();
		for (int i = 0; i < size; i++) {
			Object data = array.get(i);
			for (Element e : tmp.children()) {
				el.append("<script type=\"text/javascript\" data-macro-script> $data = '"
						+ data + "'; </script>");
				el.appendChild(e.clone());
			}
		}
		el.attributes().remove("data-macro-foreach");
	}

}
