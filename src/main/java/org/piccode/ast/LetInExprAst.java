package org.piccode.ast;

import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class LetInExprAst extends Ast {

	public List<Ast> vars;
	public Ast expr;

	public LetInExprAst(List<Ast> vars, Ast expr) {
		this.vars = vars;
		this.expr = expr;
	}

	@Override
	public String toString() {
		var sb = new StringBuilder();
		sb.append("let\n");
		for (var decl : vars) {
			sb.append(decl.toString().indent(4));
		}
		sb.append("in\n");
		sb.append(expr.toString().indent(4));
		return sb.toString();
	}

}
