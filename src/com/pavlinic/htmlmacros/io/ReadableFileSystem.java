package com.pavlinic.htmlmacros.io;

import java.util.Locale;
import java.util.ResourceBundle;

public interface ReadableFileSystem {
	String contents(String path);
	ResourceBundle i18n(Locale locale);
}
