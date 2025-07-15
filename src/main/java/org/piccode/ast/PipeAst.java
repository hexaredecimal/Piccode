package org.piccode.ast;

import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeValue;
import java.util.ArrayList;
import org.piccode.piccodescript.TargetEnvironment;

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
	public PiccodeValue execute(Integer frame) {
		return Ast.safeExecute(frame, this, (expr) -> {
			if (!(rhs instanceof CallAst) && !(rhs instanceof IdentifierAst) && !(rhs instanceof CCOperationAst)) {
				var err = new PiccodeException(file, line, column, "Invalid expression at the right side of |> : " + rhs.toString());
				err.frame = frame;
				throw err;
			}

			if (rhs instanceof IdentifierAst id) {
				var res = id.execute(frame);
				if (res instanceof PiccodeClosure closure) {
					var left = lhs.execute(frame);
					return closure.call(left);
				} else {
					var err = new PiccodeException(file, line, column, "Invalid expression at the right side of |> : " + id.text);
					err.frame = frame;
					throw err;
				}
			}

			if (rhs instanceof CCOperationAst dot) {
				if (dot.rhs instanceof CallAst call) {
					if (call.nodes == null) {
						call.nodes = new ArrayList<>();
					}
					call.nodes.addFirst(lhs);
					return rhs.execute(frame);
				}

				if (dot.rhs instanceof IdentifierAst id) {
					var args = new ArrayList<Ast>();
					args.addFirst(lhs);
					var call = new CallAst(id, args);
					dot.rhs = Ast.finalizeNode(call, id);
					return rhs.execute(frame);
				}

				var err = new PiccodeException(file, line, column, "Invalid expression at the right side of |> : " + dot.rhs);
				err.frame = frame;
				throw err;
			}

			var call = (CallAst) rhs;
			call.nodes.addFirst(lhs);
			return call.execute(frame);
		});
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}
}
