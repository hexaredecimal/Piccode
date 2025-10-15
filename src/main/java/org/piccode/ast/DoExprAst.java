package org.piccode.ast;

import java.util.List;

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
}
