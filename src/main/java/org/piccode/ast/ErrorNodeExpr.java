package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class ErrorNodeExpr extends Ast {
	public String message;

	public ErrorNodeExpr(String message) {
		this.message = message;
	}
}
