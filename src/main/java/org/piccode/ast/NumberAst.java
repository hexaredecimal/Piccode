package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class NumberAst extends  Ast {
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
	public PiccodeValue execute() {
		return new PiccodeNumber(text);
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return text;
	}

}
