package org.piccode.ast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class ClauseAst extends Ast {
	public List<Ast> args;
	public Ast body;

	public ClauseAst(List<Ast> args, Ast body) {
		this.args = args;
		this.body = body;
	}

	public boolean isStable() {
		var arg_count= 0;
		for (var arg: args) {
			if (arg instanceof Arg) {
				arg_count++;
			}
		}
		return arg_count == args.size();
	}

	public List<Ast> getArgs() {
		var _args = new ArrayList<Ast>();
		for (var arg: args) {
			if (arg instanceof Arg _arg) {
				var id = new IdentifierAst(_arg.name);
				id.column = _arg.column;
				id.line = _arg.line;
				id.file = _arg.file;
				_args.add(id);
			} else {
				_args.add(arg);
			}
		}
		return _args;
	}

	@Override
	public String toString() {
		return "Clause: " + args;
	}
	
}
