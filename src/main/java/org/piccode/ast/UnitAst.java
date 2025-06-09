package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
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

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

}
