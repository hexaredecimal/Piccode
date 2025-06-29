package org.piccode.ast;

import java.util.function.Consumer;
import org.piccode.backend.Compiler;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeException;
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
		StringBuilder finalString = new StringBuilder();
		parseInterpolatedString(text, expr -> {
			var result = Compiler.program(file, expr).execute(frame).toString();
			finalString.append(result);
		}, finalString);
		
		var str = unescapeString(finalString.toString());
		return new PiccodeString(str);
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
	public void parseInterpolatedString(String input, Consumer<String> expressionHandler, StringBuilder outputBuilder) {
		int length = input.length();
		int i = 0;

		while (i < length) {
			char c = input.charAt(i);
			if (c == '{') {
				if (i + 1 < length && input.charAt(i + 1) == '{') {
					outputBuilder.append('{');
					i += 2;
					continue;
				}

				
				int startExpr = i + 1;
				int braceDepth = 1;
				i++; 

				while (i < length && braceDepth > 0) {
					if (input.charAt(i) == '{') {
						braceDepth++;
					} else if (input.charAt(i) == '}') {
						braceDepth--;
						if (braceDepth == 0) {
							break;
						}
					}
					i++;
				}

				if (braceDepth == 0) {
					String expr = input.substring(startExpr, i).trim();
					expressionHandler.accept(expr); 
					i++;
				} else {
					throw new PiccodeException(file, line, column, "Unmatched '{' in input string.");
				}
			} 
			else if (c == '}') {
				if (i + 1 < length && input.charAt(i + 1) == '}') {
					outputBuilder.append('}');
					i += 2;
					continue;
				}
			} else {
				outputBuilder.append(c);
				i++;
			}
		}
	}
	
}
