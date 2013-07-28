package com.pavlinic.htmlmacros;

import java.util.Locale;
import java.util.ResourceBundle;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class Processor {
	private final ReadableFileSystem fileProvider;

	public Processor(ReadableFileSystem fileProvider) {
		this.fileProvider = fileProvider;
	}

	public Document process(Document input) {
		Document doc = input.clone();

		doc.traverse(new NodeVisitor() {
			@Override
			public void tail(Node node, int depth) {
				if (node.hasAttr("data-macro-i18n")) {
					processI18N(node);
				} else if (node.hasAttr("data-macro-inline")) {
					processInline(node);
				}
			}

			@Override
			public void head(Node node, int depth) {

			}
		});

		return doc;
	}

	private void processI18N(Node node) {
		Element el = (Element) node;
		Locale locale = new Locale("en", "US");
		ResourceBundle i18n = fileProvider.i18n(locale);

		String key = el.attr("data-macro-i18n");
		el.attributes().remove("data-macro-i18n"); // el.removeAttr does not work!!! Blame jsoup

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
