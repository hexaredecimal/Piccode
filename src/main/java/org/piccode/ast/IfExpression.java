package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import static org.piccode.piccodescript.TargetEnvironment.JS;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class IfExpression extends Ast{
	public Ast cond, then, elze;

	public IfExpression(Ast cond, Ast then, Ast elze) {
		this.cond = cond;
		this.then = then;
		this.elze = elze;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("if ").append(cond).append(" {\n");
		sb.append(then.toString().indent(4));
		sb.append(" else {\n");
		sb.append(elze.toString().indent(4));
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PiccodeValue execute() {
		var _cond = cond.execute().raw().toString();
		if (_cond.equals("true")) {
			return then.execute();
		} 

		return elze.execute();
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return switch (target) {
			case JS ->
				codeGenJsIfExpr(target);
			default ->
				"todo";
		};
	}
	
	private String codeGenJsIfExpr(TargetEnvironment env) {
		var c = cond.codeGen(env);
		var t = then.codeGen(env);
		var e = elze.codeGen(env);
		return String.format("%s ? %s : %s", c, t, e);
	}
}
