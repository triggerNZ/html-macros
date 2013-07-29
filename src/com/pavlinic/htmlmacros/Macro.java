package com.pavlinic.htmlmacros;

import javax.script.ScriptEngine;

import org.jsoup.nodes.Node;

public interface Macro {
	void handle(Node node, ScriptEngine engine);
}
