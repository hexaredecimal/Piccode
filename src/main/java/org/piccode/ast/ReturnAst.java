package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class ReturnAst extends Ast {
	public Ast expr;

	public ReturnAst(Ast expr) {
		this.expr = expr;
	}

	


	@Override
	public String toString() {
		return "return " + expr;
	}

}
