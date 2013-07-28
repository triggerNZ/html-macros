package com.pavlinic.htmlmacros.handlers;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.pavlinic.htmlmacros.Handler;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class BindHandler implements Handler {

	public BindHandler(ReadableFileSystem fileSystem) {
	}

	@Override
	public void handle(Node node, ScriptEngine engine) {
		final Element el = (Element) node;
		final String expr = el.attr("data-macro-bind");
		try {
			final Object value = engine.eval(expr);
			if (value != null) {
				el.text(value.toString());
			}
			el.attributes().remove("data-macro-bind");
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}

}
