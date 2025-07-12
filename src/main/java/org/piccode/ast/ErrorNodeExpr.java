package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ErrorNodeExpr extends Ast {
	public String message;

	public ErrorNodeExpr(String message) {
		this.message = message;
	}
	
	@Override
	public PiccodeValue execute(Integer frame) {
		throw new PiccodeException(file, line, column, message);
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public String toString() {
		return "ErrorExpr";
	}
}
