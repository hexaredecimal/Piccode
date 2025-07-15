package org.piccode.ast;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeReturnException;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class LetInExprAst extends Ast {

	public List<Ast> vars;
	public Ast expr;

	public LetInExprAst(List<Ast> vars, Ast expr) {
		this.vars = vars;
		this.expr = expr;
	}

	@Override
	public String toString() {
		var sb = new StringBuilder();
		sb.append("let\n");
		for (var decl : vars) {
			sb.append(decl.toString().indent(4));
		}
		sb.append("in\n");
		sb.append(expr.toString().indent(4));
		return sb.toString();
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		return Ast.safeExecute(frame, this, (func) -> {
			for (var decl : vars) {
				decl.execute(frame);
			}
			return Ast.safeExecute(frame, () -> expr.execute(frame));
		});
	}

	
	@Override
	public String codeGen(TargetEnvironment target) {
		return switch (target) {
			case JS ->
				codeGenJSDoExpr(target);
			default ->
				"todo";
		};
	}

	private String codeGenJSDoExpr(TargetEnvironment env) {
		return null;
	}
}
