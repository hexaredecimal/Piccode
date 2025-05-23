package org.piccode.ast;

import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeModule;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ModuleAst implements Ast {
	public String name;
	public List<Ast> nodes;

	public ModuleAst(String name, List<Ast> nodes) {
		this.name = name;
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("module ");
		sb.append(name).append(" {\n");
		for (var node: nodes) {
			sb.append(node.toString().indent(4));
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public PiccodeValue execute() {
		if (Context.modules.containsKey(name)) {
			var mod = Context.modules.get(name);
			mod.nodes.addAll(nodes);
			return mod;
		} else {
			var module = new PiccodeModule(name, nodes);
			Context.modules.put(name, module);
			return module;
		}
	}
	
}
