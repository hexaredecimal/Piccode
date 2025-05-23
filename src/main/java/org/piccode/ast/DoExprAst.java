package org.piccode.ast;

import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class DoExprAst implements Ast {
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

}
