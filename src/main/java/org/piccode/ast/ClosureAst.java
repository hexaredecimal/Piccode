package org.piccode.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ClosureAst extends Ast {
	public List<Arg> args;
	public Ast body;

	public ClosureAst(List<Arg> args, Ast body) {
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
			sb.append(args);
		}
		sb.append(") => ");
		sb.append(body);
		return sb.toString();
	}

	@Override
	public PiccodeValue execute() {
		Map<String, PiccodeValue> newArgs = new HashMap<>();
		var result = new PiccodeClosure(args, newArgs, 0, body);
		result.file = file;
		result.line = line;
		result.column = column;
		return result;
	}

}
