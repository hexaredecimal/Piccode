package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class CCOperationAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public CCOperationAst(Ast lhs, Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + "." + rhs;
	}
}
