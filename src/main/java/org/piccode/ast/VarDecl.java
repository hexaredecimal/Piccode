package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class VarDecl extends Ast {
	public String name; 
	public Ast value;

	public VarDecl(String name, Ast value) {
		this.name = name;
		this.value = value;
	}


	@Override
	public String toString() {
		return "let " + name + " = " + value;
	}

	@Override
	public PiccodeValue execute() {
		var _value = value.execute();
		Context.top.putLocal(name, _value);
		return _value;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("let %s = %s;", name, value.codeGen(target));
	}
}
