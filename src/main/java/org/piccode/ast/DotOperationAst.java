package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class DotOperationAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public DotOperationAst(Ast lhs, Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + "." + rhs;
	}

}
