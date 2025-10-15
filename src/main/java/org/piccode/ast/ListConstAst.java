package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class ListConstAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public ListConstAst(Ast lhs, Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + ":" + rhs;
	}
}
