package com.pavlinic.htmlmacros;

import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import com.pavlinic.htmlmacros.dom.DomTraverse;
import com.pavlinic.htmlmacros.dom.Visitor;
import com.pavlinic.htmlmacros.handlers.BindHandler;
import com.pavlinic.htmlmacros.handlers.ForeachHandler;
import com.pavlinic.htmlmacros.handlers.I18NHandler;
import com.pavlinic.htmlmacros.handlers.InlineHandler;
import com.pavlinic.htmlmacros.handlers.ScriptHandler;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;

public class Processor {
	private final ReadableFileSystem fileProvider;
	private final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	private final Map<String, Macro> handlers = new HashMap<>();
	
	public Processor(ReadableFileSystem fileProvider) {
		this.fileProvider = fileProvider;
		
		registerHandlers();
	}

	private void registerHandlers() {
		registerHandler("i18n", new I18NHandler(fileProvider));
		registerHandler("inline", new InlineHandler(fileProvider));
		registerHandler("script", new ScriptHandler(fileProvider));
		registerHandler("bind", new BindHandler(fileProvider));
		registerHandler("foreach", new ForeachHandler(fileProvider));
	}

	private void registerHandler(String tag, Macro handler) {
		handlers.put("data-macro-" + tag, handler);
	}

	public Document process(Document input) {
		// create a JavaScript engine
		final ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
		final Document doc = input.clone();

		DomTraverse.traverse(doc, new Visitor() {
			@Override
			public void visit(Node node) {
				for (String key : handlers.keySet()) {
					if (node.hasAttr(key)) {
						Macro handler = handlers.get(key);
						handler.handle(node, engine);
					}
				}
			}
		});;
		
		return doc;
	}
}
