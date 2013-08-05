package com.pavlinic.htmlmacros;

import static com.pavlinic.htmlmacros.TestUtil.assertEqualsIgnoreWhitespace;
import static com.pavlinic.htmlmacros.Util.resourceAsString;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import com.pavlinic.htmlmacros.io.ClasspathFileSystem;

public class I18NTest {
	private Processor proc;
	
	@Before
	public void setUp() {
		 proc = new Processor(new ClasspathFileSystem("testData/"));
	}
	
	@Test
	public void defaultLocale() throws IOException {
		String str = resourceAsString("testData/i18n.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/i18n.out.html"), proc.process(doc).toString());
	}
	
	@Test
	public void specifiedLocale() throws IOException {
		proc.setProperty("i18n.locale", "hr-HR");
		
		String str = resourceAsString("testData/i18n.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/i18n.hr.out.html"), proc.process(doc).toString());
	}
	@Test
	public void localeSpecifiedInDocument() throws IOException {
		
		String str = resourceAsString("testData/i18n.hr.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/i18n.hr.out.html"), proc.process(doc).toString());
	}
}
