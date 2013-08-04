package com.pavlinic.htmlmacros;

import org.jsoup.nodes.Node;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

public interface Macro {
	void handle(Node node, Context ctx, ScriptableObject scope);
}
