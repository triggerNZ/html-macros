package com.pavlinic.htmlmacros;

import org.jsoup.nodes.Document;

import com.pavlinic.htmlmacros.io.FileSystemFileSystem;
import com.pavlinic.htmlmacros.io.FileVisitor;

public class Main {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java -jar htmlmacros.jar <input-dir> <output-dir>");
			System.exit(1);
		}
		final FileSystemFileSystem in = new FileSystemFileSystem(args[0]);
		final FileSystemFileSystem out = new FileSystemFileSystem(args[1]);
		final Processor processor = new Processor(in);
		
		in.walk(new FileVisitor() {
			@Override
			public void visit(String path) {
				final String contents = in.contents(path);
				if (Html.isHtmlFile(path)) {
					Document processed = processor.process(Html.parse(contents));
					out.write(path, processed.toString());
				} else {
					out.write(path, contents);
				}
			}
		});
	}
}
