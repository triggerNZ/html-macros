package com.pavlinic.htmlmacros;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import static com.pavlinic.htmlmacros.TestUtil.*;
import static org.junit.Assert.*;

public class ProcessorTest {
	private Processor proc = new Processor(new ClasspathResourceProvider("testData/"));
	
	@Test
	public void documentWithNoMacroAttributesIsUnchanged() throws IOException {
		String str = resourceAsString("testData/empty.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(str, proc.process(doc).toString());
	}
	
	@Test
	public void inlineCss() throws IOException {
		String str = resourceAsString("testData/cssExternal.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/cssInline.html"), proc.process(doc).toString());
	}
	
	@Test
	public void i18n() throws IOException {
		String str = resourceAsString("testData/beforeI18n.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/afterI18n.html"), proc.process(doc).toString());
	}
}
