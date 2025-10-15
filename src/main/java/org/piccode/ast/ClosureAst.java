package org.piccode.ast;

import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class ClosureAst extends Ast {
	public List<Ast> args;
	public Ast body;

	public ClosureAst(List<Ast> args, Ast body) {
		this.args = args;
		this.body = body;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("")
		  .append("(");
		if (args != null) {
			sb.append(formatArgs());
		} 
		sb.append(") -> ...");
		return sb.toString();
	}

	private String formatArgs() {
		var sb = new StringBuilder();
		var size = args.size();
		for (int i = 0; i < size; i++) {
			var top_arg = args.get(i);
			sb.append(top_arg);
			if (i < size - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}
