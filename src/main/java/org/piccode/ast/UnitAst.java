package org.piccode.ast;

import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class UnitAst extends Ast {
	@Override
	public String toString() {
		return "()";
	}

	@Override
	public PiccodeValue execute() {
		return new PiccodeUnit();
	}

}
