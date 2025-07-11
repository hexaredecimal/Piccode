package org.piccode.ast;

import java.util.ArrayList;
import java.util.List;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeValue;

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
	public PiccodeValue execute(Integer frame) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public String toString() {
		return "Clause: " + args;
	}
	
}
