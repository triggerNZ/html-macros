package com.pavlinic.htmlmacros;

import static org.junit.Assert.assertEquals;

public class TestUtil {
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
