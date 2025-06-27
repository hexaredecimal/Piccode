package org.piccode.rt;

import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import org.piccode.ast.Ast;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class StackFrame {

	private Stack<ConcurrentHashMap<String, PiccodeValue>> scope_stack;

	public Ast caller;

	public StackFrame(Ast caller) {
		this.caller = caller;
		this.scope_stack = new Stack<>();
	}

	public StackFrame(Ast caller, StackFrame from) {
		this.caller = caller;
		this.scope_stack = new Stack<>();
		var clone = (Stack<ConcurrentHashMap<String, PiccodeValue>>) from.scope_stack.clone();
		scope_stack.addAll(clone);
	}

	public void addScope() {
		synchronized (this) {
			if (!scope_stack.isEmpty()) {
				var _top = scope_stack.peek();
				var new_stack = new ConcurrentHashMap<>(_top);
				scope_stack.push(new_stack);
				return;
			}
			scope_stack.push(new ConcurrentHashMap<>());
		}
	}

	public void putLocal(String name, PiccodeValue value) {
		synchronized (this) {
			if (scope_stack.isEmpty()) {
				scope_stack.push(new ConcurrentHashMap<>());
			}
			var frame = scope_stack.peek();
			frame.put(name, value);
		}
	}

	public PiccodeValue getValue(String name) {
		synchronized (this) {
			if (scope_stack.isEmpty()) {
				return null;
			}
			var frame = scope_stack.peek();
			return frame.get(name);
		}
	}

	public ConcurrentHashMap<String, PiccodeValue> toMap() {
		synchronized (this) {
			if (scope_stack.isEmpty()) {
				return new ConcurrentHashMap<>();
			}
			return scope_stack.peek();
		}
	}

	public void dropScope() {
		synchronized (this) {
			if (scope_stack.isEmpty()) {
				return;
			}
			scope_stack.pop();
		}
	}

}
