package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class IfExpression extends Ast{
	public Ast cond, then, elze;

	public IfExpression(Ast cond, Ast then, Ast elze) {
		this.cond = cond;
		this.then = then;
		this.elze = elze;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("if ").append(cond).append(" {\n");
		sb.append(then.toString().indent(4));
		sb.append(" else {\n");
		sb.append(elze.toString().indent(4));
		sb.append("}");

		return sb.toString();
	}
}
