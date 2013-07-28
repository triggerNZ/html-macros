package com.pavlinic.htmlmacros;

import javax.script.ScriptEngine;

import org.jsoup.nodes.Node;

public interface Handler {
	void handle(Node node, ScriptEngine engine);
}
