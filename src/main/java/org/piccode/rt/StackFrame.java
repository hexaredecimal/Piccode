package org.piccode.rt;

import java.util.HashMap;
import java.util.Stack;
import org.piccode.ast.Ast;
import org.piccode.rt.PiccodeValue;


/**
 *
 * @author hexaredecimal
 */
public class StackFrame {
	private Stack<HashMap<String, PiccodeValue>> scope_stack;

	public Ast caller;

	public StackFrame(Ast caller) {
		this.caller = caller;
		this.scope_stack = new Stack<>();
	}

	public void addScope() {
		if (!scope_stack.isEmpty()) {
			var _top = scope_stack.peek();
			var new_stack = new HashMap<>(_top);
			scope_stack.push(new_stack);
			return;
		}
		scope_stack.push(new HashMap<>());
	}

	public void putLocal(String name, PiccodeValue value) {
		if (scope_stack.isEmpty()) {
			scope_stack.push(new HashMap<>());
		}
		var frame = scope_stack.peek();
		frame.put(name, value);
	}

	public PiccodeValue getValue(String name) {
		if (scope_stack.isEmpty()) {
			return null;
		}
		var frame = scope_stack.peek();
		return frame.get(name);
	}

	public HashMap<String, PiccodeValue> toMap() {
		if (scope_stack.isEmpty()) {
			return new HashMap<>();
		}
		return scope_stack.peek();
	}


	public void dropScope() {
		if (scope_stack.isEmpty())
			return;
		scope_stack.pop();
	}
	
}
