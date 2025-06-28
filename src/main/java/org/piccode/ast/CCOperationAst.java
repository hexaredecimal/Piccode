package org.piccode.ast;

import java.util.concurrent.ExecutionException;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeModule;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeSimpleNote;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class CCOperationAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public CCOperationAst(Ast lhs, Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + "." + rhs;
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		if (lhs instanceof IdentifierAst id && Context.modules.containsKey(id.text)) {
			var mod = Context.modules.get(id.text);

			if (!(rhs instanceof CallAst) && !(rhs instanceof IdentifierAst)) {
				throw new PiccodeException(file, line, column, "No node " + rhs + " found in module " + id.text);
			}

			return process(id, mod, frame);
		}

		var err = new PiccodeException(file, line, column, "Invalid use of `::`. Expected a module on the lhs");
		err.frame = frame;
		throw err;
	}

	private PiccodeValue process(IdentifierAst id, PiccodeModule mod, Integer frame) {
		var ctx = frame == null
			? Context.top
			: Context.getContextAt(frame);
		
		if (rhs instanceof IdentifierAst _id) {
			for (var node : mod.nodes) {
				if (node instanceof VarDecl vd && vd.name.equals(_id.text)) {
					return node.execute(frame);
				}
				if (node instanceof FunctionAst func && func.name.equals(_id.text)) {
					node.execute(frame);
					var result = ctx.getValue(_id.text);
					if (result == null) {
						var err = new PiccodeException(func.file, func.line, func.column, "Function `" + _id.text + "` is not defined");
						err.frame = frame;
						var nm = ctx.getSimilarName(_id.text);
						if (nm != null && !nm.isEmpty()) {
							var note = new PiccodeException(func.file, func.line, func.column, "Maybe you meant `" + nm + "`");
							err.addNote(note);
						}
						throw err;
					}
					return result;
				}
				if (node instanceof ModuleAst _mod && _mod.name.equals(_id.text)) {
					node.execute(frame);
					return Context.modules.get(_id.text);
				}
			}

			var err = new PiccodeException(file, line, column, "No function or identifier " + _id.text + " found in module " + id.text);
			err.frame = frame;
			throw err;
		}

		var call = (CallAst) rhs;

		if (!(call.expr instanceof IdentifierAst)) {
			var err = new PiccodeException(file, line, column, "Invalid function reference in module access module " + id.text + ": " + call.expr);
			err.frame = frame;
			throw err;
		}

		var _id = (IdentifierAst) call.expr;
		for (var node : mod.nodes) {
			if (node instanceof VarDecl vd && vd.name.equals(_id.text)) {
				return node.execute(frame);
			}
			if (node instanceof FunctionAst func && func.name.equals(_id.text)) {
				node.execute(frame);
				//return Context.top.getValue(_id.text);
				return call.execute(frame);
			}
			if (node instanceof ModuleAst _mod && _mod.name.equals(_id.text)) {
				node.execute(frame);
				return Context.modules.get(_id.text);
			}
		}

		var err = new PiccodeException(file, line, column, "No function or identifier " + _id.text + " found in module " + id.text);
		err.frame = frame;
		throw err;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("%s.%s", lhs, rhs);
	}
}
