package org.piccode.rt;

import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author hexaredecimal
 */
public class Context {
	private static HashMap<String, PiccodeValue> global_scope = new HashMap<>();
	private Stack<HashMap<String, PiccodeValue>> scope_stack;

	public static Context top = new Context();
	public static HashMap<String, PiccodeModule> modules = new HashMap<>();
	
	public Context() {
		scope_stack = new Stack<>();
	}

	public static void addGlobal(String name, PiccodeValue value) {
		global_scope.put(name, value);
	}

	public void pushStack() {
		if (!scope_stack.isEmpty()) {
			var _top = scope_stack.peek();
			var new_stack = new HashMap<>(_top);
			scope_stack.push(new_stack);
			return;
		}
		scope_stack.push(new HashMap<>());
	}

	public void dropStackFrame() {
		scope_stack.pop();
	}

	public void putLocal(String name, PiccodeValue value) {
		var frame = scope_stack.peek();
		frame.put(name, value);
	}

	public PiccodeValue getValue(String name) {
		if (scope_stack.isEmpty()) {
			return global_scope.get(name);
		}
		var frame = scope_stack.peek();
		var value = frame.get(name);
		if (value == null) {
			return global_scope.get(name);
		}
		return value;
	}


	public String getSimilarName(String id) {
		HashMap<String, Integer> calculations = new HashMap<>();
		var top = scope_stack.peek();
		top.putAll(global_scope);
		for (var entry : top.entrySet()) {
			int count = 0;
			String base = entry.getKey();
			try {
				for (int i = 0; i < id.length(); i++) {
					if (id.charAt(i) == base.charAt(i)) {
						count++;
					} else {
						break;
					}
				}
			} catch (Exception ex) {
				// skip
			}
			calculations.put(base, count);
		}

		String result = "";
		int oldCount = 0;
		for (var entry : calculations.entrySet()) {
			String fword = entry.getKey();
			int count = entry.getValue();
			if (count > oldCount) {
				result = fword;
			}
		}
		return result;
	}
}
