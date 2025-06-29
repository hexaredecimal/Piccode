package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeReturnException;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ReturnAst extends Ast {
	public Ast expr;

	public ReturnAst(Ast expr) {
		this.expr = expr;
	}

	

	@Override
	public PiccodeValue execute(Integer frame) {
		var result = expr.execute(frame);
		throw new PiccodeReturnException(result);
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return toString();
	}

	@Override
	public String toString() {
		return "return " + expr;
	}

}
