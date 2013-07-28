package com.pavlinic.htmlmacros;

import java.util.Locale;
import java.util.ResourceBundle;

public interface FileProvider {
	String contents(String href);
	ResourceBundle i18n(Locale locale);
}
