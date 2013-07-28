package com.pavlinic.htmlmacros.io;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.pavlinic.htmlmacros.Util;

public class ClasspathFileSystem implements ReadableFileSystem {

	private final String basePath;

	public ClasspathFileSystem(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public String contents(String href) {
		try {
			return Util.resourceAsString(basePath + href);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResourceBundle i18n(Locale locale) {
		return ResourceBundle.getBundle(basePath +  "/i18n", locale);
	}

}
