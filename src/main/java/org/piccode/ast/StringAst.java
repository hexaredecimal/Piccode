package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class StringAst extends Ast {
	public String text; 

	public StringAst(String text) {
		this.text = text.substring(1, text.length() - 1);
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		return new PiccodeString(unescapeString(text));
	}
	
	public String unescapeString(String str) {
		StringBuilder sb = new StringBuilder();
		boolean isEscaping = false;

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (isEscaping) {
				switch (c) {
					case 'n':
						sb.append('\n');
						break;
					case 't':
						sb.append('\t');
						break;
					case 'b':
						sb.append('\b');
						break;
					case 'r':
						sb.append('\r');
						break;
					case 'f':
						sb.append('\f');
						break;
					case '\'':
						sb.append('\'');
						break;
					case '\"':
						sb.append('\"');
						break;
					case '\\':
						sb.append('\\');
						break;
					default:
						sb.append(c);
						break; // treat unknown escape as literal
				}
				isEscaping = false;
			} else if (c == '\\') {
				isEscaping = true;
			} else {
				sb.append(c);
			}
		}

		// In case string ends with single backslash
		if (isEscaping) {
			sb.append('\\');
		}

		return sb.toString();
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("\"%s\"", text);
	}
}
