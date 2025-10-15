package org.piccode.typechecker;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author hexaredecimal
 */
public final class SymbolTable<V> {
	private final Stack<HashMap<String, V>> table;

	public SymbolTable() {
		table = new Stack<>();
		pushScope();
	}
	
	public SymbolTable(SymbolTable<V> other) {
		table = new Stack<>();
		pushScope();
		var top = other.table.peek();
		table.push(top);
	}

	public void putSymbol(String name, V value) {
		if (table.isEmpty()) {
			table.push(getEmptyMap());
		}

		var top = table.peek();
		top.put(name, value);
	}

	public V getValueForSymbol(String name) {
		if (table.isEmpty()) {
			return null;
		}
		var top = table.peek();
		return top.get(name);
	}
	
	public void pushScope() {
		if (table.isEmpty()) {
			table.push(getEmptyMap());
			return;
		}

		var top = table.peek();
		var newMap = getEmptyMap();
		newMap.putAll(top);
		table.push(newMap);
	}

	public void dropScope() {
		if (table.isEmpty()) {
			return;
		}

		table.pop();
	}

	public List<V> getAllValues() {
		if (table.isEmpty()) {
			return List.of();
		}

		var top = table.peek();
		return top.values().stream().toList();
	}

	private HashMap<String, V> getEmptyMap() {
		return new HashMap<>();
	}

	public void clear() {
		while (!table.isEmpty()) table.pop();
		table.push(getEmptyMap());
	}

	@Override
	public String toString() {
		var sb = new StringBuilder();
		for (var kv: table.peek().entrySet()) {
			sb.append(kv.getKey()).append(": ").append(kv.getValue()).append("\n");
		}
		return sb.toString();
	}

	
}
