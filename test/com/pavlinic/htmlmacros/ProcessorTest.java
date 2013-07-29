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
		assertEqualsIgnoreWhitespace(resourceAsString("testData/cssExternal.out.html"), proc.process(doc).toString());
	}
	
	@Test
	public void i18n() throws IOException {
		String str = resourceAsString("testData/i18n.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/i18n.out.html"), proc.process(doc).toString());
	}
	
	@Test
	public void scriptAndBind() throws IOException {
		String str = resourceAsString("testData/scriptAndBind.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/scriptAndBind.out.html"), proc.process(doc).toString());
	}
	
	@Test
	public void foreach() throws IOException {
		String str = resourceAsString("testData/foreach.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/foreach.out.html"), proc.process(doc).toString());
	}
	@Test
	public void foreachNoVar() throws IOException {
		String str = resourceAsString("testData/foreachNoVar.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/foreachNoVar.out.html"), proc.process(doc).toString());
	}
}
