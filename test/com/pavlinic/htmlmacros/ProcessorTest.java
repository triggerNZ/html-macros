package com.pavlinic.htmlmacros;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.pavlinic.htmlmacros.io.ClasspathFileSystem;

import static com.pavlinic.htmlmacros.Util.*;
import static com.pavlinic.htmlmacros.TestUtil.*;

public class ProcessorTest {
	private Processor proc = new Processor(new ClasspathFileSystem("testData/"));
	
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
	
	@Test
	public void scriptAndBind() throws IOException {
		String str = resourceAsString("testData/scriptAndBind.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/scriptAndBind.out.html"), proc.process(doc).toString());
	}
}
