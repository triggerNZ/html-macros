package com.pavlinic.htmlmacros.js;

import org.mozilla.javascript.Function;

import com.pavlinic.htmlmacros.MacroRegister;
import com.pavlinic.htmlmacros.handlers.JavascriptMacro;


public class MacroObject {
	private final MacroRegister register;
	public MacroObject(MacroRegister register) {
		this.register = register;
	}
	public void register(String name, Function fn) {
		register.registerMacro(name, new JavascriptMacro(fn));
	}
}
