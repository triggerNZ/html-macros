package com.pavlinic.htmlmacros;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import static org.junit.Assert.*;

public class TestUtil {
	public static String resourceAsString(String path) throws IOException {
		return readAll(TestUtil.class.getClassLoader().getResourceAsStream(path));
	}

	public static String readAll(InputStream stream) throws IOException {
		StringBuilder buf = new StringBuilder();
		Reader reader = new BufferedReader(new InputStreamReader(stream));
		
		return readAll(buf, reader);
	}

	public static String readAll(StringBuilder buf, Reader reader) throws IOException {
		int in;
		while ((in = reader.read()) != -1) {
			char ch = (char) in;
			buf.append(ch);
		}
		return buf.toString();
	}

	public static String stripWhitespace(String str) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!Character.isWhitespace(c)) {
				buf.append(c);
			}
		}
		return buf.toString();
	}
	
	public static void assertEqualsIgnoreWhitespace(String a, String b) {
		assertEquals(stripWhitespace(a), stripWhitespace(b));
	}
}
