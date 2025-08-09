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
			if (!(rhs instanceof CallAst) && !(rhs instanceof IdentifierAst) && !(rhs instanceof CCOperationAst)  && !(rhs instanceof ClosureAst)) {
				var err = new PiccodeException(file, line, column, "Invalid expression at the right side of |> : " + rhs.toString());
				err.frame = frame;
				throw err;
			}

			var newArgs = new ArrayList<Ast>();
			newArgs.add(lhs);
			if (rhs instanceof IdentifierAst id) {
				var call = new CallAst(id, newArgs);
				var node = Ast.finalizeNode(call, id);
				return node.execute(frame);
			}

			if (rhs instanceof ClosureAst closure) {
				var call = new CallAst(closure, newArgs);
				var node = Ast.finalizeNode(call, closure);
				return node.execute(frame);
			}

			if (rhs instanceof CCOperationAst dot) {
				if (dot.rhs instanceof CallAst call) {
					if (call.nodes != null) {
						newArgs.addAll(call.nodes);
					}
					var _call = new CallAst(call.expr, newArgs);
					var nd = new CCOperationAst(dot.lhs, _call);
					var node = Ast.finalizeNode(nd, rhs);
					return node.execute(frame);
				}

				if (dot.rhs instanceof IdentifierAst id) {
					var call = new CallAst(id, newArgs);
					var nd = new CCOperationAst(dot.lhs, call);
					var node = Ast.finalizeNode(nd, id);
					return node.execute(frame);
				}

				var err = new PiccodeException(file, line, column, "Invalid expression at the right side of |> : " + dot.rhs);
				err.frame = frame;
				throw err;
			}

			var call = (CallAst) rhs;
			newArgs.addAll(call.nodes);
			var _call = new CallAst(call, newArgs);
			return _call.execute(frame);
		});
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}
}
