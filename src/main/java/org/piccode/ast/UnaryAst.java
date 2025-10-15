package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class UnaryAst extends Ast{
	public String op;
	public Ast expr;

	public UnaryAst(String op, Ast expr) {
		this.op = op;
		this.expr = expr;
	}

	@Override
	public String toString() {
		return String.format("%s %s", op, expr);
	}
	
}
