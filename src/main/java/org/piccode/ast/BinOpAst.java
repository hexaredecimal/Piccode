package org.piccode.ast;

import org.piccode.piccode.Piccode;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class BinOpAst implements Ast {

	public Ast lhs;
	public String op;
	public Ast rhs;

	public BinOpAst(Ast lhs, String op, Ast rhs) {
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " " + op + " " + rhs;
	}

	@Override
	public PiccodeValue execute() {
		var left = lhs.execute();
		var right = rhs.execute();

		if (left instanceof PiccodeNumber lf && right instanceof PiccodeNumber rh) {
			double result = 0;
			if (op.equals("+")) {
				result = (double) lf.raw() + (double) rh.raw();
				return new PiccodeNumber(String.valueOf(result));
			}
			if (op.equals("-")) {
				result = (double) lf.raw() - (double) rh.raw();
				return new PiccodeNumber(String.valueOf(result));
			}
			if (op.equals("*")) {
				result = (double) lf.raw() * (double) rh.raw();
				return new PiccodeNumber(String.valueOf(result));
			}
			if (op.equals("/")) {
				result = (double) lf.raw() / (double) rh.raw();
				return new PiccodeNumber(String.valueOf(result));
			}
			if (op.equals("%")) {
				result = (double) lf.raw() % (double) rh.raw();
				return new PiccodeNumber(String.valueOf(result));
			}

			if (op.equals(">")) {
				return new PiccodeBoolean((double) lf.raw() > (double) rh.raw() ? "true" : "false");
			}

			if (op.equals("<")) {
				return new PiccodeBoolean((double) lf.raw() < (double) rh.raw() ? "true" : "false");
			}

			if (op.equals(">=")) {
				return new PiccodeBoolean((double) lf.raw() >= (double) rh.raw() ? "true" : "false");
			}

			if (op.equals("<=")) {
				return new PiccodeBoolean((double) lf.raw() <= (double) rh.raw() ? "true" : "false");
			}

			if (op.equals("<<")) {
				result = ((int) (double)lf.raw()) << ((int) (double)rh.raw());
				return new PiccodeNumber(String.valueOf(result));
			}
			if (op.equals(">>")) {
				result = ((int) (double)lf.raw()) >> ((int) (double)rh.raw());
				return new PiccodeNumber(String.valueOf(result));
			}

			if (op.equals("|")) {
				result = ((int) (double)lf.raw()) | ((int) (double)rh.raw());
				return new PiccodeNumber(String.valueOf(result));
			}

			if (op.equals("&")) {
				result = ((int) (double)lf.raw()) & ((int) (double)rh.raw());
				return new PiccodeNumber(String.valueOf(result));
			}
		}

		if (op.equals("+")) {
			var result = String.format("%s%s", left.raw(), right.raw());
			return new PiccodeString(result);
		}

		if (op.equals("==")) {
			return new PiccodeBoolean(left.equals(right) ? "true" : "false");
		}

		if (op.equals("!=")) {
			return new PiccodeBoolean(left.equals(right) ? "true" : "false");
		}

		throw new PiccodeException("Invalid operator:  " + op + " " + left.getClass() + " vs " + right.getClass());
	}
}
