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
	public PiccodeValue execute(Integer frame) {
		var expr_val = expr.execute(frame);
		if (!(expr_val instanceof PiccodeClosure) && !(expr_val instanceof NativeFunction)) {
			var err = new PiccodeException(file, line, column, "Attempt to call a non-callable expression. Issue is: " + expr + " = " + expr_val);
			err.frame = frame;
			throw err;
		}
		lastCall = expr;

		if (expr_val instanceof NativeFunction nat) {
			nat.frame = frame;
			nat.file = file;
			nat.line = line;
			nat.column = column;
			for (var node : nodes) {
				if (node instanceof NamedCallArg named) {
					nat = (NativeFunction) nat.callNamed(named.name, named.value.execute(frame));
					nat.frame = frame;
					nat.file = file;
					nat.line = line;
					nat.column = column;
				} else {
					nat = (NativeFunction) nat.call(node.execute(frame));
					nat.frame = frame;
					nat.file = file;
					nat.line = line;
					nat.column = column;
				}
			}

			var result = nat.evaluateIfReady();
			return result;
		}

		var closure = (PiccodeClosure) expr_val;
		closure.callSite = new Ast.Location(line, column);
		closure.callSiteFile = file;
		closure.frame = frame;
		
		for (var node : nodes) {
			if (node instanceof NamedCallArg named) {
				closure = (PiccodeClosure) closure.callNamed(named.name, named.value.execute(frame));
				closure.callSite = new Ast.Location(line, column);
				closure.callSiteFile = file;
				closure.frame = frame;
			} else {
				closure = (PiccodeClosure) closure.call(node.execute(frame));
				closure.callSite = new Ast.Location(line, column);
				closure.callSiteFile = file;
				closure.frame = frame;
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
