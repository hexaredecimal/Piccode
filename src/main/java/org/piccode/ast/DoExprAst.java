package org.piccode.ast;

import java.util.List;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class DoExprAst extends Ast {
	public List<Ast> nodes; 

	public DoExprAst(List<Ast> nodes) {
		this.nodes = nodes;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodes.size(); i++) {
			sb.append(nodes.get(i));
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public PiccodeValue execute() {
		PiccodeValue result = null;
		Context.top.pushStack();
		for (var stmt: nodes) {
			result = stmt.execute();
		}
		Context.top.dropStackFrame();
		return result;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return switch (target) {
			case JS -> codeGenJSDoExpr(target);
			default -> "todo";
		};
	}

	private String codeGenJSDoExpr(TargetEnvironment env) {
		var sb = new StringBuilder()
		.append("() => {\n");

		nodes.forEach(node -> {
			sb.append(node.codeGen(env)).append(";");
		});
		
		sb.append("};\n");
		return sb.toString();
	}
}
