package org.piccode.rt;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class Context {
	private static HashMap<String, PiccodeValue> global_scope = new HashMap<>();
	private Stack<StackFrame> call_frames;

	public static Context top = new Context();
	public static HashMap<String, PiccodeModule> modules = new HashMap<>();
	private HashMap<String, List<Ast>> import_cache = new HashMap<>();
	
	public Context() {
		call_frames = new Stack<>();
		import_cache = new HashMap<>();
	}

	public Stack<StackFrame> getCallStack() {
		return call_frames;
	}

	public static void addGlobal(String name, PiccodeValue value) {
		global_scope.put(name, value);
	}

	public boolean isImportCached(String path) {
		return import_cache.containsKey(path);
	}

	public void cacheImport(String path, List<Ast> nodes) {
		import_cache.put(path, nodes);
	}

	public List<Ast> getCached(String path) {
		return import_cache.get(path);
	}
	
	
	public void pushScope() {
		call_frames.peek().addScope();
	}

	public void dropScope() {
		call_frames.peek().dropScope();
	}
	
	public void pushStackFrame(Ast top) {
		call_frames.push(new StackFrame(top));
	}

	public void dropStackFrame() {
		call_frames.pop();
	}

	public void putLocal(String name, PiccodeValue value) {
		var frame = call_frames.peek();
		frame.putLocal(name, value);
	}

	public PiccodeValue getValue(String name) {
		if (call_frames.isEmpty()) {
			return global_scope.get(name);
		}
		var frame = call_frames.peek();
		var value = frame.getValue(name);
		if (value == null) {
			return global_scope.get(name);
		}
		return value;
	}


	public String getSimilarName(String id) {
		HashMap<String, Integer> calculations = new HashMap<>();
		var top = call_frames.peek().toMap();
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
