package org.piccode.ast;

import java.util.List;


/**
 *
 * @author hexaredecimal
 */
public class WhenAst extends Ast {

	public Ast cond;
	public List<WhenCase> cases;
	public Ast else_case;

	public WhenAst(Ast cond, List<WhenCase> cases, Ast else_case) {
		this.cond = cond;
		this.cases = cases;
		this.else_case = else_case;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("when ").append(cond).append(" {\n");
		for (var when_c : cases) {
			sb.append(when_c.toString().indent(4));
		}
		if (else_case != null) {
			sb.append(String.format("else %s", else_case).indent(4));
		}
		sb.append("}");
		return sb.toString();
	}

}
