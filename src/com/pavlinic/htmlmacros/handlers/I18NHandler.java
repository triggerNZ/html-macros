package com.pavlinic.htmlmacros.handlers;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.script.ScriptEngine;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.pavlinic.htmlmacros.Macro;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class I18NHandler implements Macro {
	private final ReadableFileSystem fileSystem;

	public I18NHandler(ReadableFileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}
	
	@Override
	public void handle(Node node, ScriptEngine engine) {
		Element el = (Element) node;
		Locale locale = new Locale("en", "US");
		ResourceBundle i18n = fileSystem.i18n(locale);

		String key = el.attr("data-macro-i18n");
		el.attributes().remove("data-macro-i18n");

		el.text(i18n.getString(key));
	}

}
