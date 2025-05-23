package org.piccode.ast;

import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class UnaryAst implements Ast{
	public String op;
	public Ast expr;

	public UnaryAst(String op, Ast expr) {
		this.op = op;
		this.expr = expr;
	}

	@Override
	public String toString() {
		return String.format("%s %s", op, expr);
	}

	@Override
	public PiccodeValue execute() {
		var result = expr.execute();

		if (op.equals("-")) {
			if (!(result instanceof PiccodeNumber)) {
				throw new PiccodeException("Cannot use `-` with a value that is not a number. expression: " + expr + " results to value " + result);
			}
			return new PiccodeNumber("-" + result.toString());
		}

		if (op.equals("~")) {
			if (!(result instanceof PiccodeNumber)) {
				throw new PiccodeException("Cannot use `~` with a value that is not a number. expression: " + expr + " results to value " + result);
			}
			var num = (int) (double) result.raw();
			return new PiccodeNumber("" + (~num));
		}

		if (op.equals("!")) {
			if (!(result instanceof PiccodeBoolean)) {
				throw new PiccodeException("Cannot use `~` with a value that is not a boolean. expression: " + expr + " results to value " + result);
			}
			var bool = (boolean) result.raw();
			return new PiccodeBoolean(String.valueOf(!bool));
		}
		
		throw new PiccodeException("Not supported yet: " + op);
	}

	
}
