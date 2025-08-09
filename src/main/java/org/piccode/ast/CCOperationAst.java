package org.piccode.ast;

import com.github.tomaslanger.chalk.Chalk;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeModule;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeReturnException;
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
		return Ast.safeExecute(frame, this, (expr) -> {
			if (lhs instanceof CCOperationAst op) {
				var mod = (PiccodeModule) op.execute(frame);
				if (!(rhs instanceof CallAst) && !(rhs instanceof IdentifierAst)) {
					throw new PiccodeException(file, line, column, "No node " + rhs + " found in module " + Chalk.on(mod.name).green());
				}

				var id = new IdentifierAst(mod.name);
				id.file = file;
				id.line = line;
				id.column = column;
				return process(id, mod, frame);
			}

			if (lhs instanceof IdentifierAst id && Context.top.getValue(id.text) != null) {
				var mod = Context.top.getValue(id.text);
				if (!(rhs instanceof CallAst) && !(rhs instanceof IdentifierAst)) {
					throw new PiccodeException(file, line, column, "No node " + rhs + " found in module " + Chalk.on(id.text).green());
				}
				return process(id, (PiccodeModule) mod, frame);
			}

			var err = new PiccodeException(file, line, column, "Invalid use of `::`. Expected a module on the lhs, but found " + Chalk.on(lhs.toString()).red());
			err.frame = frame;

			if (lhs instanceof IdentifierAst id) {
				var nm = Context.top.getSimilarName(id.text);
				if (nm != null && !nm.isEmpty()) {
					var note = new PiccodeSimpleNote("Did you mean `" + Chalk.on(nm).green() + "` instead of `" + Chalk.on(id.text).red() + "` ?");
					err.addNote(note);
				}
			}
			throw err;
		});
	}

	private PiccodeValue process(IdentifierAst id, PiccodeModule mod, Integer frame) {
		var ctx = frame == null
						? Context.top
						: Context.getContextAt(frame);

		if (rhs instanceof IdentifierAst _id) {
			for (var node : mod.nodes) {
				if (node instanceof VarDecl vd && vd.name.equals(_id.text)) {
					var result = node.execute(frame);
					ctx.deleteLocal(vd.name);
					return result;
				}
				if (node instanceof FunctionAst func && func.name.equals(_id.text)) {
					node.execute(frame);
					var result = ctx.getValue(_id.text);
					ctx.deleteLocal(func.name);
					if (result == null) {
						var err = new PiccodeException(func.file, func.line, func.column, "Function `" + Chalk.on(_id.text).red() + "` is not defined");
						err.frame = frame;
						var nm = ctx.getSimilarName(_id.text);
						if (nm != null && !nm.isEmpty()) {
							var note = new PiccodeException(func.file, func.line, func.column, "Maybe you meant `" + Chalk.on(nm).green() + "`");
							err.addNote(note);
						}
						throw err;
					}
					return result;
				}
				if (node instanceof ModuleAst _mod && _mod.name.equals(_id.text)) {
					var result = node.execute(frame);
					ctx.deleteLocal(_mod.name);
					return result;
				}
			}

			var err = new PiccodeException(file, line, column, "No function or identifier " + Chalk.on(_id.text).red() + " found in module " + Chalk.on(id.text).green());
			err.frame = frame;
			throw err;
		}

		var call = (CallAst) rhs;

		if (!(call.expr instanceof IdentifierAst)) {
			var err = new PiccodeException(file, line, column, "Invalid function reference in module access module " + Chalk.on(id.text).red() + ": " + call.expr);
			err.frame = frame;
			throw err;
		}

		var _id = (IdentifierAst) call.expr;
		for (var node : mod.nodes) {
			if (node instanceof VarDecl vd && vd.name.equals(_id.text)) {
				var result = node.execute(frame);
				ctx.deleteLocal(vd.name);
				return result;
			}
			if (node instanceof FunctionAst func && func.name.equals(_id.text)) {
				PiccodeValue result = null;
				if (func.rtObject == null) {
					result = Ast.safeExecute(frame, func, (expr) -> {
						func.setRtObject((PiccodeClosure) node.execute(frame));
						return call.execute(frame);
					});
				} else {
					result = Ast.safeExecute(frame, func, (expr) -> {
						ctx.putLocal(func.name, func.rtObject);
						return call.execute(frame);
					});
				}
				ctx.deleteLocal(func.name);
				return result;
			}
			if (node instanceof ModuleAst _mod && _mod.name.equals(_id.text)) {
				node.execute(frame);
				var result = ctx.getValue(_id.text);
				ctx.deleteLocal(_id.text);
				return result;
			}
		}

		var err = new PiccodeException(file, line, column, "No function or identifier " + Chalk.on(_id.text).red() + " found in module " + Chalk.on(id.text).green());
		err.frame = frame;
		throw err;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("%s.%s", lhs, rhs);
	}

}
