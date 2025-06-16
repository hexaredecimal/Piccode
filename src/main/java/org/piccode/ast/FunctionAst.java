package org.piccode.ast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class FunctionAst extends Ast {

	public String name;
	public List<Arg> arg;
	public Ast body;

	public FunctionAst(String name, List<Arg> arg, Ast body) {
		this.name = name;
		this.arg = arg;
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
						.append("function ")
						.append(name)
						.append("(");
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
	public PiccodeValue execute() {
		Map<String, PiccodeValue> newArgs = new HashMap<>();
		var cl = new PiccodeClosure(arg, newArgs, 0, body);
		cl.creator = this;
		cl.callSite = new Ast.Location(line, column);
		cl.callSiteFile = file;
		cl.file = file;
		cl.column = column;
		cl.line = line;
		Context.addGlobal(name, cl);
		return cl;
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
		var sb = new StringBuilder()
						.append("function ");

		if (arg.isEmpty()) {
			sb
							.append(name)
							.append("() { \n")
							.append("return ")
							.append(body.codeGen(env))
							.append(";\n}");
			return sb.toString();
		}

		sb
						.append(name);

		for (int i = 0; i < arg.size(); i++) {
			var ar = arg.get(i);
			if (i == 0) {
				sb
								.append("(")
								.append(ar.name)
								.append(") { \n return ");
				continue;
			}
			var fn = String.format("inner_%sl%dc%d", name, ar.line, ar.column);
			sb
							.append("function ")
							.append(fn)
							.append("(")
							.append(ar.name)
							.append(") { \n return ");
		}

		var done = "}".repeat(arg.size());
		sb
						.append(body.codeGen(env).indent(4))
						.append(done)
						.append("\n");
		return sb.toString();
	}

}
