package org.piccode.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			sb.append(arg);
		}
		sb.append(") = ");
		sb.append(body);
		return sb.toString();
	}

	@Override
	public PiccodeValue execute() {
		Map<String, PiccodeValue> newArgs = new HashMap<>();
		var cl = new PiccodeClosure(arg, newArgs, 0, body);
		cl.callSite = new Ast.Location(line, column);
		cl.callSiteFile = file;
		cl.file = file;
		cl.column = column;
		cl.line = line;
		Context.addGlobal(name, cl);
		return cl;
	}

}
