package org.piccode.rt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.piccode.ast.Arg;
import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeClosure implements PiccodeValue {

	List<Arg> params; // Full list of args (with name and default val)
	Map<String, PiccodeValue> appliedArgs; // Applied named args
	int positionalIndex; // How many positional args have been applied so far
	Ast body;

	public String file;
	public int line, column;
	public PiccodeClosure(List<Arg> params, Map<String, PiccodeValue> appliedArgs, int positionalIndex, Ast body) {
		this.params = params == null ? List.of() : params;
		this.appliedArgs = appliedArgs;
		this.positionalIndex = positionalIndex;
		this.body = body;
	}

	public PiccodeValue call(PiccodeValue arg) {
		if (positionalIndex >= params.size()) {
			throw new PiccodeException(file, line, column, "Too many arguments. Expected " + params.size() + " but got " + positionalIndex);
		}

		String paramName = params.get(positionalIndex).name;
		Map<String, PiccodeValue> newArgs = new HashMap<>(appliedArgs);
		newArgs.put(paramName, arg);

		return new PiccodeClosure(params, newArgs, positionalIndex + 1, body);
	}

	public PiccodeValue callNamed(String name, PiccodeValue arg) {
		boolean paramExists = params.stream().anyMatch(p -> p.name.equals(name));
		if (!paramExists) {
			throw new PiccodeException(file, line, column, "Function does not have a parameter named '" + name + "'");
		}

		if (positionalIndex >= params.size()) {
			throw new PiccodeException(file, line, column, "Too many arguments. Expected " + params.size() + " but got " + positionalIndex);
		}

		if (appliedArgs.containsKey(name)) {
			throw new PiccodeException(file, line, column, "Duplicate argument: " + name);
		}

		Map<String, PiccodeValue> newArgs = new HashMap<>(appliedArgs);
		newArgs.put(name, arg);

		return new PiccodeClosure(params, newArgs, positionalIndex + 1, body);
	}

	public PiccodeValue evaluateIfReady() {
		for (Arg param : params) {
			boolean isSet = appliedArgs.containsKey(param.name);
			boolean hasDefault = param.def_val != null;
			if (!isSet && !hasDefault) {
				return this; // Not ready yet — required arg still missing
			}
		}

		// All required args satisfied (either by user or by default), run
		Context.top.pushStack();
		for (Arg param : params) {
			PiccodeValue val = appliedArgs.getOrDefault(
							param.name,
							param.def_val != null ? param.def_val.execute() : null
			);
			Context.top.putLocal(param.name, val);
		}

		var result = body.execute();
		Context.top.dropStackFrame();
		return result;
	}

	@Override
	public Object raw() {
		return body;
	}

	@Override
	public String toString() {
		return "Rt.Fn/" + positionalIndex;
	}

}
