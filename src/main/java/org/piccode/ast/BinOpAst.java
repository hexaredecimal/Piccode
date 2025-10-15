package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class BinOpAst extends Ast {

	public Ast lhs;
	public String op;
	public Ast rhs;

	public BinOpAst(Ast lhs, String op, Ast rhs) {
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " " + op + " " + rhs;
	}
}
