package org.piccode.ast;

import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class PipeAst implements Ast {
	public Ast lhs;
	public Ast rhs;

	public PipeAst(Ast lhs,Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " |> " + rhs;
	}

	@Override
	public PiccodeValue execute() {
		if (!(rhs instanceof CallAst) && !(rhs instanceof IdentifierAst)) {
			throw new PiccodeException("Invalid expression at the right side of |> " + rhs.toString());
		}

		if (rhs instanceof IdentifierAst id) {
			var res = id.execute();
			if (res instanceof PiccodeClosure closure) {
				var left = lhs.execute();
				return closure.call(left);
			} else {
				throw new PiccodeException("Invalid expression at the right side of |> : " + id.text);
			}
		}

		var call = (CallAst) rhs; 
		call.nodes.addFirst(lhs);
		return call.execute();
	}
}
