package org.piccode.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class ModuleAst extends Ast {

	public String name;
	public List<Ast> nodes;
	public boolean createSymbol;

	public ModuleAst(String name, List<Ast> nodes) {
		this.name = name;
		this.nodes = nodes;
		this.createSymbol = true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("module ");
		sb.append(name).append(" {\n");
		for (var node : nodes) {
			sb.append(node.toString().indent(4));
		}
		sb.append("}");
		return sb.toString();
	}

	private void processNodes() {
		HashMap<String, FunctionAst> funcs = new HashMap<>();
		List<Ast> newNodes = new ArrayList<>();
		for (var node : nodes) {
			if (node instanceof FunctionAst func) {
				if (funcs.containsKey(func.name)) {
					var fx = funcs.get(func.name);
					var clause = new ClauseAst(func.arg, func.body);
					// fx.clauses.add(clause);
					funcs.put(func.name, fx);
				} else {
					funcs.put(func.name, func);
				}
			} else {
				newNodes.add(node);
			}
		}

		if (funcs.isEmpty()) {
			return;
		}

		for (var fx : funcs.values()) {
			newNodes.add(fx);
		}
		nodes = newNodes;
	}

}
