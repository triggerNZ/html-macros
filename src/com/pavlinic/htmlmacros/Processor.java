package com.pavlinic.htmlmacros;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.pavlinic.htmlmacros.dom.DomTraverse;
import com.pavlinic.htmlmacros.dom.Visitor;
import com.pavlinic.htmlmacros.handlers.BindHandler;
import com.pavlinic.htmlmacros.handlers.ForeachHandler;
import com.pavlinic.htmlmacros.handlers.I18NHandler;
import com.pavlinic.htmlmacros.handlers.InlineHandler;
import com.pavlinic.htmlmacros.handlers.ScriptHandler;
import com.pavlinic.htmlmacros.io.ReadableFileSystem;
import com.pavlinic.htmlmacros.js.MacroObject;

public class Processor implements MacroRegister {
	private final ReadableFileSystem fileProvider;
	private final Map<String, Macro> macros = new HashMap<>();
	
	public Processor(ReadableFileSystem fileProvider) {
		this.fileProvider = fileProvider;
		
		registerDefaultHandlers();
	}

	private void registerDefaultHandlers() {
		registerMacro("i18n", new I18NHandler(fileProvider));
		registerMacro("inline", new InlineHandler(fileProvider));
		registerMacro("script", new ScriptHandler(fileProvider));
		registerMacro("bind", new BindHandler(fileProvider));
		registerMacro("foreach", new ForeachHandler(fileProvider));
	}

	public void registerMacro(String tag, Macro handler) {
		macros.put("data-macro-" + tag, handler);
	}

	public Document process(Document input) {
		// create a JavaScript engine
		final Context ctx = Context.enter();
		final ScriptableObject scope = initEnvironment(ctx);
		final Document doc = input.clone();

		DomTraverse.traverse(doc, new Visitor() {
			@Override
			public void visit(Node node) {
				for (String key : macros.keySet()) {
					if (node.hasAttr(key)) {
						Macro macro = macros.get(key);
						macro.handle(node, ctx, scope);
						node.attributes().remove(key);
					}
				}
			}
		});;
		
		return doc;
	}

	private ScriptableObject initEnvironment(Context engine) {
		ScriptableObject scope = engine.initStandardObjects();
		ScriptableObject.putProperty(scope, "macro", new MacroObject(this));
		return scope;
	}
}
