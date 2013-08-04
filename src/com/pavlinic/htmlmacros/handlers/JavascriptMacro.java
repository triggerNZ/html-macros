package com.pavlinic.htmlmacros.handlers;

import org.jsoup.nodes.Node;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

import com.pavlinic.htmlmacros.Macro;

public class JavascriptMacro implements Macro {
	private final Function fn;

	public JavascriptMacro(Function fn) {
		this.fn = fn;
	}

	@Override
	public void handle(Node node, Context ctx, ScriptableObject scope) {
		fn.call(ctx, scope, null, new Object[] {node});
	}

}
