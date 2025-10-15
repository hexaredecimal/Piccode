package org.piccode.ast;

import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class ArrayAst extends Ast {
	public List<Ast> nodes; 

	public ArrayAst(List<Ast> nodes) {
		this.nodes = nodes;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		for (int i = 0; i < nodes.size(); i++) {
			sb.append(nodes.get(i));
			if (i < nodes.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
