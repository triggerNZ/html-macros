package com.pavlinic.htmlmacros;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeVisitor;

import com.pavlinic.htmlmacros.dom.DomTraverse;
import com.pavlinic.htmlmacros.dom.Visitor;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class Processor {
	private final ReadableFileSystem fileProvider;
	private final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

	public Processor(ReadableFileSystem fileProvider) {
		this.fileProvider = fileProvider;
	}

	public Document process(Document input) {
		// create a JavaScript engine
		final ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
		final Document doc = input.clone();

		DomTraverse.traverse(doc, new Visitor() {
			@Override
			public void visit(Node node) {
				if (node.hasAttr("data-macro-i18n")) {
					processI18N(node);
				} else if (node.hasAttr("data-macro-inline")) {
					processInline(node);
				} else if (node.hasAttr("data-macro-script")) {
					processScript(node, engine);
				} else if (node.hasAttr("data-macro-bind")) {
					processBind(node, engine);
				}
			}
		});;
		
		return doc;
	}

	protected void processBind(Node node, ScriptEngine engine) {
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

	protected void processScript(Node node, ScriptEngine engine) {
		Element el = (Element) node;
		String text = el.data();
		try {
			engine.eval(text);
			el.remove();
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}

	private void processI18N(Node node) {
		Element el = (Element) node;
		Locale locale = new Locale("en", "US");
		ResourceBundle i18n = fileProvider.i18n(locale);

		String key = el.attr("data-macro-i18n");
		el.attributes().remove("data-macro-i18n");

		el.text(i18n.getString(key));
	}

	private void processInline(Node el) {
		if (el.attr("rel").equals("stylesheet")) {
			String href = el.attr("href");
			String text = fileProvider.contents(href);
			Element styleElement = new Element(Tag.valueOf("style"), "");

			styleElement.text(text);
			el.replaceWith(styleElement);
		}
	}

}
