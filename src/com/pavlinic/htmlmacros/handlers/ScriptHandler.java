package com.pavlinic.htmlmacros.handlers;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.pavlinic.htmlmacros.Macro;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class ScriptHandler implements Macro {

	public ScriptHandler(ReadableFileSystem fileProvider) {
	}

	@Override
	public void handle(Node node, ScriptEngine engine) {
		Element el = (Element) node;
		String text = el.data();
		try {
			engine.eval(text);
			el.remove();
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}

}
