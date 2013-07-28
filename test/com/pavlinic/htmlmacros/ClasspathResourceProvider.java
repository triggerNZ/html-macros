package com.pavlinic.htmlmacros;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClasspathResourceProvider implements FileProvider {

	private final String basePath;

	public ClasspathResourceProvider(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public String contents(String href) {
		try {
			return TestUtil.resourceAsString(basePath + href);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResourceBundle i18n(Locale locale) {
		return ResourceBundle.getBundle(basePath +  "/i18n", locale);
	}

}
