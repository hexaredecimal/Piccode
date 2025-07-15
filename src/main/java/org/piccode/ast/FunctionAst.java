package org.piccode.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class FunctionAst extends Ast {

	public String name;
	public List<Ast> arg;
	public Ast body;
	public List<ClauseAst> clauses = new ArrayList<>();
	public List<String> annotations = new ArrayList<>();

	public FunctionAst(String name, List<Ast> arg, Ast body) {
		this.name = name;
		this.arg = arg;
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
						.append(name)
						.append(" :: (");
		if (arg != null) {
			sb.append(formatArgs());
		}
		sb.append(") = ...");
		return sb.toString();
	}

	private String formatArgs() {
		var sb = new StringBuilder();
		var size = arg.size();
		for (int i = 0; i < size; i++) {
			var top_arg = arg.get(i);
			sb.append(top_arg);
			if (i < size - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		var ctx = frame == null
						? Context.top
						: Context.getContextAt(frame);

		Map<String, PiccodeValue> newArgs = new HashMap<>();
		var cl = new PiccodeClosure(arg, newArgs, 0, body);
		cl.creator = this;
		cl.frame = frame;
		cl.callSite = new Ast.Location(line, column);
		cl.callSiteFile = file;
		cl.file = file;
		cl.column = column;
		cl.line = line;
		cl.annotations = annotations;
		var value = ctx.getValue(name);
		if (value == null) {
			if (!clauses.isEmpty()) {
				for (var clause : clauses) {
					cl.clauses.add(clause);
				}
				clauses.clear();
			}
			ctx.putLocal(name, cl);
			return cl;
		} else if (!(value instanceof PiccodeClosure)) {
			throw new PiccodeException(file, line, column, "Symbol `" + name + "` already exists. ");
		}
		var closure = (PiccodeClosure) value;
		if (closure.params.size() != arg.size()) {
			var exprected = closure.params.size();
			throw new PiccodeException(file, line, column, "Clause declaration for `" + name + "` has too many parameteres. Only " + exprected + " parameters expected");
		}
		if (!clauses.isEmpty()) {
			for (var clause : clauses) {
				cl.clauses.add(clause);
			}
			clauses.clear();
		}
		if (closure.annotations.isEmpty()) {
			closure.annotations = annotations;
		} else {
			for (var anot: annotations) {
				if (!closure.annotations.contains(anot)) {
					closure.annotations.add(anot);
				}
			}
		}
		closure.clauses.add(new ClauseAst(arg, body));
		return closure;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return switch (target) {
			case JS ->
				codeGenJSFunction(target);
			default ->
				"todo";
		};
	}

	private String codeGenJSFunction(TargetEnvironment env) {
		return "";
	}

}
