package org.piccode.ast;

import java.util.ArrayList;
import java.util.List;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public class FunctionAst extends Ast implements TypeCheckable {

	public String name;
	public List<Ast> arg;
	public Ast body;
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
	public Type getType(Context ctx) {
		var localTable = ctx.getLocalTable();
		var functionTable = ctx.getFunctionTable();
		var functionType= functionTable.getValueForSymbol(name);
		
		var count = arg.size();
		for (int i = 0; i < count; ++i) {
			var topArg = (Arg) arg.get(i);
			var topType = functionType.args.get(i);
			localTable.putSymbol(topArg.name, topType);
		}


		var typedBody = (TypeCheckable)body;
		var returnedType = typedBody.getType(ctx);
		
		System.out.println("" + returnedType);
		localTable.clear();
		return null;
	}




}
