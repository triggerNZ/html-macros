package com.pavlinic.htmlmacros.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

import com.pavlinic.htmlmacros.Util;


public class FileSystemFileSystem implements ReadableFileSystem, WalkableFileSystem, WritableFileSystem {
	private final File root;

	public FileSystemFileSystem(String rootDirectory) {
		root = new File(rootDirectory);
		validateRoot();
	}
	
	
	private void validateRoot() {
		if (!root.exists() || !root.isDirectory()) {
			throw new RuntimeException("directory " + root + " does not exist or is not a directory");
		}
	}


	@Override
	public String contents(String path) {
		try {
			return Util.readAll(new FileReader(new File(root, path)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResourceBundle i18n(Locale locale) {

		URL resourceURL;
		try {
		   resourceURL  = root.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}           

		URLClassLoader urlLoader = new URLClassLoader(new java.net.URL[]{resourceURL});
		return  java.util.ResourceBundle.getBundle( "i18n", locale, urlLoader);
	}


	@Override
	public void walk(FileVisitor fileVisitor) {
		walk(root, fileVisitor);
	}


	private void walk(File dir, FileVisitor fileVisitor) {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				walk(f, fileVisitor);
			} else {
				String relativePath = root.toURI().relativize(f.toURI()).getPath();
				fileVisitor.visit(relativePath);
			}
		}
	}


	public void write(String path, String contents) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(root, path));
			writer.write(contents);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
		
	}

}
