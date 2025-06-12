package org.piccode.ast;

import java.util.List;
import org.piccode.piccodescript.Piccode;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.NativeFunction;
import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class CallAst extends Ast {

	public Ast expr;
	public List<Ast> nodes;
	public static Ast lastCall = null;

	public CallAst(Ast expr, List<Ast> nodes) {
		this.expr = expr;
		this.nodes = nodes;
		var loc = Ast.getLocation(expr);
		this.line = loc.line;
		this.column = loc.col;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(expr);
		sb.append(" (");

		for (int i = 0; i < nodes.size(); i++) {
			sb.append(nodes.get(i));
			if (i < nodes.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public PiccodeValue execute() {
		var expr_val = expr.execute();
		if (!(expr_val instanceof PiccodeClosure) && !(expr_val instanceof NativeFunction)) {
			throw new PiccodeException(file, line, column, "Attempt to call a non-callable expression");
		}
		lastCall = expr;

		if (expr_val instanceof NativeFunction nat) {
			for (var node : nodes) {
				if (node instanceof NamedCallArg named) {
					nat = (NativeFunction) nat.callNamed(named.name, named.value.execute());
				} else {
					nat = (NativeFunction) nat.call(node.execute());
				}
			}

			var result = nat.evaluateIfReady();
			return result;
		}

		var closure = (PiccodeClosure) expr_val;
		closure.callSite = new Ast.Location(line, column);
		closure.callSiteFile = file;
		
		for (var node : nodes) {
			if (node instanceof NamedCallArg named) {
				closure = (PiccodeClosure) closure.callNamed(named.name, named.value.execute());
				closure.callSite = new Ast.Location(line, column);
				closure.callSiteFile = file;
			} else {
				closure = (PiccodeClosure) closure.call(node.execute());
				closure.callSite = new Ast.Location(line, column);
				closure.callSiteFile = file;
			}
		}

		// Evaluate only if fully applied
		var result = closure.evaluateIfReady();
		return result;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return switch (target) {
			case JS -> codegenJSCall(target);
			default -> "todo";
		};
	}

	private String codegenJSCall(TargetEnvironment env) {
		var sb = new StringBuilder()
			.append("(")
			.append(expr.codeGen(env))
			.append(")");

		nodes.forEach(node -> {
			sb
				.append("(")
				.append(node.codeGen(env))
				.append(")");
		});
		
		return sb.toString();
	}

}
