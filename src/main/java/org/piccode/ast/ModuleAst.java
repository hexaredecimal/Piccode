package org.piccode.ast;

import java.util.List;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeModule;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ModuleAst extends Ast {
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

	@Override
	public String codeGen(TargetEnvironment target) {
		var sb = new StringBuilder();
		sb.append("var ")
			.append(name)
			.append(" = {\n");

		int exprs = 0;
		for (var node: nodes) {
			if (node instanceof FunctionAst func) {
				sb
					.append(func.name)
					.append(":")
					.append(func.codeGen(target));
				continue;
			}
			
			if (node instanceof VarDecl vardel) {
				sb
					.append(vardel.name)
					.append(":")
					.append(vardel.codeGen(target));
				continue;
			}
			
			sb
				.append(String.format("_%s:", exprs++))
				.append(node.codeGen(target));
		}

		sb.append("}\n");
		return sb.toString();
	}
	
}
