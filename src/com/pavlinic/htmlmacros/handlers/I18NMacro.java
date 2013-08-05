package com.pavlinic.htmlmacros.handlers;

import java.util.Locale;
import java.util.ResourceBundle;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.pavlinic.htmlmacros.Macro;
import com.pavlinic.htmlmacros.PropertyProvider;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class I18NMacro implements Macro {
	private final ReadableFileSystem fileSystem;

	public I18NMacro(ReadableFileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}
	
	@Override
	public void handle(Node node, Context ctx, ScriptableObject scope, PropertyProvider props) {
		Element el = (Element) node;
		
		final Locale locale;
		String localeProperty = props.getProperty("i18n.locale");
		if (localeProperty != null) {
			locale = Locale.forLanguageTag(localeProperty);
		} else {
			locale = Locale.getDefault();
		}
		
//		Locale locale = new Locale("en", "US");
		ResourceBundle i18n = fileSystem.i18n(locale);

		String key = el.attr("data-macro-i18n");

		el.text(i18n.getString(key));
	}

}
