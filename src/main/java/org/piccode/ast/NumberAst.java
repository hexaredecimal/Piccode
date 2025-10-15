package org.piccode.ast;

import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.NumberType;
import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public class NumberAst extends  Ast implements TypeCheckable {
	public String text; 

	public NumberAst(String text) {
		this.text = parse(text);
	}

	@Override
	public String toString() {
		return text;
	}

	private String parse(String input) {
		String cleaned = input.replace("_", "");

		// Hexadecimal: starts with 0x or 0X
		if (cleaned.matches("(?i)^0x[0-9a-f]+$")) {
			return "" + Integer.parseUnsignedInt(cleaned.substring(2), 16);
		}

		// Binary: starts with 0b or 0B
		if (cleaned.matches("(?i)^0b[01]+$")) {
			return "" + Integer.parseUnsignedInt(cleaned.substring(2), 2);
		}

		// Octal: starts with 0o or 0O
		if (cleaned.matches("(?i)^0o[0-7]+$")) {
			return "" + Integer.parseUnsignedInt(cleaned.substring(2), 8);
		}

		// Decimal or scientific notation
		if (cleaned.matches("^\\d*\\.?\\d+([eE][+-]?\\d+)?$") || cleaned.matches("^\\d+[eE][+-]?\\d+$")) {
			return "" + Double.parseDouble(cleaned);
		}

		// Pure integer
		if (cleaned.matches("^\\d+$")) {
			return "" + Long.parseLong(cleaned);
		}

		return cleaned;
	}

	@Override
	public Type getType(Context ctx) {
		return new NumberType(this);
	}
}
