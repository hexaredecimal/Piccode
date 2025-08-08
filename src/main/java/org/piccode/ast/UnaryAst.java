package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeReference;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class UnaryAst extends Ast{
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
	public PiccodeValue execute(Integer frame) {
		var result = expr.execute(frame);

		if (op.equals("-")) {
			if (!(result instanceof PiccodeNumber)) {
				var err = new PiccodeException(file, line, column,"Cannot use `-` with a value that is not a number. expression: " + expr + " results to value " + result);
				err.frame = frame;
				throw err;
			}
			return new PiccodeNumber("-" + result.toString());
		}

		if (op.equals("~")) {
			if (!(result instanceof PiccodeNumber)) {
				var err = new PiccodeException(file, line, column,"Cannot use `~` with a value that is not a number. expression: " + expr + " results to value " + result);
				err.frame = frame;
				throw err;
			}
			var num = (int) (double) result.raw();
			return new PiccodeNumber("" + (~num));
		}

		if (op.equals("!")) {
			if (!(result instanceof PiccodeBoolean)) {
				var err = new PiccodeException(file, line, column,"Cannot use `~` with a value that is not a boolean. expression: " + expr + " results to value " + result);
				err.frame = frame;
				throw err;
			}
			var bool = (boolean) result.raw();
			return new PiccodeBoolean(String.valueOf(!bool));
		}

		if (op.equals("&")) {
			return new PiccodeReference(result);
		}

		if (op.equals("*")) {
			if (!(result instanceof PiccodeReference)) {
				var err = new PiccodeException(file, line, column,"Cannot dereference a value that is not a Referebce. expression: " + expr + " results to value " + result);
				err.frame = frame;
				throw err;
			}
			var value = ((PiccodeReference)result).deref();
			if (!(value instanceof PiccodeValue)) {
				var err = new PiccodeException(file, line, column,"Cannot dereference a native reference. expression: " + expr + " results to value " + value);
				err.frame = frame;
				throw err;
			}

			return (PiccodeValue) value;
		}


		
		var err = new PiccodeException(file, line, column,"Not supported yet: " + op);
		err.frame = frame;
		throw err;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("%s %s", op, expr.codeGen(target));
	}

	
}
