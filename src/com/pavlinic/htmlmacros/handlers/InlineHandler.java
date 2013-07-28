package com.pavlinic.htmlmacros.handlers;

import javax.script.ScriptEngine;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;

import com.pavlinic.htmlmacros.Handler;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class InlineHandler implements Handler {
	private final ReadableFileSystem fs;

	public InlineHandler(ReadableFileSystem fs) {
		this.fs = fs;
	}

	@Override
	public void handle(Node el, ScriptEngine engine) {
		if (el.attr("rel").equals("stylesheet")) {
			String href = el.attr("href");
			String text = fs.contents(href);
			Element styleElement = new Element(Tag.valueOf("style"), "");

			styleElement.text(text);
			el.replaceWith(styleElement);
		}
	}

}
