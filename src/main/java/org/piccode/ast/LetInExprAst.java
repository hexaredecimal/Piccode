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
		return evaluate(frame, () -> {
			for (var decl : vars) {
				decl.execute(frame);
			}
			return evaluate(frame, () -> expr.execute(frame));
		});
	}

	private PiccodeValue evaluate(Integer frame, Supplier<PiccodeValue> fx) {
		var ctx = frame == null
						? Context.top
						: Context.getContextAt(frame);
		ctx.pushScope();
		try {
			return fx.get();
		} catch (PiccodeReturnException | PiccodeException e) {
			ctx.dropScope();
			throw e;
		}
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
