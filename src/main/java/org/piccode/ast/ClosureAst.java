package org.piccode.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.piccode.piccodescript.TargetEnvironment;
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
		if (args == null || args.isEmpty()) {
			var loc = Ast.getLocation(body);
			this.line = loc.line;
			this.column = loc.col;
		} else {
			var arg = args.getFirst();
			this.line = arg.line;
			this.column = arg.column;
		}
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
		result.callSite = new Ast.Location(line, column);
		result.callSiteFile = file;
		result.file = file;
		result.line = line;
		result.column = column;
		return result;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return switch (target) {
			case JS -> codeGenJsClosure(target);
			default -> "todo";
		};
	}

	private String codeGenJsClosure(TargetEnvironment env) {
		var sb = new StringBuilder();
		if (args.isEmpty()) {
			sb
				.append("(")
				.append(") => {\n")
				.append(body.codeGen(env).indent(4))
				.append("};\n");
			return sb.toString();
		}

		args.forEach(arg -> {
			sb
				.append("(")
				.append(arg.name)
				.append(") => ");
		});

		sb.append("{\n")
			.append(body.codeGen(env).indent(4))
			.append("};\n");
		return sb.toString();
	}
}
