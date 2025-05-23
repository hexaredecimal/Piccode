package org.piccode.ast;

import java.util.List;
import org.piccode.piccodescript.Piccode;
import org.piccode.rt.NativeFunction;
import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class CallAst implements Ast {

	public Ast expr;
	public List<Ast> nodes;

	public CallAst(Ast expr, List<Ast> nodes) {
		this.expr = expr;
		this.nodes = nodes;
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
			throw new PiccodeException("Attempt to call a non-callable expression");
		}

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
		for (var node : nodes) {
			if (node instanceof NamedCallArg named) {
				closure = (PiccodeClosure) closure.callNamed(named.name, named.value.execute());
			} else {
				closure = (PiccodeClosure) closure.call(node.execute());
			}
		}

		// Evaluate only if fully applied
		var result = closure.evaluateIfReady();
		return result;
	}

}
