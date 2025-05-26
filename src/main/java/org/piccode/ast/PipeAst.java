package org.piccode.ast;

import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeValue;
import java.util.ArrayList;

/**
 *
 * @author hexaredecimal
 */
public class PipeAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public PipeAst(Ast lhs, Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " |> " + rhs;
	}

	@Override
	public PiccodeValue execute() {
		if (!(rhs instanceof CallAst) && !(rhs instanceof IdentifierAst) && !(rhs instanceof DotOperationAst)) {
			throw new PiccodeException(file, line, column, "Invalid expression at the right side of |> " + rhs.toString());
		}

		if (rhs instanceof IdentifierAst id) {
			var res = id.execute();
			if (res instanceof PiccodeClosure closure) {
				var left = lhs.execute();
				return closure.call(left);
			} else {
				throw new PiccodeException(file, line, column, "Invalid expression at the right side of |> : " + id.text);
			}
		}

		if (rhs instanceof DotOperationAst dot) {
			if (dot.rhs instanceof CallAst call) {
        if (call.nodes == null) {
          call.nodes = new ArrayList<>();
        }
				call.nodes.addFirst(lhs);
				return rhs.execute();
			}

			if (dot.rhs instanceof IdentifierAst id) {
				var res = id.execute();
				if (res instanceof PiccodeClosure closure) {
					var left = lhs.execute();
					return closure.call(left);
				} else {
					throw new PiccodeException(file, line, column,"Invalid expression at the right side of |> : " + id.text);
				}
			}

			throw new PiccodeException(file, line, column,"Invalid expression at the right side of |> : " + dot.rhs);
		}

		var call = (CallAst) rhs;
		call.nodes.addFirst(lhs);
		return call.execute();
	}
}
