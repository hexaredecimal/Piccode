package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class CatchAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public CatchAst(Ast lhs, Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " catch " + rhs;
	}
}
