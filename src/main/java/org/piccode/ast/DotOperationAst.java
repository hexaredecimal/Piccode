package org.piccode.ast;

import com.github.tomaslanger.chalk.Chalk;
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
public class DotOperationAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public DotOperationAst(Ast lhs, Ast rhs) {
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
			var err = new PiccodeException(file, line, column, "Cannot access the module `" + id.text + "` using dot. Please use `::` instead");
			err.frame = frame;
			throw err;
		}

		var left = lhs.execute(frame);

		if (left instanceof PiccodeArray arr && rhs instanceof IdentifierAst id && id.text.equals("len")) {
			return new PiccodeNumber("" + arr.array().length);
		}

		if (left instanceof PiccodeString str && rhs instanceof IdentifierAst id && id.text.equals("len")) {
			return new PiccodeNumber("" + str.toString().length());
		} else if (left instanceof PiccodeArray arr) {
			return processArrayIndexing(arr.array(), rhs.execute(frame), frame);
		} else if (left instanceof PiccodeTuple tupl) {
			return processArrayIndexing(tupl.array(), rhs.execute(frame), frame);
		}

		if (left instanceof PiccodeModule mod) {
			return process(new IdentifierAst(mod.name), mod, frame);
		}

		if (!(left instanceof PiccodeObject)) {
			var err = new PiccodeException(file, line, column, "Invalid expression on the side of `.` : " + lhs + " has value " + left + " which is not an object");
			err.frame = frame;
			err.addNote(new PiccodeSimpleNote("Pehaphs consider adding a check to verify if " + lhs + " is indeed an object."));
			throw err;
		}

		var obj = (PiccodeObject) left;

		String key = null;
		if (rhs instanceof IdentifierAst id
						&& id.text.equals("await")
						&& obj.obj.containsKey("uuid")) {
			var uuid = obj.obj.get("uuid").raw().toString();

			var future = Context.getFuture(uuid);
			if (future == null) {
				var err = new PiccodeException(file, line, column, "Invalid reference to a future: " + Chalk.on(uuid).red());
				err.frame = frame;
				throw err;
			}
			
			try {
				var result = future.get();
				return result;
			} catch (InterruptedException | ExecutionException ex) {
				var cause = ex.getCause();
				if (cause instanceof PiccodeException pex) {
					pex.message += ". Thread id: " + frame;
					throw pex;
				}
				var err = new PiccodeException(file, line, column, "Internal error: " + ex.getMessage());
				err.frame = frame;
				throw err;
			}
		}
		if (rhs instanceof IdentifierAst id) {
			key = id.text;
		} else {
			key = rhs.execute(frame).raw().toString();
		}

		var value = obj.getValue(key);
		if (value == null) {
			var err = new PiccodeException(file, line, column, "Field `" + key + "` is not part of " + obj.raw());
			err.frame = frame;
			throw err;
		}

		return value;
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

	private PiccodeValue processArrayIndexing(PiccodeValue[] arr, PiccodeValue execute, Integer frame) {
		if (!(execute instanceof PiccodeNumber)) {
			var err = new PiccodeException(file, line, column, "Attempt to index array value with non numeric index: " + rhs + " which evaluates to " + execute + " is used as an index");
			err.frame = frame;
			throw err;
		}

		int index = (int) ((double) execute.raw());
		if (index < 0 || index >= arr.length) {
			var err = new PiccodeException(file, line, column, "Array index out of bounds: " + lhs + " evaluates to an array with size" + arr.length + " which is indexed with " + execute);
			err.frame = frame;
			throw err;
		}

		return arr[index];
	}

	private PiccodeValue processTupleIndexing(PiccodeValue[] arr, PiccodeValue execute, Integer frame) {
		if (!(execute instanceof PiccodeNumber)) {
			var err = new PiccodeException(file, line, column, "Attempt to index a tuple value with non numeric index: " + rhs + " which evaluates to " + execute + " is used as an index");
			err.frame = frame;
			throw err;
		}

		int index = (int) ((double) execute.raw());
		if (index < 0 || index >= arr.length) {
			var err = new PiccodeException(file, line, column, "Array index out of bounds: " + lhs + " evaluates to a tuple with size" + arr.length + " which is indexed with " + execute);
			err.frame = frame;
			throw err;
		}

		return arr[index];
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("%s.%s", lhs, rhs);
	}
}
