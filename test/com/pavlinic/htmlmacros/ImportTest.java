package com.pavlinic.htmlmacros;

import static com.pavlinic.htmlmacros.TestUtil.assertEqualsIgnoreWhitespace;
import static com.pavlinic.htmlmacros.Util.resourceAsString;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.pavlinic.htmlmacros.io.ClasspathFileSystem;

public class ImportTest {
	private Processor proc = new Processor(new ClasspathFileSystem("testData/"));

	@Test
	public void import1() throws IOException {
		String str = resourceAsString("testData/import.html");
		Document doc = Jsoup.parse(str);
		assertEqualsIgnoreWhitespace(resourceAsString("testData/empty.html"), proc.process(doc).toString());
	}
}
