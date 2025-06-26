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
	public PiccodeValue execute() {

		if (lhs instanceof IdentifierAst id && Context.modules.containsKey(id.text)) {
			throw new PiccodeException(file, line, column, "Cannot access the module `" + id.text + "` using dot. Please use `::` instead");
		}

		var left = lhs.execute();

		if (left instanceof PiccodeArray arr && rhs instanceof IdentifierAst id && id.text.equals("len")) {
			return new PiccodeNumber("" + arr.array().length);
		}

		if (left instanceof PiccodeString str && rhs instanceof IdentifierAst id && id.text.equals("len")) {
			return new PiccodeNumber("" + str.toString().length());
		} else if (left instanceof PiccodeArray arr) {
			return processArrayIndexing(arr.array(), rhs.execute());
		} else if (left instanceof PiccodeTuple tupl) {
			return processArrayIndexing(tupl.array(), rhs.execute());
		}

		if (left instanceof PiccodeModule mod) {
			return process(new IdentifierAst(mod.name), mod);
		}

		if (!(left instanceof PiccodeObject)) {
			var err = new PiccodeException(file, line, column, "Invalid expression on the side of `.` : " + lhs + " has value " + left + " which is not an object");
			err.addNote(new PiccodeSimpleNote("Pehaphs consider adding a check to verify if " + lhs + " is indeed an object."));
			throw err;
		}

		var obj = (PiccodeObject) left;

		String key = null;
		if (rhs instanceof IdentifierAst id
						&& id.text.equals("await")
						&& obj.obj.containsKey("uuid")
						&& obj.obj.containsKey("future")) {
			var uuid = obj.obj.get("uuid").raw().toString();
			var future = Context.top.getFuture(uuid);
			Context.top.removeFuture(uuid);
			try {
				var result = future.get();
				return result;
			} catch (InterruptedException | ExecutionException ex) {
				throw new PiccodeException(file, line, column, "Internal error: " + ex.getMessage());
			}
		}
		if (rhs instanceof IdentifierAst id) {
			key = id.text;
		} else {
			key = rhs.execute().raw().toString();
		}

		var value = obj.getValue(key);
		if (value == null) {
			throw new PiccodeException(file, line, column, "Field `" + key + "` is not part of " + obj.raw());
		}

		return value;
	}

	private PiccodeValue process(IdentifierAst id, PiccodeModule mod) {
		if (rhs instanceof IdentifierAst _id) {
			for (var node : mod.nodes) {
				if (node instanceof VarDecl vd && vd.name.equals(_id.text)) {
					return node.execute();
				}
				if (node instanceof FunctionAst func && func.name.equals(_id.text)) {
					node.execute();
					var result = Context.top.getValue(_id.text);
					if (result == null) {
						var err = new PiccodeException(func.file, func.line, func.column, "Function `" + _id.text + "` is not defined");
						var nm = Context.top.getSimilarName(_id.text);
						if (nm != null && !nm.isEmpty()) {
							var note = new PiccodeException(func.file, func.line, func.column, "Maybe you meant `" + nm + "`");
							err.addNote(note);
						}
						throw err;
					}
					return result;
				}
				if (node instanceof ModuleAst _mod && _mod.name.equals(_id.text)) {
					node.execute();
					return Context.modules.get(_id.text);
				}
			}

			throw new PiccodeException(file, line, column, "No function or identifier " + _id.text + " found in module " + id.text);
		}

		var call = (CallAst) rhs;

		if (!(call.expr instanceof IdentifierAst)) {
			throw new PiccodeException(file, line, column, "Invalid function reference in module access module " + id.text + ": " + call.expr);
		}

		var _id = (IdentifierAst) call.expr;
		for (var node : mod.nodes) {
			if (node instanceof VarDecl vd && vd.name.equals(_id.text)) {
				return node.execute();
			}
			if (node instanceof FunctionAst func && func.name.equals(_id.text)) {
				node.execute();
				//return Context.top.getValue(_id.text);
				return call.execute();
			}
			if (node instanceof ModuleAst _mod && _mod.name.equals(_id.text)) {
				node.execute();
				return Context.modules.get(_id.text);
			}
		}

		throw new PiccodeException(file, line, column, "No function or identifier " + _id.text + " found in module " + id.text);
	}

	private PiccodeValue processArrayIndexing(PiccodeValue[] arr, PiccodeValue execute) {
		if (!(execute instanceof PiccodeNumber)) {
			throw new PiccodeException(file, line, column, "Attempt to index array value with non numeric index: " + rhs + " which evaluates to " + execute + " is used as an index");
		}

		int index = (int) ((double) execute.raw());
		if (index < 0 || index >= arr.length) {
			throw new PiccodeException(file, line, column, "Array index out of bounds: " + lhs + " evaluates to an array with size" + arr.length + " which is indexed with " + execute);
		}

		return arr[index];
	}

	private PiccodeValue processTupleIndexing(PiccodeValue[] arr, PiccodeValue execute) {
		if (!(execute instanceof PiccodeNumber)) {
			throw new PiccodeException(file, line, column, "Attempt to index a tuple value with non numeric index: " + rhs + " which evaluates to " + execute + " is used as an index");
		}

		int index = (int) ((double) execute.raw());
		if (index < 0 || index >= arr.length) {
			throw new PiccodeException(file, line, column, "Array index out of bounds: " + lhs + " evaluates to a tuple with size" + arr.length + " which is indexed with " + execute);
		}

		return arr[index];
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("%s.%s", lhs, rhs);
	}
}
