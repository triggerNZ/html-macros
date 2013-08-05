package com.pavlinic.htmlmacros.handlers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.pavlinic.htmlmacros.Macro;
import com.pavlinic.htmlmacros.PropertyProvider;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class InlineHandler implements Macro {
	private final ReadableFileSystem fs;

	public InlineHandler(ReadableFileSystem fs) {
		this.fs = fs;
	}

	@Override
	public void  handle(Node el, Context ctx, ScriptableObject scope, PropertyProvider props) {
		if (el.attr("rel").equals("stylesheet")) {
			String href = el.attr("href");
			String text = fs.contents(href);
			Element styleElement = new Element(Tag.valueOf("style"), "");

			styleElement.text(text);
			el.replaceWith(styleElement);
		}
	}

}
