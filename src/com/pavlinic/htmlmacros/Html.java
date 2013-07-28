package com.pavlinic.htmlmacros;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Html {

	public static boolean isHtmlFile(String path) {
		return path.endsWith("html");
	}

	public static Document parse(String contents) {
		return Jsoup.parse(contents);
	}

}
