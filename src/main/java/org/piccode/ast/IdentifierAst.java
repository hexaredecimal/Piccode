package org.piccode.ast;

import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public class IdentifierAst extends Ast implements TypeCheckable {

	public String text;

	public IdentifierAst(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public Type getType(Context ctx) {
		var symbolTable = ctx.getLocalTable();
		var functionTable = ctx.getFunctionTable();

		var type = symbolTable.getValueForSymbol(text);
		if (type != null) {
			return type;
		}

		type = functionTable.getValueForSymbol(text);
		if (type != null) {
			return type;
		}

		throw new PiccodeException(file, line, column, "Identifier `" + text + "` is not defined");
	}
}
