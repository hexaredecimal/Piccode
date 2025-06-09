package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public abstract class Ast {
	public int line = 1, column = 1;
	public String file;
	public abstract PiccodeValue execute();
	public abstract String codeGen(TargetEnvironment target);

	public static class Location {
		public int line;
	  public int col;

		public Location(int line, int col) {
			this.line = line;
			this.col = col;
		}
	}

	public static Location getLocation(Ast node) {
		if (node instanceof IdentifierAst id) {
			return new Location(id.line, id.column);
		}

		if (node instanceof StringAst str) {
			return new Location(str.line, str.column);
		}
		
		if (node instanceof NumberAst num) {
			return new Location(num.line, num.column);
		}
		
		if (node instanceof ArrayAst arr) {
			return new Location(arr.line, arr.column);
		}
		
		if (node instanceof TupleAst tup) {
			return new Location(tup.line, tup.column);
		}
		
		if (node instanceof ObjectAst obj) {
			return new Location(obj.line, obj.column);
		}

		if (node instanceof BinOpAst binop) {
			return new Location(binop.line, binop.column);
		}

		if (node instanceof UnaryAst unary) {
			return new Location(unary.line, unary.column);
		}

		if (node instanceof IfExpression ie) {
			return getLocation(ie.cond);
		}

		if (node instanceof WhenAst when) {
			return getLocation(when.cond);
		}

		if (node instanceof WhenCase c) {
			return getLocation(c.match.getFirst());
		}

		if (node instanceof DoExprAst dot) {
			return new Location(dot.line, dot.column);
		}
		
		if (node instanceof DotOperationAst dot) {
			return new Location(dot.line, dot.column);
		}

		return null;
	}
}
