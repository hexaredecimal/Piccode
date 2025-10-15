package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class PipeAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public PipeAst(Ast lhs, Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " |> " + rhs;
	}
}
