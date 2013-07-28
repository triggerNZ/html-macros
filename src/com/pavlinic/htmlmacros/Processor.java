package com.pavlinic.htmlmacros;

import java.util.Locale;
import java.util.ResourceBundle;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class Processor {
	private final FileProvider fileProvider;

	public Processor(FileProvider fileProvider) {
		this.fileProvider = fileProvider;
	}
	
	public Document process(Document input) {
		Document doc = input.clone();
		processInline(doc);
		processI18N(doc);
		return doc;
	}

	private void processI18N(Document doc) {
		Elements allI18n = doc.select("[data-macro-i18n]");
		Locale locale = new Locale("en", "US");
		ResourceBundle i18n = fileProvider.i18n(locale);

		for (Element el : allI18n) {
			String key = el.attr("data-macro-i18n");
			el.attributes().remove("data-macro-i18n");  //el.removeAttr does not work!!! Blame jsoup
			
			el.text(i18n.getString(key));
		}
	}

	private void processInline(Document doc) {
		Elements allInline = doc.select("[data-macro-inline]");
		for (Element el : allInline) {
			if (el.attr("rel").equals("stylesheet")) {
				String href = el.attr("href");
				String text = fileProvider.contents(href);
				Element styleElement = new Element(Tag.valueOf("style"), "");
				
				styleElement.text(text);
				el.replaceWith(styleElement);
			}
		}
	}

}
