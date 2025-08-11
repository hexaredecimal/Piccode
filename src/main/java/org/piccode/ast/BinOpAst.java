package org.piccode.ast;

import com.github.tomaslanger.chalk.Chalk;
import org.piccode.piccodescript.Piccode;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class BinOpAst extends Ast {

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
	public PiccodeValue execute(Integer frame) {
		var left = lhs.execute(frame);
		var right = rhs.execute(frame);

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


		if (left instanceof PiccodeBoolean lf && right instanceof PiccodeBoolean rh) {
			if (op.equals("&&")){
				var _left = (boolean) lf.raw();
				var _right = (boolean) rh.raw();
				return new PiccodeBoolean(String.valueOf(_left && _right));
			}

			if (op.equals("||")) {
				var _left = (boolean) lf.raw();
				var _right = (boolean) rh.raw();
				return new PiccodeBoolean(String.valueOf(_left || _right));
			}
		}

		var err =  new PiccodeException(file, line, column,"Operator `" + Chalk.on(op).blue() + "`  cannot be used with types " 
			+ Chalk.on(left.type()).red()
			+ " and " + Chalk.on(right.type()).red());

		err.frame = frame;
		throw err;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("%s %s %s", lhs, op, rhs);
	}
}
