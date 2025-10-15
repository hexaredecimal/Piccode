package org.piccode.ast;

import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class CallAst extends Ast {

	public Ast expr;
	public List<Ast> nodes;
	public static Ast lastCall = null;

	public CallAst(Ast expr, List<Ast> nodes) {
		this.expr = expr;
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(expr);
		sb.append(" (");

		for (int i = 0; i < nodes.size(); i++) {
			sb.append(nodes.get(i));
			if (i < nodes.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
		return sb.toString();
	}
}
