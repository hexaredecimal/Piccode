package org.piccode.ast;

import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class NumberAst implements  Ast {
	public String text; 

	public NumberAst(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public PiccodeValue execute() {
		return new PiccodeNumber(text);
	}

}
