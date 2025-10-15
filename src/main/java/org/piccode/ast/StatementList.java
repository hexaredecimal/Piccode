package org.piccode.ast;

import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class StatementList extends Ast {
	public List<Ast> nodes; 

	public StatementList(List<Ast> nodes) {
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
